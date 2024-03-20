package org.comit.nrogowski.wows_whiteboard.logical_model;

import java.util.HashMap;
import java.util.Map;

abstract class UserBookPrivilegeObservable {

	private final Map<WargamingUserId, UserBookPrivilegeObserver> observers = new HashMap<>();
			
	final public void addUserBookPrivilegeObserver(WargamingUserId observerId, UserBookPrivilegeObserver observer) {
		UserBookPrivilegeObserver oldObserver = observers.put(observerId, observer);
		if (oldObserver != null) {
			// TODO: log this event?
		}
	}
	
	final public void removeUserBookPrivilegeObserver(WargamingUserId observerId, UserBookPrivilegeObserver observer) {
		// By requiring the observer to be passed in, we avoid a category of possible defects/exploits which result
		// in an active user being disconnected from changes in privileges 
		if (observer == observers.get(observerId)) {
			observers.remove(observerId);
		} else {
			// How did we get a request to remove a wrong user? Probably, a user is logged in with multiple sessions.
			// TODO: log this event?
		}
	}
	
	/**
	 * Call this once for each user ID added or removed from a StratBook's privileges so we can propagate
	 * changes in privilege when a user is actively logged in.
	 * <p>
	 * This does not eliminate the need for the entity in which the privilege changes originated from updating the DB.
	 * @param observers
	 * @param idToUpdate
	 * @param stratBook
	 */
	final protected void update(WargamingUserId idToUpdate, StratBook stratBook) {
		UserBookPrivilegeObserver observer = observers.get(idToUpdate);
		if (observer != null) {
			observer.updateUserBookPrivileges(stratBook);
		}
	}
}
