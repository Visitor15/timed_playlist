package com.flabs.mobile.music.controller;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import com.flabs.mobile.core.music_object.BaseMusicObject;
import com.flabs.mobile.core.music_object.MusicInfo;
import com.flabs.mobile.util.Utils;

public class MusicListTask extends AsyncTask<String[], Double, ArrayList<MusicInfo>>{

	public static final String TAG = "MusicListTask";

	public static final Uri GENRES_URI = MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI;
	public static final String GENRE_ID = MediaStore.Audio.Genres._ID;
    public static final String GENRE_NAME = MediaStore.Audio.Genres.NAME;
    public static final String[] GENRE_PROJECTION = { MediaStore.Audio.Media.DISPLAY_NAME };
	
	private Context mContext;
	private String[] mProjection;
	private HashMap<String, ArrayList<String>> genreToSongListMap;
	private ArrayList<MusicInfo> mMusicInfoList;
	private ArrayList<Uri> queryUris;
	private IMusicListTask mCallback;
	private MusicInfo tempInfo;

	public MusicListTask(Context mContext, ArrayList<Uri> queryUris, IMusicListTask callback) {
		this.mContext = mContext;
		this.mCallback = callback;
		this.queryUris = queryUris;
	}

	@Override
	protected void onPreExecute() {
		Log.d(TAG, "NCC - Starting MusicListTask");
		mMusicInfoList = new ArrayList<MusicInfo>();
		genreToSongListMap = new HashMap<String, ArrayList<String>>();
	}

	@Override
	protected ArrayList<MusicInfo> doInBackground(String[]... params) {
		mProjection = params[0];
		mMusicInfoList = createMusicInfoList(runSearch(mContext, queryUris));
		return mMusicInfoList;
	}

	@Override
	protected void onProgressUpdate(Double... progress) {
		mCallback.handleOnProgressUpdate(progress[0]);
	}

	@Override
	protected void onPostExecute(ArrayList<MusicInfo> result) {
		mCallback.onMusicTaskFinished(returnResult(result));

		mContext = null;
		tempInfo = null;
		mCallback = null;
	}

	private ArrayList<Cursor> runSearch(Context c, ArrayList<Uri> queryUriList) {
		ArrayList<Cursor> cursorList = new ArrayList<Cursor>();

		for(Uri mUri : queryUriList) {
			cursorList.add(c.getContentResolver().query(mUri, mProjection, null, null, null));
		}

		return cursorList;
	}

	private ArrayList<MusicInfo> createMusicInfoList(ArrayList<Cursor> cursorList) {
		int musicInfoProgress = 0;
		double totalCount;
		createGenreListMap(mContext);
		for(Cursor c : cursorList) {
			if(c != null) {
				musicInfoProgress = 0;
				totalCount = c.getCount();
				c.moveToFirst();
				while(c.moveToNext()) {
					String path = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA));
					String displayName = c.getString(c.getColumnIndex(MediaStore.Audio.Media.TITLE));
					String artist = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST));
					String album = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ALBUM));
					String duration = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DURATION));
					String trackNum = c.getString(c.getColumnIndex(MediaStore.Audio.Media.TRACK));
					String fileName = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
					Uri uri = Utils.createUriFromStringPath(path);
					
					tempInfo = createMusicInfo(uri, fileName);
					tempInfo.setmDisplayName(displayName);
					tempInfo.setmAlbum(album);
					tempInfo.setmArtist(artist);
					tempInfo.setmFileName(fileName);
					tempInfo.setmStringDuration(duration);
					tempInfo.setUri(uri);

					//Some audio files don't have a track number. So this can return a null at times.
					if(trackNum != null) {
						tempInfo.setmTrackNumber(Integer.valueOf(trackNum));
					}
					
					tempInfo.setmGenre(setGenre(tempInfo.getmFileName()));

					mMusicInfoList.add(tempInfo);
					publishProgress((++musicInfoProgress / totalCount) * 100);
				}
			}
			c.close();
		}

		return mMusicInfoList;
	}
	
	private String setGenre(String songTitle) {
		String mGenre = "";
		boolean exitLoop = false;
		for(String genre : genreToSongListMap.keySet()) {
			for(String mTitle : genreToSongListMap.get(genre)) {
				if(songTitle.equalsIgnoreCase(mTitle)) {
					mGenre = genre;
					exitLoop = true;
					break;
				}
			}
			if(exitLoop) {
				break;
			}
			else {
				continue;
			}
		}
		
		return mGenre;
	}

	private void createGenreListMap(Context c) throws NullPointerException {
		Cursor tempCursor;
		Uri mUri;
		HashMap<String, String> genreIdMap = new HashMap<String, String>();
		Cursor mCursor = c.getContentResolver().query(GENRES_URI, new String[] { GENRE_ID, GENRE_NAME }, null, null, null);
		ArrayList<String> songTitleList;
		
		for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
			songTitleList = new ArrayList<String>();
            genreIdMap.put(mCursor.getString(0), mCursor.getString(1));
            
            int indexCount = mCursor.getColumnIndex(GENRE_NAME); 
            String genreName = mCursor.getString(indexCount);
            Log.d(TAG, "NCC - Tag-Genre name " + genreName);
            
            mUri = MediaStore.Audio.Genres.Members.getContentUri("external", Long.parseLong(mCursor.getString(0)));
            
            genreToSongListMap.put(genreName, songTitleList);
            
            tempCursor = c.getContentResolver().query(mUri, GENRE_PROJECTION, null, null, null);
            for (tempCursor.moveToFirst(); !tempCursor.isAfterLast(); tempCursor.moveToNext()) {
            	int index = tempCursor.getColumnIndexOrThrow(GENRE_PROJECTION[0]);
            	String songTitle = tempCursor.getString(index);
            	genreToSongListMap.get(genreName).add(songTitle);
            }
        }
	}

	private ArrayList<MusicInfo> returnResult(ArrayList<MusicInfo> result) {
		ArrayList<MusicInfo> localList = new ArrayList<MusicInfo>();

		for(MusicInfo obj : result) {
			localList.add(obj);
		}

		return localList;
	}

	private ArrayList<MusicInfo> getList() {
		return mMusicInfoList;
	}

	private MusicInfo createMusicInfo(Uri mUri, String mTitle) {
		return new MusicInfo(mUri, mTitle);
	}

}
