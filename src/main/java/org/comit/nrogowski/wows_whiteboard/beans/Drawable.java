package org.comit.nrogowski.wows_whiteboard.beans;

public abstract class Drawable implements Comparable<Drawable> {

	@Override
	public int compareTo(Drawable d) {
		return Integer.compare(getLayer(), d.getLayer());
	}
	abstract protected int getLayer();
}
