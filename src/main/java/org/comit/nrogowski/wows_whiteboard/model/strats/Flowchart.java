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