package actio.app;

import org.json.JSONException;
import org.json.JSONObject;

import actio.app.functions.ActioActivity;
import actio.app.functions.OnlineDatabaseHandler;
import actio.app.functions.OnlineDatabaseHandler.WebDbUser;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends ActioActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		GoogleMapOptions gmo = new GoogleMapOptions();
		gmo.zoomControlsEnabled(true);
		gmo.compassEnabled(true);
		gmo.scrollGesturesEnabled(true);
		gmo.zoomGesturesEnabled(true);
		gmo.tiltGesturesEnabled(true);
		gmo.rotateGesturesEnabled(true);
		final SupportMapFragment smp = SupportMapFragment.newInstance();
		OnlineDatabaseHandler dbo = new OnlineDatabaseHandler(this);
		dbo.getAllFromDB(new WebDbUser() {
			@Override
			public void gottenFromWeb(final JSONObject json, boolean good, boolean success) {
				final Handler handler = new Handler();
				handler.post(new Runnable() {
					LocationClient lc;
					@Override
					public void run() {
						if(smp.getMap() != null) {
							lc = new LocationClient(MapActivity.this, new ConnectionCallbacks() {
									@Override
									public void onDisconnected() {
									}
									
									@Override
									public void onConnected(Bundle connectionHint) {
										smp.getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lc.getLastLocation().getLatitude(), lc.getLastLocation().getLongitude()), 9f));
									}
								}, null);
							lc.connect();
							smp.getMap().setMyLocationEnabled(true);
							smp.getMap().getUiSettings().setZoomControlsEnabled(true);
							smp.getMap().getUiSettings().setCompassEnabled(true);
							smp.getMap().getUiSettings().setMyLocationButtonEnabled(true);
							smp.getMap().getUiSettings().setScrollGesturesEnabled(true);
							smp.getMap().getUiSettings().setZoomGesturesEnabled(true);
							smp.getMap().getUiSettings().setTiltGesturesEnabled(true);
							smp.getMap().getUiSettings().setRotateGesturesEnabled(true);
							smp.getMap().setInfoWindowAdapter(new InfoWindowAdapter() {
					            @Override
					            public View getInfoWindow(Marker args) {
					                return null;
					            }
					            @Override
					            public View getInfoContents(final Marker args) {
					                View v = getLayoutInflater().inflate(R.layout.text, null);
					                TextView title = (TextView) v.findViewById(R.id.text);
					                title.setText(args.getTitle());
					                smp.getMap().setOnInfoWindowClickListener(new OnInfoWindowClickListener() {          
					                    public void onInfoWindowClick(Marker marker) {
					                    	Intent i = new Intent(MapActivity.this, PlaceDetailsActivity.class);
					                    	i.putExtra("id", Integer.parseInt(args.getSnippet()));
					                    	startActivity(i);
					                    }
					                });
					                return v;
					            }
					        });  
							try {
								for(int i = 0; i < json.getJSONArray("values").length(); i++) {
									double lat = Double.parseDouble(json.getJSONArray("values").getJSONObject(i).getJSONObject("address").getJSONArray("c").getString(1));
									double lng = Double.parseDouble(json.getJSONArray("values").getJSONObject(i).getJSONObject("address").getJSONArray("c").getString(2));
									String name = json.getJSONArray("values").getJSONObject(i).getJSONArray("name").getString(1);
									String id = json.getJSONArray("rows").getJSONObject(i).getString("id");
									smp.getMap().addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(name).snippet(id).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
							handler.removeCallbacksAndMessages(null);
						} else
							handler.postDelayed(this, 500);
					}
					
			 	});
			}
		}, "places", "data", "", "");
	    mFragment = smp;
	    Bundle args = new Bundle();
	    mFragment.setArguments(args);
    	drawer_home = 2;
		super.onCreate(savedInstanceState);
		from_details = getIntent().getBooleanExtra("FROM_DETAILS", false);
	}
	
}