package com.yakdere.apps.mytwitterapp.activities;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yakdere.apps.fragments.TweetsListFragment;
import com.yakdere.apps.mytwitterapp.MyTwitterApp;
import com.yakdere.apps.mytwitterapp.R;
import com.yakdere.apps.mytwitterapp.TweetsAdapter;
import com.yakdere.apps.mytwitterapp.TwitterClient;
import com.yakdere.apps.mytwitterapp.models.Tweet;
import com.yakdere.apps.mytwitterapp.models.User;

public class ProfileActivity extends FragmentActivity {
	TextView tvName;
	TextView tvTagline;
	TextView tvTweets;
	TextView tvFollowers;
	TextView tvFollowing;
	ImageView ivProfile;

	TweetsListFragment fragmentTweetList;
	TwitterClient client;
	User user;
	User user_fromlist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		//To see another user's profile first we need user
		user_fromlist = (User) getIntent().getSerializableExtra("user");
		client = MyTwitterApp.getRestClient();
		//If there is no user, user will visit own profile
		if (user_fromlist == null) {
			client.getMyInfo(new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(JSONObject jsonresponse) {
					user = User.fromJson(jsonresponse);
					getActionBar().setTitle("@"+user.getScreenName());
					Log.d("DEBUG", "Get profile of"+user.getScreenName()+"success"+jsonresponse);
					populateProfileHeader(user);
					populateUserTweets(user);
				}
			});
			//Set another user profile we already have user info from TweetsListFragment via Intent
		} else { 
			Log.d("DEBUG", "Get profile of"+user_fromlist.getScreenName()+"success");
			getActionBar().setTitle("@"+user_fromlist.getScreenName());
			populateProfileHeader(user_fromlist);
			populateUserTweets(user_fromlist);
		}

	}
	private void populateProfileHeader(User u) {
		tvName = (TextView) findViewById(R.id.tvName);
		tvTagline = (TextView) findViewById(R.id.tvTagline);
		tvTweets = (TextView) findViewById(R.id.tvTweetCounts);
		tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ivProfile = (ImageView) findViewById(R.id.ivProfileImage);
		//set data to view
		tvName.setText(u.getName());
		tvTagline.setText(u.getDescription());
		tvTweets.setText(Integer.toString(u.getTweetCount()) + " Tweets");
		tvFollowers.setText(Integer.toString(u.getFollowersCount()) + " Followers");
		tvFollowing.setText(Integer.toString(u.getFollowingCount()) + " Following" );
		//load profile image
		ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), ivProfile);
	}
	/*
	 * Since user tweets population located here in ProfileActivity we don't need UserTimelineFragment anymore
	 * Remove UserTimelineFragment and change create new fragment in activity_profile layout set it as TweetsListFragment
	 */

	private void populateUserTweets(User u) {
		//To reference a Fragment at the runtime we need FragmentManager
		fragmentTweetList = (TweetsListFragment) getSupportFragmentManager()
			.findFragmentById(R.id.fragment_tweets_list);
		
		if (fragmentTweetList == null || ! fragmentTweetList.isInLayout()) {
			// fragment doesn't exist in activity
			Log.d("DEBUG", "Tweet List Fragment doesn't exist.");

		} else {
			// fragment does exist
			client.getUserTimeline(u.getScreenName(), new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONArray jsonUserTweets) {

					Log.d("DEBUG", "Success of populating user's tweets"+jsonUserTweets);
					TweetsAdapter adapter =  fragmentTweetList.getAdapter();
					adapter.clear();
					adapter.addAll(Tweet.fromJson(jsonUserTweets));
					adapter.notifyDataSetChanged();
				}
				@Override
				public void onFailure(Throwable e) {
					e.printStackTrace();
					Log.e("ERROR", "Fail on uploading user's tweets. ");
				}
			});
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
