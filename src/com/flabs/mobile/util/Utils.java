package com.flabs.mobile.util;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.flabs.mobile.music.controller.MusicListTask;

public class Utils {
	
	public static final String TAG = "Utils";

	public static Uri createUriFromStringPath(String path) {
		File mFile = new File(path);
		return  Uri.fromFile(mFile);
	}
	
	public static ArrayList<String> getAvailableGenreList(Context c) {
		ArrayList<String> genreList = new ArrayList<String>();
		
		Cursor mCursor = c.getContentResolver().query(MusicListTask.GENRES_URI, new String[] { MusicListTask.GENRE_ID, MusicListTask.GENRE_NAME }, null, null, null);
		
		for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
            
            int indexCount = mCursor.getColumnIndex(MusicListTask.GENRE_NAME); 
            String genreName = mCursor.getString(indexCount);
            
            genreList.add(genreName);
		}
		
		return genreList;
	}
}
