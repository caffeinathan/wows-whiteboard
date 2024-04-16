package org.comit.nrogowski.wows_whiteboard.beans;

public abstract class Drawable implements Comparable<Drawable> {

	@Override
	public int compareTo(Drawable d) {
		return Integer.compare(getLayer(), d.getLayer());
	}
	
	abstract int getId();
	/**
	 * Retrieves the layer index for this Drawable. 0 represents the back-most layer,
	 * and the maximum value is the front-most layer, to appear unobscured over every other Drawable.
	 * @return the layer index for this Drawable
	 */
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
