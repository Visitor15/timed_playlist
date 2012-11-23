package com.flabs.mobile.music.controller;

import java.util.ArrayList;

import com.flabs.mobile.core.music_object.MusicInfo;

public interface IMusicListTask {

	public void onMusicTaskFinished(ArrayList<MusicInfo> mList);
	
	public void handleOnProgressUpdate(double progress);
	
}
