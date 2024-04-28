package org.comit.nrogowski.wows_whiteboard.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.comit.nrogowski.wows_whiteboard.beans.DrawablePathPoint;
import org.springframework.jdbc.core.RowMapper;

public class DrawablePathPointMapper implements RowMapper<DrawablePathPoint> {

	@Override
	public DrawablePathPoint mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new DrawablePathPoint(
			rs.getInt("ID_DRAWABLE_PATH_POINT"),
			rs.getInt("POINT_PATH_INDEX"),
			rs.getInt("TAIL_RELATIVE_X"),
			rs.getInt("TAIL_RELATIVE_Y"),
			rs.getInt("DRAWABLE_PATH_ID"),
			rs.getInt("STRAT_DIAGRAM_ID")
		);
	}

}
