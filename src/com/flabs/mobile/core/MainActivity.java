package com.flabs.mobile.core;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flabs.mobile.R;
import com.flabs.mobile.core.music_object.BaseMusicObject;
import com.flabs.mobile.core.music_object.MusicInfo;
import com.flabs.mobile.music.controller.IMusicController;
import com.flabs.mobile.music.controller.MusicController;
import com.flabs.mobile.ui.fragments.BodyFragment;
import com.flabs.mobile.ui.fragments.FooterFragment;
import com.flabs.mobile.ui.fragments.HeaderFragment;
import com.flabs.mobile.ui.fragments.MusicListFragment;
import com.flabs.mobile.ui.fragments.MusicListFragment.MusicListFragmentCallback;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements IMusicController.ICallback, MusicListFragmentCallback {
	public static final String TAG = "MainActivity";
	
//	public static final String GENRE_LIST_TAG = "genre_list";
//	public static final String ARTIST_LIST_TAG = "artist_list";
//	public static final String ALBUM_LIST_TAG = "album_list";
//	public static final String SONG_LIST_TAG = "song_list";
	
	public static final String MUSIC_LIST_FRAG_TAG = "music_list_frag_tag";
	
	//UI Elements
	Button btnSearch;
	LinearLayout listContainer;

	private ArrayList<MusicInfo> masterList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
        
        setContentView(R.layout.main);
        
        
        
		listContainer = (LinearLayout) findViewById(R.id.ll_container);
		btnSearch = (Button) findViewById(R.id.btn_search);
		setSearchBtnListener(btnSearch);
		
		HeaderFragment mHeader = new HeaderFragment();
		BodyFragment mBody = new BodyFragment();
		FooterFragment mFooter = new FooterFragment();
		
		

	      getSupportFragmentManager().beginTransaction()
	          .add(R.id.ll_container, mHeader, TAG)
	          .add(R.id.ll_container, mBody, TAG)
	          .add(R.id.ll_container, mFooter, TAG)
	          .commitAllowingStateLoss();
	      
	      MusicController.getInstance().generateRandomListOfSpecifiedLength(getApplicationContext(), -1).setCallback(this);
	      
//	      FrameLayout secondaryContainer = (FrameLayout) findViewById(R.id.secondary_container);
//	      listContainer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
////	      ((ViewGroup) secondaryContainer.getParent()).removeView(secondaryContainer);
////	      listContainer.addView(secondaryContainer);
//	      secondaryContainer.setLayoutParams(new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
//	      secondaryContainer.setBackgroundResource(android.R.color.holo_blue_bright);
//	      secondaryContainer.setVisibility(View.VISIBLE);
	}
	
	private void setSearchBtnListener(Button btn) {
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				runSearch();
			}
			
		});
	}
	
	private void runSearch() {
		MusicController.getInstance().generateRandomListOfSpecifiedLength(getApplicationContext(), -1).setCallback(this);
	}

	private void printToList(ArrayList<MusicInfo> list) {
		for(MusicInfo item : list) {
			View v = getLayoutInflater().inflate(R.layout.music_item_list_layout, null);
			decorateView(v, item);
			listContainer.addView(v);
			listContainer.invalidate();
		}
	}

	private void decorateView(View v, MusicInfo item) {
		TextView mTitle;
		TextView mArtist;
		TextView mAlbum;
		TextView mGenre;
		
		mTitle = (TextView) v.findViewById(R.id.tv_display_title);
		mArtist = (TextView) v.findViewById(R.id.tv_artist);
		mAlbum = (TextView) v.findViewById(R.id.tv_album);
		mGenre = (TextView) v.findViewById(R.id.tv_genre);
		
		mTitle.setText(item.getmDisplayName());
		mArtist.setText(item.getmArtist());
		mAlbum.setText(item.getmAlbum());
		mGenre.setText(item.getmGenre());
	}

	@Override
	public void onMusicTaskFinished(ArrayList<? extends BaseMusicObject> list) {
		masterList = (ArrayList<MusicInfo>) list;
		ArrayList<MusicInfo> mList = new ArrayList<MusicInfo>();

		MusicListFragment musicListFrag = new MusicListFragment();
		mList = musicListFrag.getGenreList(masterList);
		musicListFrag.setMasterList(mList);
		musicListFrag.setCallback(this);
		musicListFrag.setCurrentCategory(MusicListFragment.CURRENT_CATEGORY.GENRE);
		
		getSupportFragmentManager().beginTransaction()
        .add(R.id.rl_base_container, musicListFrag, MUSIC_LIST_FRAG_TAG)
        .addToBackStack(MUSIC_LIST_FRAG_TAG)
        .commitAllowingStateLoss();
		
//		printToList(masterList);
	}

	@Override
	public void handleGenreListItemClicked(String genre) {
		ArrayList<MusicInfo> list = new ArrayList<MusicInfo>();

		MusicListFragment musicListFrag = new MusicListFragment();
		list = musicListFrag.getArtistForGenreList(masterList, genre);
		musicListFrag.setMasterList(list);
		musicListFrag.setCallback(this);
		musicListFrag.setCurrentCategory(MusicListFragment.CURRENT_CATEGORY.ARTIST);
		
		FragmentManager fragManager = getSupportFragmentManager();
		
		fragManager.beginTransaction().remove(fragManager.findFragmentByTag(MainActivity.MUSIC_LIST_FRAG_TAG)).add(R.id.rl_base_container, musicListFrag, MUSIC_LIST_FRAG_TAG).addToBackStack(MUSIC_LIST_FRAG_TAG).commit();
	}

	@Override
	public void handleArtistListItemClicked(String artist) {
		ArrayList<MusicInfo> list = new ArrayList<MusicInfo>();
		
		MusicListFragment musicListFrag = new MusicListFragment();
		list = musicListFrag.getAlbumForArtistList(masterList, artist);
		musicListFrag.setMasterList(list);
		musicListFrag.setCallback(this);
		musicListFrag.setCurrentCategory(MusicListFragment.CURRENT_CATEGORY.ALBUM);
		
		FragmentManager fragManager = getSupportFragmentManager();
		
		fragManager.beginTransaction().remove(fragManager.findFragmentByTag(MainActivity.MUSIC_LIST_FRAG_TAG)).add(R.id.rl_base_container, musicListFrag, MUSIC_LIST_FRAG_TAG).addToBackStack(MUSIC_LIST_FRAG_TAG).commit();
	}

	@Override
	public void handleAlbumListItemClicked(String album) {
ArrayList<MusicInfo> list = new ArrayList<MusicInfo>();
		
		MusicListFragment musicListFrag = new MusicListFragment();
		list = musicListFrag.getSongsForAlbumList(masterList, album);
		musicListFrag.setMasterList(list);
		musicListFrag.setCallback(this);
		musicListFrag.setCurrentCategory(MusicListFragment.CURRENT_CATEGORY.SONG);
		
		FragmentManager fragManager = getSupportFragmentManager();
		
		fragManager.beginTransaction().remove(fragManager.findFragmentByTag(MainActivity.MUSIC_LIST_FRAG_TAG)).add(R.id.rl_base_container, musicListFrag, MUSIC_LIST_FRAG_TAG).addToBackStack(MUSIC_LIST_FRAG_TAG).commit();
	}

	@Override
	public void handleSongsListItemClicked() {
		// TODO Auto-generated method stub
		
	}
	
	public static class GenerateMusicInfoList extends AsyncTask<ArrayList<? extends MusicInfo>, Integer, ArrayList<? extends MusicInfo>> {

		public static final String TAG = "GenerateMusicInfoList";
		
		private ArrayList<? extends MusicInfo> masterList;
		private ArrayList<MusicInfo> resultsList;
		
		private LIST_TYPE dataType;
		
		public enum LIST_TYPE {
			GENRE,
			ARTIST,
			ALBUM,
			SONG
		}
		
		public GenerateMusicInfoList(ArrayList<? extends MusicInfo> masterList, LIST_TYPE dataType) {
			this.masterList = masterList;
			this.dataType = dataType;
		}
		
		@Override
		protected void onPreExecute() {
			this.resultsList = new ArrayList<MusicInfo>();
		}
		
		@Override
		protected ArrayList<? extends MusicInfo> doInBackground(
				ArrayList<? extends MusicInfo>... params) {
			
			
			
			return resultsList;
		}
		
		@Override
		protected void onPostExecute(ArrayList<? extends MusicInfo> list) {
			
		}
		
	}
}
