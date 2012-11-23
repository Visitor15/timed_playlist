package com.flabs.mobile.core.music_object;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class MusicInfo extends BaseMusicObject {

	public static final String TAG = "MusicInfo";
	
	public enum DATA_TYPE {
		GENRE,
		ARTIST,
		ALBUM,
		SONG_TITLE
	}
	
	private String mDisplayName;
	private String mFileName;
	private String mArtist;
	private String mAlbum;
	private String mGenre;
	private String mStringDuration;
	private long mDuration;
	private int mTrackNumber;

	public MusicInfo(Parcel in) {
		super(in);
	}
	
	public MusicInfo(Uri mUri, String name) {
		super(mUri, name);
	}
	
//	public Uri getmUri() {
//		return mUri;
//	}
//
//	public void setmUri(Uri mUri) {
//		this.mUri = mUri;
//	}

	public String getmFileName() {
		return mFileName;
	}

	public void setmFileName(String mFileName) {
		this.mFileName = mFileName;
	}
	
	public String getmDisplayName() {
		return mDisplayName;
	}

	public void setmDisplayName(String mDisplayName) {
		this.mDisplayName = mDisplayName;
	}

	public String getmArtist() {
		return mArtist;
	}

	public void setmArtist(String mArtist) {
		this.mArtist = mArtist;
	}

	public String getmAlbum() {
		return mAlbum;
	}

	public void setmAlbum(String mAlbum) {
		this.mAlbum = mAlbum;
	}

	public String getmGenre() {
		return mGenre;
	}

	public void setmGenre(String mGenre) {
		this.mGenre = mGenre;
	}

	public long getmDuration() {
		return mDuration;
	}

	public void setmDuration(long mDuration) {
		this.mDuration = mDuration;
	}
	
	public String getmStringDuration() {
		return mStringDuration;
	}

	public void setmStringDuration(String mStringDuration) {
		this.mStringDuration = mStringDuration;
	}

	public int getmTrackNumber() {
		return mTrackNumber;
	}

	public void setmTrackNumber(int mTrackNumber) {
		this.mTrackNumber = mTrackNumber;
	}
	
	public String getDataInfo(DATA_TYPE type) {
		switch(type) {
		case GENRE: {
			return getmGenre();
		}
		case ARTIST: {
			return getmArtist();
		}
		case ALBUM: {
			return getmAlbum();
		}
		default: {
			return getmDisplayName();
		}
		}
	}
	
	@Override
	public MusicInfo createNewObjectFromParcelable(Parcel in) {
		return new MusicInfo(in);
	}

	@Override
	public void createFromBundle(Bundle mBundle) {
		
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
	}
	
	public static final Parcelable.Creator<BaseMusicObject> CREATOR = new Parcelable.Creator<BaseMusicObject>() {
		@Override
		public BaseMusicObject createFromParcel(final Parcel in) {
			
			return new MusicInfo(in);
		}

		@Override
		public BaseMusicObject[] newArray(final int size) {
			return new BaseMusicObject[size];
		}
	};

	@Override
	public String toString() {
		String mString = "Display Name: " + getmDisplayName() + " ";
		mString += "Filename: " + getmFileName() + " ";
		mString += "Artist: " + getmArtist() + " ";
		mString += "Album: " + getmAlbum() + " ";
		mString += "Genre: " + getmGenre() + " ";
		mString += "Track #: " + getmTrackNumber() + " ";
		mString += "Duration: " + getmStringDuration() + " ";
		mString += "Uri: " + getUri();
		
		return mString;
	}

}
