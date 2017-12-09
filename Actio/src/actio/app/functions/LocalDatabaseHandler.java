package actio.app.functions;
 
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class LocalDatabaseHandler extends SQLiteOpenHelper {
 
    public LocalDatabaseHandler(Context context) {
        super(context, "actio", null, 1);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE stuff(id INTEGER PRIMARY KEY, name TEXT, value TEXT)");
        db.execSQL("CREATE TABLE friends(id INTEGER PRIMARY KEY, user TEXT, name TEXT)");
        db.execSQL("CREATE TABLE nearby_places(id INTEGER PRIMARY KEY, data TEXT)");
        db.execSQL("CREATE TABLE herenow(id INTEGER PRIMARY KEY, data TEXT)");
        db.execSQL("CREATE TABLE dev_notes(id INTEGER PRIMARY KEY, note TEXT)");
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS stuff");
        db.execSQL("DROP TABLE IF EXISTS friends");
        db.execSQL("DROP TABLE IF EXISTS nearby_places");
        db.execSQL("DROP TABLE IF EXISTS herenow");
        db.execSQL("DROP TABLE IF EXISTS dev_notes");
        onCreate(db);
    }
 
    public String getStuffValue(String what) {
    	String result = "";
    	String countQuery = "SELECT * FROM `stuff` WHERE `name` = '" + what + "'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor.getCount() > 0) {
	        cursor.moveToFirst();
	        result = cursor.getString(cursor.getColumnIndex("value"));
        } else
        	result = "DOES NOT EXIST";
        cursor.close();
    	return result;
    }
    
    public void setStuffValue(String name, String value) {
    	SQLiteDatabase db = getWritableDatabase();
		String sql = !(getStuffValue(name).equals("DOES NOT EXIST")) ? (value.equals("*DELETE*")) ?
				("DELETE FROM `stuff` WHERE `name` = '" + name + "'") :
				("UPDATE `stuff` SET `value` = '" + value + "' WHERE `name` = '" + name + "'") : 
				("INSERT INTO `stuff` (`name`, `value`)VALUES('" + name + "', '" + value + "')");
		db.execSQL(sql);
    }
    
    public String getFromDb(String table, String id_name, String id_value, String column) {
    	String result = "";
    	String countQuery = "SELECT * FROM `" + table + "` WHERE `" + id_name + "` = '" + id_value + "'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor.getCount() > 0) {
	        cursor.moveToFirst();
	        result = cursor.getString(cursor.getColumnIndex(column));
        } else
        	result = "DOES NOT EXIST";
        cursor.close();
    	return result;
    }
    
    public ArrayList<String> getAllFromDb(String table, String column) {
    	ArrayList<String> result = new ArrayList<String>();
    	String countQuery = "SELECT * FROM `" + table + "`";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor.getCount() > 0) {
	        cursor.moveToFirst();
    		result.add(cursor.getString(cursor.getColumnIndex(column)));
        	while(cursor.moveToNext()) {
        		result.add(cursor.getString(cursor.getColumnIndex(column)));
        	}
        } else
        	result = null;
        cursor.close();
    	return result;
    }
    
    public void smartInUp(String table, String id_name, String id_value, String column, String value) {
    	SQLiteDatabase db = getWritableDatabase();
    	String sql = !(getFromDb(table, id_name, id_value, "id").equals("DOES NOT EXIST")) ? (column.equals("") && value.equals("")) ? 
				("DELETE FROM `" + table + "` WHERE `" + id_name + "` = '" + id_value + "'") :  
					("UPDATE `" + table + "` SET `" + column  + "` = '" + value + "' WHERE `" + id_name + "` = '" + id_value + "'") :
				("INSERT INTO `" + table + "` (`" + id_name + "`, `" + column + "`)VALUES('" + id_value + "', '" + value + "')");
		db.execSQL(sql);
    }
    
    public void execSQL(String sql) {
    	SQLiteDatabase db = getWritableDatabase();
    	db.execSQL(sql);
    }
   
    public static JSONObject explode(String string) throws JSONException {
    	JSONObject json = new JSONObject();
    	String[] level1 = string.split("[{][{][;][}][}]");
    	for(int i = 0; i < level1.length; i++) {
    		if(level1[i].contains("{{:}}")) {
    			String[] level2 = level1[i].split("[{][{][:][}][}]");
    			boolean l2_obj = false;
    			for(int ii = 0; ii < level2.length; ii++) {
    				if(level2[ii].contains("{{,}}")) {
    					String[] level3 = level2[ii].split("[{][{][,][}][}]");
    					if(!l2_obj)
    						json.put(level2[0], new JSONObject());
						l2_obj = true;
    					json.getJSONObject(level2[0]).put(level3[0], new JSONArray());
    	    			for(int iii = 0; iii < level3.length; iii++) {
//    	    				if(level2[ii].contains("{{,}}")) {
//    	    					String[] level3 = level2[ii].split("[{][{][,][}][}]");
//    	    	    			json.put(level3[0], new JSONArray());
//    	    				} else {
    	    					json.getJSONObject(level2[0]).getJSONArray(level3[0]).put(level3[iii]);
//    	    				}
    	    			}
    				} else {
    	    			json.put(level2[0], new JSONArray());
    					json.getJSONArray(level2[0]).put(level2[ii]);
    				}
    			}
    		} else
    			json.put(Integer.toString(i), level1[i]);
    	}
    	return json;
    }
    
 
}