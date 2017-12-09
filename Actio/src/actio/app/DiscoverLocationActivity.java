package actio.app;

import actio.app.functions.ActioActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

public class DiscoverLocationActivity extends ActioActivity {

	static PagerSlidingTabStrip tabs;
	static ViewPager pager;
	static MyPagerAdapter adapter;
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
	    mFragment = new DiscoverFragment();
	    Bundle args = new Bundle();
	    mFragment.setArguments(args);
    	drawer_home = -1;
		super.onCreate(savedInstanceState);
		from_details = getIntent().getBooleanExtra("FROM_DETAILS", false);
		Toast.makeText(this, Integer.toString(getIntent().getIntExtra("id", 0)), Toast.LENGTH_SHORT).show();
	}

	public static class DiscoverFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.tabs, container, false);
    		tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabsn);
    		pager = (ViewPager) rootView.findViewById(R.id.pagern);
    		adapter = new MyPagerAdapter(getChildFragmentManager());
    		pager.setAdapter(adapter);
    		pager.setOffscreenPageLimit(adapter.getCount() - 1);
    		tabs.setViewPager(pager);
    		tabs.setIndicatorColor(0xff0ab246);
    		tabs.setBackgroundResource(R.drawable.borderless_button);
            return rootView;
        }
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.explore, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.action_sort:
	        	//sortblabla (guck mal tutorials oda apidemos oda so da is das gut gemacht)
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	public static class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { "Overview", "Here & Now", "Popular Places Narby", "Popular Locations Nearby" };

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
        		fragment = new FragmentOverview();
        	if(i == 1)
        		fragment = new FragmentHereNow();
        	if(i == 2)
        		fragment = new FragmentPlacesNearby();
        	if(i == 3)
        		fragment = new FragmentLocationsNearby();
            return fragment;
        }

	}

	public static class FragmentOverview extends Fragment {
        
		@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.list, container, false);
            return rootView;
        }
    }

	public static class FragmentHereNow extends Fragment {
        
		@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.list, container, false);
            return rootView;
        }
    }
	
	public static class FragmentPlacesNearby extends Fragment {

		@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.list, container, false);
			return rootView;
		}
	}
	
	public static class FragmentLocationsNearby extends Fragment {
        
		@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.list, container, false);
            return rootView;
        }
    }
}