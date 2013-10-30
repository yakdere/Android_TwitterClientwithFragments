package com.yakdere.apps.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.yakdere.apps.mytwitterapp.R;
import com.yakdere.apps.mytwitterapp.TweetsAdapter;
import com.yakdere.apps.mytwitterapp.activities.ProfileActivity;
import com.yakdere.apps.mytwitterapp.models.Tweet;
import com.yakdere.apps.mytwitterapp.models.User;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TweetsListFragment extends Fragment  {
	TweetsAdapter tweetsAdapter;
	PullToRefreshListView lvTweets;
	ArrayList<Tweet> tweets;
	FragmentActivity listener;
	/*
	 * This list fragment contains all tweets from different tabs-Profile, Mentions, HomeTimeline
	 * to visit of every user's usertimeline this fragment implements OnItemClickListener
	 * and send  user's data to Profile Activity via Intent
	 */

	// This event fires 1st, before creation of fragment or any views
	// The onAttach method is called when the Fragment instance is associated with an Activity instance. 
	// This does not mean the Activity is fully initialized.
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof FragmentActivity) {
			this.listener = (FragmentActivity) activity;
			Log.d("DEBUG", "onAttach fired");
		} else {
			throw new ClassCastException("Must implement FragmentActivity interface");
		}
	}
	// This event fires 2nd, before views are created for the fragment
	// The onCreate method is called when the Fragment instance is being created, or re-created.
	// Use onCreate for any standard setup that does not require the activity to be completed
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tweets = new ArrayList<Tweet>();
		tweetsAdapter = new TweetsAdapter(getActivity(), tweets);
	}
	// This event fires 3rd, and is the first time views are available in the fragment
	// The onCreateView method is called when Fragment should create its View object hierarchy. 
	// Use onCreateView to get a handle to views as soon as they are freshly inflated
	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
		Log.d("DEBUG", "Time to inflate the view");
		return inf.inflate(R.layout.fragment_tweets_list, parent , false);
	}
	// This fires 4th, and this is the first time the Activity is fully created.
	// Accessing the view hierarchy of the parent activity must be done in the onActivityCreated
	// At this point, it is safe to search for View objects by their ID, for example.
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		lvTweets = (PullToRefreshListView) getActivity().findViewById(R.id.lvTweets);
		lvTweets.setAdapter(tweetsAdapter);
		lvTweets.setOnItemClickListener( new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View parent,
					int location, long id) {
				Tweet tweet = tweets.get(location);
				User user = tweet.getUser();
				Intent i = new Intent(getActivity().getApplicationContext(), ProfileActivity.class);
				//go to User model and make it serializable
				i.putExtra("user", user);
				startActivity(i);
			}

		});
		lvTweets.setOnRefreshListener(new OnRefreshListener() {
			// Your code to refresh the list contents
            // Make sure you call listView.onRefreshComplete()
            // once the loading is done. This can be done from here or any
            // place such as when the network request has completed successfully.
			@Override
			public void onRefresh() {
					tweetsAdapter.clear();
			}
			
		});
	}
	
	public void addTweetsToAdapter(ArrayList<Tweet> tweets) {
		tweetsAdapter.addAll(tweets);
		lvTweets.onRefreshComplete();
	}
	public TweetsAdapter getAdapter() {
		return this.tweetsAdapter;
	}
	public void onDetach() {
		super.onDetach();
		listener = null;
	}
}
