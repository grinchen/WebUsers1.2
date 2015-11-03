package ua.org.oa.grinchenkoa.webusers.managers;

import java.util.ResourceBundle;


/**
 * @author Andrei Grinchenko
 * 
 * Class extract configuration from file
 */

public class ConfigurationManager {
	
	/**
	 * ConfigurationManager instance
	 */
	private static ConfigurationManager instance;
	private ResourceBundle resourceBundle;
	
	/**
	 * Property's file
	 */
	private static final String BUNDLE_NAME = "config";
	public static final String DATABASE_DRIVER_NAME = "DATABASE_DRIVER_NAME";
	public static final String DATABASE_URL = "DATABASE_URL";
	public static final String DATABASE_LOGIN = "DATABASE_LOGIN";
	public static final String DATABASE_PASSWORD = "DATABASE_PASSWORD";
	public static final String LOGIN_PAGE_PATH = "LOGIN_PAGE_PATH";
	public static final String MAIN_PAGE_PATH = "MAIN_PAGE_PATH";
	public static final String MAIN_MODER_PAGE_PATH = "MAIN_MODER_PAGE_PATH";
	public static final String MAIN_ADMIN_PAGE_PATH = "MAIN_ADMIN_PAGE_PATH";
	public static final String REG_PAGE_PATH = "REG_PAGE_PATH";
	public static final String UPDATE_PAGE_PATH = "UPDATE_PAGE_PATH";
	public static final String ERROR_PAGE_PATH = "ERROR_PAGE_PATH";
	public static final String USER = "USER";
	public static final String MODERATOR = "MODERATOR";
	public static final String ADMIN = "ADMIN";
	
	/**
	 * Private constructor, unable to create object outside
	 */
	private ConfigurationManager() {}
	

	/**
	 * 
	 * @return single ConfigurationManager instance
	 */
	public static ConfigurationManager getInstance() {
		if (instance == null) {
			instance = new ConfigurationManager();
			instance.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
		}
		return instance;
	}
	
	/**
	 * Getting property with such key
	 * 
	 * @param key
	 * @return Property
	 */
	public String getProperty(String key) {
		return resourceBundle.getString(key);
	}
}
