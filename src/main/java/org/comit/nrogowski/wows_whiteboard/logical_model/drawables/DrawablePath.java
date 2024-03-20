package org.comit.nrogowski.wows_whiteboard.logical_model.drawables;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public final class DrawablePath extends Drawable {
	@Id
	private Integer id;
//	private DrawablePathPoint[] points;
	
//	public DrawablePath() {
//		
//	}
	
	public DrawablePath(Integer id) {
		this.id = id;
	}

//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//	
}
