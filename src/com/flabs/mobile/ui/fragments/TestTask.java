package com.flabs.mobile.ui.fragments;

import java.util.Random;

import android.os.AsyncTask;
import android.widget.TextView;

class TestTask extends AsyncTask<Void, Void, Boolean> {

		private static final long WAIT_TIME = 300;
		
		private long mDuration;
		private long mStartTime;
		private long mCurrentTime;
		private int mRandomNumber;
		private int mRandomNumber2;
		
		private TextView mTarget;
		
		private Random ran;
		
		public TestTask(long duration, TextView target) {
			mTarget = target;
			mDuration = duration;
		}
		
		@Override
		protected void onPreExecute() {
			mCurrentTime = 0;
			mStartTime = System.currentTimeMillis();
			ran = new Random();
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			
			while((mCurrentTime - mStartTime) < mDuration) {
				
				mRandomNumber = ran.nextInt(99);
				mRandomNumber2 = ran.nextInt(99);
				
				try {
					mTarget.post(new Runnable() {

						@Override
						public void run() {
							mTarget.setText(mRandomNumber + ":" + mRandomNumber2);
						}
						
					});
					Thread.sleep(WAIT_TIME);
					mCurrentTime = System.currentTimeMillis();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			
		}
	}
