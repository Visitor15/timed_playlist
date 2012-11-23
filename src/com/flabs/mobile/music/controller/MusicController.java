package com.flabs.mobile.music.controller;

import java.util.ArrayList;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.flabs.mobile.core.music_object.BaseMusicObject;
import com.flabs.mobile.core.music_object.MusicInfo;

public class MusicController implements IMusicController, IMusicListTask {

	public static final String TAG = "MusicController";
	public static final String[] projection = { MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.TRACK, MediaStore.Audio.Media.DATA };
	public static final String[] genreProjection = { MediaStore.Audio.Genres._ID, MediaStore.Audio.Genres.NAME };
//	private int mProgress;
//	private ArrayList<MusicInfo> musicList;
	
	private static MusicController mController;
	private ArrayList<MusicInfo> mLastListProcessed;
	private IMusicController.ICallback mCallback;
	
	private MusicController() {
		mController = this;
	}
	
	public static MusicController getInstance() {
		if(mController == null) {
			return new MusicController();
		}
		
		return mController;
	}
	
	public ArrayList<MusicInfo> returnList(ArrayList<MusicInfo> list) {
		mLastListProcessed = list;
		return list;
	}
	
	public ArrayList<MusicInfo> returnList() {
		return mLastListProcessed;
	}
	
	public ArrayList<Uri> getQueryUris() {
		ArrayList<Uri> uriQueryList = new ArrayList<Uri>();
		
		uriQueryList.add(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
//		uriQueryList.add(MediaStore.Audio.Media.INTERNAL_CONTENT_URI);
		
		return uriQueryList;
	}

	@Override
	public void onMusicTaskFinished(ArrayList<MusicInfo> mList) {
		Log.d(TAG, "NCC - Music task finished");
		
//		for(MusicInfo infoItem : mList) {
//			Log.d(TAG, "NCC - " + infoItem.toString());
//		}
		
		if(mCallback != null) {
			mCallback.onMusicTaskFinished(returnList(mList));
		}
		else {
			throw new NullPointerException("ERROR: Callback<IMusicController.ICallback> is NULL");
		}
	}

	@Override
	public void handleOnProgressUpdate(double progress) {
//		Log.d(TAG, "NCC - Got progress: " + progress);
	}

	@Override
	public MusicController generateRandomListOfSpecifiedLength(Context mContext, long mDuration) {
		MusicListTask listTask = new MusicListTask(mContext, getQueryUris() , this);
		listTask.execute(MusicController.projection);
		
		return getInstance();
	}

	public void setCallback(IMusicController.ICallback callback) {
		mCallback = callback;
	}
}
