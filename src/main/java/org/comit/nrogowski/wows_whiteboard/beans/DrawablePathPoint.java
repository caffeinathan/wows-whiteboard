package org.comit.nrogowski.wows_whiteboard.beans;

public class DrawablePathPoint {
	private int idDrawablePathPoint;
	private int pointPathIndex;
	private int tailRelativeX;
	private int tailRelativeY;
	private int drawablePathId;
	private int stratDiagramId;
	
	public DrawablePathPoint(int idDrawablePathPoint, int pointPathIndex, int tailRelativeX, int tailRelativeY,
			int drawablePathId, int stratDiagramId) {
		super();
		this.idDrawablePathPoint = idDrawablePathPoint;
		this.pointPathIndex = pointPathIndex;
		this.tailRelativeX = tailRelativeX;
		this.tailRelativeY = tailRelativeY;
		this.drawablePathId = drawablePathId;
		this.stratDiagramId = stratDiagramId;
	}

	public int getIdDrawablePathPoint() {
		return idDrawablePathPoint;
	}

	public int getPointPathIndex() {
		return pointPathIndex;
	}

	public int getTailRelativeX() {
		return tailRelativeX;
	}

	public int getTailRelativeY() {
		return tailRelativeY;
	}

	public int getDrawablePathId() {
		return drawablePathId;
	}

	public int getStratDiagramId() {
		return stratDiagramId;
	}
}
