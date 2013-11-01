package com.julie.apps.mytwitter.fragments;


import org.json.JSONArray;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.julie.apps.mytwitter.TwitterClientApp;
import com.julie.apps.mytwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class FriendTweetFragment extends TweetsListFragment {
String screenName;
		
public static FriendTweetFragment newInstance(String screenName) {
	FriendTweetFragment ftf = new FriendTweetFragment();
    Bundle b = new Bundle();
    b.putString("screenName", screenName);
    ftf.setArguments(b);
    return ftf;
  }

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Get back arguments from Profile activity	
    screenName = getArguments().getString("screenName", "");	
}

	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		//Log.d("DEBUG", "FriendTweet "+screenName);
		TwitterClientApp.getRestClient().getFriendTimeline(new JsonHttpResponseHandler(){
			public void onSuccess(JSONArray jsonTweets) {
				 getAdapter().addAll(Tweet.fromJson(jsonTweets));
				 getPb().setVisibility(ProgressBar.INVISIBLE);				 
			}
		},screenName);
	}
		
	
	
		
}
