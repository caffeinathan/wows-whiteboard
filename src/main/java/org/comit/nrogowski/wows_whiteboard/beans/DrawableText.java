package org.comit.nrogowski.wows_whiteboard.beans;

public class DrawableText extends Drawable {
	int idDrawableText;
	int textStyle;
	int positionX;
	int positionY;
	String contents;
	int diagramLayer;
	int stratDiagramId;
	
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
		return idDrawableText;
	}
}
