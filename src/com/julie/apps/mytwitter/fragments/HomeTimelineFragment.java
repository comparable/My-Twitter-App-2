package com.julie.apps.mytwitter.fragments;

import org.json.JSONArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.julie.apps.mytwitter.EndlessScrollListener;
import com.julie.apps.mytwitter.TwitterClientApp;
import com.julie.apps.mytwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimelineFragment extends TweetsListFragment {

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		loadHomeTimeline();
		
		
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
	
	private void loadHomeTimeline() {
		TwitterClientApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler(){
			public void onSuccess(JSONArray jsonTweets) {
				 getAdapter().addAll(Tweet.fromJson(jsonTweets));
			}
		});	
		
	}
}
