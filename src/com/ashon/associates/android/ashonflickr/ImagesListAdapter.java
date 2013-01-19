package com.ashon.associates.android.ashonflickr;


import java.util.ArrayList;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class ImagesListAdapter extends ArrayAdapter<FlickrImage> {
	

	protected final Activity context;
	protected final ArrayList<FlickrImage> imageList;
	
	// Static View Objects holder
	static class ViewHolder {
		public TextView		imageTitle;
		public TextView		imageDesc;
		public TextView		imageOwner;
		public ImageView	image;
	}
	
	public ImagesListAdapter(Activity context, int textViewResourceId, ArrayList<FlickrImage> varList) {
		super(context, textViewResourceId, varList);
		this.context		= context;
		this.imageList		= varList;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View	view	= convertView;
		// Check if we don't have 
		if (null == view) {
			LayoutInflater layoutInflater	= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view	= layoutInflater.inflate(R.layout.gallery_list, null);
			ViewHolder	viewHolder	= new ViewHolder();
			
			// Fill the params
			viewHolder.imageTitle	= (TextView) view.findViewById(R.id.item_title);
			viewHolder.imageDesc	= (TextView) view.findViewById(R.id.item_description);
			viewHolder.imageOwner	= (TextView) view.findViewById(R.id.item_owner);
			viewHolder.image		= (ImageView) view.findViewById(R.id.item_image);
			
			view.setTag(viewHolder);
		}
		FlickrImage flickrImage	= imageList.get(position);
		
		ViewHolder holder	= (ViewHolder) view.getTag();

		holder.imageTitle.setText(flickrImage.getImageTitle());
		holder.imageDesc.setText(flickrImage.getImageDesc());
		holder.imageOwner.setText(flickrImage.getImageOwner());
		holder.image.setImageDrawable(flickrImage.getDrawableImage());
		// Set new image size
		int newHeight	= flickrImage.getDrawableImage().getIntrinsicHeight();
		int oriheight	= flickrImage.getImageOriginalHeight();
		double	ratio	= newHeight / oriheight;
		int newWidth	= (int)ratio * flickrImage.getImageOriginalWidth();
//		holder.image.setLayoutParams(new LayoutParams.();
System.out.println("Called for "+ flickrImage.getImageOwner());
		return view;
//		return super.getView(position, convertView, parent);
	}

}
