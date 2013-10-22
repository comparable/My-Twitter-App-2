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
	private String ScreenName;
	private String ProfileBackgroundImageUrl;
	private int NumTweets;
	private int FollowersCount;
	private int FriendsCount;
	private String getProfileImageUrl;
	
	
	
	
    public String getName() {
        return this.name;
    }

    public Long getId() {
        return this.id;
    }

    public String getScreenName() {
        return this.ScreenName;
    }

    public String getProfileBackgroundImageUrl() {
        return this.ProfileBackgroundImageUrl;
    }

    public int getNumTweets() {
        return this.NumTweets;
    }

    public int getFollowersCount() {
        return this.FollowersCount;
    }

    public int getFriendsCount() {
        return this.FriendsCount;
    }

    public String getProfileImageUrl() {
    	return this.getProfileImageUrl;
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
      		user.ScreenName = jsonObject.getString("screen_name");
      		user.ProfileBackgroundImageUrl = jsonObject.getString("profile_background_image_url");
      		user.NumTweets = jsonObject.getInt("statuses_count");
      		user.FollowersCount = jsonObject.getInt("followers_count");
      		user.FriendsCount = jsonObject.getInt("friends_count");
      		user.getProfileImageUrl = jsonObject.getString("profile_image_url");
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
      	// Return user object
      	return user;
      }

	


}