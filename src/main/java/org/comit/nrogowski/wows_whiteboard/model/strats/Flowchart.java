package org.comit.nrogowski.wows_whiteboard.model.strats;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.comit.nrogowski.wows_whiteboard.model.WowsWhiteboardModelException;



/**
 * A Flowchart is essentially a linked list which may branch any number of times in the forward direction.
 * @param <D>
 */
public abstract class Flowchart<D> {
	
	// Logically this class could implement set. For now this is deemed unnecessary.
	
	/** Origin node can be null, indicating this flowchart is empty */
	private Node<D> origin;
	
	/**
	 * Basically an Iterator, but branching-sensitive
	 */
	public class Navigator {
		Node<D> position = null;
		
		/**
		 * Gets the number of branches directly forward from the current node, or zero if there are none.
		 * This is equivalent to the size of the list of nodes available to navigate to using the
		 * {@link Navigator#next(int)} method.
		 * @return the number of nodes immediately following the current node
		 */
		public int numPathsForward() {
			if (position == null) {
				return origin != null ? 1 : 0;
			}
			return position.nexts().size();
		}
		
		/**
		 * Gets the next element in the flowchart, using the provided index to choose which branch to take.
		 * <p>
		 * If no next elements exist, this method will return <code>null</code> instead of an error if it receives
		 * an index of zero.
		 * @param index A valid index for which branch to take, or zero.
		 * Non-zero values which are out of the valid range
		 * <br />(<code>index >= </code>{@link Navigator#numPathsForward()})
		 * <br />will cause an exception.
		 * @see {@link Navigator#numPathsForward()}
		 * @return The chosen element, or <code>null</code> if there are no elements beyond this one.
		 * @throws IndexOutOfBoundsException If index is non-zero and out of bounds for this node. 
		 */
		public D next(int index) {
			D returnValue;
			
			// Initial call (or empty flowchart)
			if (position == null) {
				if (index != 0) {
					throw new IndexOutOfBoundsException(
						"null position cannot have multiple branches, requested index was " + index);
				}
				position = origin;
				returnValue = position != null ? position.contents : null;  
			}
			// Subsequent calls
			else {
				// we specifically do not test for index >= position.nexts().size(), so the exception rises to caller
				position = position.nexts().get(index);
				returnValue = position.contents;
			}
			
			return returnValue;
		}
		
		public D prev() {
			// null means either the flowchart is empty, or prev() was called first, either way nothing's back there 
			position = position == null ? null : position.prev();
			
			// Unlike for next(), since we can only have one node behind us, we never want to throw an exception,
			// so just return null if nothing is back there
			return position == null ? null : position.contents;
		}
	}
}






/**
 * This Node class defines an element in the above Flowchart for  
 * @param <D>
 */
class Node<D> {
	
	/*
	 * Constructor and setters
	 */
	
	Node(D contents, boolean isOriginal, boolean isTerminal) {
		if (isTerminal && isOriginal) {
			throw new WowsWhiteboardModelException(new IllegalArgumentException(
					"Nodes cannot be both original and terminal in a flowchart."));
		}
		if (contents == null) {
			throw new WowsWhiteboardModelException(new IllegalArgumentException(
					"Nodes cannot have null contents."));
		}
		this.isOriginal = isOriginal;
		this.isTerminal = isTerminal;
		this.contents = contents;
		nexts = new ArrayList<Node<D>>();
	}
	
	boolean setPrev(Node<D> prev) {
		if (isOriginal) {
			throw new NodeSideNotEnabledException("original nodes cannot have a previous node");
		}
		boolean hadValue = this.prev != null;
		this.prev = prev;
		return hadValue;
	}
	
	boolean setNexts(List<Node<D>> nexts) {
		// empty lists are expressly permitted, and are the only way to tell a node it points to no others 
		if (nexts == null) {
			throw new WowsWhiteboardModelException(new IllegalArgumentException(
					"Node.addNexts does not accept null lists"));
		}
		if (isTerminal) {
			throw new NodeSideNotEnabledException("terminal nodes cannot have any next nodes");
		}
		
		// deep copy
		boolean hadValue = this.nexts.size() > 0;
		this.nexts.clear();
		this.nexts.addAll(nexts);
		return hadValue;
	}
	
	/*
	 * Usage
	 */
	
	/**
	 * 
	 * @return the previous
	 */
	Node<D> prev() {
		return prev;
	}
	
	int nextCount() {
		return nexts.size();
	}
	
	List<Node<D>> nexts() {
		return List.copyOf(nexts);
	}
	
	/*
	 * Internals and misc
	 */
	
	// meta-data
	private final boolean isOriginal;
	private final boolean isTerminal;
	
	// logical data
	final D contents;
	private Node<D> prev;
	private final List<Node<D>> nexts;
	
	
	
	/**
	 * Provides non-generically typed access to the element contained in this node. 
	 * This method exists only to avoid doing weird reflection things in {@link Node#equals(Object)}.
	 * @return the element this node contains, as a non-generic type.
	 */
	private Object getElement() {
		return contents;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(contents);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof Node) {
			return Objects.equals(contents, ((Node)obj).getElement());
		}
		return false;
	}

	@SuppressWarnings("serial")
	static class NodeSideNotEnabledException extends WowsWhiteboardModelException {
		public NodeSideNotEnabledException(String message) {
			super(message);
		}
	}
}