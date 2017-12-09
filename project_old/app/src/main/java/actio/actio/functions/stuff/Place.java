package actio.actio.functions.stuff;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

public class Place {

    Context context;
    public int mID;
    String mName;
    String mCategory;
    String mDescription;
    public ArrayList<Bitmap> mImages;


    //rating
    //comments
    //images
    //adresse
    //opening hours
    //location
    //prices
    //people who've been there (friends..?)
    //features

    public Place(int id, Context c) {
        context = c;
        mID = id;
        /*OnlineDatabaseHandler dbo = new OnlineDatabaseHandler(context);
        dbo.getFromDB(new OnlineDatabaseHandler.WebDbUser() {
            @Override
            public void gottenFromWeb(final JSONObject json, boolean good,
                                      boolean success) {
                if (success && good) {
//						((ActioActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(false);
                    try {
                        ((TextView) rootView.findViewById(R.id.title)).setText(json.getJSONObject("values").getJSONArray("name").getString(1));
                        address = json.getJSONObject("values").getJSONObject("address").getJSONArray("a").getString(1);
                        ((TextView) rootView.findViewById(R.id.category)).setText(json.getJSONObject("values").getJSONArray("category").getString(1));
                        OnlineDatabaseHandler dbo = new OnlineDatabaseHandler(getActivity());
                        dbo.getFromDB(new WebDbUser() {
                            @Override
                            public void gottenFromWeb(JSONObject json, boolean good, boolean success) {
                                try {
                                    ((TextView) rootView.findViewById(R.id.category)).setText(json.getString("value"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, "categories", "level", json.getJSONObject("values").getJSONArray("category").get(1).toString(), "name");
                        description = ((TextView) rootView.findViewById(R.id.description));
                        if (!json.getJSONObject("values").has("description"))
                            (rootView.findViewById(R.id.description_card)).setVisibility(View.GONE);
                        else
                            description.setText(json.getJSONObject("values").getJSONArray("description").get(1).toString());
                        des_wantLines = description.getLineCount();
                        des_maxLines = 5;
                        description.setMaxLines(des_maxLines);
                        if (des_wantLines > des_maxLines)
                            description.setEllipsize(TruncateAt.END);
                        mMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
                        if (mMap != null) {//52.423823, 9.471795
                            lat = json.getJSONObject("values").getJSONObject("address").getJSONArray("c").getDouble(1);
                            lng = json.getJSONObject("values").getJSONObject("address").getJSONArray("c").getDouble(2);
                            mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 13.5f));
                            mMap.setMyLocationEnabled(false);
                            mUiSettings = mMap.getUiSettings();
                            mUiSettings.setZoomControlsEnabled(true);
                            mUiSettings.setCompassEnabled(false);
                            mUiSettings.setMyLocationButtonEnabled(false);
                            mUiSettings.setScrollGesturesEnabled(false);
                            mUiSettings.setZoomGesturesEnabled(false);
                            mUiSettings.setTiltGesturesEnabled(true);
                            mUiSettings.setRotateGesturesEnabled(false);
                            lc = new LocationClient(getActivity(), new ConnectionCallbacks() {

                                @Override
                                public void onDisconnected() {
                                }

                                @Override
                                public void onConnected(Bundle connectionHint) {
                                    System.out.println(Double.toString(lc.getLastLocation().getLatitude()));
                                    System.out.println(Double.toString(lc.getLastLocation().getLongitude()));
                                    try {
                                        System.out.println(json.getJSONObject("values").getJSONObject("address").getJSONArray("c").getString(1));
                                        System.out.println(json.getJSONObject("values").getJSONObject("address").getJSONArray("c").getString(2));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, null);
                            lc.connect();
//					            System.out.println(Double.toString(mMap.getMyLocation().getLatitude()));
//					            System.out.println(Double.toString(mMap.getMyLocation().getLongitude()));
                        }
//				        (new GetTimeDistance()).execute(Double.toString(mMap.getMyLocation().getLatitude()), Double.toString(mMap.getMyLocation().getLongitude()), json.getJSONObject("values").getJSONObject("address").getJSONArray("c").getString(1), json.getJSONObject("values").getJSONObject("address").getJSONArray("c").getString(2));
                        mAdView = (AdView) rootView.findViewById(R.id.ad);
                        mAdView.loadAd(new AdRequest.Builder().build());
                        if (!PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("slowConnection", false)) {
                            for (int i = 1; i < ((json.getJSONObject("values").getJSONArray("img").length() < 4) ? json.getJSONObject("values").getJSONArray("img").length() : 4); i++) {
                                (new DownloadImageTask((ImageView) rootView.findViewById((i == 1 ? R.id.iv1 : ((i == 2) ? R.id.iv2 : R.id.iv3))), (ProgressBar) rootView.findViewById((i == 1 ? R.id.loading1 : ((i == 2) ? R.id.loading2 : R.id.loading3))))).execute(
                                        json.getJSONObject("values").getJSONArray("img").getJSONArray(i).getString(1));
                            }
                        } else {
//				            	((View)rootView.findViewById(R.id.img_wrapper)).setVisibility(View.GONE);
//				            	((View)rootView.findViewById(R.id.img_wrapper_sc)).setVisibility(View.VISIBLE);
                        }
                        rootView.findViewById(R.id.loading).setVisibility(View.GONE);
                        rootView.findViewById(R.id.scrollView).setVisibility(View.VISIBLE);
                    } catch (
                            JSONException e
                            )

                    {
                        Toast.makeText(getActivity(), R.string.error_sorry, Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                } else

                {
                    Toast.makeText(getActivity(), R.string.error_sorry, Toast.LENGTH_SHORT).show();
                }
            }
        }, "places", "id", Integer.toString(id), "data");*/
    }
}