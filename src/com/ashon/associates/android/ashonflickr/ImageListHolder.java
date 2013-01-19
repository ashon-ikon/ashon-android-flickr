package com.ashon.associates.android.ashonflickr;

import java.util.HashMap;

public class ImageListHolder {
	protected	HashMap<String, FlickrImage> imageHashMap	= new HashMap<String, FlickrImage>();
	
	/**
	 * @param singleton instance
	 */
	protected	static ImageListHolder		instance;
	
	/**
	 * Gaurd against class instantiation
	 */
	protected ImageListHolder() { }
	/**
	 * Singleton Instance
	 * @return	self
	 */
	public static ImageListHolder getInstance(){
		if (null == ImageListHolder.instance){
			ImageListHolder.instance	= new ImageListHolder();
		}
		return ImageListHolder.instance;
	}
	
	/**
	 * Added new image
	 * @param imageUrl
	 */
	public void addImage(FlickrImage imageUrl) {
		if (null != imageUrl) {
			String	imageName	= imageUrl.getImageUrl();
			this.imageHashMap.put(imageName, imageUrl);
		}
	}
	/**
	 * Gets an existing image
	 * @param imageUrl
	 * @return FlickrImage | null on empty
	 */
	public FlickrImage getImage(String imageUrl) {
		return this.imageHashMap.get(imageUrl);
	}
}
