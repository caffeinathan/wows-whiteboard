package org.comit.nrogowski.wows_whiteboard.beans;

public class DrawableText extends Drawable {
	int idDrawableText;
	int textStyle;
	int positionX;
	int positionY;
	String contents;
	int diagramLayer;
	int stratDiagramId;
	
	
	public DrawableText(int idDrawableText, int textStyle, int positionX, int positionY, String contents,
			int diagramLayer, int stratDiagramId) {
		super();
		this.idDrawableText = idDrawableText;
		this.textStyle = textStyle;
		this.positionX = positionX;
		this.positionY = positionY;
		this.contents = contents;
		this.diagramLayer = diagramLayer;
		this.stratDiagramId = stratDiagramId;
	}
	
	public int getTextStyle() {
		return textStyle;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public String getContents() {
		return contents;
	}

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
