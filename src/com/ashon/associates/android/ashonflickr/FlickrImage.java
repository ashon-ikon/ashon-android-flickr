package com.ashon.associates.android.ashonflickr;

import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Image class to hold each image data
 * 
 * @author yinka
 *
 */
public class FlickrImage extends ConnectionManager {
	
	protected	String	imageOwner		= "";
	protected	String	imageUrlThmb	= "";
	protected	String	imageUrl		= "";
	protected	String	imageDesc		= "No description";
	protected	String	imageTitle		= "Unknown Title";
	protected	int		imageOriWidth;
	protected	int		imageOriHeight;
	protected	int		imagethmbWidth;
	protected	int		imagethmbHeight;
	
	/** DEFAULT STRING CONTENTS */
	public final static String	STRING_UNKNOWN_DESCRIPTION		= "Unknown description";
	public final static String	STRING_UNKNWON_OWNER			= "Unknown Owner";
	public final static String	STRING_UNKNOWN_TITLE			= "Unknown title";
	
	protected	Drawable drawableImage;
	
	/** IMAGE SIZES */
	public final static int IMAGE_SIZE_NONE				=	0;
	public final static int IMAGE_SIZE_SMALL_75			=	1 << 1;
	public final static int IMAGE_SIZE_LARGE_SQUARE_150	=	1 << 2;
	public final static int IMAGE_SIZE_THUMBNAIL_100	=	1 << 3;
	public final static int IMAGE_SIZE_SMALL_240		=	1 << 4;
	public final static int IMAGE_SIZE_SMALL_320		=	1 << 5;
	public final static int IMAGE_SIZE_MEDIUM_500		=	1 << 6;
	public final static int IMAGE_SIZE_MEDIUM_640		=	1 << 7;
	public final static int IMAGE_SIZE_MEDIUM_800		=	1 << 8;
	public final static int IMAGE_SIZE_LARGE_1024		=	1 << 9;
	public final static int IMAGE_SIZE_ORIGINAL			=	1 << 10;
	
	/**
	 * Constructor
	 */
	public FlickrImage(String imageUrl) {
		setImageUrl(imageUrl);
		if (null != getDrawableImage()) {
			// Set up the image params
			imageOriWidth	= getDrawableImage().getIntrinsicWidth();
			imageOriHeight	= getDrawableImage().getIntrinsicHeight();
		}
	}
	
	
	/**
	 * @return the imageWidth
	 */
	public int getImageOriginalWidth() {
		return imageOriWidth;
	}
	/**
	 * @param originalWidth the imageWidth to set
	 */
	public void setImageOriginalWidth(int originalWidth) {
		this.imageOriWidth = originalWidth;
	}
	/**
	 * @return the imageHeight
	 */
	public int getImageOriginalHeight() {
		return imageOriHeight;
	}
	/**
	 * @param imageOriginalHeight the imageHeight to set
	 */
	public void setImageOriginalHeight(int imageOriginalHeight) {
		this.imageOriHeight = imageOriginalHeight;
	}
	/**
	 * @return the imageWidth
	 */
	public int getImageThumbnailWidth() {
		return imagethmbWidth;
	}
	/**
	 * @param thmbWidth the imageWidth to set
	 */
	public void setImageThumbnailWidth(int thmbWidth) {
		this.imagethmbWidth = thmbWidth;
	}
	/**
	 * @return the imageHeight
	 */
	public int getImageThumbnailHeight() {
		return imagethmbHeight;
	}
	/**
	 * @param imageOriginalHeight the imageHeight to set
	 */
	public void setImageThumbnailHeight(int thmbHeight) {
		this.imagethmbHeight	= thmbHeight;
	}
	
	/**
	 * @return the imageUrlThmb
	 */
	public String getImageUrlThmb() {
		return imageUrlThmb;
	}
	/**
	 * @param imageUrlThmb the Image thumbnail URL to set
	 */
	public void setImageUrlThmb(String imageUrlThmb) {
		this.imageUrlThmb = imageUrlThmb;
	}
	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}
	/**
	 * @param imageUrl the image URL to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	/**
	 * @return the imageDesc
	 */
	public String getImageDesc() {
		if (imageDesc.length() == 0) {
			setImageDesc(imageDesc);
		}
		return imageDesc;
	}
	/**
	 * @param imageDesc the image description to set
	 */
	public void setImageDesc(String imageDesc) {
		if (imageDesc.length() == 0) {
			// Use default description!
			imageDesc 	= STRING_UNKNOWN_DESCRIPTION;
		}
		this.imageDesc = imageDesc;
	}
	/**
	 * @return the imageTitle
	 */
	public String getImageTitle() {
		if (imageTitle.length() == 0) {
			setImageTitle(imageTitle);
		}
		return imageTitle;
	}
	/**
	 * @param imageTitle the imageTitle to set
	 */
	public void setImageTitle(String imageTitle) {
		if (imageTitle.length() == 0) {
			// Use default title
			imageTitle 	= STRING_UNKNOWN_TITLE;
		}
		this.imageTitle = imageTitle;
	}
	
	/**
	 * @return the imageOwner
	 */
	public String getImageOwner() {
		if (imageOwner.length() == 0) {
			setImageOwner(imageOwner);
		}
		return imageOwner;
	}


	/**
	 * @param imageOwner the imageOwner to set
	 */
	public void setImageOwner(String imageOwner) {
		if (imageOwner.length() == 0) {
			this.imageOwner	= STRING_UNKNWON_OWNER;
		}
		this.imageOwner = imageOwner;
	}


	/**
	 * Gets the drawable image
	 * @return
	 */
	public Drawable getDrawableImage() {
		if (null == this.drawableImage){
			// If we have a valid URL then attempt to fetch it
			// otherwise return null
			if (!getImageUrl().equals("")) {
				this.drawableImage	= fetchDrawable(getImageUrl());
			} else {
				return null;
			}
		}
		return this.drawableImage;
	}
	
	public Drawable createThumbNail(Drawable image) {
		
		return null;
	}
	
	public void debugProperties() {
		Log.d(
			this.getClass().getSimpleName(),
			"Title: " 				+ getImageTitle() + "\n"
			+"Description: " 		+ getImageDesc() + "\n"
			+"Owner: " 				+ getImageOwner() + "\n"
			+"Original Height: " 	+ getImageOriginalHeight() + "\n"
			+"Original Width: " 	+ getImageOriginalWidth() + "\n"
			+"URL: " 				+ getImageUrl()
		);
	}
}