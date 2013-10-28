package com.julie.apps.mytwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;

import com.julie.apps.mytwitter.EndlessScrollListener;
import com.julie.apps.mytwitter.TwitterClientApp;
import com.julie.apps.mytwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsFragment extends TweetsListFragment {

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		loadMention();

	}

	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		getListView().setOnScrollListener(new EndlessScrollListener(){
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				long max_id = getTweetArray().get(totalItemsCount-1).getId();
				TwitterClientApp.getRestClient().getOlderTweet(new JsonHttpResponseHandler(){
					@Override
					public void onSuccess(JSONArray jsonTweets) {
						getAdapter().addAll(Tweet.fromJson(jsonTweets));
					}
				}, max_id);					
				//Log.d("DEBUG", "max_id "+max_id+" totalItemsCount "+totalItemsCount);				
			}
			
		});
	}
	
	private void loadMention() {
		TwitterClientApp.getRestClient().getMentions(new JsonHttpResponseHandler(){
			public void onSuccess(JSONArray jsonTweets){
				getAdapter().addAll(Tweet.fromJson(jsonTweets));
			}
		});
		
	}
}