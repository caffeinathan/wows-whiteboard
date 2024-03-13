package org.comit.nrogowski.wows_whiteboard.model;

/**
 * We anticipate fewer than 10 000 concurrent users at peak usage. 
 */
public class WargamingUser implements UserBookPrivilegeObserver {
	private final WargamingUserId wgUid;
}
