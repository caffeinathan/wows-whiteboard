package org.comit.nrogowski.wows_whiteboard.beans;


public class DrawableIcon extends Drawable {
	int idDrawableIcon;
	int stratDiagramId;
	int diagramLayer;
	int iconSprite;
	int iconStyle;
	int iconRotation;
	int positionX;
	int positionY;
	
	@Override
	public int getLayer() {
		return diagramLayer;
	}

	@Override
	public int getStratDiagramId() {
		return stratDiagramId;
	}
	
}
