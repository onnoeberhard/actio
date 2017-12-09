package actio.app.functions;

import actio.app.DevNoteActivity;
import actio.app.ExploreActivity;
import actio.app.FriendsActivtiy;
import actio.app.MapActivity;
import actio.app.ProfileActivity;
import actio.app.R;
import actio.app.Settings;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ActioActivity extends ActionBarActivity {

    private String[] mDrawerTitles;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    public ListView mDrawerList;

    public String this_is = "";
    public int drawer_home = 0;
    public Fragment mFragment;
    
//    public boolean created = false;
//    public boolean layouted = false;
    public boolean itemSelected = false;
    
    public boolean from_details = false;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setSupportProgressBarIndeterminateVisibility(false);
		setContentView(R.layout.activity_main);
		
		mDrawerTitles = getResources().getStringArray(R.array.drawer_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer_home >= 0) {
	        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
	                R.drawable.ic_navigation_drawer, R.string.drawer_open, R.string.drawer_close) {
	            public void onDrawerClosed(View view) {
	                super.onDrawerClosed(view);
	                getSupportActionBar().setTitle(mTitle);
	                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
	            }
	            public void onDrawerOpened(View drawerView) {
	                super.onDrawerOpened(drawerView);
	                getSupportActionBar().setTitle(mDrawerTitle);
	                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
	            }
	        };
	        mDrawerLayout.setDrawerListener(mDrawerToggle);
        }
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mDrawerTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
//        created = true;
        selectItem(drawer_home);
	}

	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(drawer_home >= 0)	
        	mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(drawer_home >= 0)	
        	mDrawerToggle.onConfigurationChanged(newConfig);
    }
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @SuppressWarnings("rawtypes")
		@Override
	    public void onItemClick(AdapterView parent, View view, int position, long id) {
	        selectItem(position);
	    }
	}
	
	private void selectItem(int position) {
		if(position == drawer_home && !itemSelected) {
			if(drawer_home >= 0)	
	        	selectDrawerItem(position);
		    FragmentManager fragmentManager = getSupportFragmentManager();
		    fragmentManager.beginTransaction()
		                   .replace(R.id.content_frame, mFragment)
		                   .commit();
		    itemSelected = true;
		} else {
//			boolean go_details = false;
			Intent i = null;
			if(position == 0) {
				i = new Intent(this, DevNoteActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			} if(position == 1) {
				i = new Intent(this, ExploreActivity.class);
				if(this_is.equals("details"))	
		        	i.putExtra("FROM_DETAILS", true);
			} else if(position == 2) {
				i = new Intent(this, MapActivity.class);
				if(this_is.equals("details"))	
		        	i.putExtra("FROM_DETAILS", true);
			} else if(position == 3) {
				i = new Intent(this, ProfileActivity.class);
				LocalDatabaseHandler dbl = new LocalDatabaseHandler(this);
				i.putExtra("username", dbl.getStuffValue("username"));
				if(this_is.equals("details"))
		        	i.putExtra("FROM_DETAILS", true);
			} else if(position == 4) {
				i = new Intent(this, FriendsActivtiy.class);
				if(this_is.equals("details"))	
		        	i.putExtra("FROM_DETAILS", true);
			} else if(position == 5) {
				i = new Intent(this, Settings.class);
			} 
			if(i != null) {
				startActivity(i);
//				if(drawer_home < 0)
//			        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
//				else
//					overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
				if(drawer_home >= 0)
					overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
			}
		}
	    mDrawerLayout.closeDrawer(mDrawerList);
	}
	
//	@Override
//	public void onWindowFocusChanged(boolean hasFocus) {
//		super.onWindowFocusChanged(hasFocus);
//		if(!layouted && created) {
//			selectItem(drawer_home);
//	    	layouted = true;
//		}
//	}

	public void selectDrawerItem(final int position) {
		for(int i = 0; i < mDrawerList.getChildCount(); i++) {
			mDrawerList.setItemChecked(i, false);
			((TextView)mDrawerList.getChildAt(i)).setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
		}
        mDrawerList.setItemChecked(position, true);
//        for(int i = 0; i < mDrawerList.getChildCount(); i++)
//        	((TextView)mDrawerList.getChildAt(i)).setTypeface(null, Typeface.NORMAL);
        final Handler handler = new Handler();
		handler.post(new Runnable() {
			@Override
			public void run() {
				if(((TextView)mDrawerList.getChildAt(position)) != null) {
			        ((TextView)mDrawerList.getChildAt(position)).setTypeface(null, Typeface.BOLD);
					handler.removeCallbacksAndMessages(null);
				} else
					handler.postDelayed(this, 100);
			}
			
	 	});
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(drawer_home >= 0)	
        	if (mDrawerToggle.onOptionsItemSelected(item))
        		return true;
	    switch (item.getItemId()) {
	        case android.R.id.home:
	        	finish();
	            overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom); 
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    if(!from_details)
	    	overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
	    if(drawer_home < 0)	
        	overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
	}
}