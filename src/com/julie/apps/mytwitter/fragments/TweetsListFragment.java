package com.julie.apps.mytwitter.fragments;

import java.util.ArrayList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.julie.apps.mytwitter.R;
import com.julie.apps.mytwitter.TweetsAdapter;
import com.julie.apps.mytwitter.models.Tweet;

public class TweetsListFragment extends Fragment {
	private TweetsAdapter tweetAdapter;
	private ListView lvTweets;
	private ArrayList<Tweet> tweets;
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState ){
		return inf.inflate(R.layout.fragment_tweets_list, parent, false);		
	}

	//you can get the access to activity when activity is created by onActivityCreated()
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		tweets = new ArrayList<Tweet>();						
		tweetAdapter = new TweetsAdapter(getActivity(), tweets);
		lvTweets = (ListView)getActivity().findViewById(R.id.lvTweets);
		lvTweets.setAdapter(tweetAdapter);	
	}
	
	public TweetsAdapter getAdapter(){
		return tweetAdapter;
	}

	public ListView getListView(){
		return lvTweets;
	}
	
	public  ArrayList<Tweet> getTweetArray(){
		return tweets;
	}
}
