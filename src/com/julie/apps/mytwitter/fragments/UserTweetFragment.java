package com.julie.apps.mytwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.widget.ProgressBar;

import com.julie.apps.mytwitter.TwitterClientApp;
import com.julie.apps.mytwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTweetFragment extends TweetsListFragment {
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		TwitterClientApp.getRestClient().getUserTimeline(new JsonHttpResponseHandler(){
			public void onSuccess(JSONArray jsonTweets) {
				 getAdapter().addAll(Tweet.fromJson(jsonTweets));
				 getPb().setVisibility(ProgressBar.INVISIBLE);
			}
		});	
	}

}
