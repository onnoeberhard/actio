package actio.actio;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import actio.actio.functions.ActioActivity;


public class BrowseCategoriesActivity extends ActioActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    mFragment = new CreateFragment();
	    Bundle args = new Bundle();
	    mFragment.setArguments(args);
    	drawer_home = -1;
		super.onCreate(savedInstanceState);
		from_details = getIntent().getBooleanExtra("FROM_DETAILS", false);
	}
	
	public static class CreateFragment extends Fragment {
        public CreateFragment() {}
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.create_fragment, container, false);
            return rootView;
        }
    }
	
}