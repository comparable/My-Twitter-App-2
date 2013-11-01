package com.julie.apps.mytwitter.fragments;

import org.json.JSONObject;

import com.julie.apps.mytwitter.R;
import com.julie.apps.mytwitter.TwitterClientApp;
import com.julie.apps.mytwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
public class FriendProfileFragment extends Fragment {
String screenName;
View v;

	public static FriendProfileFragment newInstance(String screenName) {
		FriendProfileFragment ftf = new FriendProfileFragment();
	    Bundle b = new Bundle();
	    b.putString("screenName", screenName);
	    ftf.setArguments(b);
	    return ftf;
	}
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    // Get back arguments from Profile activity	
	    screenName = getArguments().getString("screenName", "");	
	}

	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState ){
		v = inf.inflate(R.layout.fragment_profile, parent, false);	
		return v;
	}

	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
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
		TextView tvName = (TextView) v.findViewById(R.id.tvName);
		TextView tvTagline = (TextView) v.findViewById(R.id.tvTagline);
		TextView tvFollower = (TextView) v.findViewById(R.id.tvFollower);
		TextView tvFollowing = (TextView) v.findViewById(R.id.tvFollowing);
		ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
		TextView tvTweets = (TextView) v.findViewById(R.id.tvTweets);
		tvName.setText(user.getName());
		tvTagline.setText(user.getTagline());
		tvTweets.setText(user.getNumOfTweets()+" Tweets");
		tvFollower.setText(user.getFollowersCount()+" Followers");
		tvFollowing.setText(user.getFriendsCount()+" Following");
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(),ivProfileImage);
	}



	
}
