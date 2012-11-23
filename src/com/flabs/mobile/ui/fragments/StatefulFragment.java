package com.flabs.mobile.ui.fragments;

import com.flabs.mobile.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public abstract class StatefulFragment extends Fragment implements IStatefulFragment {

	public RelativeLayout mContainer;
	
	public StatefulFragment() {
		super();
	}
	
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// load the state
		if(!getFromBundle(savedInstanceState)) {
			getFromBundle(getArguments());
		}
	}
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.base_fragment_layout, null);
		
		mContainer = (RelativeLayout) v.findViewById(R.id.rl_container_with_margin);
		mContainer.setVisibility(View.VISIBLE);
		
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public ViewGroup getContainerView() {
		return mContainer;
	}

	@Override
	public void onSaveInstanceState(final Bundle outState) {
		this.addToBundle(outState);
		super.onSaveInstanceState(outState);
	}

	@Override
	public abstract Bundle addToBundle(Bundle bundle);

	@Override
	public abstract boolean getFromBundle(Bundle bundle);

}
