package com.yakdere.apps.mytwitterapp.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import com.yakdere.apps.mytwitterapp.R;
import com.yakdere.apps.fragments.MentionsFragment;
import com.yakdere.apps.fragments.HomeTimelineFragment;

public class TweetsActivity extends FragmentActivity implements TabListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupNavigationTabs();
	}
	private void setupNavigationTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		Tab tabHome = actionBar.newTab().setText("Home")
				.setTag("HomeTimelineFragment")
				.setIcon(R.drawable.ic_home)
				.setTabListener(this);
		Tab tabMentions = actionBar.newTab().setText("Mentions")
				.setTag("MentionsFragment")
				.setIcon(R.drawable.ic_mention)
				.setTabListener(this);
		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		actionBar.selectTab(tabHome);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_newTweet:
			Intent i = new Intent(getBaseContext(), ComposeTweetActivity.class);
			startActivityForResult(i, 0);
			break;

		case R.id.action_profile:
			Intent p = new Intent(getBaseContext(), ProfileActivity.class);
			startActivity(p);
		default:
			break;
		}

		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager
				.beginTransaction();
		if(tab.getTag() == "HomeTimelineFragment"){
			fts.replace(R.id.frameLayout, new HomeTimelineFragment()).commit();

		} else {
			fts.replace(R.id.frameLayout, new MentionsFragment()).commit();
		}
	}
	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {	
	}
	@Override
	public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {	
	}

}
