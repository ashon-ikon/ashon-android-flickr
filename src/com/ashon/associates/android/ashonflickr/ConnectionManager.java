/**
 * 
 */
package com.ashon.associates.android.ashonflickr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * @author ashon
 *
 */
public class ConnectionManager {

	protected HttpGet 		mhttpGet;
	protected HttpPost 		mhttpPost;
	protected HttpResponse 	mhttpResponse;
	protected HttpClient	mhttpClient;
	
	final static int		HTTP_RESPONSE_OK		= 200;

	/**
	 * This runs a HTTP post operation. A proxy for calling doPost without params
	 *  
	 * @param url
	 * @return String Result of operation
	 */
	public String doPost(String url) {
		ArrayList<NameValuePair> emptyName	= new ArrayList<NameValuePair>(1);
		
		return doPost(url, emptyName);
	}

	/**
	 * This runs a HTTP post operation
	 *  
	 * @param url
	 * @param nameValuePairs
	 * @return String Result of operation
	 */
	public String doPost(String url, ArrayList<NameValuePair> nameValuePairs) {
	
		HttpClient		httpClient	= new DefaultHttpClient();
		StringBuilder	strBuilder	= new StringBuilder();
		BufferedReader	bReader;
		// Test the connection
		
		
		// Add the name pairs
		try {
			mhttpPost		= new HttpPost(url);
			mhttpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			mhttpResponse	= httpClient.execute(mhttpPost);
			StatusLine statusCode	= mhttpResponse.getStatusLine();
			if (statusCode.getStatusCode() == HTTP_RESPONSE_OK) {
				bReader			= new BufferedReader(
					new InputStreamReader(mhttpResponse.getEntity().getContent())
				);
				String line;
				while ((line = bReader.readLine()) != null) {
					strBuilder.append(line);
				}
				// Consume the entire response
				mhttpResponse.getEntity().consumeContent();
			} else {
				// Failed to retrieve correctly
				Log.d(this.getClass().getSimpleName(), "Failed to do POST ("+url+")");
				return null;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return "";
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return strBuilder.toString();
	}
	
	public InputStream doGet(String url) throws IOException, MalformedURLException {
		if (null == mhttpClient) {
			mhttpClient	= new DefaultHttpClient();
		}
		// Recreate the Get Object
		mhttpGet				= new HttpGet(url);
		mhttpResponse			= mhttpClient.execute(mhttpGet);
		StatusLine statusCode	= mhttpResponse.getStatusLine();
		if (statusCode.getStatusCode() == HTTP_RESPONSE_OK) {
			HttpEntity	entity		= mhttpResponse.getEntity();
			InputStream is			= entity.getContent();
//		EntityUtils.consume(entity);
			return is;
		} else {
			// Failed to retrieve correctly
			Log.d(this.getClass().getSimpleName(), "Failed to do GET ("+url+")");
			return null;
		}
	}

	/**
	 * Gets the Drawable from network resource specified
	 * @param imageUrl
	 * @return Drawable | null
	 */
	public Drawable fetchDrawable(String imageUrl) {
		Log.d(this.getClass().getSimpleName(), "Fetching...: " + imageUrl);
		
			try {
				InputStream	is			= doGet(imageUrl);
				Drawable	drawable	= Drawable.createFromStream(is, "test_");
				if (drawable != null) {
	                return drawable;
	            } else {
	              Log.w(this.getClass().getSimpleName(), "could not get image");
	            }
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return null;
	}

}
