package com.julie.apps.mytwitter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.julie.apps.mytwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetsAdapter extends ArrayAdapter<Tweet>{
	ImageView ivProfile;
	public TweetsAdapter(Context context, List<Tweet> objects) {
		super(context, 0, objects);
		
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		View view = convertView;
		if(view == null){
			LayoutInflater inflator = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflator.inflate(R.layout.tweet_item,null);
		}
		
		Tweet tweet = getItem(position);
		
		ivProfile = (ImageView) view.findViewById(R.id.ivProfile); 
		TextView tvName = (TextView) view.findViewById(R.id.tvComposeName);
		TextView tvBody = (TextView) view.findViewById(R.id.tvBody);
		TextView tvId = (TextView) view.findViewById(R.id.tvId);
		TextView tvTime = (TextView) view.findViewById(R.id.tvTime);
		
		ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), ivProfile);
		ivProfile.setTag(tweet.getUser().getScreenName());
		tvName.setText("@"+tweet.getUser().getScreenName());
		tvId.setText(tweet.getUser().getName());
		tvBody.setText(tweet.getBody());
		String rtime = (String) DateUtils.getRelativeDateTimeString(
                getContext(), tweet.getLongTime(tweet.getTime()), 
                DateUtils.SECOND_IN_MILLIS, 
                DateUtils.WEEK_IN_MILLIS, 
                0 );
		tvTime.setText(rtime);
		
		ivProfile.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				String screenName = view.getTag().toString();
				//Log.d("DEBUG",screenName);
				Intent i = new Intent(getContext(), ProfileActivity.class);
				i.putExtra("screen_name",screenName);
				i.putExtra("tag", "friend");
				getContext().startActivity(i);
				
			}
			
		});
		
		
		return view;
	}

}
