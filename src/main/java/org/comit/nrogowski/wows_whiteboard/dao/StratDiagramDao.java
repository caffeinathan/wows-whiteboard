package org.comit.nrogowski.wows_whiteboard.dao;

import org.comit.nrogowski.wows_whiteboard.beans.StratDiagram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StratDiagramDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public StratDiagram getDiagram(int id) {
		return null;
	}
	
	public void updateDiagram(StratDiagram diagram) {
		
	}
}
