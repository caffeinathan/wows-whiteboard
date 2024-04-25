package org.comit.nrogowski.wows_whiteboard;

import org.comit.nrogowski.wows_whiteboard.util.ThymeleafConstant;

public class Constants {

	public static final boolean ENABLE_DEBUG = true;
	
	@ThymeleafConstant
	public static final String APPLICATION_TITLE = "WoWS Whiteboard";
	
	
	private static final String APPLICATION_DOMAIN = "localhost:8080";

	private static final String WG_APPLICATION_ID = "c0df59e2cbe1ea0c3dd1347c742066be";
	
	private static final String WG_NA_AUTH_PATH = "https://api.worldoftanks.com/wows/auth/login/";
	private static final String WG_EU_AUTH_PATH = "https://api.worldoftanks.eu/wot/auth/login/";
	private static final String WG_AS_AUTH_PATH = "https://api.worldoftanks.asia/wot/auth/login/";
	
	@ThymeleafConstant
	public static final String WG_NA_AUTH_URI_WITHOUT_BINDING =
			String.format("%s?application_id=%s&redirect_uri=http://%s",
			WG_NA_AUTH_PATH, WG_APPLICATION_ID, APPLICATION_DOMAIN);
	@ThymeleafConstant
	public static final String WG_EU_AUTH_URI_WITHOUT_BINDING =
			String.format("%s?application_id=%s&redirect_uri=http://%s",
			WG_EU_AUTH_PATH, WG_APPLICATION_ID, APPLICATION_DOMAIN);
	@ThymeleafConstant
	public static final String WG_AS_AUTH_URI_WITHOUT_BINDING =
			String.format("%s?application_id=%s&redirect_uri=http://%s",
			WG_AS_AUTH_PATH, WG_APPLICATION_ID, APPLICATION_DOMAIN);
}
