package com.yakdere.apps.mytwitterapp.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
@Table(name = "Tweet")
public class Tweet extends Model implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7593286872344935825L;

	@Column(name = "User")
	private User user;
	@Column(name = "Body")
	private String body;
	@Column(name = "TweetId")
	private long tweetId;
	@Column(name = "CreatedAt")
	private Date createdAt;

	public Tweet() {
		super();
	}

	public Tweet(JSONObject jsonObject) throws JSONException {
		super();
		user = User.fromJson(jsonObject.getJSONObject("user"));
		body = jsonObject.getString("text");
		tweetId = jsonObject.getLong("id");
		createdAt = parseDate(jsonObject.getString("created_at"));
	}

	private Date parseDate(String date) {
		Date d;
		try {
			d = new SimpleDateFormat(
					"EE MMM DD HH:mm:ss Z yyyy", Locale.ENGLISH
					).parse(date);
			//Sat Oct 19 17:03:38 +0000 2013
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return d;
	}

	public Date getDate() {
		return createdAt;
	}

	public User getUser() {
		return user;
	}

	public String getBody() {
		return body;
	}

	public long getTweetId() {
		return tweetId;
	}

	public static Tweet fromJson(JSONObject jsonObject) {
		Tweet tweet = null;
		try {
			tweet = new Tweet(jsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		tweet.save();
		return tweet;
	}

	public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject tweetJson = null;
			try {
				tweetJson = jsonArray.getJSONObject(i);
			} catch (JSONException e) {
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
	
}