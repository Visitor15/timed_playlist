package com.flabs.mobile.ui.fragments;

import java.util.Random;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flabs.mobile.R;

@SuppressLint("NewApi")
public class HeaderFragment extends StatefulFragment {

	static int width;
	static int height;
	
	View mView;
	View mContainer;
	RelativeLayout viewGroupContainer;
	TextView mTitleText;
	
	public HeaderFragment() {
		super();
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
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContainer = inflater.inflate(R.layout.base_fragment_layout, null);
		viewGroupContainer = (RelativeLayout) mContainer.findViewById(R.id.rl_container_without_margin);
		viewGroupContainer.setVisibility(View.VISIBLE);
		mView = inflater.inflate(R.layout.header_fragment_layout, (ViewGroup) viewGroupContainer);
		
		mTitleText = (TextView) mView.findViewById(R.id.tv_title);
		
		return mContainer;
	}
	
	@Override
	public void onResume() {
		super.onResume();
//		getContainerView().addView(mView);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		
		if(size.y > height) { height = size.y; }
		
		this.getView().setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height/3));
		
		TestTask mTask = new TestTask(300000, mTitleText);
		TestTask.execute(new Runnable() {

			@Override
			public void run() {
				
			}
			
		});
	}

	
//	private class TestTask extends AsyncTask<Void, Void, Boolean> {
//
//		private static final long WAIT_TIME = 300;
//		
//		private long mDuration;
//		private long mStartTime;
//		private long mCurrentTime;
//		private int mRandomNumber;
//		private int mRandomNumber2;
//		
//		private TextView mTarget;
//		
//		private Random ran;
//		
//		public TestTask(long duration, TextView target) {
//			mTarget = target;
//			mDuration = duration;
//		}
//		
//		@Override
//		protected void onPreExecute() {
//			mCurrentTime = 0;
//			mStartTime = System.currentTimeMillis();
//			ran = new Random();
//		}
//		
//		@Override
//		protected Boolean doInBackground(Void... params) {
//			
//			while((mCurrentTime - mStartTime) < mDuration) {
//				
//				mRandomNumber = ran.nextInt(99);
//				mRandomNumber2 = ran.nextInt(99);
//				
//				try {
//					mTarget.post(new Runnable() {
//
//						@Override
//						public void run() {
//							mTarget.setText(mRandomNumber + ":" + mRandomNumber2);
//						}
//						
//					});
//					Thread.sleep(WAIT_TIME);
//					mCurrentTime = System.currentTimeMillis();
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//			
//			return true;
//		}
//		
//		@Override
//		protected void onPostExecute(Boolean result) {
//			
//		}
//	}
}
