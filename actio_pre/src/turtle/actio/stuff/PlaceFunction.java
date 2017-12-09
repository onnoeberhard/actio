package turtle.actio.stuff;
 
import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
 
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
 
public class PlaceFunction {
     
    private JSONParser jsonParser;
     
    // Testing in localhost using wamp or xampp 
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    private static String loginURL = "http://actio.bplaced.net/android_login_api/";
    private static String registerURL = "http://actio.bplaced.net/android_login_api/";
     
    private static String login_tag = "login";
    private static String register_tag = "register";
     
    // constructor
    public PlaceFunction(){
        jsonParser = new JSONParser();
    }
     
    /**
     * function make Login Request
     * @param email
     * @param password
     * */
//    public JSONObject loginUser(String email, String password){
//        // Building Parameters
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("tag", login_tag));
//        params.add(new BasicNameValuePair("email", email));
//        params.add(new BasicNameValuePair("password", password));
//        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
//        // return json
//        // Log.e("JSON", json.toString());
//        return json;
//    }
     
    /**
     * function make Login Request
     * @param name
     * @param email
     * @param password
     * */
    public JSONObject createPlace(String... address){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "addPlace"));
        for(int i = 0; i < address.length; i++)
        	params.add(new BasicNameValuePair("address" + Integer.toString(i), address[i]));
        Log.i("", params.get(1).getValue());
        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        // return json
        return json;
    }
     
}