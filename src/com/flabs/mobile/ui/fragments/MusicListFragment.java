package com.flabs.mobile.ui.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.flabs.mobile.R;
import com.flabs.mobile.core.music_object.MusicInfo;

public class MusicListFragment extends ListFragment {

	public static final String TAG = "MusicListFragment";

	public static final String titleId = "tv_display_title";
	
	//UI Elements
	private TextView mTitleText;

	private ArrayList<MusicInfo> masterList;
	private ArrayList<MusicInfo> currentlyDisplayedList;
	
	//May want to change this to a list of callbacks.
	private MusicListFragmentCallback mCallBack;
	
	private CURRENT_CATEGORY mCurrentCategory;
	
	public enum CURRENT_CATEGORY {
		GENRE,
		ARTIST,
		ALBUM,
		SONG
	}

	public MusicListFragment() {
		super();
	}

	public MusicListFragment(ArrayList<MusicInfo> list, MusicListFragment.CURRENT_CATEGORY dataToShow, MusicListFragmentCallback callback) {
		this.mCallBack = callback;
		this.masterList = list;
		this.mCurrentCategory = dataToShow;
	}
	
	public void setMasterList(ArrayList<MusicInfo> list) {
		this.masterList = list;
	}
	
	public void setCallback(MusicListFragmentCallback callback) {
		this.mCallBack = callback;
	}

	public void setCurrentCategory(CURRENT_CATEGORY category) {
		this.mCurrentCategory = category;
	}

	public ArrayList<MusicInfo> getGenreList(ArrayList<MusicInfo> list) {
		ArrayList<MusicInfo> genreList = new ArrayList<MusicInfo>();
		ArrayList<String> tempGenreList = new ArrayList<String>();
		
		String mGenre;

		for (MusicInfo ob : list) {
			mGenre = ob.getmGenre();
			if(!tempGenreList.contains(mGenre) && mGenre.trim().length() > 0) {
				tempGenreList.add(mGenre);
				genreList.add(ob);
			}
		}

		mCurrentCategory = CURRENT_CATEGORY.GENRE;
		
		return genreList;
	}

	public ArrayList<MusicInfo> getArtistList(ArrayList<MusicInfo> list) {
		ArrayList<MusicInfo> artistList = new ArrayList<MusicInfo>();
		ArrayList<String> tempArtistList = new ArrayList<String>();
		
		String mArtist;

		for (MusicInfo ob : list) {
			mArtist = ob.getmArtist();
			if(!tempArtistList.contains(mArtist) && mArtist.trim().length() > 0) {
				Log.d(TAG, "NCC - ADDING ARTIST: " + mArtist);
				tempArtistList.add(mArtist);
				artistList.add(ob);
			}
		}
		
		mCurrentCategory = CURRENT_CATEGORY.ARTIST;

		return artistList;
	}

	public ArrayList<MusicInfo> getAlbumList(ArrayList<MusicInfo> list) {
		ArrayList<MusicInfo> albumList = new ArrayList<MusicInfo>();
		ArrayList<String> tempAlbumList = new ArrayList<String>();
		
		String mAlbum;

		for (MusicInfo ob : list) {
			mAlbum = ob.getmAlbum();
			if(!tempAlbumList.contains(mAlbum) && mAlbum.trim().length() > 0) {
				Log.d(TAG, "NCC - ADDING ALBUM: " + mAlbum);
				tempAlbumList.add(mAlbum);
				albumList.add(ob);
			}
		}
		
		mCurrentCategory = CURRENT_CATEGORY.ALBUM;

		return albumList;
	}
	
	public ArrayList<MusicInfo> getAlbumForArtistList(ArrayList<MusicInfo> list, String artist) {
		ArrayList<MusicInfo> albumList = new ArrayList<MusicInfo>();
		ArrayList<String> tempAlbumList = new ArrayList<String>();
		
		String mArtist = artist;
		String mAlbum;

		for (MusicInfo ob : list) {
			mAlbum = ob.getmAlbum();
			if(!tempAlbumList.contains(mAlbum) && 
					ob.getmArtist().equalsIgnoreCase(mArtist) &&
					mAlbum.trim().length() > 0) {
				
				Log.d(TAG, "NCC - ADDING ALBUM: " + mAlbum);
				tempAlbumList.add(mAlbum);
				albumList.add(ob);
			}
		}
		
		mCurrentCategory = CURRENT_CATEGORY.ALBUM;

		return albumList;
	}
	
	public ArrayList<MusicInfo> getArtistForGenreList(ArrayList<MusicInfo> list, String genre) {
		ArrayList<MusicInfo> artistList = new ArrayList<MusicInfo>();
		ArrayList<String> tempArtistList = new ArrayList<String>();
		
		String mGenre = genre;
		String mArtist;

		for (MusicInfo ob : list) {
			mArtist = ob.getmArtist();
			if(!tempArtistList.contains(mArtist) && 
					ob.getmGenre().equalsIgnoreCase(mGenre) &&
					mArtist.trim().length() > 0) {
				
				Log.d(TAG, "NCC - ADDING ARTIST: " + mArtist);
				tempArtistList.add(mArtist);
				artistList.add(ob);
			}
		}
		
		mCurrentCategory = CURRENT_CATEGORY.ARTIST;

		return artistList;
	}
	
	public ArrayList<MusicInfo> getSongsForAlbumList(ArrayList<MusicInfo> list, String album) {
		ArrayList<MusicInfo> songsList = new ArrayList<MusicInfo>();
		ArrayList<String> tempSongsList = new ArrayList<String>();
		
		String mAlbum = album;
		String mSong;

		for (MusicInfo ob : list) {
			mSong = ob.getmDisplayName();
			if(!tempSongsList.contains(mSong) && 
					ob.getmAlbum().equalsIgnoreCase(mAlbum) &&
					mSong.trim().length() > 0) {
				
				Log.d(TAG, "NCC - ADDING SONG: " + mSong);
				tempSongsList.add(mSong);
				songsList.add(ob);
			}
		}
		
		mCurrentCategory = CURRENT_CATEGORY.ARTIST;

		return songsList;
	}

	public ArrayList<String> getSongList(ArrayList<MusicInfo> list) {
		ArrayList<String> songList = new ArrayList<String>();

		for (MusicInfo ob : list) {
			songList.add(ob.getmDisplayName());
		}

		mCurrentCategory = CURRENT_CATEGORY.SONG;
		
		return songList;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public void onStart() {
		super.onStart();
		currentlyDisplayedList = masterList;
		List<Map<String, Integer>> data = generateSingleLineListData(currentlyDisplayedList,
				mCurrentCategory);
		
		SimpleAdapter mAdapter = new SimpleAdapter(getActivity().getApplicationContext(), (List<? extends Map<String, ?>>) data, R.layout.music_item_single_line_layout, new String[] {
			"iv_left_side_icon", "tv_text", "iv_right_side_icon" }, new int[] { R.id.iv_left_side_icon, R.id.tv_text,
					R.id.iv_right_side_icon });

		getListView().setAdapter(mAdapter);
		getListView().requestFocus();
		
		mTitleText.setText(mCurrentCategory.name());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.music_list_fragment_base_layout, null);

		mTitleText = (TextView) v.findViewById(R.id.tv_title);
		
		return v;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		MusicInfo info = (MusicInfo) currentlyDisplayedList.get(position);
		switch(mCurrentCategory) {
		case GENRE: {
			Log.d(TAG, "NCC - CLICKED: " + info.getmGenre());
			mCallBack.handleGenreListItemClicked(info.getmGenre());
			break;
		}
		case ARTIST: {
			Log.d(TAG, "NCC - CLICKED: " + info.getmArtist());
			mCallBack.handleArtistListItemClicked(info.getmArtist());
			break;
		}
		case ALBUM: {
			Log.d(TAG, "NCC - CLICKED: " + info.getmAlbum());
			mCallBack.handleAlbumListItemClicked(info.getmAlbum());
			break;
		}
		case SONG: {
			Log.d(TAG, "NCC - CLICKED: " + info.getmDisplayName());
			mCallBack.handleSongsListItemClicked();
			break;
		}
		}
		
	}

	public List<Map> generateSongListData(ArrayList<MusicInfo> list) {
		List<Map> listData = new ArrayList<Map>();
		Map map = new HashMap();

		for (MusicInfo obj : list) {

		}

		return listData;
	}

	public List<Map<String, Integer>> generateSingleLineListData(ArrayList<MusicInfo> list, CURRENT_CATEGORY type) {
		List<Map<String, Integer>> listData = new ArrayList<Map<String, Integer>>();
		
		Map map;
		for (MusicInfo obj : list) {
			map = new HashMap();
			map.put("iv_left_side_icon", R.drawable.ic_launcher);
			
			switch(type) {
			case GENRE: {
				map.put("tv_text", obj.getmGenre());
				break;
			}
			case ARTIST: {
				map.put("tv_text", obj.getmArtist());
				break;
			}
			case ALBUM: {
				map.put("tv_text", obj.getmAlbum());
				break;
			}
			case SONG: {
				map.put("tv_text", obj.getmDisplayName());
				break;
			}
			default: {
				map.put("tv_text", obj.toString());
			}
			}
			map.put("iv_right_side_icon", R.drawable.right_arrow_icon);
			
			listData.add(map);
		}

		return listData;
	}
	
	public interface MusicListFragmentCallback {
		public void handleGenreListItemClicked(String genre);
		public void handleArtistListItemClicked(String artist);
		public void handleAlbumListItemClicked(String album);
		public void handleSongsListItemClicked();
	}


}
