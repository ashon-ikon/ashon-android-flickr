package com.ashon.associates.android.ashonflickr;

import java.util.ArrayList;

import com.ashon.associates.android.ashonflickr.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

public class GalleryActivity extends ApplicaitonActivity {
	final static int LISTVIEW_LOADER_ID	= 200;
	
	// Properties
	protected String	pixFeed					 	= "";
	protected ArrayList<FlickrImage>	imageList	= new ArrayList<FlickrImage>(1);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		// Store instance in settings
		
		// Launch the view
		prepareView();
	}
	

	/**
	 * Prepares all the is need for the view
	 */
	private void prepareView() {
		// Check if we have connection otherwise show nothing else
		if (isNetworkAvailable()){
			ImagesLoaderAsyncTask downloadTask	= new ImagesLoaderAsyncTask();
			downloadTask.execute();
		} else {
			// Network is not available let's notify use and 
			// show empty screen!
			Toast.makeText(this, "Please check your Internet connection.\n I can't reach Flickr.com", Toast.LENGTH_LONG).show();
			Log.d(this.getClass().getSimpleName(), "!!!!Network is not available !!!!");
		}
		
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_gallery, menu);
		return true;
	}
	
	protected class ImagesLoaderAsyncTask extends AsyncTask<Void, Void, ArrayList<FlickrImage>> {

		@Override
		protected ArrayList<FlickrImage> doInBackground(Void... params) {
			System.out.println("About to start donwloading images...");
			// Initialize the FlickrObj
			FlickrApi flickrApi	= getFlickrApi(GalleryActivity.this);
			// Get feed of most recent pictures...
			String pixFeed		= flickrApi.getTopImagesFeed();
			
			try {
				return flickrApi.getImagesListsFromFeed(pixFeed);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onCancelled()
		 */
		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(ArrayList<FlickrImage> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			ListView galleryImages	= (ListView) findViewById(R.id.results_list);
			galleryImages.setAdapter(new ImagesListAdapter(GalleryActivity.this, R.id.results_list, result));
			System.out.println("Done downloading images...");
		}
		
		
		
	}

}
