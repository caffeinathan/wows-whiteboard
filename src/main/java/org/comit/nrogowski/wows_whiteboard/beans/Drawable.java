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
	
	/**
	 * If writing code that needs to be updated when new drawable types are added, you can check your logic against this
	 * value to enact the principle of failing early.
	 * <p>
	 * Example:<br>
	 * <code>if (drawable instanceof DrawableIcon) ...</code><br>
	 * <code>else if (drawable instganceof DrawablePath) ...</code><br><br>
	 * <code>if (Drawable.NUM_TYPES > 2) throw new OperationUnsupportedException("implement new drawable case!");</code>
	 */
	public static final int NUM_TYPES = 3;
}
