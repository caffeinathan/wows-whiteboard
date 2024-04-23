package org.comit.nrogowski.wows_whiteboard;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	public static final boolean ENABLE_DEBUG = true;
	
	public static final String APPLICATION_TITLE = "appTitle";
	public static final String COPYRIGHT_NOTICE = "copyrightNotice";
	
	public static final Map<String, String> strings = new HashMap<>(); {
		strings.put(APPLICATION_TITLE, "WoWS Whiteboard");
		strings.put(COPYRIGHT_NOTICE, strings.get(APPLICATION_TITLE)
				+ " is a third-party tool under Wargaming Developers Partner Program."
				+ "<br>Copyright &copy; 2024, all rights reserved");
	}

}
