package com.flabs.mobile.music.controller;

import java.util.ArrayList;

import android.content.Context;

import com.flabs.mobile.core.music_object.BaseMusicObject;

public interface IMusicController {

	MusicController generateRandomListOfSpecifiedLength(Context mContext, long mDuration);
	
	public interface ICallback {
		
		void onMusicTaskFinished(ArrayList<? extends BaseMusicObject> list);
	}
}