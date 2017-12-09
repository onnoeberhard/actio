package actio.app.stuff;
 
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class DatabaseHandler extends SQLiteOpenHelper {
 
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "actio";
    private static final String TABLE_STUFF = "stuff";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String VALUE = "value";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ACCOUNTS_TABLE = "CREATE TABLE " + TABLE_STUFF + "("
                + ID + " INTEGER PRIMARY KEY,"
                + NAME + " TEXT,"
                + VALUE + " TEXT" + ")";
        db.execSQL(CREATE_ACCOUNTS_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUFF);
        onCreate(db);
    }
 
    public void SaveSetup(int how, String ha_street, String ha_city, String ha_country) {
    	if(how == 1) {
	    	if(getStuffValue("FIRST_START").equals("0") || getStuffValue("FIRST_START").equals("")) {
	        	setStuffValue("FIRST_START", "1", false);
	        	setStuffValue("ME_HA_STREET", ha_street, false);
	        	setStuffValue("ME_HA_CITY", ha_city, false);
	        	setStuffValue("ME_HA_COUNTRY", ha_country, false);
	    	} else if(getStuffValue("FIRST_START").equals("1") || getStuffValue("FIRST_START").equals("3")) {
	        	setStuffValue("FIRST_START", "1", true);
	        	setStuffValue("ME_HA_STREET", ha_street, true);
	        	setStuffValue("ME_HA_CITY", ha_city, true);
	        	setStuffValue("ME_HA_COUNTRY", ha_country, true);
	    	} else if(getStuffValue("FIRST_START").equals("2")) {
	        	setStuffValue("FIRST_START", "1", true);
	        	setStuffValue("ME_HA_STREET", ha_street, false);
	        	setStuffValue("ME_HA_CITY", ha_city, false);
	        	setStuffValue("ME_HA_COUNTRY", ha_country, false);
	    	}
    	} else if(how == 2) {
	    	if(getStuffValue("FIRST_START").equals("0") || getStuffValue("FIRST_START").equals(""))
	        	setStuffValue("FIRST_START", "2", false);
    	} else if(how == 3) {
    		setStuffValue("FIRST_START", "3", true);
    	} else if(how == 4) {
    		setStuffValue("FIRST_START", "1", true);
    	}
    }
    
    public String getStuffValue(String what) {
    	String result = "";
    	String countQuery = "SELECT * FROM " + TABLE_STUFF + " WHERE name = '" + what + "'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        System.out.println("OK1");
        if(cursor.getCount() > 0) {
        	System.out.println("OK2");
	        cursor.moveToFirst();
	        result = cursor.getString(cursor.getColumnIndex("value"));
        } cursor.close();
        db.close();
        System.out.println("OK3");
    	return result;
    }
    public void setStuffValue(String name, String value, boolean update) {
    	SQLiteDatabase db = getWritableDatabase();
		String sql = update ? ("UPDATE " + TABLE_STUFF + " SET `value` = '" + value + "' WHERE `name` = '" + name + "'") : 
				("INSERT INTO " + TABLE_STUFF + " (`name`, `value`)VALUES('" + name + "', '" + value + "')");
		db.execSQL(sql);
		String s = getStuffValue("name");
		System.out.println(s);
		db.close();
    }
 
}