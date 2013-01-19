/**
 * 
 */
package com.ashon.associates.android.ashonflickr;

import java.util.HashMap;

/**
 * @author ashon
 *
 */
public class ApplicationsRegistry {
	/**
	 * @param singleton instance
	 */
	protected	static ApplicationsRegistry		instance;
	
	protected	HashMap<String, Object>	settings;
	/**
	 * Gaurd against class instantiation
	 */
	protected ApplicationsRegistry(){ }
	/**
	 * Singleton Instance
	 * @return	self
	 */
	public static ApplicationsRegistry	getInstance(){
		if (null == ApplicationsRegistry.instance){
			ApplicationsRegistry.instance	= new ApplicationsRegistry();
		}
		return ApplicationsRegistry.instance;
	}
	
	public Object getSettings(String key) {
		return settings.get(key);
	}
	
	public void addSettings(String key, Object value) {
//		settings.put(key, value);
	}

}
