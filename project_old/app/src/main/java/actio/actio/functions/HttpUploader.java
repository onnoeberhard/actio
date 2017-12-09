package actio.actio.functions;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HttpUploader extends AsyncTask<String, Void, String> {

	Context context;
	
	public HttpUploader(Context c) {
		super();
		context = c;
	}
	
	protected String doInBackground(String... args) {

		String outPut = null;

//		for (String sdPath : path) {

			Bitmap bitmapOrg = BitmapFactory.decodeFile(args[0]);
			ByteArrayOutputStream bao = new ByteArrayOutputStream();

			double width = bitmapOrg.getWidth();
			double height = bitmapOrg.getHeight();
			double ratio = 400 / width;
			int newheight = (int) (ratio * height);

			bitmapOrg = Bitmap.createScaledBitmap(bitmapOrg, 400, newheight,
					true);

			bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 95, bao);
			byte[] ba = bao.toByteArray();
			String ba1 = Base64.encodeToString(ba, 0);

			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
    		Date date = new Date();
    		
			String img_name = dateFormat.format(date) + "_" + args[1] + "_img.";
			
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("type", "img"));
			nameValuePairs.add(new BasicNameValuePair("format", "jpg"));
			nameValuePairs.add(new BasicNameValuePair("image", ba1));
			nameValuePairs.add(new BasicNameValuePair("img_name", img_name));
			nameValuePairs.add(new BasicNameValuePair("title", args[2]));
			nameValuePairs.add(new BasicNameValuePair("number", args[3]));
			
			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(
						"http://actio.cwsurf.de/API/upload.php");
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();

				outPut = EntityUtils.toString(entity);

				bitmapOrg.recycle();
				System.out.println("upload1");
				OnlineDatabaseHandler dbo = new OnlineDatabaseHandler(context);
				dbo.inUpData(new OnlineDatabaseHandler.WebDbUser() {
					@Override
					public void gottenFromWeb(JSONObject json, boolean good, boolean success) {
						if(success && good)
							Toast.makeText(context, "SUCCESS!", Toast.LENGTH_SHORT).show();
						else
							Toast.makeText(context, "NO SUCCESSS!!", Toast.LENGTH_SHORT).show();
					}
				}, "activities", "id", args[1], "data1", "img", Integer.toString(Integer.parseInt(args[3]) + 1), Integer.toString(Integer.parseInt(args[3]) + 1) + "{{:}}http://actio.cwsurf.de/upload/" + img_name + "jpg{{:}}" + args[2], "", "");

			} catch (Exception e) {
				e.printStackTrace();
			}
//		}
		return outPut;
	}
}