package actio.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import actio.app.ProfileActivity.ScoreFragmentFriends;
import actio.app.functions.ActioActivity;
import actio.app.functions.LocalDatabaseHandler;
import actio.app.functions.MyViewPager;
import actio.app.functions.OnlineDatabaseHandler;
import actio.app.functions.OnlineDatabaseHandler.WebDbUser;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBarActivity;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphMultiResult;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphUser;

public class FriendsActivtiy extends ActioActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mFragment = new FriendsFragment();
		Bundle args = new Bundle();
		mFragment.setArguments(args);
		drawer_home = 4;
		super.onCreate(savedInstanceState);
		find(this, null, true);
	}
	
	static PagerSlidingTabStrip ftabs;
	static MyViewPager fpager;
	static FriendsPagerAdapter fadapter;
	static boolean found = false;
	static FriendsFragmentFriends fff; 
	
	public static class FriendsFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.tabs, container, false);
            fff = new FriendsFragmentFriends();
    		ftabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabsn);
    		fpager = (MyViewPager) rootView.findViewById(R.id.pagern);
    		fadapter = new FriendsPagerAdapter(getChildFragmentManager());
//    		fpager.setParentViewPager(pager);
//    		fpager.setSwipeable(false);
    		fpager.setAdapter(fadapter);
    		fpager.setOffscreenPageLimit(fadapter.getCount() - 1);
    		ftabs.setViewPager(fpager);
    		ftabs.setIndicatorColor(0xff0ab246);
    		ftabs.setBackgroundResource(R.drawable.borderless_button);
            return rootView;
        }
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.friends, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    	case R.id.action_refresh:
	    		find(this, item, true);
	    		item.setVisible(false);
	    		setSupportProgressBarIndeterminateVisibility(true);
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public static class FriendsPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { "Friends", "Just Following", "Just Followers" };

		public FriendsPagerAdapter(FragmentManager fm) {
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
        		fragment = fff;
        	if(i == 1)
        		fragment = new ScoreFragmentFriends();
        	if(i == 2)
        		fragment = new FriendsFragmentFollowers();
            return fragment;
        }

	}
	
	public static class FriendsFragmentFriends extends Fragment {
        FriendsArrayAdapter adapter;
        View rootView;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.list, container, false);
            if(!found)
            	rootView.findViewById(R.id.loading).setVisibility(View.VISIBLE);
            else
            	reload();
//            LocalDatabaseHandler dbl = new LocalDatabaseHandler(getActivity());
//    		new OnlineDatabaseHandler(getActivity()).getFromDB(new WebDbUser() {
//    			@Override
//    			public void gottenFromWeb(JSONObject json, boolean good, boolean success) {
//    				if(success && good) {
//	    				final ArrayList<String> usernames = new ArrayList<String>();
//	    				final ArrayList<String> names = new ArrayList<String>();
//	    				try {
//	    					if(!json.getJSONObject("values").isNull("friends"))
//	    						for(int i = 1; i < json.getJSONObject("values").getJSONArray("friends").length(); i++) {
//	    							usernames.add(json.getJSONObject("values").getJSONArray("friends").getJSONArray(i).getString(1));
//	    							names.add(json.getJSONObject("values").getJSONArray("friends").getJSONArray(i).getString(2));
//	    						}
//					            if(usernames != null) {
//					            	ArrayList<String[]> values = new ArrayList<String[]>();
//					            	for(int i = 0; i < usernames.size(); i++) {
//					                	String[] vals = {usernames.get(i), names.get(i)};
//					                	values.add(vals);
//					            	}
//					            	adapter = new FriendsArrayAdapter(getActivity(), R.layout.friend_list_item, values);
//						            ((ListView)rootView.findViewById(R.id.list)).setAdapter(adapter);
//						            ((ListView)rootView.findViewById(R.id.list)).setDividerHeight(0);
//					            }
//	    				} catch(JSONException e) {
//	    					e.printStackTrace();
//							Toast.makeText(getActivity(), R.string.error_sorry, Toast.LENGTH_SHORT).show();
//	    				}
//    				} else
//    					Toast.makeText(getActivity(), R.string.error_sorry, Toast.LENGTH_SHORT).show();
//    			}
//    		}, "accounts", "username", dbl.getStuffValue("username"), "data");
//    		dbl.close();
            return rootView;
        }
        public void reload() {
        	LocalDatabaseHandler dbl = new LocalDatabaseHandler(getActivity());
    		new OnlineDatabaseHandler(getActivity()).getFromDB(new WebDbUser() {
    			@Override
    			public void gottenFromWeb(JSONObject json, boolean good, boolean success) {
    				if(success && good) {
	    				final ArrayList<String> usernames = new ArrayList<String>();
	    				final ArrayList<String> names = new ArrayList<String>();
	    				try {
	    					if(!json.getJSONObject("values").isNull("friends"))
	    						for(int i = 1; i < json.getJSONObject("values").getJSONArray("friends").length(); i++) {
	    							usernames.add(json.getJSONObject("values").getJSONArray("friends").getJSONArray(i).getString(1));
	    							names.add(json.getJSONObject("values").getJSONArray("friends").getJSONArray(i).getString(2));
	    						}
					            if(usernames != null) {
						            ArrayList<String[]> values = new ArrayList<String[]>();
					            	for(int i = 0; i < usernames.size(); i++) {
					                	String[] vals = {usernames.get(i), names.get(i)};
					                	values.add(vals);
					            	}
					            	adapter = null;
					            	adapter = new FriendsArrayAdapter(getActivity(), R.layout.friend_list_item, values);
					            	adapter.notifyDataSetChanged();
						            ((ListView)rootView.findViewById(R.id.list)).setAdapter(adapter);
						            ((ListView)rootView.findViewById(R.id.list)).setDividerHeight(0);
					            }
	    				} catch(JSONException e) {
	    					e.printStackTrace();
							Toast.makeText(getActivity(), R.string.error_sorry, Toast.LENGTH_SHORT).show();
	    				}
    				} else
    					Toast.makeText(getActivity(), R.string.error_sorry, Toast.LENGTH_SHORT).show();
    			}
    		}, "accounts", "username", dbl.getStuffValue("username"), "data");
    		dbl.close();
        }
    }
	
	public static void find(final Context c, final MenuItem item, final boolean reload) {
		LocalDatabaseHandler dbl = new LocalDatabaseHandler(c);
		new OnlineDatabaseHandler(c).getFromDB(new WebDbUser() {
			@Override
			public void gottenFromWeb(JSONObject json, boolean good, boolean success) {
				if(success && good) {
					final ArrayList<String> friend_usernames = new ArrayList<String>();
					final ArrayList<String> friend_names = new ArrayList<String>();
					try {
						if(!json.getJSONObject("values").isNull("friends"))
							for(int i = 1; i < json.getJSONObject("values").getJSONArray("friends").length(); i++) {
								friend_usernames.add(json.getJSONObject("values").getJSONArray("friends").getJSONArray(i).getString(1));
								friend_names.add(json.getJSONObject("values").getJSONArray("friends").getJSONArray(i).getString(2));
							}
						if(Session.getActiveSession() == null || !Session.getActiveSession().isOpened())
							Session.openActiveSessionFromCache(c);
						if(Session.getActiveSession() != null && Session.getActiveSession().isOpened()) {
							Request request = Request.newGraphPathRequest(Session.getActiveSession(), "me/friends", null);
	
						    Set<String> fields = new HashSet<String>();
						    String[] requiredFields = new String[] { "id", "name", "installed" };
						    fields.addAll(Arrays.asList(requiredFields));
	
						    Bundle parameters = request.getParameters();
						    parameters.putString("fields", TextUtils.join(",", fields));
						    request.setParameters(parameters);
						    request.setCallback(new Request.Callback() {
	
						        @Override
						        public void onCompleted(Response response) {
						            GraphMultiResult multiResult = response.getGraphObjectAs(GraphMultiResult.class);
						            GraphObjectList<GraphObject> data = multiResult.getData();
						            List<GraphUser> friends = data.castToListOf(GraphUser.class);
						            ArrayList<GraphUser> okFriends = new ArrayList<GraphUser>();
						            for(int i = 0; i < friends.size(); i++) {
							            GraphUser user = friends.get(i);
							            if(user.getProperty("installed") != null && (Boolean) user.getProperty("installed"))
						                	okFriends.add(user);
						            }
						            final ArrayList<GraphUser> friendsK = okFriends;
				            		OnlineDatabaseHandler dbo = new OnlineDatabaseHandler(c);
				            		final LocalDatabaseHandler dbl = new LocalDatabaseHandler(c);
				            		dbo.getAllFromDB(new WebDbUser() {
										@Override
										public void gottenFromWeb(JSONObject json,
												boolean good, boolean success) {
											if(good && success) {
												try {
													 for(int i = 0; i < json.getJSONArray("rows").length(); i++) {
														 for(int ii = 0; ii < friendsK.size(); ii++) {
															 if(friendsK.get(ii).getId().equals(json.getJSONArray("rows").getJSONObject(i).getString("facebook"))) {
	//															 dbl.smartInUp("friends", "user", json.getJSONArray("rows").getJSONObject(i).getString("user"), 
	//																	 "name", friendsK.get(ii).getName());
																 if(!friend_usernames.contains(json.getJSONArray("rows").getJSONObject(i).getString("user"))) {
																	 friend_usernames.add(json.getJSONArray("rows").getJSONObject(i).getString("user"));
																	 friend_names.add(friendsK.get(ii).getName());
																 }
																 friend_names.set(friend_usernames.indexOf(json.getJSONArray("rows").getJSONObject(i).getString("user")), friendsK.get(ii).getName());
															 }
														 }
													 }
												} catch (JSONException e) {
													e.printStackTrace();
													Toast.makeText(c, R.string.error_sorry, Toast.LENGTH_SHORT).show();
												}
											} else
												Toast.makeText(c, R.string.error_sorry, Toast.LENGTH_SHORT).show();
											dbl.close();
											find_2(c, item, friend_usernames, friend_names, reload);
										}
				            		}, "account_links", "user", "", "");
						        }
						    });
						    request.executeAsync();
						} else {
							find_2(c, item, friend_usernames, friend_names, reload);
						}
					} catch(JSONException e) {
						e.printStackTrace();
						Toast.makeText(c, R.string.error_sorry, Toast.LENGTH_SHORT).show();
					}
				} else
					Toast.makeText(c, R.string.error_sorry, Toast.LENGTH_SHORT).show();
			}
		}, "accounts", "username", dbl.getStuffValue("username"), "data");
		dbl.close();
	}
	
	public static void find_2(final Context c, final MenuItem item, final ArrayList<String> friend_usernames, final ArrayList<String> friend_names, final boolean reload) {
		final ArrayList<String> numbers = new ArrayList<String>();
		final ArrayList<String> names = new ArrayList<String>();
		final Cursor phones = c.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
		while (phones.moveToNext()) {
			numbers.add(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
			names.add(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
		}
		final OnlineDatabaseHandler dbo = new OnlineDatabaseHandler(c);
		final LocalDatabaseHandler dbl = new LocalDatabaseHandler(c);
		dbo.getFromDB(new WebDbUser() {
			@Override
			public void gottenFromWeb(JSONObject json, boolean good, boolean success) {
				try {
					final String myNumber = json.getString("value");
					dbo.getAllFromDB(new WebDbUser() {
						@Override
						public void gottenFromWeb(JSONObject json, boolean good,
								boolean success) {
							if (success && good) {
								try {
									 for(int i = 0; i < json.getJSONArray("rows").length(); i++) {
										 if(!json.getJSONArray("rows").getJSONObject(i).getString("phone").equals(myNumber)) {
											 for(int ii = 0; ii < numbers.size(); ii++) {
												 if(PhoneNumberUtils.compare(numbers.get(ii), json.getJSONArray("rows").getJSONObject(i).getString("phone"))) {
//													 dbl.smartInUp("friends", "user", json.getJSONArray("rows").getJSONObject(i).getString("user"), "name", names.get(ii));
													 if(!friend_usernames.contains(json.getJSONArray("rows").getJSONObject(i).getString("user"))) {
														 friend_usernames.add(json.getJSONArray("rows").getJSONObject(i).getString("user"));
														 friend_names.add(names.get(ii));
													 }
													 friend_names.set(friend_usernames.indexOf(json.getJSONArray("rows").getJSONObject(i).getString("user")), names.get(ii));
												 }
											 }
										 }
									 }
									 String friends = (friend_usernames.size() > 0) ? "friends{{:}}" : "";
									 for(int i = 0; i < friend_usernames.size(); i++) {
										 if(i > 0)
											 friends += "{{:}}";
										 friends += Integer.toString(i + 1) + "{{,}}" + friend_usernames.get(i) + "{{,}}" + friend_names.get(i);
									 }
									 new OnlineDatabaseHandler(c).inUpData(new WebDbUser() {
										@Override
										public void gottenFromWeb(JSONObject json, boolean good, boolean success) {
											 if(reload) {
												 if(fff != null)
													 fff.reload();
//												 c.startActivity(((Activity) c).getIntent());
//												 ((Activity) c).finish();
//												 ((Activity) c).overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
											 }
										}
									}, "accounts", "username", dbl.getStuffValue("username"), "data", "friends", "", "", "", friends);
//									System.out.println(friends);
								} catch (JSONException e) {
									e.printStackTrace();
									Toast.makeText(c, R.string.error_sorry, Toast.LENGTH_SHORT).show();
								}
								dbl.close();
							} else
								Toast.makeText(c, R.string.error_sorry, Toast.LENGTH_SHORT).show();
							if(item != null) {
					    		((ActionBarActivity) c).setSupportProgressBarIndeterminateVisibility(false);
					        	item.setVisible(true);
							}
							found = true;
						}
					}, "account_links", "user", "", "");
				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(c, R.string.error_sorry, Toast.LENGTH_SHORT).show();
					dbl.close();
				}
			}
		}, "account_links", "user", dbl.getStuffValue("username"), "phone");
	}
	
	public static class FriendsArrayAdapter extends ArrayAdapter<String> {

		Context context;
		ArrayList<String[]> values;
		
		public FriendsArrayAdapter(Context context, int resource, ArrayList<String[]> objects) {
			super(context, resource, new String[objects.size()]);
			this.context = context;
		    this.values = objects;
		}

		TextView text1;
		TextView text2;
		View view;
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.friend_list_item, parent, false);
			text1 = (TextView) view.findViewById(R.id.text1);
			text2 = (TextView) view.findViewById(R.id.text2);
			if(!values.get(position)[1].equals("")) {
				text1.setText(values.get(position)[1]);
				text2.setText(values.get(position)[0]);
			} else 
				text1.setText(values.get(position)[0]);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(context, ProfileActivity.class);
					i.putExtra("username", values.get(position)[0]);
					context.startActivity(i);
			        ((Activity) context).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top); 
				}
			});
			return view;
		}

	}
	
	public static class FriendsFragmentFollowing extends Fragment {

		@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.list, container, false);
			return rootView;
		}
	}
	
	public static class FriendsFragmentFollowers extends Fragment {

		@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.list, container, false);
			return rootView;
		}
	}
	
}