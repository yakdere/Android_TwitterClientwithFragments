package com.yakdere.apps.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yakdere.apps.mytwitterapp.MyTwitterApp;
import com.yakdere.apps.mytwitterapp.TwitterClient;
import com.yakdere.apps.mytwitterapp.models.Tweet;

public class MentionsFragment extends TweetsListFragment{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//fragmentTweets = (TweetsListFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.frameLayout);
		TwitterClient client = MyTwitterApp.getRestClient();
		client.getMentions(new JsonHttpResponseHandler(){

			public void onSuccess(JSONArray jsonTweets){
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets); 
				addTweetsToAdapter(tweets);

			}
			public void onFailure(Throwable e) {
				Log.e("ERROR", e.toString());
				Toast.makeText(getActivity(), "Tweet upload error", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
