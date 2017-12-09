package actio.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import actio.app.stuff.DatabaseHandler;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends ActionBarActivity {

	public static Menu menu;
    
	private String[] mDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
	
	public static DatabaseHandler db;
	
	public int selectedItem = 0;
	public boolean beginning = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		db = new DatabaseHandler(this);
		

        mDrawerItemTitles = getResources().getStringArray(R.array.drawer_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer , R.string.drawer_open,R.string.drawer_close){
        	
       	 /** Called when drawer is closed */
           public void onDrawerClosed(View view) {            		
               supportInvalidateOptionsMenu();       
           }

           /** Called when a drawer is opened */
           public void onDrawerOpened(View drawerView) {   	
           		supportInvalidateOptionsMenu();      
           		if(beginning && selectedItem == 0)
           			selectDrawerItem(0, false);
           		beginning = false;
           }
       };
        
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
 
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mDrawerItemTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
 
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
 
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        
		if(db.getStuffValue("FIRST_START").equals("0") || db.getStuffValue("FIRST_START").equals("3") || 
				db.getStuffValue("FIRST_START").equals("")) {
			selectItem(-1, false);
			
		}
		else {
			selectItem(0, false);  
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		MainActivity.menu = menu;
//		if(selectedItem == -1)
//			menu.getItem(0).setVisible(false);
		return true;
	}

	public void selectItem(int position, boolean showDrawerTitle) {
		selectedItem = position;
		if (position == -1) {
			Fragment fragment = new FirstStartFragment();
			Bundle args = new Bundle();
			fragment.setArguments(args);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
			getSupportActionBar().setTitle("Setup");
		} else if (position == 0) {
			Fragment fragment = new StartFragment();
			Bundle args = new Bundle();
			fragment.setArguments(args);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
		} else if (position == 1) {
			Fragment fragment = new DiscoverFragment();
			Bundle args = new Bundle();
			fragment.setArguments(args);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
		}/*
		 * if(isInDrawer) { mDrawerList.setItemChecked(position, true); mTitle =
		 * mDrawerItemTitles[position]; getActionBar().setTitle(mTitle);
		 * mDrawerLayout.closeDrawer(mDrawerList); }
		 */
	}
	
	//First Start / Setup
	public static class FirstStartFragment extends Fragment {
        public FirstStartFragment() {}
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.firststartfr, container, false);
            Locale[] locales = Locale.getAvailableLocales();
            ArrayList<String> countries = new ArrayList<String>();
            for (Locale locale : locales) {
                String country = locale.getDisplayCountry();
                if (country.trim().length()>0 && !countries.contains(country)) {
                    countries.add(country);
                }
            }
            Collections.sort(countries);
            Spinner countrySpinner = (Spinner) rootView.findViewById(R.id.firststart_homeaddress_country);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, countries);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            countrySpinner.setAdapter(adapter);
            Locale locale = Locale.getDefault();
            String country = db.getStuffValue("ME_HA_COUNTRY");
            int pos = adapter.getPosition((country.equals("")) ? locale.getDisplayCountry() : country);
            countrySpinner.setSelection(pos);
            final Button ok_or_sa = (Button) rootView.findViewById(R.id.firststart_homeaddress_ok);
            final EditText streetEdit = (EditText) rootView.findViewById(R.id.firststart_homeaddress_street);
            streetEdit.setText(db.getStuffValue("ME_HA_STREET"));
            final EditText cityEdit = (EditText) rootView.findViewById(R.id.firststart_homeaddress_city);
            cityEdit.setText(db.getStuffValue("ME_HA_CITY"));
            if(!streetEdit.getText().toString().equals("") && !cityEdit.getText().toString().equals(""))
				ok_or_sa.setText(android.R.string.ok);
            streetEdit.setOnKeyListener(new OnKeyListener() {
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					if(!streetEdit.getText().toString().equals("") && !cityEdit.getText().toString().equals(""))
						ok_or_sa.setText(android.R.string.ok);
					else
						ok_or_sa.setText("Skip & don\'t show again");
					return false;
				}
			});
            cityEdit.setOnKeyListener(new OnKeyListener() {
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					if(!streetEdit.getText().toString().equals("") && !cityEdit.getText().toString().equals(""))
						ok_or_sa.setText(android.R.string.ok);
					else
						ok_or_sa.setText("Skip & don\'t show again");
					return false;
				}
			});
            return rootView;
        }
    }
    public void firststart_ok(View v) {
		EditText streetEdit = (EditText) ((ViewGroup)v.getParent().getParent()).findViewById(R.id.firststart_homeaddress_street);
		EditText cityEdit = (EditText) ((ViewGroup)v.getParent().getParent()).findViewById(R.id.firststart_homeaddress_city);
		Spinner countrySpinner = (Spinner) ((ViewGroup)v.getParent().getParent()).findViewById(R.id.firststart_homeaddress_country);
		if (!streetEdit.getText().toString().equals("") && !cityEdit.getText().toString().equals(""))
			db.SaveSetup(1, streetEdit.getText().toString(), cityEdit.getText().toString(), ((String) countrySpinner.getSelectedItem()));
		if (streetEdit.getText().toString().equals("") || cityEdit.getText().toString().equals(""))
			db.SaveSetup(2, "", "", "");
    	selectItem(0, true);
    }
    public void firststart_skip(View v) {
    	if(db.getStuffValue("FIRST_START").equals("3"))
    		db.SaveSetup(4, "", "", "");
    	selectItem(0, true);
    }
    public void firststart_homeaddress_why(View v) {
		new AlertDialog.Builder(this)
			.setTitle("Why should I enter my address?")
			.setMessage("Your home address will be used to find activities in your area.")
			.setNeutralButton(android.R.string.ok, 
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
						dialog.cancel();
					}
				})
			.setIcon(android.R.drawable.ic_dialog_info)
			.show();
    }
	
    //Start
	public static class StartFragment extends Fragment {
        public StartFragment() {}
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.startfr, container, false);
            return rootView;
        }
    }
	public void startDoSth(View v) {
		/*db.SaveSetup(3, "", "", "");
		finish();
		startActivity(getIntent());*/
    	selectItem(1, false);
    	selectDrawerItem(1, false);
    }

	//Discover
	public static class DiscoverFragment extends Fragment {
        public DiscoverFragment() {}
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.discoverfr, container, false);
            return rootView;
        }
    }
	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void selectDrawerItem(int position, boolean close) {
        mDrawerList.setItemChecked(position, true);
        for(int i = 0; i < mDrawerList.getChildCount(); i++)
            mDrawerList.getChildAt(i).setBackgroundResource(android.R.color.transparent);
        mDrawerList.getChildAt(mDrawerList.getCheckedItemPosition()).setBackgroundResource(R.color.apptheme_color);
        if(close)
        	mDrawerLayout.closeDrawer(mDrawerList);
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @SuppressWarnings("rawtypes")
		@Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectDrawerItem(position, true);
            selectItem(position, false);
        }
    }
	
}
