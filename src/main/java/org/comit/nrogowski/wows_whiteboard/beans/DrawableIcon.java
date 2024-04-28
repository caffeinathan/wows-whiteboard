package org.comit.nrogowski.wows_whiteboard.beans;


public class DrawableIcon extends Drawable {
	private int idDrawableIcon;
	private int stratDiagramId;
	private int diagramLayer;
	private int iconSprite;
	private int iconStyle;
	private int iconRotation;
	private int positionX;
	private int positionY;
	
	

	public DrawableIcon(int idDrawableIcon, int stratDiagramId, int diagramLayer, int iconSprite, int iconStyle,
			int iconRotation, int positionX, int positionY) {
		super();
		this.idDrawableIcon = idDrawableIcon;
		this.stratDiagramId = stratDiagramId;
		this.diagramLayer = diagramLayer;
		this.iconSprite = iconSprite;
		this.iconStyle = iconStyle;
		this.iconRotation = iconRotation;
		this.positionX = positionX;
		this.positionY = positionY;
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
		return idDrawableIcon;
	}

	public int getIconSprite() {
		return iconSprite;
	}

	public int getIconStyle() {
		return iconStyle;
	}

	public int getIconRotation() {
		return iconRotation;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}
	
	
}
