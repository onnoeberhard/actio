//package turtle.actio;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import turtle.actio.stuff.DatabaseHandler;
//import turtle.actio.stuff.UserFunctions;
//import android.app.Activity;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//public class LoginActivity extends Activity {
//	Button btnLogin;
//	Button btnLinkToRegister;
//	EditText inputEmail;
//	EditText inputPassword;
//	TextView loginErrorMsg;
//
//	// JSON Response node names
//	private static String KEY_SUCCESS = "success";
//	private static String KEY_ID = "id";
//	private static String KEY_EMAIL = "email";
//	private static String KEY_DATA1 = "data1";
//	private static String KEY_DATA2 = "data2";
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.login);
//
//		// Importing all assets like buttons, text fields
//		inputEmail = (EditText) findViewById(R.id.loginEmail);
//		inputPassword = (EditText) findViewById(R.id.loginPassword);
//		btnLogin = (Button) findViewById(R.id.btnLogin);
//		btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
//		loginErrorMsg = (TextView) findViewById(R.id.login_error);
//
//		// Login button Click Event
//		btnLogin.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View view) {
//				String email = inputEmail.getText().toString();
//				String password = inputPassword.getText().toString();
//				new MyAsyncTask().execute(email, password);
//			}
//		});
//
//		// Link to Register Screen
//		btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View view) {
//				Intent i = new Intent(getApplicationContext(),
//						RegisterActivity.class);
//				startActivity(i);
//				finish();
//			}
//		});
//	}
//
//	private class MyAsyncTask extends AsyncTask<String, Void, JSONObject> {
//
//		protected JSONObject doInBackground(String... params) {
//			UserFunctions userFunction = new UserFunctions();
//			if (params.length != 2)
//				return null;
//			JSONObject json = userFunction.loginUser(params[0], params[1]);
//			return json;
//		}
//
//		protected void onPostExecute(JSONObject json) {
//			try {
//				if (json != null && json.getString(KEY_SUCCESS) != null) {
//					loginErrorMsg.setText("");
//					String res = json.getString(KEY_SUCCESS);
//					if (Integer.parseInt(res) == 1) {
//						DatabaseHandler db = new DatabaseHandler(
//								getApplicationContext());
//						JSONObject json_user = json.getJSONObject("user");
//						UserFunctions userFunction = new UserFunctions();
//						userFunction.logoutUser(getApplicationContext());
//						db.addUser(json_user.getInt(KEY_ID),
//								json_user.getString(KEY_EMAIL),
//								json_user.getString(KEY_DATA1),
//								json_user.getString(KEY_DATA2));
//						Intent dashboard = new Intent(getApplicationContext(),
//								MainActivity.class);
//						dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//						startActivity(dashboard);
//						finish();
//					} else
//						loginErrorMsg.setText("Incorrect username/password");
//				}
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//}