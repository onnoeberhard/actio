package actio.actio;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import actio.actio.functions.ActioActivity;
import actio.actio.functions.Functions;
import actio.actio.functions.ImageGallery;
import actio.actio.functions.JSONParser;
import actio.actio.functions.OnlineDatabaseHandler;
import actio.actio.functions.stuff.Place;

public class PlaceDetailsActivity extends ActioActivity {

    public static Place p;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if(getIntent().getIntExtra("id", 0) != 0) {
			int id = getIntent().getIntExtra("id", 0);
            p = new Place(id, this);
		} else {
			Toast.makeText(this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
			finish();
		}
		mFragment = new PlaceDetailsFragment();
		Bundle args = new Bundle();
		mFragment.setArguments(args);
		drawer_home = -1;
		this_is = "details";
		super.onCreate(savedInstanceState);
	}
	
	static PagerSlidingTabStrip tabs;
	static ViewPager pager;
	static MyPagerAdapter adapter;

	public static class PlaceDetailsFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.tabs, container, false);
    		tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabsn);
    		pager = (ViewPager) rootView.findViewById(R.id.pagern);
    		adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
    		pager.setAdapter(adapter);
    		pager.setOffscreenPageLimit(adapter.getCount() - 1);
    		tabs.setViewPager(pager);
    		tabs.setIndicatorColor(0xff0ab246);
    		tabs.setBackgroundResource(R.drawable.borderless_button);
            return rootView;
        }
    }
	
	public static class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { "Overview", "Details", "Comments" };

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
        		fragment = new AboutFragment();
        	if(i == 1)
        		fragment = new DetailsFragment();
        	if(i == 2)
        		fragment = new CommentsFragment();
            return fragment;
        }

	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.details, menu);
//		return true;
//	}
//
//	boolean is_bookmark = false;
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//	    switch (item.getItemId()) {
//	        case R.id.action_bookmark:
//	        	item.setIcon(is_bookmark ? R.drawable.ic_action_bookmark_no : R.drawable.ic_action_bookmark_ok);
//	        	is_bookmark = !is_bookmark;
//	            return true;
//	        default:
//	            return super.onOptionsItemSelected(item);
//	    }
//	}
	
	static GoogleMap mMap;
	static UiSettings mUiSettings;
	static TextView description;
	static int des_maxLines;
	static int des_wantLines;
	static String address;
	static double lat, lng;
	static AdView mAdView;

	static View iwas;
	static View iwasnt;
	
	static LocationClient lc;
	public static class AboutFragment extends Fragment {
		@SuppressWarnings("deprecation")
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.place_overview, container, false);

            /*Drawable[] layers = new Drawable[p.mImages.size()];
            for()*//*
*//*{
                    new BitmapDrawable(Functions.fastblur(BitmapFactory.decodeResource(getResources(), R.drawable.place_sample_image_1), 4)),
                    new BitmapDrawable(Functions.fastblur(BitmapFactory.decodeResource(getResources(), R.drawable.place_sample_image_3), 4))};*//*
            //bilder laden

            //titel anzeigen


            //categorie anzeigen

            //description anzeigen

			final View adContainer = rootView.findViewById(R.id.adContainer);
			if(new LocalDatabaseHandler(getActivity(), true).getStuffValue("premium").equals("true"))
				adContainer.setVisibility(View.GONE);
			CyclicTransitionDrawable transition = new CyclicTransitionDrawable(layers);
			((ImageView)rootView.findViewById(R.id.iv1)).setImageDrawable(transition);
			iwas = rootView.findViewById(R.id.iwas);
			iwasnt = rootView.findViewById(R.id.iwasnt);
//			((ImageView)rootView.findViewById(R.id.iv2)).setImageDrawable(transition2);
			transition.startTransition(3000, 1000);
//			((ImageView)rootView.findViewById(R.id.iv3)).setImageBitmap(fastblur(BitmapFactory.decodeResource(getResources(), R.drawable.place_sample_image_3), 8));
//			rootView.findViewById(R.id.scrollView).setVisibility(View.GONE);
//			rootView.findViewById(R.id.loading).setVisibility(View.VISIBLE);*/
//
			return rootView;
		}
	}
	
	public static class DetailsFragment extends Fragment {
        
		@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.place_details, container, false);
            rootView.findViewById(R.id.oh).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.oh1).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.oh2).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.oh3).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.oh4).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.oh5).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.oh6).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.oh7).setVisibility(View.VISIBLE);
            TextView oh1p1 = (TextView) rootView.findViewById(R.id.oh1p1);
            oh1p1.setText("Monday");
            TextView oh1p2 = (TextView) rootView.findViewById(R.id.oh1p2);
            oh1p2.setText("14 - 21 Uhr");
            TextView oh2p1 = (TextView) rootView.findViewById(R.id.oh2p1);
            oh2p1.setText("Tuesday");
            TextView oh2p2 = (TextView) rootView.findViewById(R.id.oh2p2);
            oh2p2.setText("14 - 21 Uhr");
            TextView oh3p1 = (TextView) rootView.findViewById(R.id.oh3p1);
            oh3p1.setText("Wednesday");
            TextView oh3p2 = (TextView) rootView.findViewById(R.id.oh3p2);
            oh3p2.setText("14 - 21 Uhr");
            TextView oh4p1 = (TextView) rootView.findViewById(R.id.oh4p1);
            oh4p1.setText("Thursday");
            TextView oh4p2 = (TextView) rootView.findViewById(R.id.oh4p2);
            oh4p2.setText("14 - 21 Uhr");
            TextView oh5p1 = (TextView) rootView.findViewById(R.id.oh5p1);
            oh5p1.setText("Friday");
            TextView oh5p2 = (TextView) rootView.findViewById(R.id.oh5p2);
            oh5p2.setText("11 - 21 Uhr");
            TextView oh6p1 = (TextView) rootView.findViewById(R.id.oh6p1);
            oh6p1.setText("Saturday");
            TextView oh6p2 = (TextView) rootView.findViewById(R.id.oh6p2);
            oh6p2.setText("11 - 21 Uhr");
            TextView oh7p1 = (TextView) rootView.findViewById(R.id.oh7p1);
            oh7p1.setText("Sunday");
            TextView oh7p2 = (TextView) rootView.findViewById(R.id.oh7p2);
            oh7p2.setText("11 - 21 Uhr");
            rootView.findViewById(R.id.ft).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.ft1).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.ft2).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.ft3).setVisibility(View.VISIBLE);
            TextView pr1p1 = (TextView) rootView.findViewById(R.id.ft1p1);
            pr1p1.setText("Erwachsene (ab 18. Lebensjahr)");
            TextView pr1p2 = (TextView) rootView.findViewById(R.id.ft1p2);
            pr1p2.setText("3,50�");
            TextView pr2p1 = (TextView) rootView.findViewById(R.id.ft2p1);
            pr2p1.setText("Jugendliche, Inhaber einer Sozialkarte");
            TextView pr2p2 = (TextView) rootView.findViewById(R.id.ft2p2);
            pr2p2.setText("1,50�");
            TextView pr3p1 = (TextView) rootView.findViewById(R.id.ft3p1);
            pr3p1.setText("Kinder unter 5 Jahren");
            TextView pr3p2 = (TextView) rootView.findViewById(R.id.ft3p2);
            pr3p2.setText("frei");
            rootView.findViewById(R.id.pr).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.pr1).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.pr2).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.pr3).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.pr4).setVisibility(View.VISIBLE);
            TextView ft1p1 = (TextView) rootView.findViewById(R.id.pr1p1);
            ft1p1.setText("Beach Volleyball Field");
            TextView ft1p2 = (TextView) rootView.findViewById(R.id.pr1p2);
            ft1p2.setText("Yes");
            TextView ft2p1 = (TextView) rootView.findViewById(R.id.pr2p1);
            ft2p1.setText("Table Tennis Table");
            TextView ft2p2 = (TextView) rootView.findViewById(R.id.pr2p2);
            ft2p2.setText("Yes");
            TextView ft3p1 = (TextView) rootView.findViewById(R.id.pr3p1);
            ft3p1.setText("Bar");
            TextView ft3p2 = (TextView) rootView.findViewById(R.id.pr3p2);
            ft3p2.setText("Yes");
            TextView ft4p1 = (TextView) rootView.findViewById(R.id.pr4p1);
            ft4p1.setText("Card Payment");
            TextView ft4p2 = (TextView) rootView.findViewById(R.id.pr4p2);
            ft4p2.setText("No");
            rootView.findViewById(R.id.co).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.co1).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.co2).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.co3).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.co4).setVisibility(View.VISIBLE);
            TextView co1p1 = (TextView) rootView.findViewById(R.id.co1p1);
            co1p1.setText("Phone");
            TextView co1p2 = (TextView) rootView.findViewById(R.id.co1p2);
            co1p2.setText("05031 / 69 49 36");
            TextView co2p1 = (TextView) rootView.findViewById(R.id.co2p1);
            co2p1.setText("Fax");
            TextView co2p2 = (TextView) rootView.findViewById(R.id.co2p2);
            co2p2.setText("05031 / 69 49 37");
            TextView co3p1 = (TextView) rootView.findViewById(R.id.co3p1);
            co3p1.setText("E-Mail");
            TextView co3p2 = (TextView) rootView.findViewById(R.id.co3p2);
            co3p2.setText("NEBL@htp-tel.de");
            TextView co4p1 = (TextView) rootView.findViewById(R.id.co4p1);
            co4p1.setText("Website");
            TextView co4p2 = (TextView) rootView.findViewById(R.id.co4p2);
            co4p2.setText("http://www.naturerlebnisbad-luthe.de/");
            return rootView;
        }
    }
	
	public static class CommentsFragment extends Fragment {
        
		@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.list, container, false);
            return rootView;
        }
    }	
	
	public static class GetTimeDistance extends AsyncTask<String, Void, JSONObject> {

		protected JSONObject doInBackground(String... params) {
			List<NameValuePair> list = new ArrayList<NameValuePair>();
//			list.add(new BasicNameValuePair("tag", "GET"));
//			list.add(new BasicNameValuePair("table", params[1]));
//			list.add(new BasicNameValuePair("id_name", params[2]));
//			list.add(new BasicNameValuePair("id_value", params[3]));
//			list.add(new BasicNameValuePair("column", params[4]));
			JSONParser jsonParser = new JSONParser();
			JSONObject json = jsonParser.getJSONFromUrl("http://actio.cwsurf.de/connect_api/", list);
			return json;
		}

		protected void onPostExecute(JSONObject json) {
			try {
				json.get("hallo");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void description_click(View v) {
		des_maxLines = (des_maxLines == 5) ? 100 : 5;
		description.setMaxLines(des_maxLines);
	}
	
	@SuppressWarnings("deprecation")
	public void navigate(View v) {
		String a = address;
		try {
			a = URLEncoder.encode(a, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			a = URLEncoder.encode(a);
			e.printStackTrace();
		}
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + address));
		startActivity(i);
//		String uri = String.format(Locale.ENGLISH, "geo:%f,%f", lat, lng);
//		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//		startActivity(intent);
	}
	
	public void gogallery(View v) {
		OnlineDatabaseHandler dbo = new OnlineDatabaseHandler(this);
		dbo.getFromDB(new OnlineDatabaseHandler.WebDbUser() {
			@Override
			public void gottenFromWeb(JSONObject json, boolean good,
					boolean success) {
				if(success && good) {
					try {
						String[] imgs = new String[json.getJSONObject("values").getJSONArray("img").length() - 1];
						String[] titles = new String[json.getJSONObject("values").getJSONArray("img").length() - 1];
						for(int i = 0; i < json.getJSONObject("values").getJSONArray("img").length() - 1; i++) {
							imgs[i] = json.getJSONObject("values").getJSONArray("img").getJSONArray(i+1).getString(1);
							titles[i] = json.getJSONObject("values").getJSONArray("img").getJSONArray(i+1).getString(2);
						}
						Intent i = new Intent(PlaceDetailsActivity.this, ImageGallery.class);
						i.putExtra("imgs", imgs);
						i.putExtra("titles", titles);
						i.putExtra("actid", p.mID);
						startActivity(i);
					} catch (JSONException e) {
						Toast.makeText(PlaceDetailsActivity.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();	
						e.printStackTrace();
					}
				} else {
					Toast.makeText(PlaceDetailsActivity.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();						
				}
			}
		}, "activities", "id", Integer.toString(p.mID), "data1");
	}


	public void iWas(View v) {
		iwas.setVisibility(View.GONE);
		iwasnt.setVisibility(View.VISIBLE);
	}
	
	public void iWasnt(View v) {
		iwas.setVisibility(View.VISIBLE);
		iwasnt.setVisibility(View.GONE);
	}

	public void disableAds(View v) {
		Functions.disableAds(this);
	}
	
    @Override
    protected void onPause() {
        mAdView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mAdView != null)
        	mAdView.resume();
    }

    @Override
    protected void onDestroy() {
        mAdView.destroy();
        super.onDestroy();
    }

}