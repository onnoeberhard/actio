package turtle.actio.stuff;
 
import android.content.ContentValues;
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
 
    public void addUser(int id, String email, String data1, String data2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, "USER");
        values.put(VALUE, Integer.toString(id)+"{{;;}}"+email+"{{;;}}"+data1+"{{;;}}"+data2);
        db.insert(TABLE_STUFF, null, values);
        db.close();
    }
     
    public boolean loggedIn() {
        String countQuery = "SELECT * FROM " + TABLE_STUFF + " WHERE name = 'USER'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        if(rowCount > 0)
        	return true;
        return false;
    }
     
    public void logOut(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_STUFF + " WHERE name = 'USER'");
        db.close();
    }
 
}