package actio.actio.functions;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

import com.facebook.AppEventsLogger;
import com.google.analytics.tracking.android.Tracker;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

import org.json.JSONException;
import org.json.JSONObject;

import actio.actio.ActivityStart;
import actio.actio.DevNoteActivity;
import actio.actio.FriendsActivtiy;
import actio.actio.R;

public class ActivityControl extends ActionBarActivity {

    public static final int dist_nearby = 1000000;
    public static final boolean dev_offline = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        //Show Loading-Screen
		setContentView(R.layout.start_activity);
		(findViewById(R.id.startpg_counter)).setVisibility(View.GONE);
		(findViewById(R.id.actio_logo)).setVisibility(View.VISIBLE);
		(findViewById(R.id.loading)).setVisibility(View.VISIBLE);
		getSupportActionBar().hide();

        //Google Analytics: App launched!
		if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("enableAnalytics", true)) {
			Tracker t = ((ActioApp) getApplication()).getTracker(ActioApp.TrackerName.APP_TRACKER);
	        t.send(new HitBuilders.EventBuilder().setCategory("General").setAction("Application_Start").build());
		}

        //Looks for internet connection. Launches goon().
        if(!dev_offline) {
            if (Functions.isConnectingToInternet(this))
                goon();
            else if (!dev_offline)
                showAlertNoInternetConnection();
        } else
            goon();

        /*//Google Analytics Example
        t.setScreenName("/ActivityControl");
        t.send(new HitBuilders.AppViewBuilder().build());
        t.send(new HitBuilders.EventBuilder().setCategory("thecategory").setAction("theaction").setLabel("alabel").build());
        GoogleAnalytics.getInstance(getBaseContext()).dispatchLocalHits();*/

        /*//facebook key hash
		try {
	        PackageInfo info = getPackageManager().getPackageInfo(
	                "actio.actio",
	                PackageManager.GET_SIGNATURES);
	        for (Signature signature : info.signatures) {
	            MessageDigest md = MessageDigest.getInstance("SHA");
	            md.update(signature.toByteArray());
	            System.out.println(Base64.encodeToString(md.digest(), Base64.DEFAULT));
	            Toast.makeText(this, Base64.encodeToString(md.digest(), Base64.DEFAULT), Toast.LENGTH_LONG).show();
	            }
	    } catch (PackageManager.NameNotFoundException e) {
	    } catch (NoSuchAlgorithmException e) {
	    }
		finish();*/
	}


	LocationClient lc;
    /**
     * Loads Data from Internet into LDB.
     * Refreshes Data already in LDB.
     * Decides what Activity to start next:
     *      -ActivityStart
     *      -DevNoteActivity
     *      -ActivityControl
     */
	public void goon() {
        final LocalDatabaseHandler dbl = new LocalDatabaseHandler(this);

        //Load Friends into LDB
        if (!(new LocalDatabaseHandler(this, true)).getStuffValue("username").equals("DOES NOT EXIST"))
            FriendsActivtiy.find(this, null, false);

        /*//Looks if user payed for disabling advertising - writes result into LDB
		String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkTjNpPiDhu0HDyfZnwV2sF3F7kcUa95bD8XEvZ80yrWbYkQP/lOym5DCkh88KNCsZMp8jeU0FCCkOzpuOh+eEjBrEQvkmXU7fSziAzUs5AzNKciNCvPFJlYPM3vF3oUhoC/2rl1VJOemzT+hezyGD9jn5mvPyyvRTDE5+7wkLKQCf/P+knUVFbS5/gqHVMsZXLnX+NuizvQNqQdFqNBUJK/gmplLPZqfmHaDi9LmbYdKY6QnpB1BHXnqAll6Zeuk0X0CwaXLGhgDFVdoebhVGilIuc+8IsjQ8O2h9Rtog2lXA3j0neqGfm1dxW6rheJpqaIOI9igj8jNWxhveEk3iQIDAQAB";
		final String SKU = "android.test.purchased";
		final IabHelper mHelper = new IabHelper(this, base64EncodedPublicKey);
		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
//				mHelper.queryInventoryAsync(new IabHelper.QueryInventoryFinishedListener() {
//					@Override
//					public void onQueryInventoryFinished(IabResult result, Inventory inv) {
//						Purchase premiumPurchase = inv.getPurchase(SKU);
//			            if(premiumPurchase != null)
//			            	dbl.setStuffValue("premium", "true");
//			            else if(!dbl.getStuffValue("premium").equals("DOES NOT EXIST"))
//			            	dbl.setStuffValue("premium", "*DELETE*");
//					}
//				});
			}
		});*/

        new OnlineDatabaseHandler(ActivityControl.this).getAllFromDB(new OnlineDatabaseHandler.WebDbUser() {
            @Override
            public void gottenFromWeb(final JSONObject json, final boolean good, final boolean success) {
                if(success && good) {
                    lc = new LocationClient(ActivityControl.this, new GooglePlayServicesClient.ConnectionCallbacks() {
                        @Override
                        public void onDisconnected() {}
                        @Override
                        public void onConnected(Bundle connectionHint) {
                            try {
                                //If here, all places from ODB loaded + location established
//                                final ArrayList<String> acts = new ArrayList<String>();
                                final LocalDatabaseHandler dbl = new LocalDatabaseHandler(ActivityControl.this);
                                //Delete old places from LDB
//                                dbl.execSQL("DELETE FROM `nearby_places`");
                                dbl.execSQL("DELETE FROM `herenow`");
                                String[] places = new String[json.getJSONArray("rows").length()];
                                for(int i = 0; i < json.getJSONArray("rows").length(); i++)
                                    places[i] = json.getJSONArray("rows").getJSONObject(i).getString("id") + "{{;;}}" + json.getJSONArray("value").getString(i);
                                for(int i = 0; i < json.getJSONArray("rows").length(); i++) {
                                    Location l = new Location("place");
                                    l.setLatitude(Double.parseDouble(json.getJSONArray("values").getJSONObject(i).getJSONObject("address").getJSONArray("c").getString(1)));
                                    l.setLongitude(Double.parseDouble(json.getJSONArray("values").getJSONObject(i).getJSONObject("address").getJSONArray("c").getString(2)));
                                    if(lc.getLastLocation().distanceTo(l) < dist_nearby) {
                                        dbl.smartInUp("herenow", "data", places[i], "data", places[i]);
                                            /*for(int ii = 1; ii < json.getJSONArray("values").getJSONObject(i).getJSONArray("activities").length(); ii++)
                                                if(!acts.contains(json.getJSONArray("values").getJSONObject(i).getJSONArray("activities").getString(ii)))
                                                    acts.add(json.getJSONArray("values").getJSONObject(i).getJSONArray("activities").getString(ii));*/
                                    }
                                }
                                    /*for(int i = 0; i < acts.size(); i++) {
                                        final int o = i;
                                        new OnlineDatabaseHandler(ActivityControl.this).getFromDB(new OnlineDatabaseHandler.WebDbUser() {
                                            @Override
                                            public void gottenFromWeb(JSONObject json, boolean good, boolean success) {
                                                if(success && good) {
                                                    try {
                                                        String data = acts.get(o) + "{{;;}}" + json.getString("value");
                                                        dbl.smartInUp("herenow", "data", data, "data", data);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                        Toast.makeText(ActivityControl.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
                                                    }
                                                    if(o == acts.size() - 1) {
                                                        dbl.close();
                                                        startActivity(new Intent(ActivityControl.this, DevNoteActivity.class).putExtra("FROM_CONTROL", true));
                                                        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                                                        finish();
                                                    }
                                                } else {
                                                    Toast.makeText(ActivityControl.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
                                                    dbl.close();
                                                    startActivity(new Intent(ActivityControl.this, DevNoteActivity.class).putExtra("FROM_CONTROL", true));
                                                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                                                    finish();
                                                }
                                            }
                                        }, "activities", "id", acts.get(i), "data");
                                    } if(acts.size() == 0) {*/

                                //If User isn't logged in and hasn't checked box "don't show log in screen", show log in screen
                                if(dbl.getStuffValue("username").equals("DOES NOT EXIST") && !dbl.getStuffValue("lr_skipped").equals("forever")) {
                                    dbl.close();
                                    startActivity(new Intent(ActivityControl.this, ActivityStart.class));
                                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                                    finish();
                                }
                                //Else if he isn't logged in (has checked box)
                                else if(dbl.getStuffValue("username").equals("DOES NOT EXIST")) {
                                    dbl.close();
                                    startActivity(new Intent(ActivityControl.this, DevNoteActivity.class).putExtra("FROM_CONTROL", true));
                                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                                    finish();
                                }
                                //Else if logged in
                                else {
                                    setContentView(R.layout.start_activity);
                                    (findViewById(R.id.startpg_counter)).setVisibility(View.GONE);
                                    (findViewById(R.id.actio_logo)).setVisibility(View.VISIBLE);
                                    getSupportActionBar().hide();
                                    OnlineDatabaseHandler dbo = new OnlineDatabaseHandler(ActivityControl.this);
                                    dbo.getFromDB(new OnlineDatabaseHandler.WebDbUser() {
                                        @Override
                                        public void gottenFromWeb(JSONObject json, boolean good, boolean success) {
                                            if (success && good) {
                                                try {
                                                    if (dbl.getStuffValue("password").equals(json.getString("value"))) {
                                                        dbl.close();
                                                        startActivity(new Intent(ActivityControl.this, DevNoteActivity.class).putExtra("FROM_CONTROL", true));
                                                        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                                                        finish();
                                                    } else {
                                                        dbl.execSQL("DELETE FROM stuff WHERE `name` = 'username'");
                                                        dbl.execSQL("DELETE FROM stuff WHERE `name` = 'password'");
                                                        dbl.setStuffValue("lr_skipped", "true");
                                                        dbl.close();
                                                        startActivity(new Intent(ActivityControl.this, ActivityControl.class));
                                                        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                                                        finish();
                                                    }
                                                } catch (JSONException e) {
                                                    (findViewById(R.id.loading)).setVisibility(View.GONE);
                                                    Toast.makeText(ActivityControl.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                (findViewById(R.id.loading)).setVisibility(View.GONE);
                                                Toast.makeText(ActivityControl.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }, "accounts", "username", dbl.getStuffValue("username"), "password");
                                }

                            } catch (JSONException e) {
                                (findViewById(R.id.loading)).setVisibility(View.GONE);
                                Toast.makeText(ActivityControl.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    }, null);
                    lc.connect();
                } else {
                    (findViewById(R.id.loading)).setVisibility(View.GONE);
                    Toast.makeText(ActivityControl.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
                }
            }
        }, "places", "data", "", "");
	}
	
	public void showAlertNoInternetConnection() {
		setContentView(R.layout.start_activity);
		(findViewById(R.id.startpg_counter)).setVisibility(View.GONE);
		(findViewById(R.id.actio_logo)).setVisibility(View.VISIBLE);
		getSupportActionBar().hide();
		new AlertDialog.Builder(this)
		.setCancelable(false)
		.setMessage("It seems that you have no internet connection!")
		.setPositiveButton("Try again!", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
                if (Functions.isConnectingToInternet(ActivityControl.this))
                    goon();
                else if (!dev_offline)
                    showAlertNoInternetConnection();
				dialog.dismiss();
			}
		})
		.setNegativeButton(android.R.string.cancel, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				finish();
			}
		}).create().show();
	}

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }
}