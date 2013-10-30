package com.julie.apps.mytwitter;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "vKvADc8uOZbXPZqJaQ9G1g";       // Change this
    public static final String REST_CONSUMER_SECRET = "9gOLPolRNqSA42Dw6etVRmYt2KUUcqQM7Be83PaScQ"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://mytwitter"; // Change this (here and in manifest)
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    public void getHomeTimeline(AsyncHttpResponseHandler handler){
    	String url = getApiUrl("statuses/home_timeline.json");
    	client.get(url,null,handler);
    	
    }
    
    public void getMentions(AsyncHttpResponseHandler handler){
    	String url = getApiUrl("statuses/mentions_timeline.json");
    	client.get(url,null,handler);
    	
    }
    
    public void getOlderTweet(AsyncHttpResponseHandler handler, long max_id){
    	max_id = max_id -1;
    	String url = getApiUrl("statuses/home_timeline.json?max_id="+max_id);
    	client.get(url,null,handler);
    }

	public void postTweet(String tweet, AsyncHttpResponseHandler handler) {
		String status = tweet;
		RequestParams params = new RequestParams();
		params.put("status", status);
		String apiUrl = getApiUrl("statuses/update.json");
    	client.post(apiUrl, params, handler);
	}

	public void getMyInfo(JsonHttpResponseHandler handler) {
		String url = getApiUrl("account/verify_credentials.json");
    	client.get(url,null,handler);	
	}
	
	public void getUserTimeline(JsonHttpResponseHandler handler) {
		String url = getApiUrl("statuses/user_timeline.json");
    	client.get(url,null,handler);
		
	}
	//get other users' info with recent tweet
	public void getFriendInfo(JsonHttpResponseHandler handler, String screenName) {
		String url = getApiUrl("users/show.json?screen_name="+screenName);
    	client.get(url,null,handler);
		
	}

	public void getFriendTimeline(JsonHttpResponseHandler handler, String screenName) {
		String url = getApiUrl("statuses/user_timeline.json?screen_name="+screenName);
    	client.get(url,null,handler);
	}

	public void getOlderMention(
			JsonHttpResponseHandler handler, long max_id) {
		max_id = max_id -1;
    	String url = getApiUrl("statuses/mentions_timeline.json?max_id="+max_id);
    	client.get(url,null,handler);
		
	}

	
    
    /* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
     * 	  i.e getApiUrl("statuses/home_timeline.json");
     * 2. Define the parameters to pass to the request (query or body)
     *    i.e RequestParams params = new RequestParams("foo", "bar");
     * 3. Define the request method and make a call to the client
     *    i.e client.get(apiUrl, params, handler);
     *    i.e client.post(apiUrl, params, handler);
     */
}