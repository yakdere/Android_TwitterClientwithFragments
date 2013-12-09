package com.yakdere.apps.mytwitterapp.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Users")
public class User extends Model implements Serializable {

	/**
	 * User data will send to ProfileActivity from TweetListFragment to see user's profile
	 */
	private static final long serialVersionUID = 669380794969131283L;
	@Column(name = "Name")
	private String name;
	@Column(name = "ScreenName")
	private String screenName;
	@Column(name = "Description")
	private String description;
	@Column(name = "ProfileImageUrl")
	private String profileImageUrl;
	@Column(name = "TweetCount")
	private int tweetsCount;
	@Column(name = "FollowersCount")
	private int followersCount;
	@Column(name = "FollowingCount")
	private int followingCount;

	public User() {
		super();
	}

	public User(JSONObject jsonObject) throws JSONException {
		super();
		name = jsonObject.getString("name");
		screenName = jsonObject.getString("screen_name");
		description = jsonObject.getString("description");
		profileImageUrl = jsonObject.getString("profile_image_url");
		tweetsCount = jsonObject.getInt("statuses_count");
		followersCount = jsonObject.getInt("followers_count");
		followingCount = jsonObject.getInt("friends_count");
	}

	public String getName() { 
		return name;
	}
	public String getScreenName() {
		return screenName;
	}
	//Description AKA Tagline
	public String getDescription() {
		return description;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public int getTweetCount() {
		return tweetsCount;
	}

	public int getFollowersCount() {
		return followersCount;
	}

	public int getFollowingCount() {
		return followingCount;
	}

	public static User fromJson(JSONObject jsonObject) {
		try {
			User user = new User(jsonObject);
			user.save();
			return user;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}


}