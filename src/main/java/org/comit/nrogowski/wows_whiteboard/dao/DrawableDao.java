package org.comit.nrogowski.wows_whiteboard.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.comit.nrogowski.wows_whiteboard.WoWsWhiteboardApplication;
import org.comit.nrogowski.wows_whiteboard.beans.Drawable;
import org.comit.nrogowski.wows_whiteboard.exceptions.DrawableDaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DrawableDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * Get the current 
	 * @param stratDiagramId
	 * @return
	 */
	public List<Drawable> selectDrawables(int stratDiagramId) {
		List<Drawable> result = new ArrayList<>(selectAndMapDrawables(stratDiagramId).values());
		Collections.sort(result);
		return result;
	}
	
	/**
	 * @param stratDiagramId the Diagram for which to read Drawables
	 * @return a map containing the Drawables keyed by their database ID
	 */
	private Map<Integer, Drawable> selectAndMapDrawables(int stratDiagramId) {
		// TODO query DB here (here should be the only select statement in the class)
		return new HashMap<Integer, Drawable>();
	}
	
	/**
	 * Updates the database records underlying a diagram's Drawable contents.
	 * <ul>
	 * <li>Adds new Drawables for any in the input which did not previously exist in the database,</li>
	 * <li>Updates ONLY the layer value of Drawables in the input which already exist in the database,</li>
	 * <li>Deletes Drawables in the database where they are absent in the input.</li>
	 * </ul>
	 * Conditions:
	 * <ol>
	 * <li>Provided Drawables should either be new, or not have any changed data except for their layer index.</li>
	 * <li>Provided Drawables which do not have a matching database record should be new and have ID of zero.</li>
	 * </ol>
	 * @param completeDrawablesInput A comprehensive list of the drawables in a particular diagram, after some number of
	 * user actions. Any drawables which were part of the diagram this list comes from and are not present in the input
	 * will be deleted.
	 * @throws DrawableDaoException
	 */
	public void update(List<Drawable> completeDrawablesInput) throws DrawableDaoException {
		final int stratDiagramId = validateDrawablesForUpdate(completeDrawablesInput);
		
		// compare the current list against the last-stored version to separate the three categories.
		// 
		final Map<Integer, Drawable> extantDrawables = selectAndMapDrawables(stratDiagramId);
		final List<Drawable> deletedDrawables =
				new ArrayList<>(extantDrawables.values()); // start from all, remove non-deletions
		final List<Drawable> createdDrawables = new ArrayList<>(completeDrawablesInput.size());
		final List<Drawable> modifiedDrawables = new ArrayList<>(completeDrawablesInput.size());
		
		for (Iterator<Drawable> iter = completeDrawablesInput.iterator(); iter.hasNext();) {
			Drawable d = iter.next();
			// if d in extant, it's updated or not-changed and we shouldn't delete it.
			if (extantDrawables.containsKey(d.getId())) {
				modifiedDrawables.add(d);
				deletedDrawables.remove(d);
			}
			// if not, it's new (or someone else just deleted it)
			else {
				if (d.getId() == 0) {
					createdDrawables.add(d);
				}
			}
		}
		
		// TODO: write changes to DB here (here should be the only update/delete statements in the class)
		
	}
	
	
	/**
	 * The validation can be hard on the database, so we may want to suppress the DB-heavy parts when not debugging
	 * <ol><li>Ensure all stratBookIds are identical</li>
	 * <li>Ensure all non-new Drawables are identical to the DB versions except for their layer values</li></ol>
	 * @param drawables
	 * @return the stratDiagramId of the provided drawables, or null if there was no data provided.
	 * @throws DrawableDaoException
	 */
	private Integer validateDrawablesForUpdate(List<Drawable> drawables) throws DrawableDaoException {
		if (drawables.size() == 0) return null;
		
		final int stratBookId = drawables.get(0).getStratDiagramId(); 
		for (Drawable d: drawables) {
			if (d.getStratDiagramId() != stratBookId) {
				throw new DrawableDaoException("Drawables list contains drawables with different strat book IDs");
			}
		}
		if (true) { //(WoWsWhiteboardApplication.ENABLE_DEBUG) { // don't optimize until we think it's necessary
			// check that the extant Drawables are identical to DB versions notwithstanding layer index
			
			// 1. read DB versions into a list
			// 2. put input versions into a map keyed on their IDs
			// 3. For each DB version, if matching ID is in input, then require their non-meta data to match
		}
		
		return stratBookId;
	}
}
