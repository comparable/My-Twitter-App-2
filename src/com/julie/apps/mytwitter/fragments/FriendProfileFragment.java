package com.julie.apps.mytwitter.fragments;

import org.json.JSONObject;

import com.julie.apps.mytwitter.R;
import com.julie.apps.mytwitter.TwitterClientApp;
import com.julie.apps.mytwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendProfileFragment extends Fragment {
String screenName;
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState ){
		return inf.inflate(R.layout.fragment_profile, parent, false);		
	}

	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		Bundle b = getActivity().getIntent().getExtras();
		screenName = b.getString("screen_name");
		loadProfileInfo();
	}
	
	public void loadProfileInfo(){
		TwitterClientApp.getRestClient().getFriendInfo(new JsonHttpResponseHandler(){
			public void onSuccess(JSONObject object){
				User u = User.fromJson(object);
				getActivity().getActionBar().setTitle("@"+u.getScreenName());
				populateProfileHeader(u);	
			}
		},screenName);
	}

	protected void populateProfileHeader(User user) {
		TextView tvName = (TextView) getActivity().findViewById(R.id.tvName);
		TextView tvTagline = (TextView) getActivity().findViewById(R.id.tvTagline);
		TextView tvFollower = (TextView) getActivity().findViewById(R.id.tvFollower);
		TextView tvFollowing = (TextView) getActivity().findViewById(R.id.tvFollowing);
		ImageView ivProfileImage = (ImageView) getActivity().findViewById(R.id.ivProfileImage);
		TextView tvTweets = (TextView) getActivity().findViewById(R.id.tvTweets);
		tvName.setText(user.getName());
		tvTagline.setText(user.getTagline());
		tvTweets.setText(user.getNumOfTweets()+" Tweets");
		tvFollower.setText(user.getFollowersCount()+" Followers");
		tvFollowing.setText(user.getFriendsCount()+" Following");
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(),ivProfileImage);
	}

	
}
