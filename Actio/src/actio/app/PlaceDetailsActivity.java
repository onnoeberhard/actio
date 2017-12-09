package actio.app;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import actio.app.functions.ActioActivity;
import actio.app.functions.CyclicTransitionDrawable;
import actio.app.functions.Functions;
import actio.app.functions.ImageGallery;
import actio.app.functions.JSONParser;
import actio.app.functions.LocalDatabaseHandler;
import actio.app.functions.OnlineDatabaseHandler;
import actio.app.functions.OnlineDatabaseHandler.WebDbUser;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.astuetz.PagerSlidingTabStrip;
import com.facebook.internal.Utility.FetchedAppSettings;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;

public class PlaceDetailsActivity extends ActioActivity {

	static int id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if(getIntent().getIntExtra("id", 0) != 0) {
			id = getIntent().getIntExtra("id", 0);
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
//		setSupportProgressBarIndeterminateVisibility(true);
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
	}
	
	boolean is_bookmark = false;
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.action_bookmark:
	        	item.setIcon(is_bookmark ? R.drawable.ic_action_bookmark_no : R.drawable.ic_action_bookmark_ok);
	        	is_bookmark = !is_bookmark;
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	static GoogleMap mMap;
	static UiSettings mUiSettings;
	static TextView description;
	static int des_maxLines;
	static int des_wantLines;
	static String address;
	static double lat, lng;
	static AdView mAdView;
	static boolean isPremium = false;
	
	static View iwas;
	static View iwasnt;
	
	static LocationClient lc;
	public static class AboutFragment extends Fragment {
		@SuppressWarnings("deprecation")
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.place_overview, container, false);
			final View adContainer = rootView.findViewById(R.id.adContainer);
			if(new LocalDatabaseHandler(getActivity()).getStuffValue("premium").equals("true"))
				adContainer.setVisibility(View.GONE);
			Drawable[] layers = {new BitmapDrawable(fastblur(BitmapFactory.decodeResource(getResources(), R.drawable.place_sample_image_1), 4)), 
					new BitmapDrawable(fastblur(BitmapFactory.decodeResource(getResources(), R.drawable.place_sample_image_3), 4))};
			CyclicTransitionDrawable transition = new CyclicTransitionDrawable(layers);
			((ImageView)rootView.findViewById(R.id.iv1)).setImageDrawable(transition);
			iwas = rootView.findViewById(R.id.iwas);
			iwasnt = rootView.findViewById(R.id.iwasnt);
//			((ImageView)rootView.findViewById(R.id.iv2)).setImageDrawable(transition2);
			transition.startTransition(3000, 1000);
//			((ImageView)rootView.findViewById(R.id.iv3)).setImageBitmap(fastblur(BitmapFactory.decodeResource(getResources(), R.drawable.place_sample_image_3), 8));
//			rootView.findViewById(R.id.scrollView).setVisibility(View.GONE);
//			rootView.findViewById(R.id.loading).setVisibility(View.VISIBLE);
//			OnlineDatabaseHandler dbo = new OnlineDatabaseHandler(getActivity());
//			dbo.getFromDB(new WebDbUser() {
//				@Override
//				public void gottenFromWeb(final JSONObject json, boolean good,
//						boolean success) {
//					if(success && good) {
////						((ActioActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(false);
//						try {
//							((TextView)rootView.findViewById(R.id.title)).setText(json.getJSONObject("values").getJSONArray("name").getString(1));
//							address = json.getJSONObject("values").getJSONObject("address").getJSONArray("a").getString(1);
//							((TextView)rootView.findViewById(R.id.category)).setText(json.getJSONObject("values").getJSONArray("category").getString(1));
//							OnlineDatabaseHandler dbo = new OnlineDatabaseHandler(getActivity());
//							dbo.getFromDB(new WebDbUser() {
//								@Override
//								public void gottenFromWeb(JSONObject json, boolean good, boolean success) {
//									try {
//										((TextView)rootView.findViewById(R.id.category)).setText(json.getString("value"));
//									} catch (JSONException e) {
//										e.printStackTrace();
//									}
//								}
//							}, "categories", "level", json.getJSONObject("values").getJSONArray("category").get(1).toString(), "name");
//							description = ((TextView)rootView.findViewById(R.id.description));
//							if(!json.getJSONObject("values").has("description"))
//								(rootView.findViewById(R.id.description_card)).setVisibility(View.GONE);
//							else
//								description.setText(json.getJSONObject("values").getJSONArray("description").get(1).toString());
//							des_wantLines = description.getLineCount();
//							des_maxLines = 5;
//							description.setMaxLines(des_maxLines);
//							if(des_wantLines > des_maxLines)
//								description.setEllipsize(TruncateAt.END);
//							mMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
//				            if (mMap != null) {//52.423823, 9.471795
//				            	lat = json.getJSONObject("values").getJSONObject("address").getJSONArray("c").getDouble(1);
//				            	lng = json.getJSONObject("values").getJSONObject("address").getJSONArray("c").getDouble(2);
//				            	mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
//				            	mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 13.5f));
//				            	mMap.setMyLocationEnabled(false);
//				                mUiSettings = mMap.getUiSettings();
//				                mUiSettings.setZoomControlsEnabled(true);
//				                mUiSettings.setCompassEnabled(false);
//				                mUiSettings.setMyLocationButtonEnabled(false);
//				                mUiSettings.setScrollGesturesEnabled(false);
//				                mUiSettings.setZoomGesturesEnabled(false);
//				                mUiSettings.setTiltGesturesEnabled(true);
//				                mUiSettings.setRotateGesturesEnabled(false);
//				                lc = new LocationClient(getActivity(), new ConnectionCallbacks() {
//									
//									@Override
//									public void onDisconnected() {
//									}
//									
//									@Override
//									public void onConnected(Bundle connectionHint) {
//							            System.out.println(Double.toString(lc.getLastLocation().getLatitude()));
//							            System.out.println(Double.toString(lc.getLastLocation().getLongitude()));
//							            try {
//											System.out.println(json.getJSONObject("values").getJSONObject("address").getJSONArray("c").getString(1));
//								            System.out.println(json.getJSONObject("values").getJSONObject("address").getJSONArray("c").getString(2));
//										} catch (JSONException e) {
//											e.printStackTrace();
//										}
//									}
//								}, null);
//				                lc.connect();
////					            System.out.println(Double.toString(mMap.getMyLocation().getLatitude()));
////					            System.out.println(Double.toString(mMap.getMyLocation().getLongitude()));
//				            }
////				            (new GetTimeDistance()).execute(Double.toString(mMap.getMyLocation().getLatitude()), Double.toString(mMap.getMyLocation().getLongitude()), json.getJSONObject("values").getJSONObject("address").getJSONArray("c").getString(1), json.getJSONObject("values").getJSONObject("address").getJSONArray("c").getString(2));
				            mAdView = (AdView)rootView.findViewById(R.id.ad);
				            mAdView.loadAd(new AdRequest.Builder().build());
//				            if(!PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("slowConnection", false)) {
//								for(int i = 1; i < ((json.getJSONObject("values").getJSONArray("img").length() < 4) ? json.getJSONObject("values").getJSONArray("img").length() : 4); i++) {
//									(new DownloadImageTask((ImageView) rootView.findViewById((i == 1 ? R.id.iv1 : ((i == 2) ? R.id.iv2 : R.id.iv3))), (ProgressBar) rootView.findViewById((i == 1 ? R.id.loading1 : ((i == 2) ? R.id.loading2 : R.id.loading3))))).execute(
//											json.getJSONObject("values").getJSONArray("img").getJSONArray(i).getString(1));
//								}
//				            } else {
////				            	((View)rootView.findViewById(R.id.img_wrapper)).setVisibility(View.GONE);
////				            	((View)rootView.findViewById(R.id.img_wrapper_sc)).setVisibility(View.VISIBLE);
//				            }
//							rootView.findViewById(R.id.loading).setVisibility(View.GONE);
//							rootView.findViewById(R.id.scrollView).setVisibility(View.VISIBLE);
//						} catch (JSONException e) {
//							Toast.makeText(getActivity(), R.string.error_sorry, Toast.LENGTH_SHORT).show();	
//							e.printStackTrace();
//						}
//					} else {
//						Toast.makeText(getActivity(), R.string.error_sorry, Toast.LENGTH_SHORT).show();						
//					}
//				}
//			}, "places", "id", Integer.toString(id), "data");
			return rootView;
		}
	}
	
	public static Bitmap fastblur(Bitmap sentBitmap, int radius) {
        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                pix[yi] = ( 0xff000000 & pix[yi] ) | ( dv[rsum] << 16 ) | ( dv[gsum] << 8 ) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
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
            pr1p2.setText("3,50€");
            TextView pr2p1 = (TextView) rootView.findViewById(R.id.ft2p1);
            pr2p1.setText("Jugendliche, Inhaber einer Sozialkarte");
            TextView pr2p2 = (TextView) rootView.findViewById(R.id.ft2p2);
            pr2p2.setText("1,50€");
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
		dbo.getFromDB(new WebDbUser() {
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
						i.putExtra("actid", id);
						startActivity(i);
					} catch (JSONException e) {
						Toast.makeText(PlaceDetailsActivity.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();	
						e.printStackTrace();
					}
				} else {
					Toast.makeText(PlaceDetailsActivity.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();						
				}
			}
		}, "activities", "id", Integer.toString(id), "data1");
	}

	public static class DownloadImageTask extends
			AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;
		ProgressBar progress;

		public DownloadImageTask(ImageView bmImage) {
			this(bmImage, null);
		}
		
		public DownloadImageTask(ImageView bmImage, ProgressBar progress) {
			this.bmImage = bmImage;
			this.progress = progress;
			if(progress != null)
				progress.setVisibility(View.VISIBLE);
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			if(progress != null)
				progress.setVisibility(View.GONE);
			bmImage.setImageBitmap(result);
		}
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