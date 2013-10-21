package com.julie.apps.mytwitter;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.julie.apps.mytwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {

	private int REQUEST_CODE = 100;
	private TweetsAdapter tweetAdapter;
	private ListView lvTweets;
	private ArrayList<Tweet> tweets;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		
		TwitterClientApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				//Log.d("DEBUG", jsobTweets.toString());
				tweets = Tweet.fromJson(jsonTweets); 			
				tweetAdapter = new TweetsAdapter(TimelineActivity.this, tweets);
				lvTweets.setAdapter(tweetAdapter);
			}
		});
		
		lvTweets.setOnScrollListener(new EndlessScrollListener(){
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				long max_id = tweets.get(totalItemsCount-1).getId();
				TwitterClientApp.getRestClient().getOlderTweet(new JsonHttpResponseHandler(){
					@Override
					public void onSuccess(JSONArray jsonTweets) {
						tweetAdapter.addAll(Tweet.fromJson(jsonTweets));
					}
				}, max_id);					
				Log.d("DEBUG", "max_id "+max_id+" totalItemsCount "+totalItemsCount);				
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	//expecting result from ComposeActivity
	public void onCompose(MenuItem mi){
		Intent i = new Intent(getApplicationContext(),ComposeActivity.class);
        startActivityForResult(i, REQUEST_CODE); 
	}
	
	
	//get data from ComposeActivity
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
		     //Toast.makeText(this, data.getExtras().getString("tweet"),Toast.LENGTH_SHORT).show();
			  
			  //tweetAdapter.notifyDataSetChanged();
		  }
		} 
	

}
