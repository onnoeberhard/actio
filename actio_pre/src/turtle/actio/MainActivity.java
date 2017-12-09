package turtle.actio;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import turtle.actio.stuff.DatabaseHandler;
import turtle.actio.stuff.PlaceFunction;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnQueryTextListener {

	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mDrawerItemTitles;
    int selectedItem = 0;
    MenuItem searchItem;
    private static ListView lv;
    static ArrayAdapter<String> adapter;
    static MainActivity ma;
    ArrayList<String> products;
    static ArrayList<Integer> productids;
    public String email;
    public String[][] data1;
    public String[][] data2;
    DatabaseHandler db;
    
//    UserFunctions userFunctions;
    PlaceFunction placeFunctions;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        userFunctions = new UserFunctions();
        placeFunctions = new PlaceFunction();
//		if (!userFunctions.isUserLoggedIn(getApplicationContext())) {
//			Intent login = new Intent(getApplicationContext(),
//					LoginActivity.class);
//			login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(login);
//			finish();
//		}
        setContentView(R.layout.activity_main);
        ma = this;
//        db = new DatabaseHandler(getApplicationContext());
//        String countQuery = "SELECT * FROM stuff WHERE name = 'USER'";
//        SQLiteDatabase sdb = db.getReadableDatabase();
//        Cursor cursor = sdb.rawQuery(countQuery, null);
//        String row = cursor.getString(0);
//        sdb.close();
//        cursor.close();
//        Log.i("", row);
        mTitle = mDrawerTitle = getTitle();
        mDrawerItemTitles = getResources().getStringArray(R.array.drawer_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mDrawerItemTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(
        		this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); 
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if (savedInstanceState == null) {
            selectItem(0, true);
        }
        String productsstr[] = {"Dell Inspiron", "HTC One X", "HTC Wildfire S", "HTC Sense", "HTC Sensation XE",
                "iPhone 4S", "Samsung Galaxy Note 800",
                "Samsung Galaxy S3", "MacBook Air", "Mac Mini", "MacBook Pro"};
        int[] productsids = {54, 26, 654, 9, 856, 68, 213, 897, 256, 65, 889};
        products = new ArrayList<String>();
        productids = new ArrayList<Integer>();
        for(int i = 0; i < productsstr.length; i++) {
        	products.add(productsstr[i]);
        	productids.add(productsids[i]);
        }
        adapter = new ArrayAdapter<String>(this, R.layout.search_list_item, R.id.product_name, products);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        searchItem = menu.findItem(R.id.action_search);
        searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        if(selectedItem > 0)
        	searchItem.setVisible(false);
        searchItem.setOnActionExpandListener(new OnActionExpandListener() {
			@Override
			public boolean onMenuItemActionCollapse(MenuItem item) {
				selectItem(0, true);
				return true;
			}
			@Override
			public boolean onMenuItemActionExpand(MenuItem item) {
				selectItem(20, false);
				return true;
			}
        });
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_search).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onQueryTextChange(String newText) {
    	adapter.getFilter().filter(newText);
        return false;
    }
 
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;
        switch(item.getItemId()) {
//	        case R.id.action_search:
//	            selectItem(20, false);
//	            Toast.makeText(getApplicationContext(), "DIDIT", Toast.LENGTH_SHORT).show();
//	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position, true);
        }
    }

    public void selectItem(int position, boolean isInDrawer) {
    	selectedItem = position;
    	if (position == 0) {
    		Fragment fragment = new DiscoverFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    	} else if (position == 4) {
    		Fragment fragment = new CreateFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    	} else if (position == 5) {
    		Intent i = new Intent(getApplicationContext(), Settings.class);
    		startActivity(i);
    		finish();
    	} else if (position == 20) {
    		Fragment fragment = new SearchFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    	} if(isInDrawer) {
	        mDrawerList.setItemChecked(position, true);
	        mTitle = mDrawerItemTitles[position];
	        getActionBar().setTitle(mTitle);
	        mDrawerLayout.closeDrawer(mDrawerList);
    	}
    }
    
    public void showDetails(int id) {
    	Intent i = new Intent(getApplicationContext(), Details.class);
    	i.putExtra(Details.ITEM_ID, id);
    	startActivity(i);
    	searchItem.collapseActionView();
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

    public static class SearchFragment extends Fragment {
        public SearchFragment() {}
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.search, container, false);
    		lv = (ListView) rootView.findViewById(R.id.search_list_view);
    		lv.setAdapter(adapter);
    		lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					ma.showDetails(productids.get(ma.products.indexOf((String)adapter.getItem(position))));
				}
    			
    		});
            return rootView;
        }
    }
    
    public static class DiscoverFragment extends Fragment {
        public DiscoverFragment() {}
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.discover, container, false);
            return rootView;
        }
    }
    public static TextView errorMsg;
    public static class CreateFragment extends Fragment {
        public CreateFragment() {}
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
         	View rootView = inflater.inflate(R.layout.create, container, false);           
            return rootView;
        }
    }
    
}
