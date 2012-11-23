package com.flabs.mobile.core.data;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

public class MusicAsyncTaskLoader extends CursorLoader {

	public MusicAsyncTaskLoader(Context context) {
		super(context);
		
	}
	
	public MusicAsyncTaskLoader(Context context, Uri uri, String[] projection,
			String selection, String[] selectionArgs, String sortOrder) {
		super(context, uri, projection, selection, selectionArgs, sortOrder);
		// TODO Auto-generated constructor stub
	}

	

}
