package org.comit.nrogowski.wows_whiteboard.beans;

public class DrawablePath extends Drawable {
	int idDrawablePath;
	int pathStyle;
	int pathTailX;
	int pathTailY;
	int stratDiagramId;
	int diagramLayer;
	
	@Override
	protected int getLayer() {
		return diagramLayer;
	}
}
