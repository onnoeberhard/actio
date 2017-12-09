package actio.app.functions;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import actio.app.R;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class OnlineDatabaseHandler {
	
	//http://actio.cwsurf.de/cwpma/index.php?db=actio	//http://localhost/xampp/phpmyadmin/db_structure.php?db=actio
	
	static final String address = "http://actio.cwsurf.de/API/";	//"http://actio.cwsurf.de/API/";	//"http://localhost:8000/actio/API/"	//"http://10.0.2.2/actio/API/";
	
	Context _context;
	boolean internet = true;

	public OnlineDatabaseHandler(Context context) {
		_context = context;
		ConnectionDetector cd = new ConnectionDetector(context);
		if (!cd.isConnectingToInternet() && ActivityControl.need_internet) {
			internet = false;
			// Toast.makeText(context, "Connection Error!",
			// Toast.LENGTH_SHORT).show();
		}
	}

	public void execSQL(WebDbUser wdu, String sql) {
		if (internet) {
			WebDbTask wdt = new WebDbTask(wdu);
			wdt.execute("EXEC", sql);
		} else
			wdu.gottenFromWeb(null, false, false);
	}
	
	public void explode(WebDbUser wdu, String string) {
		if (internet) {
			WebDbTask wdt = new WebDbTask(wdu);
			wdt.execute("EXPLODE", string);
		} else
			wdu.gottenFromWeb(null, false, false);
	}

	public void getFromDB(WebDbUser wdu, String table, String id_name,
			String id_value, String column) {
		if (internet) {
			WebDbTask wdt = new WebDbTask(wdu);
			wdt.execute("GET", table, id_name, id_value, column);
		} else
			wdu.gottenFromWeb(null, false, false);
	}

	public void getAllFromDB(WebDbUser wdu, String table, String column,
			String id_name, String id_value) {
		if (internet) {
			WebDbTask wdt = new WebDbTask(wdu);
			wdt.execute("GETALL", table, column, id_name, id_value);
		} else
			wdu.gottenFromWeb(null, false, false);
	}

	public void smartInUp(WebDbUser wdu, String table, String name_0,
			String value_0, String name_1, String value_1, String name_2,
			String value_2, String name_3, String value_3, String name_4,
			String value_4) {
		if (internet) {
			WebDbTask wdt = new WebDbTask(wdu);
			wdt.execute("SMARTINUP", table, name_0, value_0, name_1, value_1,
					name_2, value_2, name_3, value_3, name_4, value_4);
		} else
			wdu.gottenFromWeb(null, false, false);
	}

	public void inUpData(WebDbUser wdu, String table, String id_name,
			String id_value, String column, String level1, String level2,
			String level3, String level4, String value) {
		if (internet) {
			WebDbTask wdt = new WebDbTask(wdu);
			wdt.execute("INUP_DATA", table, id_name, id_value, column, level1,
					level2, level3, level4, value);
		} else
			wdu.gottenFromWeb(null, false, false);
	}

	public static String md5(String input) throws NoSuchAlgorithmException {
		String result = input;
		if (input != null) {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input.getBytes());
			BigInteger hash = new BigInteger(1, md.digest());
			result = hash.toString(16);
			while (result.length() < 32) {
				result = "0" + result;
			}
		}
		return result;
	}

	private class WebDbTask extends AsyncTask<String, Void, JSONObject> {

		WebDbUser mwdu;
		String command;

		public WebDbTask(WebDbUser wdu) {
			mwdu = wdu;
		}

		protected JSONObject doInBackground(String... params) {
			command = params[0];
			if (command.equals("EXEC")) {
				if (params.length != 2)
					return null;
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				list.add(new BasicNameValuePair("tag", "EXEC"));
				list.add(new BasicNameValuePair("sql", params[1]));
				JSONParser jsonParser = new JSONParser();
				JSONObject json = jsonParser.getJSONFromUrl(
						address, list);
				return json;
			} else if (command.equals("EXPLODE")) {
				if (params.length != 2)
					return null;
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				list.add(new BasicNameValuePair("tag", "EXPLODE"));
				list.add(new BasicNameValuePair("string", params[1]));
				JSONParser jsonParser = new JSONParser();
				JSONObject json = jsonParser.getJSONFromUrl(
						address, list);
				return json;
			} else if (command.equals("GET")) {
				if (params.length != 5)
					return null;
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				list.add(new BasicNameValuePair("tag", "GET"));
				list.add(new BasicNameValuePair("table", params[1]));
				list.add(new BasicNameValuePair("id_name", params[2]));
				list.add(new BasicNameValuePair("id_value", params[3]));
				list.add(new BasicNameValuePair("column", params[4]));
				JSONParser jsonParser = new JSONParser();
				JSONObject json = jsonParser.getJSONFromUrl(
						address, list);
				return json;
			} else if (command.equals("GETALL")) {
				if (params.length != 5)
					return null;
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				list.add(new BasicNameValuePair("tag", "GETALL"));
				list.add(new BasicNameValuePair("table", params[1]));
				list.add(new BasicNameValuePair("column", params[2]));
				list.add(new BasicNameValuePair("id_name", params[3]));
				list.add(new BasicNameValuePair("id_value", params[4]));
				JSONParser jsonParser = new JSONParser();
				JSONObject json = jsonParser.getJSONFromUrl(
						address, list);
				return json;
			} else if (command.equals("SMARTINUP")) {
				if (params.length != 12)
					return null;
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				list.add(new BasicNameValuePair("tag", "SMARTINUP"));
				list.add(new BasicNameValuePair("table", params[1]));
				list.add(new BasicNameValuePair("name_0", params[2]));
				list.add(new BasicNameValuePair("value_0", params[3]));
				list.add(new BasicNameValuePair("name_1", params[4]));
				list.add(new BasicNameValuePair("value_1", params[5]));
				list.add(new BasicNameValuePair("name_2", params[6]));
				list.add(new BasicNameValuePair("value_2", params[7]));
				list.add(new BasicNameValuePair("name_3", params[8]));
				list.add(new BasicNameValuePair("value_3", params[9]));
				list.add(new BasicNameValuePair("name_4", params[10]));
				list.add(new BasicNameValuePair("value_4", params[11]));
				JSONParser jsonParser = new JSONParser();
				JSONObject json = jsonParser.getJSONFromUrl(
						address, list);
				return json;
			} else if (command.equals("INUP_DATA")) {
				if (params.length != 10)
					return null;
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				list.add(new BasicNameValuePair("tag", "INUP_DATA"));
				list.add(new BasicNameValuePair("table", params[1]));
				list.add(new BasicNameValuePair("id_name", params[2]));
				list.add(new BasicNameValuePair("id_value", params[3]));
				list.add(new BasicNameValuePair("column", params[4]));
				list.add(new BasicNameValuePair("level1", params[5]));
				list.add(new BasicNameValuePair("level2", params[6]));
				list.add(new BasicNameValuePair("level3", params[7]));
				list.add(new BasicNameValuePair("level4", params[8]));
				list.add(new BasicNameValuePair("value", params[9]));
				JSONParser jsonParser = new JSONParser();
				JSONObject json = jsonParser.getJSONFromUrl(
						address, list);
				return json;
			}
			return null;
		}

		protected void onPostExecute(JSONObject json) {
			try {
				if (json != null && json.has("success") && json.getString("success") != null
						&& Integer.parseInt(json.getString("success")) == 1) {
					mwdu.gottenFromWeb(json, true, (json != null));
				} else
					mwdu.gottenFromWeb(json, false, (json != null));
			} catch (JSONException e) {
				e.printStackTrace();
				Toast.makeText(_context, R.string.error_sorry, Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	public interface WebDbUser {
		public void gottenFromWeb(JSONObject json, boolean good, boolean success);
	}

}