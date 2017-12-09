package actio.app;

import net.sourceforge.zbar.Symbol;
import actio.app.R;
import actio.app.functions.ActioActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;

public class ScanActivity extends ActioActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    mFragment = new ScanFragment();
	    Bundle args = new Bundle();
	    mFragment.setArguments(args);
    	drawer_home = 2;
		super.onCreate(savedInstanceState);
	}
	
	public static class ScanFragment extends Fragment {
        public ScanFragment() {}
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.scan_fragment, container, false);
            return rootView;
        }
    }

	public static final int ZBAR_SCANNER_REQUEST = 0;
	
	public void scan(View v) {
		Intent intent = new Intent(this, ZBarScannerActivity.class);
		intent.putExtra(ZBarConstants.SCAN_MODES, new int[]{Symbol.QRCODE});
		startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{    
	    if (resultCode == RESULT_OK) 
	    {
	        Toast.makeText(this, "Scan Result = " + data.getStringExtra(ZBarConstants.SCAN_RESULT), Toast.LENGTH_SHORT).show();
	        Toast.makeText(this, "Scan Result Type = " + data.getIntExtra(ZBarConstants.SCAN_RESULT_TYPE, 0), Toast.LENGTH_SHORT).show();
	    } else if(resultCode == RESULT_CANCELED) {
	        Toast.makeText(this, "Camera unavailable", Toast.LENGTH_SHORT).show();
	    }
	}
	
}