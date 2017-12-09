package actio.app.functions;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

public class SearchSuggest extends ContentProvider {
	public static final String AUTHORITY = "actio.app.functions.SearchSuggest";
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		System.out.println("HI");
		Cursor c = null;
		MatrixCursor mCursor = null;
		mCursor = new MatrixCursor(new String[] { "_id", SearchManager.SUGGEST_COLUMN_TEXT_1, SearchManager.SUGGEST_COLUMN_INTENT_EXTRA_DATA }  );
		mCursor.addRow(new String[] { "-1", "Browse Categories", "" });				
		c = mCursor;
		return c;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		System.out.println("HI");
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		System.out.println("HI");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		System.out.println("HI");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		System.out.println("HI");
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		System.out.println("HI");
		// TODO Auto-generated method stub
		return 0;
	}
}