package com.flabs.mobile.ui.fragments;

import com.flabs.mobile.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

@SuppressLint("NewApi")
public class FooterFragment extends StatefulFragment {

	public static final String TAG = "FooterFragment";
	
	static int width;
	static int height;
	
	private RelativeLayout viewGroupContainer;
	private View mContainer;
	private View mView;
	
	public FooterFragment() {
		super();
	}
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContainer = inflater.inflate(R.layout.base_fragment_layout, null);
		viewGroupContainer = (RelativeLayout) mContainer.findViewById(R.id.rl_container_with_margin);
		viewGroupContainer.setVisibility(View.VISIBLE);
		mView = inflater.inflate(R.layout.footer_fragment_layout, (ViewGroup) viewGroupContainer);
		
		return mContainer;
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}

	@TargetApi(13)
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		
		if(size.y > height) { height = size.y; }
		
		this.getView().setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height/3));
	}
	

	@Override
	public Bundle addToBundle(Bundle bundle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getFromBundle(Bundle bundle) {
		// TODO Auto-generated method stub
		return false;
	}

}
