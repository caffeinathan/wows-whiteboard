package org.comit.nrogowski.wows_whiteboard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.comit.nrogowski.wows_whiteboard.Constants;
import org.comit.nrogowski.wows_whiteboard.beans.Drawable;
import org.comit.nrogowski.wows_whiteboard.beans.DrawableIcon;
import org.comit.nrogowski.wows_whiteboard.beans.DrawablePath;
import org.comit.nrogowski.wows_whiteboard.beans.DrawablePathPoint;
import org.comit.nrogowski.wows_whiteboard.beans.DrawableText;
import org.comit.nrogowski.wows_whiteboard.dao.mapper.DrawableIconMapper;
import org.comit.nrogowski.wows_whiteboard.dao.mapper.DrawablePathMapper;
import org.comit.nrogowski.wows_whiteboard.dao.mapper.DrawablePathPointMapper;
import org.comit.nrogowski.wows_whiteboard.dao.mapper.DrawableTextMapper;
import org.comit.nrogowski.wows_whiteboard.exceptions.DrawableDaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use cases for diagram modification:
 * 
 * <ol>
 *   <li>Client drew a Drawable
 *     <ul><li>Add it with layer = max value, not touching other layering values.
 *     This minimizes desync problems vs other editors</li></ul></li>
 *   <li>Client deleted some Drawables
 *     <ul><li>Remove them from DB and update layer values to prevent gaps</li></ul></li>
 *   </li>
 *   <li>Client shuffled Drawable ordering
 *     <ul><li>If their list has Drawables that aren't in DB,
 *     pull them out from processing and ignore them.
 *     Someone else probably deleted them immediately prior,
 *     so their order no longer matters.</li>
 *     <li>If their list is missing Drawables that are in DB,
 *     move them in tandem with the Drawable directly above them.
 *     Someone else probably created them immediately prior,
 *     so staying on top makes sense, and if for some reason that's not where it was,
 *     then we at least have a consistent behaviour.</li></ul></li>
 *   </li>
 *  </ol>
 *  Above all, keep operations as primitive as possible. We are not going to solve all client race conditions when
 *  diagrams have multiple simultaneous editors, and we are not trying to.
 */
@Repository
public class DrawableDao {
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * Writes a Drawable to the database, completing the process of Drawable creation.
	 * @param drawable A newly drawn drawable with ID of zero and a properly set stratDiagramId.
	 */
	public void createDrawable(Drawable drawable) {
		Drawable.assumingDrawableTypeCount(3);
		
		// count layers already in this diagram
		// TODO: this is a commonly needed value, we might want to optimize this operation
		final int newTopLayer;
		final int diagramId = drawable.getStratDiagramId();
		try {
			Statement drawableCountStatement = jdbcTemplate.getDataSource().getConnection().createStatement();
			ResultSet rs = drawableCountStatement.executeQuery(String.format(
					"SELECT COUNT(*) "
							+ "FROM ("
							+ "    SELECT STRAT_DIAGRAM_ID FROM DRAWABLE_TEXT WHERE STRAT_DIAGRAM_ID = %s "
							+ "    UNION ALL "
							+ "    SELECT STRAT_DIAGRAM_ID FROM DRAWABLE_ICONS WHERE STRAT_DIAGRAM_ID = %s "
							+ "    UNION ALL "
							+ "    SELECT STRAT_DIAGRAM_ID FROM DRAWABLE_PATHS WHERE STRAT_DIAGRAM_ID = %s"
							+ ")",
					diagramId, diagramId, diagramId));
			if (rs.next()) {
				newTopLayer = rs.getInt(1);
			} else {
				throw new DrawableDaoException(String.format(
						"implementation error counting number of drawables on diagram %d", diagramId));
			} 
		} catch (SQLException e) {
			throw new DrawableDaoException(String.format(
					"DB error counting number of drawables on diagram %d", diagramId), e);
		}

		if (drawable instanceof DrawableIcon) {
			int rows = createDrawableIcon((DrawableIcon) drawable, newTopLayer);
			if (rows == 0) {
				throw new DrawableDaoException("no rows added to database on icon save attempt");
			}
		} else if (drawable instanceof DrawableText) {
			int rows = createDrawableText((DrawableText) drawable, newTopLayer);
				if (rows == 0) {
					throw new DrawableDaoException("no rows added to database on text save attempt");
				}
		} else if (drawable instanceof DrawablePath) try {
			createDrawablePath((DrawablePath) drawable, newTopLayer);
		} catch (SQLException e) {
			throw new DrawableDaoException("unable to save drawable path to database", e);
		}
	}
	
	private int createDrawableIcon(DrawableIcon drawableIcon, int topLayer) {
		return jdbcTemplate.update(
				"INSERT INTO DRAWABLE_ICONS "
						+ "(STRAT_DIAGRAM_ID, LAYER, ICON_SPRITE, ICON_STYLE, ICON_ROTATION, POSITION_X, POSITION_Y) "
						+ "VALUES(?,?,?,?,?,?,?)",
				drawableIcon.getStratDiagramId(),
				topLayer,
				drawableIcon.getIconSprite(),
				drawableIcon.getIconStyle(),
				drawableIcon.getIconRotation(),
				drawableIcon.getPositionX(),
				drawableIcon.getPositionY()
		);
	}
	
	private int createDrawableText(DrawableText drawableText, int topLayer) {

		return jdbcTemplate.update(
				"INSERT INTO DRAWABLE_TEXT "
						+ "(STYLE, POSITION_X, POSITION_Y, CONTENTS, LAYER, STRAT_DIAGRAM_ID) "
						+ "VALUES(?,?,?,?,?,?,?)",
				drawableText.getTextStyle(),
				drawableText.getPositionX(),
				drawableText.getPositionY(),
				drawableText.getContents(),
				topLayer,
				drawableText.getStratDiagramId()
		);
	}
	
	@Transactional
	private void createDrawablePath(DrawablePath drawablePath, int topLayer) throws SQLException {
		
		final Connection connection = jdbcTemplate.getDataSource().getConnection();
		
		// add the path before its points, so we get an ID to assign on the points
		PreparedStatement preparedPathInsert = connection.prepareStatement(
				"INSERT INTO DRAWABLE_PATHS "
						+ "(STYLE, PATH_TAIL_X, PATH_TAIL_Y, STRAT_DIAGRAM_ID, LAYER) "
						+ "VALUES(?,?,?,?,?)",
				Statement.RETURN_GENERATED_KEYS
		);
		preparedPathInsert.setInt(1, drawablePath.getPathStyle());
		preparedPathInsert.setInt(2, drawablePath.getPathTailX());
		preparedPathInsert.setInt(3, drawablePath.getPathTailY());
		preparedPathInsert.setInt(4, drawablePath.getStratDiagramId());
		preparedPathInsert.setInt(5, topLayer);
		preparedPathInsert.execute();
		
		// now grab that ID from the DB
		final int idDrawablePath = preparedPathInsert.getGeneratedKeys().getInt(1);
		
		// add each set of x,y coords into the database as separate points
		PreparedStatement preparedPointsInsert = connection.prepareStatement(
				"INSERT INTO DRAWABLE_PATH_POINTS"
						+ "(POINT_PATH_INDEX, TAIL_RELATIVE_X, TAIL_RELATIVE_Y, DRAWABLE_PATH_ID, STRAT_DIAGRAM_ID)"
						+ "VALUES(?,?,?,?,?)");
		int[] x = drawablePath.getX();
		int[] y = drawablePath.getY();
		for (int i = 0; i < drawablePath.getNumPoints(); i++) {
			preparedPointsInsert.setInt(1, i);
			preparedPointsInsert.setInt(2, x[i]);
			preparedPointsInsert.setInt(3, y[i]);
			preparedPointsInsert.setInt(4, idDrawablePath);
			preparedPointsInsert.setInt(5, drawablePath.getStratDiagramId());
			preparedPointsInsert.addBatch();
		}
		int[] rowCounts = preparedPointsInsert.executeBatch();
		
	}
	
	/**
	 * Deletes Drawables from the database
	 * @param drawableIds the IDs for the Drawables to delete 
	 * @param stratDiagramId the ID of the diagram these Drawables came from, for safety checking. 
	 */
	public void deleteDrawables(int[] drawableIds, int stratDiagramId) {
		// TODO
	}
	
	
	/**
	 * Retrieves the current extant set of Drawables in a diagram.
	 * <p>
	 * This may be called when a diagram is opened for viewing, when opened for editing,
	 * 	or when an ongoing edit session is notified of changes to fetch. 
	 * @param stratDiagramId the ID of the diagram of interest
	 * @return a list of all Drawables on the specified diagram, sorted by layer.
	 */
	@Transactional
	public List<Drawable> selectDrawables(int stratDiagramId) {
		Drawable.assumingDrawableTypeCount(3);
		
		List<Drawable> results = new ArrayList<Drawable>();
		String statement;
		
		statement = "SELECT * FROM DRAWABLE_TEXT WHERE STRAT_DIAGRAM_ID = ?";
		results.addAll(jdbcTemplate.query(statement, new DrawableTextMapper(), stratDiagramId));
		
		statement = "SELECT * FROM DRAWABLE_ICON WHERE STRAT_DIAGRAM_ID = ?";
		results.addAll(jdbcTemplate.query(statement, new DrawableIconMapper(), stratDiagramId));
		
		statement = "SELECT * FROM DRAWABLE_PATH WHERE STRAT_DIAGRAM_ID = ?";
		results.addAll(populatePathsWithPoints(
				jdbcTemplate.query(statement, new DrawablePathMapper(), stratDiagramId)));
		
		return results;
	}

	private Collection<? extends Drawable> populatePathsWithPoints(List<DrawablePath> rawPathBeans) {
		// update the paths in-place so we can return the original collection
		for (DrawablePath path: rawPathBeans) {
			// TODO: rewrite so we aren't querying database in a loop
			List<DrawablePathPoint> points = jdbcTemplate.query(
					"SELECT * FROM DRAWABLE_PATH_POINT WHERE DRAWABLE_PATH_ID = ?",
					new DrawablePathPointMapper(), path.getId());

			int[] x = new int[points.size()];
			int[] y = new int[points.size()];
			for (DrawablePathPoint point: points) {
				final int i = point.getPointPathIndex(); 
				if (Constants.ENABLE_DEBUG) {
					// check we're not overwriting data
					// may be performance-problematic so only check in debug build
					if (x[i] != 0 || y[i] != 0) {
						throw new DrawableDaoException(String.format(
								"Path#%d has Point%d which duplicates POINT_PATH_INDEX %d",
								point.getDrawablePathId(), point.getIdDrawablePathPoint(), i));
					}
				}
				try {
					x[i] = point.getTailRelativeX() + path.getPathTailX();
					y[i] = point.getTailRelativeY() + path.getPathTailY();
				} catch (IndexOutOfBoundsException e) {
					throw new DrawableDaoException(String.format(
							"Path#%d has Point%d with out-of-bounds POINT_PATH_INDEX %d",
							point.getDrawablePathId(), point.getIdDrawablePathPoint(), i), e);
				}
			}
				
			path.setPoints(x, y);
		}
		
		return rawPathBeans;
	}
	
}
