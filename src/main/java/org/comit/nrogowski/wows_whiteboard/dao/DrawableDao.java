package org.comit.nrogowski.wows_whiteboard.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.comit.nrogowski.wows_whiteboard.beans.Drawable;
import org.comit.nrogowski.wows_whiteboard.beans.DrawablePath;
import org.comit.nrogowski.wows_whiteboard.beans.DrawablePathPoint;
import org.comit.nrogowski.wows_whiteboard.dao.mapper.DrawableIconMapper;
import org.comit.nrogowski.wows_whiteboard.dao.mapper.DrawablePathMapper;
import org.comit.nrogowski.wows_whiteboard.dao.mapper.DrawablePathPointMapper;
import org.comit.nrogowski.wows_whiteboard.dao.mapper.DrawableTextMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * Writes a Drawable to the database, completing the process of Drawable creation.
	 * @param d A newly drawn drawable with ID of zero and a properly set stratDiagramId.
	 */
	public void createDrawable(Drawable d) {
		// TODO 
	}
	
	/**
	 * Deletes Drawables from the database
	 * @param drawableIds the IDs for the Drawables to delete 
	 * @param stratDiagramId the ID of the diagram these Drawables came from, for safety checking. 
	 */
	public void deleteDrawables(int[] drawableIds, int stratDiagramId) {
		
	}
	
	
	/**
	 * Retrieves the current extant set of Drawables in a diagram.
	 * <p>
	 * This may be called when a diagram is opened for viewing, when opened for editing,
	 * 	or when an ongoing edit session is notified of changes to fetch. 
	 * @param stratDiagramId the ID of the diagram of interest
	 * @return a list of all Drawables on the specified diagram, sorted by layer.
	 */
	public List<Drawable> selectDrawables(int stratDiagramId) {
//		List<Drawable> result = new ArrayList<>(selectAndMapDrawables(stratDiagramId).values());
//		Collections.sort(result);
//		return result;
		List<Drawable> results = new ArrayList<Drawable>();
		String statement;
		
		statement = "SELECT * FROM DRAWABLE_TEXT WHERE STRAT_DIAGRAM_ID = ?";
		results.addAll(jdbcTemplate.query(statement, new DrawableTextMapper(), stratDiagramId));
		
		statement = "SELECT * FROM DRAWABLE_ICON WHERE STRAT_DIAGRAM_ID = ?";
		results.addAll(jdbcTemplate.query(statement, new DrawableIconMapper(), stratDiagramId));
		
		statement = "SELECT * FROM DRAWABLE_PATH WHERE STRAT_DIAGRAM_ID = ?";
		results.addAll(populatePathsWithPoints(
				jdbcTemplate.query(statement, new DrawablePathMapper(), stratDiagramId)));
		
		return null; // TODO just doing this to compile app while doing other stuff
	}

	private Collection<Drawable> populatePathsWithPoints(List<DrawablePath> rawPathBeans) {
		return null; // TODO just doing this to compile app while doing other stuff, uncomment the loop below too
//		for (DrawablePath path: rawPathBeans) {
//			List<DrawablePathPoint> points = jdbcTemplate.query(
//					"SELECT * FROM DRAWABLE_PATH_POINT WHERE DRAWABLE_PATH_ID = ?",
//					new DrawablePathPointMapper(), path.getIdDrawablePath());
//			
//		}
	}
	
}
