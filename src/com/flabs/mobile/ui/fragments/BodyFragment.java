package com.flabs.mobile.ui.fragments;

import java.util.ArrayList;
import java.util.Random;

import android.annotation.TargetApi;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flabs.mobile.R;

@TargetApi(13)
public class BodyFragment extends StatefulFragment {
	
	public static final String TAG = "BodyFragment";
	
	static int width;
	static int height;
	
	private RelativeLayout viewGroupContainer;
	private View mContainer;
	private View mView;
	private TextView textView1;
	private TextView textView2;
	private TextView textView3;
	private TextView textView4;
	
	private ArrayList<TextView> viewList = new ArrayList<TextView>();
	
	public BodyFragment() {
		super();
	}
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContainer = inflater.inflate(R.layout.base_fragment_layout, null);
		viewGroupContainer = (RelativeLayout) mContainer.findViewById(R.id.rl_container_with_margin);
		viewGroupContainer.setVisibility(View.VISIBLE);
		mView = inflater.inflate(R.layout.body_fragment_layout, (ViewGroup) viewGroupContainer);
		
		textView1 = (TextView) mView.findViewById(R.id.textView1);
		textView2 = (TextView) mView.findViewById(R.id.textView2);
		textView3 = (TextView) mView.findViewById(R.id.textView3);
		textView4 = (TextView) mView.findViewById(R.id.textView4);
		
		viewList.add(textView1);
		viewList.add(textView2);
		viewList.add(textView3);
		viewList.add(textView4);
		
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
		
		TestTask mTask = new TestTask(3000000, viewList);
		Log.d(TAG, "NCC - Executing task");
		mTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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

	private class TestTask extends AsyncTask<Void, Void, Boolean> {

		private static final long WAIT_TIME = 300;
		
		private long mDuration;
		private long mStartTime;
		private long mCurrentTime;
		private int mRandomNumber;
		private int mRandomNumber2;
		
		private ArrayList<TextView> mTargets;
		
		private Random ran;
		
		public TestTask(long duration, ArrayList<TextView> targets) {
			mTargets = targets;
			mDuration = duration;
		}
		
		@Override
		protected void onPreExecute() {
			Log.d(TAG, "NCC - onPreExecute HIT");
			mCurrentTime = 0;
			mStartTime = System.currentTimeMillis();
			ran = new Random();
		}
		

		
		@Override
		protected void onPostExecute(Boolean result) {
			
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			Log.d(TAG, "NCC - doInBackground HIT");
			return true;
		}
	}
	
}
