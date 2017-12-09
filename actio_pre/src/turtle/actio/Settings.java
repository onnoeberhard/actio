package turtle.actio;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

public class Settings extends PreferenceActivity{

//	PreferenceCategory appPC;
//	CheckBoxPreference bonw;
//	PreferenceCategory widgetPC;
//	CheckBoxPreference newaw;
	PreferenceScreen logout;
	
	boolean loggingOut = false;
	
	@SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPreferenceScreen(createPreferenceHierarchy());
    }
	
	@SuppressWarnings({ "deprecation"})
	public PreferenceScreen createPreferenceHierarchy(){
    	PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);

//    	logout = getPreferenceManager().createPreferenceScreen(this);
//    	logout.setKey("logout_pref");
//    	logout.setTitle("Logout");
//    	logout.setOnPreferenceClickListener(new OnPreferenceClickListener() {
//
//			@Override
//			public boolean onPreferenceClick(Preference preference) {
//				UserFunctions userFunctions = new UserFunctions();
//				userFunctions.logoutUser(getApplicationContext());
//                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
//                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(login);
//                finish();
//				return false;
//			}
//    		
//    	});
//        root.addPreference(logout);
    	
//    	appPC = new PreferenceCategory(this);
//    	appPC.setTitle(R.string.app);
//        root.addPreference(appPC);
//    	
    	
//    	bonw = new CheckBoxPreference(this);
//        bonw.setKey("Light_Theme");
//        bonw.setTitle(R.string.Light_Theme);
//        bonw.setDefaultValue(true);
//        root.addPreference(bonw);
    	
//        widgetPC = new PreferenceCategory(this);
//        widgetPC.setTitle(R.string.widget);
//        root.addPreference(widgetPC);
//
//    	newaw = new CheckBoxPreference(this);
//    	newaw.setKey("new_aw");
//    	newaw.setTitle(R.string.new_aw);
//    	newaw.setDefaultValue(true);
//    	widgetPC.addPreference(newaw);
        
        return root;
	}

	@Override
	protected void onStop() {
		if(!loggingOut) {
			Intent i = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(i);	
		}
		super.onStop();
	}
	
}
