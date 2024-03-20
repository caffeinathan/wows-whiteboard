package org.comit.nrogowski.wows_whiteboard.logical_model;

/**
 * Existence of an object of this class is designed to guarantee validity and legitimacy for the ID in question.
 * It allows users of IDs to assume that an ID is verified to be a genuine WG account ID, and belong to the user
 * who is using our service.
 */
public class WargamingUserId implements Comparable<WargamingUserId> {
	public final int value;
	
	WargamingUserId(int value) {
		validate(value);
		this.value = value;
	}
	
	private static void validate(int id) {
		// TODO valid WGID when 3rd party integration is ready
	}

	@Override
	public int compareTo(WargamingUserId that) {
		return Integer.compare(this.value, that.value);
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WargamingUserId other = (WargamingUserId) obj;
		return value == other.value;
	}
	
	
}
