package actio.actio;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import org.json.JSONException;
import org.json.JSONObject;

import actio.actio.functions.ActioActivity;
import actio.actio.functions.LocalDatabaseHandler;
import actio.actio.functions.MyViewPager;
import actio.actio.functions.OnlineDatabaseHandler;

public class ProfileActivity extends ActioActivity {

	static String username;
	static boolean me = false;
	static boolean is_friend = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		is_friend = false;
		if(getIntent().getStringExtra("username") != null) {
			username = getIntent().getStringExtra("username");
		} else {
			Toast.makeText(this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
			finish();
		}
		LocalDatabaseHandler dbl = new LocalDatabaseHandler(this);
		me = username.equals(dbl.getStuffValue("username"));
		mFragment = new ProfileFragment();
		Bundle args = new Bundle();
		mFragment.setArguments(args);
		drawer_home = me ? 3 : -1;
		this_is = me ? "" : "details";
		super.onCreate(savedInstanceState);
//		setSupportProgressBarIndeterminateVisibility(true);
		if(!me) {
			new OnlineDatabaseHandler(this).getFromDB(new OnlineDatabaseHandler.WebDbUser() {
				@Override
				public void gottenFromWeb(JSONObject json, boolean good, boolean success) {
					if(success && good) {
						try {
							if(!json.getJSONObject("values").isNull("friends"))
								for(int i = 1; i < json.getJSONObject("values").getJSONArray("friends").length(); i++) {
									if(json.getJSONObject("values").getJSONArray("friends").getJSONArray(i).getString(1).equals(username))
										is_friend = true;
								}
							if(mMenu != null) {
								MenuItemCompat.setActionView(mMenu.findItem(R.id.action_add_friend), null);
								mMenu.findItem(R.id.action_add_friend).setIcon(is_friend ? R.drawable.ic_action_social_person_added : R.drawable.ic_action_social_add_person);
							}
//				    		setSupportProgressBarIndeterminateVisibility(false);
						} catch(JSONException e) {
							e.printStackTrace();
							Toast.makeText(ProfileActivity.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
						}
					} else
						Toast.makeText(ProfileActivity.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
				}
			}, "accounts", "username", dbl.getStuffValue("username"), "data");
			dbl.close();
		} else if(mMenu != null)
			mMenu.findItem(R.id.action_add_friend).setVisible(false);
	}
	
	static PagerSlidingTabStrip tabs;
	static ViewPager pager;
	static MyPagerAdapter adapter;
	
	public static class ProfileFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.tabs, container, false);
    		tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabsn);
    		pager = (MyViewPager) rootView.findViewById(R.id.pagern);
    		adapter = new MyPagerAdapter(getChildFragmentManager());
    		pager.setAdapter(adapter);
    		pager.setOffscreenPageLimit(adapter.getCount() - 1);
    		tabs.setViewPager(pager);
    		tabs.setIndicatorColor(0xff0ab246);
    		tabs.setBackgroundResource(R.drawable.borderless_button);
            return rootView;
        }
    }
	
	public static class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { "Overview", "Adventure", "Score" };

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
        public Fragment getItem(int i) {
        	Fragment fragment = null;
        	if(i == 0)
        		fragment = new OverviewFragment();
        	if(i == 1)
        		fragment = new AdventureFragment();
        	if(i == 2)
        		fragment = new ScoreFragment();
            return fragment;
        }

	}
	
	static Menu mMenu;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		mMenu = menu;
		getMenuInflater().inflate(R.menu.profile, menu);
		if(!me)
			MenuItemCompat.setActionView(mMenu.findItem(R.id.action_add_friend), R.layout.loading);
		else
			mMenu.findItem(R.id.action_add_friend).setVisible(false);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.action_add_friend:
	        	item.setIcon(is_friend ? R.drawable.ic_action_social_add_person : R.drawable.ic_action_social_person_added);
	        	is_friend = !is_friend;
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public static class OverviewFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	if(me && mMenu != null)
        		MenuItemCompat.setActionView(mMenu.findItem(R.id.action_add_friend), null);
            View rootView = inflater.inflate(R.layout.profile_overview, container, false);
            ((TextView)rootView.findViewById(R.id.name)).setText(username);
            return rootView;
        }
    }
	
	public static class AdventureFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.profile_overview, container, false);
            ((TextView)rootView.findViewById(R.id.name)).setText(username);
            return rootView;
        }
    }
	
	static PagerSlidingTabStrip stabs;
	static MyViewPager spager;
	static ScorePagerAdapter sadapter;
	
	public static class ScoreFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.tabs, container, false);
    		stabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabsn);
    		spager = (MyViewPager) rootView.findViewById(R.id.pagern);
    		sadapter = new ScorePagerAdapter(getChildFragmentManager());
//    		spager.setParentViewPager(pager);
//    		spager.setSwipeable(false);
    		spager.setAdapter(sadapter);
    		spager.setOffscreenPageLimit(sadapter.getCount() - 1);
    		stabs.setViewPager(spager);
    		stabs.setIndicatorColor(0xff0ab246);
    		stabs.setBackgroundResource(R.drawable.borderless_button);
            return rootView;
        }
    }
	
	public static class ScorePagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { "Achievements", "Leaderboard Friends", "Leaderboard Global" };

		public ScorePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
        public Fragment getItem(int i) {
        	Fragment fragment = null;
        	if(i == 0)
        		fragment = new ScoreFragmentAchievements();
        	if(i == 1)
        		fragment = new ScoreFragmentFriends();
        	if(i == 2)
        		fragment = new ScoreFragmentGlobal();
            return fragment;
        }

	}
	
	public static class ScoreFragmentAchievements extends Fragment {
        
		@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.list, container, false);
            return rootView;
        }
    }
	
	public static class ScoreFragmentFriends extends Fragment {

		@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.list, container, false);
			return rootView;
		}
	}
	
	public static class ScoreFragmentGlobal extends Fragment {
	    
		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View rootView = inflater.inflate(R.layout.list, container, false);
	        return rootView;
	    }
	}
	
}