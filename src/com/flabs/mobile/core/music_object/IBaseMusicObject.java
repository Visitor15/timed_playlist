package com.flabs.mobile.core.music_object;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;

public interface IBaseMusicObject{
	
	public IBaseMusicObject createNewObjectFromParcelable(Parcel in);
	
	public Uri getUri();
	
	public String getName();
	
	public void setUri(Uri mUri);
	
	public void setName(String mName);
	
	public void createFromBundle(Bundle mBundle);
	
}
