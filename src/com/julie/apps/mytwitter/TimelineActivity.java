package com.julie.apps.mytwitter;

import org.json.JSONArray;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.julie.apps.mytwitter.fragments.HomeTimelineFragment;
import com.julie.apps.mytwitter.fragments.MentionsFragment;
import com.julie.apps.mytwitter.fragments.TweetsListFragment;
import com.julie.apps.mytwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends FragmentActivity implements TabListener {

	private int REQUEST_CODE = 100;
	private TweetsAdapter tweetAdapter;
	//private ListView lvTweets;
	//private ArrayList<Tweet> tweets;
	TweetsListFragment fragmentTweets;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);		
		setUpNavigationTabs();
	}

	public void setUpNavigationTabs(){
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		//showing title bar option
		actionBar.setDisplayShowTitleEnabled(true);
		Tab tabHome = actionBar.newTab().setText("Home")
				.setTag("HomeTimelineFragment")
		 		.setIcon(R.drawable.ic_home)
		 		.setTabListener(this);
		Tab tabMention = actionBar.newTab().setText("Mention")
				.setTag("MentionsFragment")
				.setIcon(R.drawable.ic_mentions)
				.setTabListener(this);
					
		
		actionBar.addTab(tabHome);
		actionBar.addTab(tabMention);
		actionBar.selectTab(tabHome);
		
		
	}
	
	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction ftransaction = manager.beginTransaction();
		if(tab.getTag().equals("HomeTimelineFragment")){
			ftransaction.replace(R.id.fragmentContainer, new HomeTimelineFragment());
		} else {
			ftransaction.replace(R.id.fragmentContainer, new MentionsFragment());
		}
		ftransaction.commit();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
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
	
	public void onProfileView(MenuItem mi){
		Intent i = new Intent(getApplicationContext(),ProfileActivity.class);
		i.putExtra("tag", "user");
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
		/**ActiveAndroid.beginTransaction();
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
		}**/
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
				//tweets = Tweet.fromJson(jsonTweets);
				//tweetAdapter = new TweetsAdapter(TimelineActivity.this, tweets);
				//lvTweets.setAdapter(tweetAdapter);
				//onSave();
			}
		});
	}


}
