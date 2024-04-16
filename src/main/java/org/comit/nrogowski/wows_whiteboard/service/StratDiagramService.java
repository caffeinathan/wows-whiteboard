package org.comit.nrogowski.wows_whiteboard.service;

import java.util.HashMap;
import java.util.Map;

import org.comit.nrogowski.wows_whiteboard.beans.StratDiagram;
import org.springframework.stereotype.Service;

@Service
public class StratDiagramService {

//	private static final Map<Integer, StratDiagram> diagrams = new HashMap<>();
	
	
	
	/**
	 * Sort drawables by layer index, then reassign indicies so duplicates and gaps are removed.
	 * 
	 * Rather than trust the front-end to preserve the integrity of the indicies we simply assume that any important
	 * meaning is preserved and then reassign them before updating entries in the database. In the worst case where
	 * the data is corrupted/nonsensical, the layering order will be randomized but the next read will have sanitized
	 * indicies for this new order.  
	 */
	private static void checkAndFixLayers(StratDiagram diagram) {
		
	}
}
