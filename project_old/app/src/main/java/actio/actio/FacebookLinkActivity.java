package actio.actio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;

import org.json.JSONObject;

import actio.actio.functions.ActivityControl;
import actio.actio.functions.LocalDatabaseHandler;
import actio.actio.functions.OnlineDatabaseHandler;

public class FacebookLinkActivity extends ActionBarActivity {
	
	ProgressBar loading;
	Button skipbutton;
	LoginButton loginButton;
	
	boolean from_settings = false;
	
    private UiLifecycleHelper uiHelper;
    
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
        }
    };

    private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
        @Override
        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
        }
        @Override
        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
        }
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
		from_settings = getIntent().getBooleanExtra("from_settings", false);
		setContentView(R.layout.start_activity_facebook);
		getSupportActionBar().hide();
        loading = (ProgressBar) findViewById(R.id.loading);
        skipbutton = (Button) findViewById(R.id.skipbutton);
		if(from_settings) {
			skipbutton.setText(android.R.string.cancel);
		}
		loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO);
        loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
            	if(user != null) {
	            	LocalDatabaseHandler dbl = new LocalDatabaseHandler(FacebookLinkActivity.this);
	            	OnlineDatabaseHandler dbo = new OnlineDatabaseHandler(FacebookLinkActivity.this);
	            	(findViewById(R.id.loading)).setVisibility(View.VISIBLE);
	            	dbo.smartInUp(new OnlineDatabaseHandler.WebDbUser() {
						@Override
						public void gottenFromWeb(JSONObject json, boolean good,
								boolean success) {
			            	(findViewById(R.id.loading)).setVisibility(View.GONE);
			            	if(!(success && good))
								Toast.makeText(FacebookLinkActivity.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
			        		if(!from_settings) {
			        			startActivity(new Intent(FacebookLinkActivity.this, ActivityControl.class));
			        			finish();
			        		}
						}
	            	}, "account_links", "user", dbl.getStuffValue("username"), "facebook", user.getId(), "", "", "", "", "", "");
            	}
            }
        });
	}
	
	public void go_on(View v) {
	}
	
	public void backbutton(View v) {
	}
	
	public void skip(View v) {
		if(from_settings)
			finish();
		else {
			startActivity(new Intent(this, ActivityControl.class));
			finish();
		}
	}

    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

}