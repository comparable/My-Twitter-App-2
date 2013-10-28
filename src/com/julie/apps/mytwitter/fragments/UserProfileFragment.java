package com.julie.apps.mytwitter.fragments;
import org.json.JSONObject;

import android.os.Bundle;

import com.julie.apps.mytwitter.TwitterClientApp;
import com.julie.apps.mytwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserProfileFragment extends ProfileFragment{

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		TwitterClientApp.getRestClient().getMyInfo(new JsonHttpResponseHandler(){
			public void onSuccess(JSONObject object){
						}
		});
	}
}
