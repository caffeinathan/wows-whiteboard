package org.comit.nrogowski.wows_whiteboard.beans;

import java.util.Arrays;

import org.comit.nrogowski.wows_whiteboard.exceptions.DrawableDaoException;

public class DrawablePath extends Drawable {
	private int idDrawablePath;
	private int pathStyle;
	private int pathTailX;
	private int pathTailY;
	private int stratDiagramId;
	private int diagramLayer;
	private int numPoints;
	private int[] x;
	private int[] y;
	
	public DrawablePath(int id, int style, int pathTailX, int pathTailY, int stratDiagramId, int layer) {
		this.idDrawablePath = id;
		this.pathStyle = style;
		this.pathTailX = pathTailX;
		this.pathTailY = pathTailY;
		this.stratDiagramId = stratDiagramId;
		this.diagramLayer = layer;
	}
	
	private static boolean arePointsSet = false;
	public void setPoints(int[] x, int[] y) {
		if (arePointsSet) {
			throw new DrawableDaoException(String.format(
					"immutability violation: attempt to re-set Path#%d points", idDrawablePath));
		}
		if (x.length != y.length) {
			throw new DrawableDaoException(String.format(
					"point coordinate arrays length mismatch: x vs y lengths of %d vs %d", x.length, y.length));
		}
		
		this.x = Arrays.copyOf(x, x.length);
		this.y = Arrays.copyOf(y, y.length);
		arePointsSet = true;
	}
	
	private void requirePointsBeSetForAccess() {
		if (!arePointsSet) {
			throw new DrawableDaoException(String.format("uninitialized path: "
					+ "attempt to access point data before points are set in Path#%d", idDrawablePath));
		}
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
	public int getId() {
		return idDrawablePath;
	}

	public int getPathStyle() {
		return pathStyle;
	}

	public int getPathTailX() {
		return pathTailX;
	}

	public int getPathTailY() {
		return pathTailY;
	}

	public int getNumPoints() {
		requirePointsBeSetForAccess();
		return numPoints;
	}
	
	public int[] getX() {
		requirePointsBeSetForAccess();
		return Arrays.copyOf(x, x.length);
	}
	
	public int[] getY() {
		requirePointsBeSetForAccess();
		return Arrays.copyOf(y, y.length);
	}
	
}
