package com.julie.apps.mytwitter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.julie.apps.mytwitter.fragments.FriendProfileFragment;
import com.julie.apps.mytwitter.fragments.FriendTweetFragment;
import com.julie.apps.mytwitter.fragments.ProfileFragment;
import com.julie.apps.mytwitter.fragments.UserTweetFragment;


public class ProfileActivity extends FragmentActivity {
	String screenName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		String tag = getIntent().getStringExtra("tag");
		screenName = getIntent().getStringExtra("screen_name");
		
		android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction ftransaction = manager.beginTransaction();
		
		//send screenName to fragments
		Fragment fragment = FriendTweetFragment.newInstance(screenName);
		Fragment profileFragment = FriendProfileFragment.newInstance(screenName);
		
		if(tag.equals("user")){
			ftransaction.replace(R.id.listContainer, new UserTweetFragment());
			ftransaction.replace(R.id.profileContainer, new ProfileFragment());
		} else if (tag.equals("friend")) {
			ftransaction.replace(R.id.listContainer, fragment);
			ftransaction.replace(R.id.profileContainer, profileFragment);
		}
		
		ftransaction.commit();
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
