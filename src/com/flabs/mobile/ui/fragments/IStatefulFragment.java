package com.flabs.mobile.ui.fragments;

import android.os.Bundle;
import android.view.ViewGroup;

public interface IStatefulFragment {
	
	public ViewGroup getContainerView();
	
	public abstract void onActivityCreated (Bundle savedInstanceState);

	public abstract Bundle addToBundle(Bundle bundle);

	public abstract boolean getFromBundle(Bundle bundle);
}
