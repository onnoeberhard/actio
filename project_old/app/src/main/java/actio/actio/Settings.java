package actio.actio;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.support.v4.content.IntentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;

import actio.actio.functions.ActivityControl;
import actio.actio.functions.Functions;
import actio.actio.functions.LocalDatabaseHandler;
import actio.actio.functions.OnlineDatabaseHandler;

public class Settings extends PreferenceActivity{

	PreferenceCategory accountPrefCat;
//		PreferenceScreen myProfile;
		PreferenceScreen linkPhoneNumber;
		PreferenceScreen linkFacebook;
		PreferenceScreen changePassword;
		PreferenceScreen deleteAccount;
		PreferenceScreen logInOut;
	PreferenceCategory generalPrefCat;
		CheckBoxPreference enableGameFunctions;
		CheckBoxPreference slowConnection;
	PreferenceCategory morePrefCat;
		CheckBoxPreference enableAnalytics;
		PreferenceScreen disableAds;
		PreferenceScreen rateUs;
		
    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPreferenceScreen(createPreferenceHierarchy());
    }

    @SuppressWarnings({ "deprecation"})
	public PreferenceScreen createPreferenceHierarchy(){
    	
		final LocalDatabaseHandler dbl = new LocalDatabaseHandler(this);

    	PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);

    	accountPrefCat = new PreferenceCategory(this);
    	accountPrefCat.setTitle("Account");
		root.addPreference(accountPrefCat);

    	if(!dbl.getStuffValue("username").equals("DOES NOT EXIST")) {
//    		myProfile = getPreferenceManager().createPreferenceScreen(this);
//    		myProfile.setKey("myProfile");
//    		myProfile.setTitle("My Profile");
//    		myProfile.setSummary("Logged in as " + dbl.getStuffValue("username"));
//    		myProfile.setOnPreferenceClickListener(new OnPreferenceClickListener() {
//    			@Override
//    			public boolean onPreferenceClick(Preference preference) {
//    		        Intent i = new Intent(this, ProfileActivity.class);
//    		        i.putExtra("username", dbl.getStuffValue("username"));
//    				startActivity(i);
//			        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
//    				finish();
//    				return false;
//    			}
//        	});
//    		accountPrefCat.addPreference(myProfile);
    		
    		linkPhoneNumber = getPreferenceManager().createPreferenceScreen(this);
    		linkPhoneNumber.setKey("linkPhoneNumber");
    		linkPhoneNumber.setTitle("Phone Number");
	    	OnlineDatabaseHandler dbo = new OnlineDatabaseHandler(this);
	    	dbo.getFromDB(new OnlineDatabaseHandler.WebDbUser() {
				@Override
				public void gottenFromWeb(JSONObject json, boolean good,
						boolean success) {
					if(success && good) {
				    	try {
				    		linkPhoneNumber.setSummary(json.getString("value"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
	    	}, "account_links", "user", dbl.getStuffValue("username"), "phone");
	    	linkPhoneNumber.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference) {
					Intent i = new Intent(Settings.this, PhoneNumberActivity.class);
					i.putExtra("from_settings", true);
					startActivity(i);
					return false;
				}
	    	});
	    	accountPrefCat.addPreference(linkPhoneNumber);
	    	
	    	linkFacebook = getPreferenceManager().createPreferenceScreen(this);
	    	linkFacebook.setKey("linkFacebook");
	    	linkFacebook.setTitle("Facebook");
			if(Session.getActiveSession() != null && Session.getActiveSession().isOpened())
				linkFacebook.setSummary("Logged in");
			linkFacebook.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference) {
					Intent i = new Intent(Settings.this, FacebookLinkActivity.class);
					i.putExtra("from_settings", true);
					startActivity(i);
					return false;
				}
	    	});
	    	accountPrefCat.addPreference(linkFacebook);
	    	
	    	changePassword = getPreferenceManager().createPreferenceScreen(this);
	    	changePassword.setKey("changePassword");
	    	changePassword.setTitle("Change Password");
	    	changePassword.setOnPreferenceClickListener(new OnPreferenceClickListener() {
    			@Override
    			public boolean onPreferenceClick(Preference preference) {
    				final View v = LayoutInflater.from(Settings.this).inflate(R.layout.changepassworddlg, null);
    				final AlertDialog d = new AlertDialog.Builder(Settings.this)
    					.setPositiveButton(android.R.string.ok, null)
						.setNegativeButton(android.R.string.cancel, new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						})
						.setView(v)
						.create();
    				d.setOnShowListener(new OnShowListener() {
						@Override
						public void onShow(DialogInterface dialog) {
							Button b = d.getButton(AlertDialog.BUTTON_POSITIVE);
    				        b.setOnClickListener(new View.OnClickListener() {
    				            @Override
    				            public void onClick(View view) {
    				            	String ecpw = ((EditText)v.findViewById(R.id.cpw)).getText().toString();
    								final String npw = ((EditText)v.findViewById(R.id.npw)).getText().toString();
    								String rnpw = ((EditText)v.findViewById(R.id.rnpw)).getText().toString();
    								try {
    									if(!OnlineDatabaseHandler.md5(ecpw).equals(dbl.getStuffValue("password"))) {
    										Toast.makeText(Settings.this, "Wrong current password!", Toast.LENGTH_SHORT).show();
    									} else if(npw.equals("")) {
    										Toast.makeText(Settings.this, "Enter a new password!", Toast.LENGTH_SHORT).show();
    									} else if(!npw.equals(rnpw)) {
    										Toast.makeText(Settings.this, "The new passwords don\'t match!", Toast.LENGTH_SHORT).show();
    									} else {
    										OnlineDatabaseHandler dbo = new OnlineDatabaseHandler(Settings.this);
    										dbo.smartInUp(new OnlineDatabaseHandler.WebDbUser() {
    											@Override
    											public void gottenFromWeb(JSONObject json, boolean good, boolean success) {
    												if(good && success) {
    													try {
															dbl.setStuffValue("password", OnlineDatabaseHandler.md5(npw));
														} catch (NoSuchAlgorithmException e) {
															e.printStackTrace();
														}
    													d.dismiss();
    												} else
    													Toast.makeText(Settings.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
    											}
    										}, "accounts", "username", dbl.getStuffValue("username"), "password", OnlineDatabaseHandler.md5(npw), "", "", "", "", "", "");
    									}
    								} catch (NoSuchAlgorithmException e) {
    									e.printStackTrace();
    									Toast.makeText(Settings.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
    								}
    				            }
    				        });
						}
					});
    				d.show();
    				return false;
    			}
        	});
    		accountPrefCat.addPreference(changePassword);
    		
    		deleteAccount = getPreferenceManager().createPreferenceScreen(this);
    		deleteAccount.setKey("deleteAccount");
    		deleteAccount.setTitle("Delete Account");
    		deleteAccount.setOnPreferenceClickListener(new OnPreferenceClickListener() {
    			@Override
    			public boolean onPreferenceClick(Preference preference) {
    				final View v = LayoutInflater.from(Settings.this).inflate(R.layout.deleteaccountdlg, null);
    				final AlertDialog d = new AlertDialog.Builder(Settings.this)
    					.setPositiveButton(android.R.string.ok, null)
						.setNegativeButton(android.R.string.cancel, new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						})
						.setView(v)
						.create();
    				d.setOnShowListener(new OnShowListener() {
						@Override
						public void onShow(DialogInterface dialog) {
							Button b = d.getButton(AlertDialog.BUTTON_POSITIVE);
    				        b.setOnClickListener(new View.OnClickListener() {
    				            @Override
    				            public void onClick(View view) {
    				            	String pw = ((EditText)v.findViewById(R.id.pw)).getText().toString();
    								try {
    									if(!OnlineDatabaseHandler.md5(pw).equals(dbl.getStuffValue("password"))) {
    										Toast.makeText(Settings.this, "Wrong password!", Toast.LENGTH_SHORT).show();
    									} else {
    										final ProgressDialog loading = ProgressDialog.show(Settings.this, "", "", true, false);
    										new AlertDialog.Builder(Settings.this)
    											.setMessage("Are you sure you want to delete your account and all your data? This cannot be undone!")
    											.setPositiveButton(android.R.string.ok, new OnClickListener() {
													@Override
													public void onClick(final DialogInterface dialog, int which) {
														OnlineDatabaseHandler dbo = new OnlineDatabaseHandler(Settings.this);
			    										dbo.smartInUp(new OnlineDatabaseHandler.WebDbUser() {
			    											@Override
			    											public void gottenFromWeb(JSONObject json, boolean good, boolean success) {
			    												if(good && success) {
																	OnlineDatabaseHandler dbo = new OnlineDatabaseHandler(Settings.this);
			    													dbo.smartInUp(new OnlineDatabaseHandler.WebDbUser() {
						    											@Override
						    											public void gottenFromWeb(JSONObject json, boolean good, boolean success) {
						    												if(good && success) {
						    													if(Session.getActiveSession() == null || !Session.getActiveSession().isOpened())
						    														Session.getActiveSession().closeAndClearTokenInformation();
					    														LocalDatabaseHandler dbl = new LocalDatabaseHandler(Settings.this);
					    														dbl.execSQL("DELETE FROM stuff WHERE `name` = 'username'");
					    														dbl.execSQL("DELETE FROM stuff WHERE `name` = 'password'");
					    												        dbl.setStuffValue("lr_skipped", "true");
					    												        dbl.close();
					    												        Intent i = new Intent(Settings.this, ActivityControl.class);
					    												        i.addFlags(IntentCompat.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
					    												        Toast.makeText(Settings.this, "Your account has been deleted", Toast.LENGTH_SHORT).show();
					    														startActivity(i);
					    														finish();
						    													dialog.dismiss();
						    												} else
						    													Toast.makeText(Settings.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
				    														loading.dismiss();
						    											}
						    										}, "account_links", "user", dbl.getStuffValue("username"), "DELETE", "", "", "", "", "", "", "");
			    												} else {
			    													Toast.makeText(Settings.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
		    														loading.dismiss();
			    												}
			    											}
			    										}, "accounts", "username", dbl.getStuffValue("username"), "DELETE", "", "", "", "", "", "", "");
													}
												})
												.setNegativeButton(android.R.string.cancel, new OnClickListener() {
													@Override
													public void onClick(DialogInterface dialog, int which) {
														dialog.cancel();
													}
												})
												.create().show();
    										d.dismiss();
    									}
    								} catch (NoSuchAlgorithmException e) {
    									e.printStackTrace();
    									Toast.makeText(Settings.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
    								}
    				            }
    				        });
						}
					});
    				d.show();
    				return false;
    			}
        	});
    		accountPrefCat.addPreference(deleteAccount);
    	}
    	
    	logInOut = getPreferenceManager().createPreferenceScreen(this);
    	logInOut.setKey("logInOut");
    	logInOut.setTitle(((dbl.getStuffValue("username").equals("DOES NOT EXIST")) ? "Login" : "Logout"));
    	logInOut.setSummary(((dbl.getStuffValue("username").equals("DOES NOT EXIST")) ? "" : ("Logged in as " + dbl.getStuffValue("username"))));
    	logInOut.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				if(Session.getActiveSession() != null && Session.getActiveSession().isOpened())
					Session.getActiveSession().closeAndClearTokenInformation();
				LocalDatabaseHandler dbl = new LocalDatabaseHandler(Settings.this);
				dbl.execSQL("DELETE FROM stuff WHERE `name` = 'username'");
				dbl.execSQL("DELETE FROM stuff WHERE `name` = 'password'");
		        dbl.setStuffValue("lr_skipped", "true");
		        dbl.close();
		        Intent i = new Intent(Settings.this, ActivityControl.class);
		        i.addFlags(IntentCompat.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
				finish();
				return false;
			}
    	});
    	accountPrefCat.addPreference(logInOut);
    	
    	generalPrefCat = new PreferenceCategory(this);
    	generalPrefCat.setTitle("General");
		root.addPreference(generalPrefCat);
    	
		enableGameFunctions = new CheckBoxPreference(this);
		enableGameFunctions.setKey("enableGameFunctions");
		enableGameFunctions.setTitle("Enable Game Functions");
		enableGameFunctions.setDefaultValue(true);
		generalPrefCat.addPreference(enableGameFunctions);
		
		slowConnection = new CheckBoxPreference(this);
		slowConnection.setKey("slowConnection");
		slowConnection.setTitle("Slow Connection");
		slowConnection.setSummary("Enable if you have a slow internet connection.");
		slowConnection.setDefaultValue(false);
		generalPrefCat.addPreference(slowConnection);
    	
    	morePrefCat = new PreferenceCategory(this);
    	morePrefCat.setTitle("More");
		root.addPreference(morePrefCat);
    	
		enableAnalytics = new CheckBoxPreference(this);
		enableAnalytics.setKey("enableAnalytics");
		enableAnalytics.setTitle("Enable Analytics");
		enableAnalytics.setSummary("Let us collect anonymous user data to make this app better.");
        enableAnalytics.setDefaultValue(true);
        morePrefCat.addPreference(enableAnalytics);
    	
        disableAds = getPreferenceManager().createPreferenceScreen(this);
        disableAds.setKey("disableAds");
        disableAds.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				Functions.disableAds(Settings.this);
				return false;
			}
		});
        disableAds.setTitle("Disable Ads");
        morePrefCat.addPreference(disableAds);
        
        rateUs = getPreferenceManager().createPreferenceScreen(this);
        rateUs.setKey("rateUs");
        Intent appIntent = new Intent(Intent.ACTION_VIEW);
        appIntent.setData(Uri.parse("market://details?id=" + getPackageName()));
        rateUs.setIntent(appIntent);
        rateUs.setTitle("Rate Us");
        rateUs.setSummary("Please");
        morePrefCat.addPreference(rateUs);
		
    	dbl.close();
        
        return root;
    }
    
}