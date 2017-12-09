package actio.actio;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.location.LocationClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import actio.actio.functions.ActioActivity;
import actio.actio.functions.Functions;
import actio.actio.functions.LocalDatabaseHandler;
import actio.actio.functions.SearchResultsActivity;
import actio.actio.functions.TileView;

public class ActivityMain extends ActioActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mFragment = new MainFragment();
        Bundle args = new Bundle();
        mFragment.setArguments(args);
        drawer_home = 0;
        super.onCreate(savedInstanceState);
//		if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("enableAnalytics", true)) {
//			Tracker t = ((ActioApp) getApplication()).getTracker(TrackerName.APP_TRACKER);
//	        t.setScreenName("/Home");
//	        t.send(new HitBuilders.AppViewBuilder().build());
//		}
        if(getIntent().getBooleanExtra("FROM_CONTROL", false)) {
            LocalDatabaseHandler dbl = new LocalDatabaseHandler(this);
            if(dbl.getStuffValue("rateDlgRepeat").equals("10")) {
                dbl.setStuffValue("rateDlgRepeat", "11");
                new AlertDialog.Builder(this).setTitle("Rate Us")
                        .setMessage("Please support this app by rating us in the Google Play Store! :-)")
                        .setPositiveButton("Rate", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent appIntent = new Intent(Intent.ACTION_VIEW);
                                appIntent.setData(Uri.parse("market://details?id=" + getPackageName()));
                                startActivity(appIntent);
                                dialog.dismiss();
                            }
                        })
                        .setNeutralButton("Never", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("No", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new LocalDatabaseHandler(ActivityMain.this).setStuffValue("rateDlgRepeat", "0");
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            } else if(dbl.getStuffValue("rateDlgRepeat").equals("DOES NOT EXIST")) {
                dbl.setStuffValue("rateDlgRepeat", "1");
            } else
                dbl.setStuffValue("rateDlgRepeat", Integer.toString(Integer.parseInt(dbl.getStuffValue("rateDlgRepeat")) + 1));
            dbl.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
//		final MenuItem searchItem = menu.findItem(R.id.action_search);
//	    final SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//	    mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//	    mSearchView.setOnQueryTextListener(new OnQueryTextListener() {
//
//			@Override
//			public boolean onQueryTextSubmit(String text) {
//				Intent i = new Intent(ActivityMain.this, SearchResultsActivity.class);
//				i.putExtra(SearchManager.QUERY, text);
//				startActivity(i);
//				overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
//				mSearchView.setQuery("", false);
//				MenuItemCompat.collapseActionView(searchItem);
//				return false;
//			}
//
//			@Override
//			public boolean onQueryTextChange(String arg0) {
//				return false;
//			}
//		});
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                startActivity(new Intent(this, SearchResultsActivity.class));
//	    		onSearchRequested();
//	        case android.R.id.home:
//	        	finish();
//	            overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
//	        	return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    static PagerSlidingTabStrip tabs;
    static ViewPager pager;
    static MyPagerAdapter adapter;

    public static class MainFragment extends Fragment {
        public MainFragment() {}
        LocationClient lc;

        @SuppressLint("NewApi")
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.home_fragment, container, false);
            tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabsn);
            tabs.setIndicatorColor(0xff0ab246);
            pager = (ViewPager) rootView.findViewById(R.id.pagern);
            adapter = new MyPagerAdapter(getChildFragmentManager());
            pager.setAdapter(adapter);
            pager.setOffscreenPageLimit(adapter.getCount() - 1);
            tabs.setViewPager(pager);
            tabs.setOnPageChangeListener(new OnPageChangeListener() {
                @Override
                public void onPageSelected(int arg0) {}
                @Override
                public void onPageScrolled(int position, float positionOffset, int arg2) {
                    if(position == 0)
                        rootView.findViewById(R.id.tabstrip).setAlpha(positionOffset);
                    if(rootView.findViewById(R.id.tabstrip).getAlpha() == 0)
                        rootView.findViewById(R.id.tabstrip).setVisibility(View.GONE);
                    else
                        rootView.findViewById(R.id.tabstrip).setVisibility(View.VISIBLE);
                }
                @Override
                public void onPageScrollStateChanged(int arg0) {}
            });
            return rootView;
        }
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = { "Here & Now", "Recents", "Bookmarks" };

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
                fragment = new FragmentHereNow();
            if(i == 1)
                fragment = new FragmentRecents();
            if(i == 2)
                fragment = new FragmentBookmarks();
            return fragment;
        }

    }

    static LocationClient lc;

    public static class FragmentHereNow extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.herenow_fragment, container, false);
            LocalDatabaseHandler dbl = new LocalDatabaseHandler(getActivity());
            ArrayList<String> things = dbl.getAllFromDb("herenow", "data");
            dbl.close();
            ArrayList<String[]> values = new ArrayList<String[]>();
            if(things != null)
                for(int i = 0; i < things.size(); i++) {
                    String id = things.get(i).split("[{][{][;][;][}][}]")[0];
                    String data = things.get(i).split("[{][{][;][;][}][}]")[1];
                    try {
                        JSONObject json = Functions.explode(data);
                        //					String name = json.getJSONObject("title").getJSONArray("EN").getString(1);
                        String name = json.getJSONObject("title").getJSONArray(json.getJSONObject("title").has(Locale.getDefault().getLanguage().toUpperCase()) ? Locale.getDefault().getLanguage().toUpperCase() : "EN").getString(1);
                        String img = "";
                        String[] k = { id, name, img };
                        values.add(k);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), R.string.error_sorry,	Toast.LENGTH_SHORT).show();
                    }
                }
            TileArrayAdapter adapter = new TileArrayAdapter(getActivity(), R.layout.tile_grid_item, values);
            rootView.findViewById(R.id.loading).setVisibility(View.GONE);
            ((GridView) rootView.findViewById(R.id.gridview)).setAdapter(adapter);
//            ArrayList<Integer> activities = new ArrayList<Integer>();
//            for(int i = 0; i < places.size(); i++) {
//            	try {
//					JSONObject json = LocalDatabaseHandler.explode(places.get(i));
//					JSONArray acts = json.getJSONArray("activities");
//					for(int ii = 1; ii < acts.length(); ii++)
//						activities.add(Integer.parseInt(acts.getString(i)));
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//            }
//            for(int i = 0; i < activities.size(); i++)
//            	Toast.makeText(getActivity(), activities.get(i), Toast.LENGTH_SHORT).show();
//			rootView.findViewById(R.id.loading).setVisibility(View.VISIBLE);
//			lc = new LocationClient(getActivity(), new ConnectionCallbacks() {
//
//				@Override
//				public void onDisconnected() {
//				}
//
//				@Override
//				public void onConnected(Bundle connectionHint) {
//					OnlineDatabaseHandler dbo = new OnlineDatabaseHandler(getActivity());
//					dbo.getAllFromDB(new WebDbUser() {
//						@Override
//						public void gottenFromWeb(JSONObject json, boolean good, boolean success) {
//							if (success && good) {
//								try {
//									ArrayList<String[]> values = new ArrayList<String[]>();
//									for (int i = 0; i < json.getJSONArray("values").length(); i++) {
//										String name = json.getJSONArray("values").getJSONObject(i).getJSONObject("title").getJSONArray("EN").getString(1);
////										String category = json.getJSONArray("values").getJSONObject(i).getJSONArray("category").getString(1);
//										String id = json.getJSONArray("rows").getJSONObject(i).getString("id");
//										String img = "";
//										// String img =
//										// json.getJSONArray("values").getJSONObject(i).getJSONObject("img").getJSONArray("1").getString(1);
//										String[] k = { id, name, img };
//										values.add(k);
//									}
//									TileArrayAdapter adapter = new TileArrayAdapter(getActivity(), R.layout.tile_grid_item, values);
//									rootView.findViewById(R.id.loading).setVisibility(View.GONE);
//									((GridView) rootView.findViewById(R.id.gridview)).setAdapter(adapter);
//									// ((ListView)
//									// rootView.findViewById(R.id.list)).setDividerHeight(0);
//								} catch (Exception e) {
//									e.printStackTrace();
//									Toast.makeText(getActivity(), R.string.error_sorry,	Toast.LENGTH_SHORT).show();
//								}
//							} else
//								Toast.makeText(getActivity(), R.string.error_sorry,	Toast.LENGTH_SHORT).show();
//						}
//					}, "activities", "data", "", "");
//				}
//			}, null);
//			lc.connect();
            return rootView;
        }
    }

    public static class FragmentRecents extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.recents_fragment, container, false);
            return rootView;
        }
    }

    public static class FragmentBookmarks extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.bookmarks_fragment, container, false);
            return rootView;
        }
    }

    public static class TileArrayAdapter extends ArrayAdapter<String> {

        Context context;
        ArrayList<String[]> values;

        public TileArrayAdapter(Context context, int resource, ArrayList<String[]> objects) {
            super(context, resource, new String[objects.size()]);
            this.context = context;
            this.values = objects;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final TileView view = (TileView) inflater.inflate(R.layout.tile_grid_item, parent, false);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    if(view.getWidth() > 0)
                        view.getLayoutParams().height = view.getWidth();
                    else
                        handler.postDelayed(this, 100);
                }

            }, 100);
            TextView title = (TextView) view.findViewById(R.id.title);
            ImageView iv = (ImageView) view.findViewById(R.id.image);
//			final TextView category = (TextView) view.findViewById(R.id.category);
//			TextView distance = (TextView) view.findViewById(R.id.distance);
            if(Integer.parseInt(values.get(position)[0]) == 1)
                iv.setImageResource(R.drawable.a1);
            else if(Integer.parseInt(values.get(position)[0]) == 2)
                iv.setImageResource(R.drawable.a2);
            else if(Integer.parseInt(values.get(position)[0]) == 3)
                iv.setImageResource(R.drawable.a3);
            else
                view.findViewById(R.id.image_panel).setVisibility(View.GONE);
            title.setText(values.get(position)[1]);
//			OnlineDatabaseHandler dbo = new OnlineDatabaseHandler(context);
//			dbo.getFromDB(new WebDbUser() {
//				@Override
//				public void gottenFromWeb(JSONObject json, boolean good, boolean success) {
//					try {
//						category.setText(json.getString("value"));
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//				}
//			}, "categories", "level", values.get(position)[2], "name");
//			distance.setText(values.get(position)[3]);
//			(new DownloadImageTask(iv)).execute(values.get(position)[3]);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Activity " + values.get(position)[0], Toast.LENGTH_SHORT).show();
//					Intent i = new Intent(context, DetailsActivity.class);
//					i.putExtra("id", Integer.parseInt(values.get(position)[0]));
//					context.startActivity(i);
//			        ((Activity) context).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
                }
            });
            return view;
        }

    }

}