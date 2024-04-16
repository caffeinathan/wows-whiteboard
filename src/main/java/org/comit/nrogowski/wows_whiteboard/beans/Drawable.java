package org.comit.nrogowski.wows_whiteboard.beans;

public abstract class Drawable implements Comparable<Drawable> {

	@Override
	public int compareTo(Drawable d) {
		return Integer.compare(getLayer(), d.getLayer());
	}
	
	public abstract int getId();
	public abstract int getLayer();
	public abstract int getStratDiagramId();
	
	public static boolean areEqual(Drawable d1, Drawable d2) {
		return d1.getId() == d2.getId();
	}
	public static boolean areSemanticallyEqual(Drawable d1, Drawable d2) {
		// TODO: implement proper checks that won't silently fail when new drawable types are added
		return true;
	}
}
