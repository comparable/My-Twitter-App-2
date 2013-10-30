package com.julie.apps.mytwitter;

import java.util.Calendar;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.julie.apps.mytwitter.models.Tweet;
import com.julie.apps.mytwitter.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeActivity extends Activity {

	EditText etTweet;
	ImageButton btnTweet;
	Button btnCancel;
	ImageView ivComposeProfile;
	TextView tvComposeName;
	User me;
	String body;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		me = new User();
		setupViews();
		onLoad();
	}
	
	public void onLoad(){
		TwitterClientApp.getRestClient().getMyInfo(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonMe) {
				me = User.fromJson(jsonMe); 
				tvComposeName.setText("@"+me.getName().toString());
				ImageLoader.getInstance().displayImage(me.getProfileImageUrl(), ivComposeProfile);
			}
		});
	}
	
	
	public void onTweet(View view){
		
		body = etTweet.getText().toString();
		TwitterClientApp.getRestClient().postTweet(body, new AsyncHttpResponseHandler() {
			public void onSuccess(String jsonTweetString) {
				String timeStamp = Calendar.getInstance().getTime().toString();	
		        Tweet twt = new Tweet(me, body, timeStamp);
				Intent i = new Intent();
				i.putExtra("tweet", twt);
				setResult(RESULT_OK, i);
				finish();
		    }

		    public void onFailure(Exception e) {
		        Log.d("DEBUG", "Error: " + e.toString());
		        Toast.makeText(ComposeActivity.this, "Posting Error! ", Toast.LENGTH_SHORT).show();
		        e.printStackTrace();
		    }
		});
		
	}
	
	public void onCancel(View view){
		finish();
	}
	
	public void setupViews(){
		etTweet = (EditText) findViewById(R.id.etTweet);
		btnTweet = (ImageButton) findViewById(R.id.btnTweet); 
		btnCancel = (Button) findViewById(R.id.btnCancel); 
		ivComposeProfile = (ImageView) findViewById(R.id.ivComposeProfile); 
		tvComposeName = (TextView) findViewById(R.id.tvComposeName); 
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}

}
