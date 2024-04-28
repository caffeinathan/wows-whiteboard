package org.comit.nrogowski.wows_whiteboard.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.comit.nrogowski.wows_whiteboard.beans.DrawableIcon;
import org.springframework.jdbc.core.RowMapper;

public class DrawableIconMapper implements RowMapper<DrawableIcon> {

	@Override
	public DrawableIcon mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new DrawableIcon(
			rs.getInt("ID_DRAWABLE_ICON"),
			rs.getInt("STRAT_DIAGRAM_ID"),
			rs.getInt("LAYER"),
			rs.getInt("ICON_SPRITE"),
			rs.getInt("ICON_STYLE"),
			rs.getInt("ICON_ROTATION"),
			rs.getInt("POSITION_X"),
			rs.getInt("POSITION_Y")
		);
	}



}
