package com.ashon.associates.android.ashonflickr;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class FlickrApi extends ConnectionManager
{
	/**
	 * @author ashon
	 *
	 */
	public class FeedInfo {
		private int		pages;
		private int		per_page;
		private int		total;
		private int 	status;
		
		public int getPages() {
			return pages;
		}
		public void setPages(int pages) {
			this.pages = pages;
		}
		public int getPer_page() {
			return per_page;
		}
		public void setPer_page(int per_page) {
			this.per_page = per_page;
		}
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		
	}


	/**
	 * @param singleton instance
	 */
	protected	static FlickrApi		instance;
	
	protected	Context			mContext;
	protected 	String 			mApiKey			= "";
	// Private Vars
	private	String			mFeedFormat			= "";
	private	String			mFeedExtras			= "description,license,owner_name,date_upload, date_taken,url_n,url_o";
	private	int				mFeedsPerRequest	= 10;
	private	boolean			mbNoJsonCallback	= true;
	

	// The array of our image class
	protected	ArrayList<FlickrImage> imagesArray	= new ArrayList<FlickrImage>();
	
	final static String FLICKR_SERVICE_ENDPOINT	= "http://api.flickr.com/services/rest";
	
	final static String FLICKR_METHOD_SEARCH_IMAGES			= "flickr.photos.search";
	final static String FLICKR_METHOD_RECENT_IMAGES			= "flickr.photos.getRecent";
	
	final static String FLICKR_FEED_TYPE_JSON				= "json";
	final static String FLICKR_FEED_TYPE_XML				= "xml";
	final static String FLICKR_FEED_TYPE_SERIAL				= "php_serial";
	/** FEED OPTIONS */
	final static String FLICKR_FEED_OPTION_API_KEY			= "api_key";
	final static String FLICKR_FEED_OPTION_EXTRAS			= "extras";
	final static String FLICKR_FEED_OPTION_FORMAT			= "format";
	final static String FLICKR_FEED_OPTION_METHOD			= "method";
	final static String FLICKR_FEED_OPTION_NO_JSON_FBACK	= "nojsoncallback";
	final static String FLICKR_FEED_OPTION_PAGE				= "page";
	final static String FLICKR_FEED_OPTION_PER_PAGE			= "per_page";
	
	/** FEED RESPONSE STATUS */
	final static String FLICKR_RESPONSE_STATUS_OKAY			= "ok";
	final static String FLICKR_RESPONSE_STATUS_FAIL			= "fail";
	
	/** RESPONSE DATA KEYS */
	final static String FLICKR_DATA_KEY_CONTENT				= "_content";
	final static String FLICKR_DATA_KEY_DATE_TAKEN			= "datetaken";
	final static String FLICKR_DATA_KEY_DATE_UPLOADED		= "dateupload";
	final static String FLICKR_DATA_KEY_IMAGE_DESCRIPTION	= "description";
	final static String FLICKR_DATA_KEY_IMAGE_HEIGHT		= "height_o";
	final static String FLICKR_DATA_KEY_IMAGE_ID			= "id";
	final static String FLICKR_DATA_KEY_IMAGE_FARM			= "farm";
	final static String FLICKR_DATA_KEY_IMAGE_OWNER			= "owner";
	final static String FLICKR_DATA_KEY_IMAGE_OWNER_NAME	= "ownername";
	final static String FLICKR_DATA_KEY_IMAGE_WIDTH			= "width_o";
	final static String FLICKR_DATA_KEY_LICENSE				= "license";
	final static String FLICKR_DATA_KEY_NO_OF_PAGES			= "pages";
	final static String FLICKR_DATA_KEY_PAGE				= "page";
	final static String FLICKR_DATA_KEY_PER_PAGE			= "perpage";
	final static String FLICKR_DATA_KEY_PHOTOS_LIST			= "photo";
	final static String FLICKR_DATA_KEY_PHOTOS				= "photos";
	final static String FLICKR_DATA_KEY_SECRET				= "secret";
	final static String FLICKR_DATA_KEY_SERVER				= "server";
	final static String FLICKR_DATA_KEY_STATUS				= "stat";
	final static String FLICKR_DATA_KEY_TOTAL				= "total";
	final static String FLICKR_DATA_KEY_TITLE				= "title";
	final static String FLICKR_DATA_KEY_URL_O				= "url_o";
	final static String FLICKR_DATA_KEY_URL_N				= "url_n";
	
	/** LOCAL ERROR CODES */
	final static int	ERROR_NO_ERROR						= 0;	// No Error, All is okay :)
	final static int	ERROR_MISSING_API_KEY				= 1 << 0;	// No API key specified
	/**
	 * sizes canblog="1" canprint="1" candownload="1">
  <size label="Square" width="75" height="75" source="http://farm2.staticflickr.com/1103/567229075_2cf8456f01_s.jpg" url="http://www.flickr.com/photos/stewart/567229075/sizes/sq/" media="photo" />
  <size label="Large Square" width="150" height="150" source="http://farm2.staticflickr.com/1103/567229075_2cf8456f01_q.jpg" url="http://www.flickr.com/photos/stewart/567229075/sizes/q/" media="photo" />
  <size label="Thumbnail" width="100" height="75" source="http://farm2.staticflickr.com/1103/567229075_2cf8456f01_t.jpg" url="http://www.flickr.com/photos/stewart/567229075/sizes/t/" media="photo" />
  <size label="Small" width="240" height="180" source="http://farm2.staticflickr.com/1103/567229075_2cf8456f01_m.jpg" url="http://www.flickr.com/photos/stewart/567229075/sizes/s/" media="photo" />
  <size label="Small 320" width="320" height="240" source="http://farm2.staticflickr.com/1103/567229075_2cf8456f01_n.jpg" url="http://www.flickr.com/photos/stewart/567229075/sizes/n/" media="photo" />
  <size label="Medium" width="500" height="375" source="http://farm2.staticflickr.com/1103/567229075_2cf8456f01.jpg" url="http://www.flickr.com/photos/stewart/567229075/sizes/m/" media="photo" />
  <size label="Medium 640" width="640" height="480" source="http://farm2.staticflickr.com/1103/567229075_2cf8456f01_z.jpg?zz=1" url="http://www.flickr.com/photos/stewart/567229075/sizes/z/" media="photo" />
  <size label="Medium 800" width="800" height="600" source="http://farm2.staticflickr.com/1103/567229075_2cf8456f01_c.jpg" url="http://www.flickr.com/photos/stewart/567229075/sizes/c/" media="photo" />
  <size label="Large" width="1024" height="768" source="http://farm2.staticflickr.com/1103/567229075_2cf8456f01_b.jpg" url="http://www.flickr.com/photos/stewart/567229075/sizes/l/" media="photo" />
  <size label="Original" width="2400" height="1800" source="http://farm2.staticflickr.com/1103/567229075_6dc09dc6da_o.jpg" url="http://www.flickr.com/photos/stewart/567229075/sizes/o/" media="photo" />
</sizes>
	 */
	
	/**
	 * Gaurd against class instantiation
	 */
	protected FlickrApi(){ }
	/**
	 * Singleton Instance
	 * @return	self
	 */
	public static FlickrApi	getInstance(){
		if (null == FlickrApi.instance){
			FlickrApi.instance	= new FlickrApi();
		}
		return FlickrApi.instance;
	}
	
	/**
	 * Constructor type two
	 * 
	 * @param String API Key
	 * @return boolean True on success
	 */
	public FlickrApi init(Context context, String apiKey) throws ExceptionInInitializerError {
		// Store the context
		this.setContext(context);
		// Store the API key
		if (!apiKey.equals("")) {
			setApiKey(apiKey);
		} else {
			throw new ExceptionInInitializerError("Empty API Key provided!");
		}

		return this;
	}
	
	public String getFeedFormat() {
		if (mFeedFormat.equals("")) {
			// Assume JSON
			setFeedFormat(FLICKR_FEED_TYPE_JSON);
		}
		return mFeedFormat;
	}
	
	public void setFeedFormat(String feedFormat) {
		this.mFeedFormat = feedFormat;
	}
	
	/**
	 * @return the mFeedsPerRequest
	 */
	public Integer getFeedsPerRequest() {
		return mFeedsPerRequest;
	}
	/**
	 * @param mFeedsPerRequest the mFeedsPerRequest to set
	 */
	public void setFeedsPerRequest(int mFeedsPerRequest) {
		this.mFeedsPerRequest = mFeedsPerRequest;
	}
	/**
	 * @return the mFeedExtras
	 */
	public String getFeedExtras() {
		return mFeedExtras;
	}
	/**
	 * @param mFeedExtras the mFeedExtras to set
	 */
	public void setFeedExtras(String mFeedExtras) {
		this.mFeedExtras = mFeedExtras;
	}
	/**
	 * @return the context
	 */
	public Context getContext() {
		return mContext;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(Context context) {
		this.mContext = context;
	}

	
	/**
	 * @return the ApiKey
	 */
	public String getApiKey() {
		return mApiKey;
	}

	/**
	 * @param mApiKey the ApiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.mApiKey = apiKey;
	}


	
	protected boolean setupApi()
	{
		return true;
	}
	
	public JSONObject names() {
		JSONObject jsonObj = new JSONObject();
		
		return jsonObj;
	}
	
	/**
	 * Reads the latest images public available
	 * @return String
	 */
	public String getTopImagesFeed()
	{
		ArrayList<NameValuePair> params	= new ArrayList<NameValuePair>(1);
		
		// Add the required params
		params.add(new BasicNameValuePair(FLICKR_FEED_OPTION_API_KEY, getApiKey()));
		params.add(new BasicNameValuePair(FLICKR_FEED_OPTION_METHOD, FLICKR_METHOD_RECENT_IMAGES));
		params.add(new BasicNameValuePair(FLICKR_FEED_OPTION_FORMAT, getFeedFormat()));
		params.add(new BasicNameValuePair(FLICKR_FEED_OPTION_EXTRAS, getFeedExtras()));
		params.add(new BasicNameValuePair(FLICKR_FEED_OPTION_PER_PAGE, getFeedsPerRequest().toString()));
		if (mbNoJsonCallback) {
			params.add(new BasicNameValuePair(FLICKR_FEED_OPTION_NO_JSON_FBACK, "1"));
		}
		
		// Return result
		return doPost(FLICKR_SERVICE_ENDPOINT, params);
	}
	
	/**
	 * 
	 * @param feed
	 * @return boolead True on successful operation
	 */
	public boolean buildImageListFromFeed(String feed)
	{
		return true;
	}
	
	/**
	 * Checks that we have a valid API key
	 * @return
	 */
	protected boolean gotApiKey() {
		// Ensure we have a valid key!
		if (getApiKey().equals("")) {
			return false;
		}
		return true;
	}
	
	/**
	 * Gets the images From string feed
	 * @param pixFeed
	 * @return
	 */
	public static FeedInfo getFeedInfo(String pixFeed) {
//		ArrayList<FlickrImage> imageList	= new ArrayList<FlickrImage>();
		if (pixFeed.length() > 0){
			try {
				JSONObject jsonObj	= new JSONObject(pixFeed);
				if (jsonObj.has(FLICKR_DATA_KEY_STATUS)) {
					if (jsonObj.getString(FLICKR_DATA_KEY_STATUS) == FLICKR_RESPONSE_STATUS_OKAY) {
						if (jsonObj.has(FLICKR_DATA_KEY_PHOTOS)) {
//							JSONArray jsonPhotos	= (JSONArray)jsonObj.getJSONArray(FLICKR_DATA_KEY_PHOTOS);
							
							// Get the
						}
					}
				}
			}  catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
							
	
	/**
	 * Gets the images From string feed
	 * @param pixFeed
	 * @return
	 */
	public ArrayList<FlickrImage> getImagesListsFromFeed(String pixFeed) throws Throwable{
		ArrayList<FlickrImage> imageList	= new ArrayList<FlickrImage>();
		if (pixFeed.length() > 0){
			try {
				JSONObject jsonObj	= new JSONObject(pixFeed);
				if (jsonObj.has(FLICKR_DATA_KEY_STATUS)) {
					if (jsonObj.getString(FLICKR_DATA_KEY_STATUS).equals(FLICKR_RESPONSE_STATUS_OKAY)) {
						
						if (jsonObj.has(FLICKR_DATA_KEY_PHOTOS)) {
							JSONObject jsonPhotos	= (JSONObject)jsonObj.getJSONObject(FLICKR_DATA_KEY_PHOTOS);
							// Get the photos 
							
							if (jsonPhotos.has(FLICKR_DATA_KEY_PHOTOS_LIST)) {
								JSONArray jsonPhotosList	= (JSONArray)jsonPhotos.getJSONArray(FLICKR_DATA_KEY_PHOTOS_LIST);

								for (int i = 0; i < jsonPhotosList.length(); i++) {
									// Make a short named instance of this photo object
									JSONObject	thisPhoto	= jsonPhotosList.getJSONObject(i); 
									
									// Get the image url
									String imageUrl	= getImageUrlFromJsonObject(
										jsonPhotosList.getJSONObject(i),
										FlickrImage.IMAGE_SIZE_THUMBNAIL_100
									);
									
									FlickrImage	dummyImage	= (FlickrImage)new FlickrImage(imageUrl);
									// Set the Description
									String imageDesc		= "";
									if (thisPhoto.has(FLICKR_DATA_KEY_IMAGE_DESCRIPTION)) {
										imageDesc	= thisPhoto.getJSONObject(FLICKR_DATA_KEY_IMAGE_DESCRIPTION)
																		.getString(FLICKR_DATA_KEY_CONTENT);
									}
									dummyImage.setImageDesc(imageDesc);
									
									// Set the Title
									String	imageTitle	= "";
									if (thisPhoto.has(FLICKR_DATA_KEY_TITLE)) {
										imageTitle		= thisPhoto.getString(FLICKR_DATA_KEY_TITLE);
									}
									dummyImage.setImageTitle(imageTitle);
									
									// Set the image dimensions
									dummyImage.setImageThumbnailHeight(dummyImage.getDrawableImage().getIntrinsicHeight());
									dummyImage.setImageThumbnailWidth(dummyImage.getDrawableImage().getIntrinsicWidth());
									
									// Set the image owner
									String imageOwner	= "";
									if (thisPhoto.has(FLICKR_DATA_KEY_IMAGE_OWNER_NAME)) {
										imageOwner		= thisPhoto.getString(FLICKR_DATA_KEY_IMAGE_OWNER_NAME);
									}
									dummyImage.setImageOwner(imageOwner);
									
									// Set the image drawable
									imageList.add(dummyImage);

									dummyImage.debugProperties();
								}
							}
						}
						
					} else {
						// Determine the error and trigger it!!
Log.d(
		FlickrApi.getInstance().getClass().getSimpleName(),
		"FEED not OKAY!!" + jsonObj.getString(FLICKR_DATA_KEY_STATUS)
);						
					}	// End FLICKR_RESPONSE_STATUS_OKAY
					
				} else {
					
				} // End has FLICKR_DATA_KEY_STATUS
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return imageList;
	}
	
	public static String getImageUrlFromJsonObject(JSONObject photoObj, int size) throws JSONException {
		if (null != photoObj) {
			// Extract all the values
			String farmId	= "";
			String serverId	= "";
			String imageId	= "";
			String secret	= "";
			String url		= "http://farm";
			boolean okay	= true;
			
			if (photoObj.has(FLICKR_DATA_KEY_IMAGE_FARM)) {
				farmId	= photoObj.getString(FLICKR_DATA_KEY_IMAGE_FARM);
			} else {
				okay 	= false;
			}
			
			if (photoObj.has(FLICKR_DATA_KEY_SERVER)) {
				serverId	= photoObj.getString(FLICKR_DATA_KEY_SERVER);
			} else {
				okay 	= false;
			}
			
			if (photoObj.has(FLICKR_DATA_KEY_IMAGE_ID)) {
				imageId	= photoObj.getString(FLICKR_DATA_KEY_IMAGE_ID);
			} else {
				okay 	= false;
			}
			
			if (photoObj.has(FLICKR_DATA_KEY_SECRET)) {
				secret	= photoObj.getString(FLICKR_DATA_KEY_SECRET);
			} else {
				okay 	= false;
			}
			// If we didn't have any troubles
			if (okay) {
				// Use size
				String sizeType	= "";
				switch(size) {
				case	FlickrImage.IMAGE_SIZE_NONE: 				sizeType 	= ""; break;
				case	FlickrImage.IMAGE_SIZE_SMALL_75: 			sizeType 	= "s"; break;
				case	FlickrImage.IMAGE_SIZE_LARGE_SQUARE_150:	sizeType 	= "q"; break;
				case	FlickrImage.IMAGE_SIZE_THUMBNAIL_100:		sizeType 	= "t"; break;
				case	FlickrImage.IMAGE_SIZE_SMALL_240:			sizeType 	= "m"; break;
				case	FlickrImage.IMAGE_SIZE_SMALL_320:			sizeType 	= "n"; break;
				case	FlickrImage.IMAGE_SIZE_MEDIUM_500:			sizeType 	= "-"; break;
				case	FlickrImage.IMAGE_SIZE_MEDIUM_640:			sizeType 	= "z"; break;
				case	FlickrImage.IMAGE_SIZE_MEDIUM_800:			sizeType 	= "c"; break;
				case	FlickrImage.IMAGE_SIZE_LARGE_1024:			sizeType 	= "b"; break;
				case	FlickrImage.IMAGE_SIZE_ORIGINAL:			sizeType 	= "o"; break;
				default:
						
				}
				
				url 	+= farmId+ ".staticflickr.com/"+serverId+"/"+imageId+"_"+secret;
				url 	+= (!sizeType.equals("")? "_"+sizeType: "")+".jpg";
Log.d(
		FlickrApi.getInstance().getClass().getSimpleName(),
		"URL: " + url
);				
				
				return url;
			}
		}
		return null;
	}
	
}
