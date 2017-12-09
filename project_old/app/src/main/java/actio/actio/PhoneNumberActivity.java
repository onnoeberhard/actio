package actio.actio;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import actio.actio.functions.LocalDatabaseHandler;
import actio.actio.functions.OnlineDatabaseHandler;

public class PhoneNumberActivity extends ActionBarActivity {
	
	EditText etpn;
	ProgressBar loading;
	Button backbutton, skipbutton, etpn_clear, okbutton;
	int verificationCode;
	TextView phonenumbernotice;
	String phoneNumber;
	boolean ok_is_save = false;
	boolean from_settings = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		from_settings = getIntent().getBooleanExtra("from_settings", false);
		setContentView(R.layout.start_activity_phonenumber);
		getSupportActionBar().hide();
        etpn = (EditText) findViewById(R.id.etpn);
        loading = (ProgressBar) findViewById(R.id.loading);
        backbutton = (Button) findViewById(R.id.backbutton);
        skipbutton = (Button) findViewById(R.id.skipbutton);
        phonenumbernotice = (TextView) findViewById(R.id.phonenumber_notice);
        okbutton = (Button) findViewById(R.id.okbutton);
        etpn_clear = (Button) findViewById(R.id.etpn_clear);
		etpn.setHintTextColor(Color.parseColor("#61a479"));
		if(from_settings) {
			skipbutton.setText(android.R.string.cancel);
			LocalDatabaseHandler dbl = new LocalDatabaseHandler(this);
			if(!dbl.getStuffValue("username").equals("DOES NOT EXIST")) {
				OnlineDatabaseHandler dbo = new OnlineDatabaseHandler(this);
		    	dbo.getFromDB(new OnlineDatabaseHandler.WebDbUser() {
					@Override
					public void gottenFromWeb(JSONObject json, boolean good,
							boolean success) {
						if(success && good) {
					    	try {
								etpn.setText(json.getString("value"));
								etpn_clear.setVisibility(View.VISIBLE);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}
		    	}, "account_links", "user", dbl.getStuffValue("username"), "phone");
			}
			etpn.addTextChangedListener(new TextWatcher() {
				@Override
				public void afterTextChanged(Editable s) {}
				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {}
				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					if(s.length() == 0) {
						etpn_clear.setVisibility(View.GONE);
						okbutton.setText("Save");
						ok_is_save = true;
					} else {
						okbutton.setText(android.R.string.ok);
						ok_is_save = false;
					}
				}
			});
		}
	}
	
	public void etpn_clear(View v) {
		etpn.setText("");
		etpn_clear.setVisibility(View.GONE);
		okbutton.setText("Save");
	}
	
	boolean sms_sent = false;
	public void go_on(View v) {
		if(!ok_is_save) {
			if(!sms_sent) {
				if(etpn.getText().toString().length() == 0)
					Toast.makeText(this, "Please enter your phone number!", Toast.LENGTH_SHORT).show();
				else {
					verificationCode = (new Random()).nextInt(99999-10000) + 10000;
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(etpn.getText().toString(), null, 
							"Your verification code is: " + verificationCode + ".", null, null);
					((TextView)findViewById(R.id.phonenumber_notice)).setText("A verification code was sent to you via SMS. Pleas enter the code.");
					phoneNumber = etpn.getText().toString();
					etpn.setText("");
					etpn.setInputType(InputType.TYPE_CLASS_NUMBER);
					etpn.setHint("Verification Code");
					backbutton.setVisibility(View.VISIBLE);
					sms_sent = true;
				}
			} else {
				if(etpn.getText().toString().length() == 0)
					Toast.makeText(this, "Please enter the verification code!", Toast.LENGTH_SHORT).show();
				else {
					if(etpn.getText().toString().equals(Integer.toString(verificationCode))) {
						final OnlineDatabaseHandler dbo = new OnlineDatabaseHandler(this);
						final LocalDatabaseHandler dbl = new LocalDatabaseHandler(this);
						loading.setVisibility(View.VISIBLE);
						phonenumbernotice.setVisibility(View.GONE);
						dbo.smartInUp(new OnlineDatabaseHandler.WebDbUser() {
							@Override
							public void gottenFromWeb(JSONObject json,
									boolean good, boolean success) {
								loading.setVisibility(View.GONE);
								if(success && good) {
									if(from_settings)
										finish();
									else {
										startActivity(new Intent(PhoneNumberActivity.this, FacebookLinkActivity.class));
										finish();
									}
								} else
									Toast.makeText(PhoneNumberActivity.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
							}
						}, "account_links", "user", dbl.getStuffValue("username"), "phone", phoneNumber, "", "", "", "", "", "");
						dbl.close();
					} else
						Toast.makeText(this, "Wrong Code!", Toast.LENGTH_SHORT).show();
				}
			}
		} else {
			final OnlineDatabaseHandler dbo = new OnlineDatabaseHandler(this);
			final LocalDatabaseHandler dbl = new LocalDatabaseHandler(this);
			loading.setVisibility(View.VISIBLE);
			phonenumbernotice.setVisibility(View.GONE);
			dbo.smartInUp(new OnlineDatabaseHandler.WebDbUser() {
				@Override
				public void gottenFromWeb(JSONObject json,
						boolean good, boolean success) {
					loading.setVisibility(View.GONE);
					if(!(success && good))
						Toast.makeText(PhoneNumberActivity.this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
					finish();
				}
			}, "account_links", "user", dbl.getStuffValue("username"), "phone", "", "", "", "", "", "", "");
		}
	}
	
	public void backbutton(View v) {
		sms_sent = false;
		etpn.setText("");
		etpn.setInputType(InputType.TYPE_CLASS_PHONE);
		etpn.setHint("Phone Number");
		backbutton.setVisibility(View.GONE);
	}
	
	public void skip(View v) {
		if(from_settings)
			finish();
		else {
			startActivity(new Intent(this, FacebookLinkActivity.class));
			finish();
		}
	}

}