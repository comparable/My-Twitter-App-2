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

import com.activeandroid.ActiveAndroid;
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
			public void onSuccess(JSONArray jsonTweets) {
				//if(jsonTweets == null){
				//tweets =	Tweet.recentItems();
				//Log.d("DEBUG","-----------loading-----------");
				//} else {
				tweets = Tweet.fromJson(jsonTweets);				
				onSave();
				//}
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
				//Log.d("DEBUG", "max_id "+max_id+" totalItemsCount "+totalItemsCount);				
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
			  Tweet t = (Tweet) data.getSerializableExtra("tweet");
			  tweetAdapter.insert(t,0);
			  tweetAdapter.notifyDataSetChanged();
		  }
		} 
	
	//persist recent tweets
	public void onSave(){
		ActiveAndroid.beginTransaction();
		try{
			for(int i = 0; i< tweets.size(); i++){
				Tweet tweet = new Tweet();
				tweet.user = tweets.get(i).getUser();
				tweet.body = tweets.get(i).getBody();
				tweet.id = tweets.get(i).getId();
				tweet.time = tweets.get(i).getTime();
				tweet.save();
			}
			ActiveAndroid.setTransactionSuccessful();
		} finally {
			ActiveAndroid.endTransaction();
		}
	}
	//Load saved tweets
	public void onSavedTweetsLoad() {
		if (!Tweet.recentItems().isEmpty()){
			//tweetAdapter.addAll(Tweet.recentItems());
			//lvTweets.setAdapter(tweetAdapter);
			//Log.d("DEBUG",tweetAdapter.getItem(5).getBody());
		}
		
	}
	
	public void onRefresh(MenuItem mi){
		TwitterClientApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				tweets = Tweet.fromJson(jsonTweets);
				tweetAdapter = new TweetsAdapter(TimelineActivity.this, tweets);
				lvTweets.setAdapter(tweetAdapter);
				onSave();
			}
		});
	}
}
