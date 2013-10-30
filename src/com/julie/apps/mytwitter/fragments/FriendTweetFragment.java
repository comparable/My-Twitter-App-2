package com.julie.apps.mytwitter.fragments;


import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.julie.apps.mytwitter.TwitterClientApp;
import com.julie.apps.mytwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class FriendTweetFragment extends TweetsListFragment {
String screenName;
		
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		Bundle b = getActivity().getIntent().getExtras();
		screenName = b.getString("screen_name");
		//Log.d("DEBUG", "FriendTweet "+screenName);
		TwitterClientApp.getRestClient().getFriendTimeline(new JsonHttpResponseHandler(){
			public void onSuccess(JSONArray jsonTweets) {
				 getAdapter().addAll(Tweet.fromJson(jsonTweets));
				 getPb().setVisibility(ProgressBar.INVISIBLE);				 
			}
		},screenName);
	}
		
		
}
