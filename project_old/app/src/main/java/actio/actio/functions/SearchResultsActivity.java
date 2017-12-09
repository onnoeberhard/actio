package actio.actio.functions;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import actio.actio.ActivityDetailsActivity;
import actio.actio.BrowseCategoriesActivity;
import actio.actio.CreateActivity;
import actio.actio.PlaceDetailsActivity;
import actio.actio.ProfileActivity;
import actio.actio.R;

public class SearchResultsActivity extends ActioActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    mFragment = new SearchFragment();
	    Bundle args = new Bundle();
	    mFragment.setArguments(args);
    	drawer_home = -1;
		super.onCreate(savedInstanceState);
		from_details = getIntent().getBooleanExtra("FROM_DETAILS", false);
//		handleIntent(getIntent());
    }

	static SearchView mSearchView;
	MenuItem searchItem;

	boolean acts_ready = false;
	boolean pepl_ready = false;
	boolean plcs_ready = false;
	boolean evnt_ready = false;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.search, menu);
		searchItem = menu.findItem(R.id.action_search);
		MenuItemCompat.expandActionView(searchItem);
		MenuItemCompat.setOnActionExpandListener(searchItem, new OnActionExpandListener() {
			@Override
			public boolean onMenuItemActionExpand(MenuItem arg0) {
				return false;
			}
			@Override
			public boolean onMenuItemActionCollapse(MenuItem arg0) {
				finish();
	            overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom); 
				return false;
			}
		});
	    mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
	    mSearchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(final String text) {
				final Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
				        lv.requestFocus();
					}
					
			 	}, 100);
				if(text.equals("")) {
					items.clear();
					types.clear();
					types.add(-1);
					String[] row = {"Browse Categories"};
					items.add(row);
					types.add(-1);
					String[] row2 = {"Create", "-2"};
					items.add(row2);
			        adapter = null;
			    	adapter = new ActioArrayAdapter(SearchResultsActivity.this, R.layout.friend_list_item, items, types);
			    	adapter.notifyDataSetChanged();
			        lv.setAdapter(adapter);
			        lv.setDividerHeight(0);
				} return false;
			}
			
			@Override
			public boolean onQueryTextChange(final String text) {
				if(text.equals("")) {
					items.clear();
					types.clear();
					types.add(-1);
					String[] row = {"Browse Categories"};
					items.add(row);
					types.add(-1);
					String[] row2 = {"Create", "-2"};
					items.add(row2);
			        adapter = null;
			    	adapter = new ActioArrayAdapter(SearchResultsActivity.this, R.layout.friend_list_item, items, types);
			    	adapter.notifyDataSetChanged();
			        lv.setAdapter(adapter);
			        lv.setDividerHeight(0);
				} else {
					loading.setVisibility(View.VISIBLE);
					acts_ready = false;
					pepl_ready = false;
					plcs_ready = false;
					evnt_ready = true;
					final ArrayList<String[]> acts = new ArrayList<String[]>();
					final ArrayList<String[]> pepl = new ArrayList<String[]>();
					final ArrayList<String[]> plcs = new ArrayList<String[]>();
					final ArrayList<String[]> evnt = new ArrayList<String[]>();
					new OnlineDatabaseHandler(SearchResultsActivity.this).getAllFromDB(new OnlineDatabaseHandler.WebDbUser() {
						@Override
						public void gottenFromWeb(JSONObject json, boolean good, boolean success) {
							if(success && good) {
								try {
									for(int i = 0; i < json.getJSONArray("rows").length(); i++) {
										String username = json.getJSONArray("rows").getJSONObject(i).getString("username");
										if(username.toLowerCase(Locale.ENGLISH).contains(text.toLowerCase(Locale.ENGLISH))) {
											String[] row = new String[4];
											row[0] = username;
											pepl.add(row);
//											Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();
										}
									}
									pepl_ready = true;
									if(acts_ready && pepl_ready && plcs_ready && evnt_ready)
										showResults(acts, pepl, plcs, evnt);
								} catch(JSONException e) {
									e.printStackTrace();
									Toast.makeText(getApplicationContext(), R.string.error_sorry, Toast.LENGTH_SHORT).show();
								}
							} else
								Toast.makeText(getApplicationContext(), R.string.error_sorry, Toast.LENGTH_SHORT).show();
						}
					}, "accounts", "", "", "");
					new OnlineDatabaseHandler(SearchResultsActivity.this).getAllFromDB(new OnlineDatabaseHandler.WebDbUser() {
						@Override
						public void gottenFromWeb(JSONObject json, boolean good, boolean success) {
							if(success && good) {
								try {
									for(int i = 0; i < json.getJSONArray("values").length(); i++) {
										String name = json.getJSONArray("values").getJSONObject(i).getJSONArray("name").getString(1);
										if(name.toLowerCase(Locale.ENGLISH).contains(text.toLowerCase(Locale.ENGLISH))) {
											String[] row = new String[4];
											row[0] = name;
											row[1] = json.getJSONArray("rows").getJSONObject(i).getString("id");
											plcs.add(row);
										}
									}
									plcs_ready = true;
									if(acts_ready && pepl_ready && plcs_ready && evnt_ready)
										showResults(acts, pepl, plcs, evnt);
								} catch(JSONException e) {
									e.printStackTrace();
									Toast.makeText(getApplicationContext(), R.string.error_sorry, Toast.LENGTH_SHORT).show();
								}
							} else
								Toast.makeText(getApplicationContext(), R.string.error_sorry, Toast.LENGTH_SHORT).show();
						}
					}, "places", "data", "", "");
					new OnlineDatabaseHandler(SearchResultsActivity.this).getAllFromDB(new OnlineDatabaseHandler.WebDbUser() {
						@Override
						public void gottenFromWeb(JSONObject json, boolean good, boolean success) {
							if(success && good) {
								try {
									for(int i = 0; i < json.getJSONArray("values").length(); i++) {
										String name = json.getJSONArray("values").getJSONObject(i).getJSONObject("title").getJSONArray("EN").getString(1);
										if(name.toLowerCase(Locale.ENGLISH).contains(text.toLowerCase(Locale.ENGLISH)) || (json.getJSONArray("values").getJSONObject(i).getJSONObject("title").has(Locale.getDefault().getLanguage().toUpperCase()) ? json.getJSONArray("values").getJSONObject(i).getJSONObject("title").getJSONArray(Locale.getDefault().getLanguage().toUpperCase()).getString(1).toLowerCase(Locale.ENGLISH).contains(text.toLowerCase(Locale.ENGLISH)) : false)) {
											String[] row = new String[4];
											row[0] = json.getJSONArray("values").getJSONObject(i).getJSONObject("title").getJSONArray(json.getJSONArray("values").getJSONObject(i).getJSONObject("title").has(Locale.getDefault().getLanguage().toUpperCase()) ? Locale.getDefault().getLanguage().toUpperCase() : "EN").getString(1);
											row[1] = json.getJSONArray("rows").getJSONObject(i).getString("id");
											acts.add(row);
										}
									}
									acts_ready = true;
									if(acts_ready && pepl_ready && plcs_ready && evnt_ready)
										showResults(acts, pepl, plcs, evnt);
								} catch(JSONException e) {
									e.printStackTrace();
									Toast.makeText(getApplicationContext(), R.string.error_sorry, Toast.LENGTH_SHORT).show();
								}
							} else
								Toast.makeText(getApplicationContext(), R.string.error_sorry, Toast.LENGTH_SHORT).show();
						}
					}, "activities", "data", "", "");
				} return false;
			}
		});
		return true;
	}
	
	public void showResults(ArrayList<String[]> acts, ArrayList<String[]> pepl, ArrayList<String[]> plcs, ArrayList<String[]> evnt) {
		loading.setVisibility(View.GONE);
		items.clear();
		types.clear();
		for(int i = 0; i < acts.size(); i++) {
			types.add(0);
			items.add(acts.get(i));
		}
		for(int i = 0; i < pepl.size(); i++) {
			types.add(1);
			items.add(pepl.get(i));
		}
		for(int i = 0; i < plcs.size(); i++) {
			types.add(2);
			items.add(plcs.get(i));
		}
		for(int i = 0; i < evnt.size(); i++) {
			types.add(3);
			items.add(evnt.get(i));
		}
		types.add(-1);
		String[] row2 = {"Create", "-2"};
		items.add(row2);
        adapter = null;
    	adapter = new ActioArrayAdapter(SearchResultsActivity.this, R.layout.friend_list_item, items, types);
    	adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
        lv.setDividerHeight(0);
//		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//		imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
//        lv.requestFocus();
	}
	
	public static class ActioArrayAdapter extends ArrayAdapter<String> {

		Context context;
		ArrayList<String[]> values;
		ArrayList<Integer> types;
		
		public ActioArrayAdapter(Context context, int resource, ArrayList<String[]> objects, ArrayList<Integer> types) {
			super(context, resource, new String[objects.size()]);
			this.context = context;
		    this.values = objects;
		    this.types = types;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if(types.get(position) == -1) {
				View view = inflater.inflate(R.layout.friend_list_item, parent, false);
				TextView title = (TextView) view.findViewById(R.id.text1);
				title.setText(values.get(position)[0]);
				TextView type = (TextView) view.findViewById(R.id.text2);
				type.setVisibility(View.GONE);
				view.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(values.get(position)[1].equals("-1")) {
							Intent i = new Intent(context, BrowseCategoriesActivity.class);
							i.putExtra("username", values.get(position)[0]);
							context.startActivity(i);
						} else if(values.get(position)[1].equals("-2")) {
							Intent i = new Intent(context, CreateActivity.class);
							i.putExtra("username", values.get(position)[0]);
							context.startActivity(i);
						}
					}
				});
				return view;
			} else if(types.get(position) == 0) {
				View view = inflater.inflate(R.layout.friend_list_item, parent, false);
				TextView title = (TextView) view.findViewById(R.id.text1);
				title.setText(values.get(position)[0]);
				TextView type = (TextView) view.findViewById(R.id.text2);
				type.setText("Activity");
				view.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent i = new Intent(context, ActivityDetailsActivity.class);
						i.putExtra("id", Integer.parseInt(values.get(position)[1]));
						context.startActivity(i);
					}
				});
				return view;
			} else if(types.get(position) == 1) {
				View view = inflater.inflate(R.layout.friend_list_item, parent, false);
				TextView title = (TextView) view.findViewById(R.id.text1);
				title.setText(values.get(position)[0]);
				TextView type = (TextView) view.findViewById(R.id.text2);
				type.setText("User");
				view.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent i = new Intent(context, ProfileActivity.class);
						i.putExtra("username", values.get(position)[0]);
						context.startActivity(i);
					}
				});
				return view;
			} else if(types.get(position) == 2) {
				View view = inflater.inflate(R.layout.friend_list_item, parent, false);
				TextView title = (TextView) view.findViewById(R.id.text1);
				title.setText(values.get(position)[0]);
				TextView type = (TextView) view.findViewById(R.id.text2);
				type.setText("Place");
				view.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent i = new Intent(context, PlaceDetailsActivity.class);
						i.putExtra("id", Integer.parseInt(values.get(position)[1]));
						context.startActivity(i);
					}
				});
				return view;
			} return null;
		}

	}
	
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		System.out.println("yeah");
//	    switch (item.getItemId()) {
//	    	case R.id.action_search:
//	    		MenuItemCompat.expandActionView(searchItem);
////	        case android.R.id.home:
////	        	finish();
////	            overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom); 
////	        	return true;
//	        default:
//	        	return super.onOptionsItemSelected(item);
//	    }
//	}
	
    @Override
    protected void onNewIntent(Intent intent) {
    	setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
//        if (Intent.ACTION_SEARCH.equals(intent.getAction()))
    	    mSearchView.setQuery(getIntent().getStringExtra(SearchManager.QUERY), true);
//		items.clear();
//		types.clear();
//        adapter = null;
//    	adapter = new ActioArrayAdapter(SearchResultsActivity.this, R.layout.friend_list_item, items, types);
//    	adapter.notifyDataSetChanged();
//        lv.setAdapter(adapter);
//        lv.setDividerHeight(0);
//		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//		imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
//        lv.requestFocus();
    }
	
    static ListView lv;
    static ArrayAdapter<String> adapter;
    static ArrayList<String[]> items;
    static ArrayList<Integer> types;
    static View loading;
    
	public static class SearchFragment extends Fragment {
        public SearchFragment() {}
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.list, container, false);
            items = new ArrayList<String[]>();
            types = new ArrayList<Integer>();
            adapter = new ActioArrayAdapter(getActivity(), R.layout.friend_list_item, items, types);
            lv = (ListView)rootView.findViewById(R.id.list);
            loading = rootView.findViewById(R.id.loading);
            items.clear();
			types.clear();
			types.add(-1);
			String[] row = {"Browse Categories", "-1"};
			items.add(row);
			types.add(-1);
			String[] row2 = {"Create", "-2"};
			items.add(row2);
	        adapter = null;
	    	adapter = new ActioArrayAdapter(getActivity(), R.layout.friend_list_item, items, types);
	    	adapter.notifyDataSetChanged();
	        lv.setAdapter(adapter);
	        lv.setDividerHeight(0);
            return rootView;
        }
    }
	
}