package com.julie.apps.mytwitter;

import com.loopj.android.http.AsyncHttpResponseHandler;

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

public class ComposeActivity extends Activity {

	EditText etTweet;
	ImageButton btnTweet;
	Button btnCancel;
	ImageView ivComposeProfile;
	TextView tvComposeName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		setupViews();
	}

	public void onTweet(View view){
		final String twt = etTweet.getText().toString();
		
		TwitterClientApp.getRestClient().postTweet(twt, new AsyncHttpResponseHandler() {
		    public void onSuccess(String jsonTweetString) {
		       
				Intent i = new Intent();
				i.putExtra("status", twt);
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
