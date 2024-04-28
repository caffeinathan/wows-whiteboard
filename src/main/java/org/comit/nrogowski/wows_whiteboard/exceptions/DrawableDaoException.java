package org.comit.nrogowski.wows_whiteboard.exceptions;

@SuppressWarnings("serial")
public class DrawableDaoException extends WowsWhiteboardException {
	public DrawableDaoException(String message) {
		super(message);
	}

	public DrawableDaoException(String message, Throwable cause) {
		super(message, cause);
	}
}
