package com.julie.apps.mytwitter;

import java.util.List;
import java.util.TimeZone;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.julie.apps.mytwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetsAdapter extends ArrayAdapter<Tweet>{

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
		
		ImageView ivProfile = (ImageView) view.findViewById(R.id.ivProfile); 
		TextView tvName = (TextView) view.findViewById(R.id.tvComposeName);
		TextView tvBody = (TextView) view.findViewById(R.id.tvBody);
		TextView tvId = (TextView) view.findViewById(R.id.tvId);
		TextView tvTime = (TextView) view.findViewById(R.id.tvTime);
		
		ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), ivProfile);
		tvName.setText("@"+tweet.getUser().getScreenName());
		tvId.setText(tweet.getUser().getName());
		tvBody.setText(tweet.getBody());
		String rtime = (String) DateUtils.getRelativeDateTimeString(
                getContext(), tweet.getLongTime(tweet.getTime()), 
                DateUtils.SECOND_IN_MILLIS, 
                DateUtils.WEEK_IN_MILLIS, 
                0 );
		tvTime.setText(rtime);
		return view;
	}

}
