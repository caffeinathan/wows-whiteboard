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
	protected int getLayer() {
		return diagramLayer;
	}
	
}