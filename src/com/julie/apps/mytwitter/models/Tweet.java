package com.julie.apps.mytwitter.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "Tweet")
public class Tweet extends Model implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5322272792772579274L;
	
	@Column (name = "user")
	public User user;
	@Column (name = "body")
	public String body;
	@Column (name = "user_id")
	public long id;
	@Column (name = "time")
	public String time;
	

    public User getUser() {
        return this.user;
    }

    public String getBody() {
        return this.body;
    }

    public Long getId() {
        return this.id;
    }
  
    public String getTime() {
		return this.time;
	}
   
    public Tweet(){
    	super();
    }
    
    public Tweet(User user, String body, String time){
    	this.user = user;
    	this.body = body;
    	this.time = time;
    }
    
    public static Tweet fromJson(JSONObject jsonObject) {
    	 Tweet tweet = new Tweet();
            // Deserialize json into object fields
      	try {
      		  tweet.id = jsonObject.getLong("id");
      		  tweet.body = jsonObject.getString("text");
      		  tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
      		  tweet.time = jsonObject.getString("created_at");
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
      	// Return tweet object
      	return tweet;
      }
    
    public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Tweet tweet = Tweet.fromJson(tweetJson);
            if (tweet != null) {
                tweets.add(tweet);
            }
        }

        return tweets;
    }
    
	public long getLongTime(String time) {//Mon Sep 03 13:24:14 +0000 2012
		SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		String dateInString = time;
		long longTime = 0;
		try {
			 
			Date date = formatter.parse(dateInString);
			longTime = date.getTime();
			//System.out.println(longTime);
			//System.out.println(formatter.format(date));
	 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return longTime;
	}
    
	// Record Finders
		public static Tweet byId(long id) {
		   return new Select().from(Tweet.class).where("id = ?", id).executeSingle();
		}
		
		public static ArrayList<Tweet> recentItems() {
	      return new Select().from(Tweet.class).orderBy("id DESC").limit("25").execute();
		}

	
	
}