package com.julie.apps.mytwitter.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;


public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3520317794489711230L;
	private String name;
	private long id;
	private String screenName;
	private String profileBackgroundImageUrl;
	private int numTweets;
	private int followersCount;
	private int friendsCount;
	private String getProfileImageUrl;
	private String tagline;
	
	
	
	
    public String getName() {
        return this.name;
    }

    public Long getId() {
        return this.id;
    }

    public String getScreenName() {
        return this.screenName;
    }

    public String getProfileBackgroundImageUrl() {
        return this.profileBackgroundImageUrl;
    }

    public int getNumTweets() {
        return this.numTweets;
    }

    public int getFollowersCount() {
        return this.followersCount;
    }

    public int getFriendsCount() {
        return this.friendsCount;
    }

    public String getProfileImageUrl() {
    	return this.getProfileImageUrl;
	}
    
    public String getTagline(){
    	return this.tagline;
    }
    
    public User(){
    	super();
    }
    
    
    public static User fromJson(JSONObject jsonObject) {
    	User user = new User();
            // Deserialize json into object fields
      	try {
      		user.id = jsonObject.getLong("id");
      		user.name = jsonObject.getString("name");
      		user.screenName = jsonObject.getString("screen_name");
      		user.profileBackgroundImageUrl = jsonObject.getString("profile_background_image_url");
      		user.numTweets = jsonObject.getInt("statuses_count");
      		user.followersCount = jsonObject.getInt("followers_count");
      		user.friendsCount = jsonObject.getInt("friends_count");
      		user.getProfileImageUrl = jsonObject.getString("profile_image_url");
      		user.tagline = jsonObject.getString("description");
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
      	// Return user object
      	return user;
      }

	


}