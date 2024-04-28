package org.comit.nrogowski.wows_whiteboard.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.comit.nrogowski.wows_whiteboard.beans.DrawablePath;
import org.springframework.jdbc.core.RowMapper;

public class DrawablePathMapper implements RowMapper<DrawablePath> {

	@Override
	public DrawablePath mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new DrawablePath(
			rs.getInt("ID_DRAWABLE_PATH"),
			rs.getInt("STYLE"),
			rs.getInt("PATH_TAIL_X"),
			rs.getInt("PATH_TAIL_Y"),
			rs.getInt("STRAT_DIAGRAM_ID"),
			rs.getInt("LAYER")
		);
	}



}
