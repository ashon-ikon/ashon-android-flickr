package com.ashon.associates.android.ashonflickr;

import java.util.ArrayList;

import android.app.Activity;
import android.net.MailTo;
import android.os.AsyncTask;
import android.widget.ListView;

public class ImagesDownloaderTask extends AsyncTask<String, Void, ArrayList<FlickrImage>> 
{
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	GalleryActivity	galleryActitivy;
	int count	= 0;
	@Override
	protected void onPreExecute() {		
		super.onPreExecute();
		galleryActitivy	= (GalleryActivity)ApplicationsRegistry.getInstance()
							.getSettings(GalleryActivity.class.getSimpleName());
	}
	@Override
	protected ArrayList<FlickrImage> doInBackground(String... params) {
		if (params.length > 0) {
			for(int i = 0; i < params.length; i++) {
				// Initialize the FlickrObj
				FlickrApi flickrApi	= galleryActitivy.getFlickrApi(galleryActitivy);
				String pixFeed		= flickrApi.getTopImagesFeed();
				// Get feed of most recent pictures...
				if (pixFeed.equals("")) {
					pixFeed	= flickrApi.getTopImagesFeed();
				}
				try {
					return flickrApi.getImagesListsFromFeed(pixFeed);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute( ArrayList<FlickrImage> imageList) {
		super.onPostExecute(imageList);
		// Update the List View
		ListView galleryImages	= (ListView) galleryActitivy.findViewById(R.id.results_list);
		galleryImages.setAdapter(new ImagesListAdapter(galleryActitivy, R.id.results_list, imageList));
	}

	

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onProgressUpdate(Progress[])
	 */
	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

}
