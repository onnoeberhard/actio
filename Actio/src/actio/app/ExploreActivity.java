package actio.app;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import actio.app.functions.ActioActivity;
import actio.app.functions.LocalDatabaseHandler;
import actio.app.functions.OnlineDatabaseHandler;
import actio.app.functions.OnlineDatabaseHandler.WebDbUser;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationClient;

public class ExploreActivity extends ActioActivity {

	static PagerSlidingTabStrip tabs;
	static ViewPager pager;
	static MyPagerAdapter adapter;
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
	    mFragment = new ExploreFragment();
	    Bundle args = new Bundle();
	    mFragment.setArguments(args);
    	drawer_home = 1;
		super.onCreate(savedInstanceState);
		from_details = getIntent().getBooleanExtra("FROM_DETAILS", false);
	}

	public static class ExploreFragment extends Fragment {

		public ExploreFragment() {}
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

		private final String[] TITLES = { "Popular Places Nearby", "Popular Locations Nearby", "Popular Locations Global", "Popular Places Global" };

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
        		fragment = new FragmentPlacesNearby();
        	if(i == 1)
        		fragment = new FragmentLocationsNearby();
        	if(i == 2)
        		fragment = new FragmentLocationsGlobal();
        	if(i == 3)
        		fragment = new FragmentPlacesGlobal();
            return fragment;
        }

	}
	
	public static class FragmentPlacesNearby extends Fragment {

        LocationClient lc;
        
		@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.list, container, false);
            rootView.findViewById(R.id.loading).setVisibility(View.VISIBLE);
			lc = new LocationClient(getActivity(), new ConnectionCallbacks() {
				@Override
				public void onDisconnected() {}
				@Override
				public void onConnected(Bundle connectionHint) {
		            LocalDatabaseHandler dbl = new LocalDatabaseHandler(getActivity());
		            final ArrayList<String> entrys = dbl.getAllFromDb("nearby_places", "data");
					final ArrayList<String[]> values = new ArrayList<String[]>();
					if(entrys != null) {
						for(int i = 0; i < entrys.size(); i++) {
							final String[] row = new String[5];
							row[0] = entrys.get(i).split(Pattern.quote("{{;;}}"))[0];
							String data = entrys.get(i).split(Pattern.quote("{{;;}}"))[1];
							new OnlineDatabaseHandler(getActivity()).explode(new WebDbUser() {
								@Override
								public void gottenFromWeb(final JSONObject json, boolean good, boolean success) {
									if(success && good) {
										try {
											row[1] = json.getJSONObject("values").getJSONArray("name").getString(1);
											new OnlineDatabaseHandler(getActivity()).getAllFromDB(new WebDbUser() {
												@Override
												public void gottenFromWeb(JSONObject j, boolean good, boolean success) {
													if(success && good) {
														try {
															for(int i = 0; i < j.getJSONArray("values").length(); i++) {
																if(j.getJSONArray("values").getJSONObject(i).getJSONArray("level").getString(1).equals(json.getJSONObject("values").getJSONArray("category").getString(1)))
																	row[2] = j.getJSONArray("values").getJSONObject(i).getJSONObject("name").getJSONArray("EN").getString(1);
															}
															row[3] = json.getJSONObject("values").getJSONArray("rating").getString(1);
															double lat = Double.parseDouble(json.getJSONObject("values").getJSONObject("address").getJSONArray("c").getString(1));
															double lng = Double.parseDouble(json.getJSONObject("values").getJSONObject("address").getJSONArray("c").getString(2));
															Location l = new Location("place");
															l.setLatitude(lat);
															l.setLongitude(lng);
															String d = "";
															double dist = roundToSignificantFigures((lc.getLastLocation().distanceTo(l)/1000), 3);
															DecimalFormat df = new DecimalFormat("#####.#");
															d = df.format(dist);
												            row[4] = d + " km";
												            values.add(row);
												            if(values.size() == entrys.size()) {
																ActioArrayAdapter adapter = new ActioArrayAdapter(getActivity(), R.layout.place_list_item, values);
																rootView.findViewById(R.id.loading).setVisibility(View.GONE);
																((ListView) rootView.findViewById(R.id.list)).setAdapter(adapter);
																((ListView) rootView.findViewById(R.id.list)).setDividerHeight(0);
												            }
														} catch (JSONException e) {
															e.printStackTrace();
															Toast.makeText(getActivity(), R.string.error_sorry, Toast.LENGTH_SHORT).show();
															rootView.findViewById(R.id.loading).setVisibility(View.GONE);
														}
													} else {
														Toast.makeText(getActivity(), R.string.error_sorry, Toast.LENGTH_SHORT).show();
														rootView.findViewById(R.id.loading).setVisibility(View.GONE);
													}
												}
											}, "categories", "data", "", "");
										} catch (JSONException e) {
											e.printStackTrace();
											Toast.makeText(getActivity(), R.string.error_sorry, Toast.LENGTH_SHORT).show();
											rootView.findViewById(R.id.loading).setVisibility(View.GONE);
										}
									} else {
										Toast.makeText(getActivity(), R.string.error_sorry, Toast.LENGTH_SHORT).show();
										rootView.findViewById(R.id.loading).setVisibility(View.GONE);
									}
								}
							}, data);
						}
					} else {
						Toast.makeText(getActivity(), "There are no places nearby. Create one!", Toast.LENGTH_SHORT).show();
						rootView.findViewById(R.id.loading).setVisibility(View.GONE);
					}
				}
			}, null);
			lc.connect();
			return rootView;
		}
	}
	
	public static class FragmentLocationsNearby extends Fragment {
        
		@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.list, container, false);
            ListView lv = (ListView) rootView.findViewById(R.id.list);
            String[] values = { "Hannover" };
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.friend_list_item, R.id.text1, values);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					if(position == 0) {
						Intent i = new Intent(getActivity(), DiscoverLocationActivity.class);
						i.putExtra("id", 1);
						startActivity(i);
					}
				}
			});
            lv.setDividerHeight(0);
            return rootView;
        }
    }

	public static class FragmentLocationsGlobal extends Fragment {
        
		@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.list, container, false);
            return rootView;
        }
    }

	public static class FragmentPlacesGlobal extends Fragment {
        
		@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.list, container, false);
            return rootView;
        }
    }
	
	public static double roundToSignificantFigures(double num, int n) {
	    if(num == 0) {
	        return 0;
	    }

	    final double d = Math.ceil(Math.log10(num < 0 ? -num: num));
	    final int power = n - (int) d;

	    final double magnitude = Math.pow(10, power);
	    final long shifted = Math.round(num*magnitude);
	    return shifted/magnitude;
	}

	public static class ActioArrayAdapter extends ArrayAdapter<String> {

		Context context;
		ArrayList<String[]> values;
		
		public ActioArrayAdapter(Context context, int resource, ArrayList<String[]> objects) {
			super(context, resource, new String[objects.size()]);
			this.context = context;
		    this.values = objects;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.place_list_item, parent, false);
			TextView title = (TextView) view.findViewById(R.id.title);
			final TextView category = (TextView) view.findViewById(R.id.category);
			RatingBar rb = (RatingBar) view.findViewById(R.id.rating);
			rb.setRating(Float.parseFloat(values.get(position)[3]));
			TextView distance = (TextView) view.findViewById(R.id.distance);
			title.setText(values.get(position)[1]);
			category.setText(values.get(position)[2]);
			distance.setText(values.get(position)[4]);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(context, PlaceDetailsActivity.class);
					i.putExtra("id", Integer.parseInt(values.get(position)[0]));
					context.startActivity(i);
			        ((Activity) context).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top); 
				}
			});
			return view;
		}

	}
	
}