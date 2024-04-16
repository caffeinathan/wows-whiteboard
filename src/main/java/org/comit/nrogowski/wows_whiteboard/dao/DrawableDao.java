package org.comit.nrogowski.wows_whiteboard.dao;

import java.util.ArrayList;
import java.util.List;

import org.comit.nrogowski.wows_whiteboard.WoWsWhiteboardApplication;
import org.comit.nrogowski.wows_whiteboard.beans.Drawable;
import org.comit.nrogowski.wows_whiteboard.exceptions.DrawableDaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DrawableDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<Drawable> selectDrawables(int stratDiagramId) {
		// TODO query DB
		return new ArrayList<Drawable>();
	}
	
	/**
	 * Ads new drawables, updates ONLY the layer value of extant drawables, and deletes absent drawables.
	 * 
	 * Provided Drawables MUST either be new, or not have any changed data except for their layer index.
	 */
	public void update(List<Drawable> drawables) throws DrawableDaoException {
		validateDrawablesForUpdate(drawables);
		
		// TODO: separate the three tasks and perform them
		
	}
	
	/**
	 * The validation can be hard on the database, so we may want to suppress the DB-heavy parts when not debugging
	 * <ol><li>Ensure all stratBookIds are identical</li>
	 * <li>Ensure all non-new Drawables are identical to the DB versions except for their layer values</li></ol>
	 */
	private void validateDrawablesForUpdate(List<Drawable> drawables) throws DrawableDaoException {
		if (drawables.size() == 0) return;
		
		final int stratBookId = drawables.get(0).getStratDiagramId(); 
		for (Drawable d: drawables) {
			if (d.getStratDiagramId() != stratBookId) {
				throw new DrawableDaoException("Drawables list contains drawables with different strat book IDs");
			}
		}
		if (WoWsWhiteboardApplication.ENABLE_DEBUG) {
			// check that the extant Drawables are identical to DB versions notwithstanding layer index
			
			// 1. read DB versions into a list
			// 2. put input versions into a map keyed on their IDs
			// 3. 
		}
	}
}
