package org.comit.nrogowski.wows_whiteboard.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.comit.nrogowski.wows_whiteboard.beans.DrawableText;
import org.springframework.jdbc.core.RowMapper;

public class DrawableTextMapper implements RowMapper<DrawableText> {

	@Override
	public DrawableText mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new DrawableText(
			rs.getInt("ID_DRAWABLE_TEXT"),
			rs.getInt("STYLE"),
			rs.getInt("POSITION_X"),
			rs.getInt("POSITION_Y"),
			rs.getString("CONTENTS"),
			rs.getInt("LAYER"),
			rs.getInt("STRAT_DIAGRAM_ID")
		);
	}



}
