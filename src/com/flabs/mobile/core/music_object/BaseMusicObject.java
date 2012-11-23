package com.flabs.mobile.core.music_object;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public abstract class BaseMusicObject implements IBaseMusicObject, Parcelable {

	private Uri mUri;
	private String name;

	public BaseMusicObject(Uri mUri, String name) {
		this.mUri = mUri;
		this.name = name;
	}

	@Override
	public Uri getUri() {
		return mUri;
	}
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setUri(Uri mUri) {
		this.mUri = mUri;
	}
	
	@Override
	public void setName(String mName) {
		this.name = mName;
	}

	public BaseMusicObject(final Parcel in) {
		createFromBundle(in.readBundle());
	}
}
