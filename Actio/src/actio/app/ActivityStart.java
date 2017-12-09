package actio.app;

import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import actio.app.functions.ActivityControl;
import actio.app.functions.LocalDatabaseHandler;
import actio.app.functions.OnlineDatabaseHandler;
import actio.app.functions.OnlineDatabaseHandler.WebDbUser;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityStart extends ActionBarActivity {

	MyPagerAdapter mStartPagerAdapter;
    ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_activity);
		(findViewById(R.id.loading)).setVisibility(View.GONE);
		getSupportActionBar().hide();
		mStartPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mStartPagerAdapter);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener(){
			@Override
			public void onPageScrollStateChanged(int state) {
				if(state == ViewPager.SCROLL_STATE_IDLE && et1 != null)
					backbutton(null);
			}
			@Override
			public void onPageScrolled(int pos, float arg1, int arg2) {
			}
			@Override
			public void onPageSelected(int arg0) {}
        	
        });
        LocalDatabaseHandler dbl = new LocalDatabaseHandler(this);
        if(dbl.getStuffValue("lr_skipped").equals("true"))
        	gotop3(null);
        dbl.close();
	}
	
	public static class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
        	Fragment fragment = null;
        	if(i == 0)
        		fragment = new StartPageFragmentP1();
        	if(i == 1)
        		fragment = new StartPageFragmentP2();
        	if(i == 2)
        		fragment = new StartPageFragmentP3();
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
	
	public static class StartPageFragmentP1 extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.startpagep1, container, false);
            return rootView;
        }
    }
	
	public void gotop2(View v) {
		mViewPager.setCurrentItem(1, true);
	}
	
	public static class StartPageFragmentP2 extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.startpagep2, container, false);
            return rootView;
        }
    }
	
	public void gotop3(View v) {
		mViewPager.setCurrentItem(2, true);
	}
	
	
	static EditText et1, et2;
	static Button loginbutton, registerbutton, backbutton;
	static View skipview;
	static CheckBox skipfecb;
	static ProgressBar loading;
	static TextView forgotPassword;
	public static class StartPageFragmentP3 extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.startpagep3, container, false);
            et1 = (EditText)rootView.findViewById(R.id.et1);
            et2 = (EditText)rootView.findViewById(R.id.et2);
            loginbutton = (Button)rootView.findViewById(R.id.loginbutton);
            registerbutton = (Button)rootView.findViewById(R.id.registerbutton);
            backbutton = (Button)rootView.findViewById(R.id.lrbackbutton);
            skipview = rootView.findViewById(R.id.skipview);
            skipfecb = (CheckBox)rootView.findViewById(R.id.skipfecb);
            loading = (ProgressBar)rootView.findViewById(R.id.loading);
            forgotPassword = (TextView)rootView.findViewById(R.id.forgotPassword);
            return rootView;
        }
    }
	
	public boolean regstep1 = false;
	public void register(View v) {
		if(!regstep1) {
			et1.setVisibility(View.VISIBLE);
			et2.setVisibility(View.VISIBLE);
			loginbutton.setVisibility(View.GONE);
			backbutton.setVisibility(View.VISIBLE);
			skipview.setVisibility(View.GONE);
			regstep1 = true;
		} else {
			if(et1.getText().length() == 0 || et2.getText().length() == 0)
				Toast.makeText(this, "Please enter the Username you want and a Password", Toast.LENGTH_SHORT).show();
			else {
				final OnlineDatabaseHandler dbo = new OnlineDatabaseHandler(this);
				loading.setVisibility(View.VISIBLE);
				dbo.getFromDB(new WebDbUser() {
					@SuppressWarnings("static-access")
					@Override
					public void gottenFromWeb(JSONObject json, boolean good, boolean success) {
						if(success) {
							if(good) {
								Toast.makeText(ActivityStart.this, "Username already registered!", Toast.LENGTH_SHORT).show();
								loading.setVisibility(View.GONE);
							}
							else {
								String username = et1.getText().toString();
								String pw = et2.getText().toString();
								try {
									pw = dbo.md5(pw);
								} catch (NoSuchAlgorithmException e) {
									e.printStackTrace();
									Toast.makeText(ActivityStart.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
								}
					    		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
					    		Date date = new Date();
								dbo.smartInUp(new WebDbUser() {
									@Override
									public void gottenFromWeb(JSONObject json,
											boolean good, boolean success) {
										loading.setVisibility(View.GONE);
										if(success && good) {
											try {
												String username = json.getJSONObject("row").getString("username");
												String password = json.getJSONObject("row").getString("password");
												LocalDatabaseHandler dbl = new LocalDatabaseHandler(ActivityStart.this);
												dbl.setStuffValue("username", username);
												dbl.setStuffValue("password", password);
												dbl.close();
												startActivity(new Intent(ActivityStart.this, PhoneNumberActivity.class));
												finish();
											} catch (JSONException e) {
												e.printStackTrace();
												Toast.makeText(ActivityStart.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
											}
										} else
											Toast.makeText(ActivityStart.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
									}
								}, "accounts", "username", username, "password", pw, "data", "created_on{{:}}"+ dateFormat.format(date), "", "", "", "");
							}
						} else {
							loading.setVisibility(View.GONE);
							Toast.makeText(ActivityStart.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
						}
					}
				}, "accounts", "username", et1.getText().toString(), "id");
			}
		}
	}

	public boolean logstep1 = false;
	public void login(View v) {
		if(!logstep1) {
			et1.setVisibility(View.VISIBLE);
			et2.setVisibility(View.VISIBLE);
			registerbutton.setVisibility(View.GONE);
			backbutton.setVisibility(View.VISIBLE);
			forgotPassword.setVisibility(View.VISIBLE);
			skipview.setVisibility(View.GONE);
			logstep1 = true;
		} else {
			if(et1.getText().length() == 0 || et2.getText().length() == 0)
				Toast.makeText(this, "Please enter your Username and Password", Toast.LENGTH_SHORT).show();
			else {
				final OnlineDatabaseHandler dbo = new OnlineDatabaseHandler(this);
				loading.setVisibility(View.VISIBLE);
				dbo.getFromDB(new WebDbUser() {
					@SuppressWarnings("static-access")
					@Override
					public void gottenFromWeb(JSONObject json, boolean good, boolean success) {
						if(success) {
							if(!good) {
								Toast.makeText(ActivityStart.this, "User doesn't exist!", Toast.LENGTH_SHORT).show();
								loading.setVisibility(View.GONE);
							}
							else {
								String pw = et2.getText().toString();
								try {
									pw = dbo.md5(pw);
								} catch (NoSuchAlgorithmException e) {
									e.printStackTrace();
									Toast.makeText(ActivityStart.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
								}
								final String pwf = pw;
								dbo.getFromDB(new WebDbUser() {
									@Override
									public void gottenFromWeb(final JSONObject json,
											boolean good, boolean success) {
										if(success && good) {
											try {
												final String username = json.getJSONObject("row").getString("username");
												if(json.getJSONObject("row").getString("password").equals(pwf)) {
													if(json.getJSONObject("values").has("new_pw")) {
														dbo.inUpData(new WebDbUser() {
															@Override
															public void gottenFromWeb(JSONObject j, boolean good, boolean success) {
																if(success && good) {
																	try {
																		String password = json.getJSONObject("row").getString("password");
																		LocalDatabaseHandler dbl = new LocalDatabaseHandler(ActivityStart.this);
																		dbl.setStuffValue("username", username);
																		dbl.setStuffValue("password", password);
																		dbl.close();
																		startActivity(new Intent(ActivityStart.this, ActivityControl.class));
																		finish();
																	} catch (JSONException e) {
																		e.printStackTrace();
																		Toast.makeText(ActivityStart.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
																	}
																} else 
																	Toast.makeText(ActivityStart.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
															} 
														}, "accounts", "username", json.getJSONObject("row").getString("username"), "data", "new_pw", "", "", "", "");
													} else {
														String password = json.getJSONObject("row").getString("password");
														LocalDatabaseHandler dbl = new LocalDatabaseHandler(ActivityStart.this);
														dbl.setStuffValue("username", username);
														dbl.setStuffValue("password", password);
														dbl.close();
														startActivity(new Intent(ActivityStart.this, ActivityControl.class));
														finish();
													}
												} else if(json.getJSONObject("values").getJSONArray("new_pw").getString(1).equals(pwf)) {
													dbo.smartInUp(new WebDbUser() {
														@Override
														public void gottenFromWeb(JSONObject j, boolean good, boolean success) {
															if(success && good) {
																dbo.inUpData(new WebDbUser() {
																	@Override
																	public void gottenFromWeb(JSONObject j, boolean good, boolean success) {
																		if(success && good) {
																			String password = pwf;
																			LocalDatabaseHandler dbl = new LocalDatabaseHandler(ActivityStart.this);
																			dbl.setStuffValue("username", username);
																			dbl.setStuffValue("password", password);
																			dbl.close();
																			startActivity(new Intent(ActivityStart.this, ActivityControl.class));
																			finish();
																		} else {
																			try {
																				dbo.smartInUp(new WebDbUser() {
																					@Override
																					public void gottenFromWeb(JSONObject json, boolean good, boolean success) {}
																				}, "accounts", "username", json.getJSONObject("row").getString("username"), "password", json.getJSONObject("row").getString("password"), "data", json.getJSONObject("row").getString("data"), "", "", "", "");
																			} catch(JSONException e) {
																				e.printStackTrace();
																			}
																			Toast.makeText(ActivityStart.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
																		}
																	}
																}, "accounts", "username", username, "data", "new_pw", "", "", "", "");
															} else {
																try {
																	dbo.smartInUp(new WebDbUser() {
																		@Override
																		public void gottenFromWeb(JSONObject json, boolean good, boolean success) {}
																	}, "accounts", "username", username, "password", json.getJSONObject("row").getString("password"), "data", json.getJSONObject("row").getString("data"), "", "", "", "");
																} catch(JSONException e) {
																	e.printStackTrace();
																}
																Toast.makeText(ActivityStart.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
															}
														}
													}, "accounts", "username", username, "password", json.getJSONObject("values").getJSONArray("new_pw").getString(1), "", "", "", "", "", "");
												} else {
													Toast.makeText(ActivityStart.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
													loading.setVisibility(View.GONE);
												}
											} catch (JSONException e) {
												e.printStackTrace();
												Toast.makeText(ActivityStart.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
											}
										} else {
											Toast.makeText(ActivityStart.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
										}
									}
								}, "accounts", "username", et1.getText().toString(), "data");
							}
						} else {
							loading.setVisibility(View.GONE);
							Toast.makeText(ActivityStart.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
						}
					}
				}, "accounts", "username", et1.getText().toString(), "id");
			}
		}		
	}
	
	public void forgotPassword(View v) {
		if(et1.getText().length() == 0)
			Toast.makeText(this, "Please enter your Username", Toast.LENGTH_SHORT).show();
		else {
			final OnlineDatabaseHandler dbo = new OnlineDatabaseHandler(this);
			loading.setVisibility(View.VISIBLE);
			dbo.getFromDB(new WebDbUser() {
				@Override
				public void gottenFromWeb(JSONObject json, boolean good, boolean success) {
					if(success) {
						if(!good) {
							Toast.makeText(ActivityStart.this, "User doesn't exist!", Toast.LENGTH_SHORT).show();
							loading.setVisibility(View.GONE);
						}
						else {
							dbo.getFromDB(new WebDbUser() {
								@Override
								public void gottenFromWeb(final JSONObject json, boolean good, boolean success) {
									if(success) {
										if(!good) {
											Toast.makeText(ActivityStart.this, "We weren't able to send you your password. You apparently didn't link your account to a phone number. We're sorry.", Toast.LENGTH_LONG).show();
											loading.setVisibility(View.GONE);
										} else {
											String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
											String new_pw = "";
											Random r = new Random();
											for(int i = 0; i < 5; i++)
												new_pw += chars.charAt(r.nextInt(chars.length()));
											final String npw = new_pw;
											try {
												dbo.inUpData(new WebDbUser() {
													@Override
													public void gottenFromWeb(JSONObject j, boolean good, boolean success) {
														if(success && good) {
															try {
																SmsManager smsManager = SmsManager.getDefault();
																smsManager.sendTextMessage(json.getString("value"), null, 
																		"Your new password is: " + npw, null, null);
																Toast.makeText(ActivityStart.this, "Your password was sent to you via SMS.", Toast.LENGTH_SHORT).show();
															} catch(JSONException e) {
																e.printStackTrace();
																Toast.makeText(ActivityStart.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
															}
														} else
															Toast.makeText(ActivityStart.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
														loading.setVisibility(View.GONE);
													}
												}, "accounts", "username", et1.getText().toString(), "data", "new_pw", "", "", "", "new_pw{{:}}" + OnlineDatabaseHandler.md5(new_pw));
											} catch (NoSuchAlgorithmException e) {
												e.printStackTrace();
											}
										}
									} else {
										loading.setVisibility(View.GONE);
										Toast.makeText(ActivityStart.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
									}
								}
							}, "account_links", "user", et1.getText().toString(), "phone");
						}
					} else {
						loading.setVisibility(View.GONE);
						Toast.makeText(ActivityStart.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
					}
				}
			}, "accounts", "username", et1.getText().toString(), "id");
		}
	}
	
	public void backbutton(View v) {
		skipview.setVisibility(View.VISIBLE);
		et1.setVisibility(View.GONE);
		et2.setVisibility(View.GONE);
		backbutton.setVisibility(View.GONE);
		registerbutton.setVisibility(View.VISIBLE);
		forgotPassword.setVisibility(View.GONE);
		logstep1 = false;
		loginbutton.setVisibility(View.VISIBLE);
		regstep1 = false;
	}
	
	public void skip(View v) {
        LocalDatabaseHandler dbl = new LocalDatabaseHandler(this);
        dbl.setStuffValue("lr_skipped", ((skipfecb.isChecked()) ? "forever" : "true"));
        dbl.close();
		startActivity(new Intent(this, ActivityMain.class));
		finish();
	}

}