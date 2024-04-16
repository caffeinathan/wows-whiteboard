package org.comit.nrogowski.wows_whiteboard.beans;

public class DrawablePath extends Drawable {
	int idDrawablePath;
	int pathStyle;
	int pathTailX;
	int pathTailY;
	int stratDiagramId;
	int diagramLayer;
	
	@Override
	public int getLayer() {
		return diagramLayer;
	}

	@Override
	public int getStratDiagramId() {
		return stratDiagramId;
	}

	@Override
	int getId() {
		return idDrawablePath;
	}
}
