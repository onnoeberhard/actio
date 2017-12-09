package actio.actio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import actio.actio.functions.ActioActivity;
import actio.actio.functions.LocalDatabaseHandler;

public class DevNoteActivity extends ActioActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    mFragment = new MainFragment();
	    Bundle args = new Bundle();
	    mFragment.setArguments(args);
    	drawer_home = 0;
		super.onCreate(savedInstanceState);
		startActivity(new Intent(this, ActivityMain.class));
	}
	
	public static class MainFragment extends Fragment {
        public MainFragment() {}
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.devnote_fragment, container, false);
            LocalDatabaseHandler dbl = new LocalDatabaseHandler(getActivity());
            ArrayList<String> notes = dbl.getAllFromDb("dev_notes", "note");
            if(notes != null) {
	            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, notes);
	            ((ListView)rootView.findViewById(R.id.home_note_list)).setAdapter(adapter);
	            ((ListView)rootView.findViewById(R.id.home_note_list)).setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						final CharSequence text = ((TextView)view).getText();
						final EditText et = new EditText(getActivity());
						et.setText(text);
						et.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
						new AlertDialog.Builder(getActivity()).setView(et)
							.setPositiveButton("K", new OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
						            LocalDatabaseHandler dbl = new LocalDatabaseHandler(getActivity());
						            dbl.smartInUp("dev_notes", "note", text.toString(), et.getText().toString().equals("") ? "" : "note", et.getText().toString());
								}
							})
							.setNeutralButton("DEL", new OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
						            LocalDatabaseHandler dbl = new LocalDatabaseHandler(getActivity());
						            dbl.smartInUp("dev_notes", "note", text.toString(), "", "");
								}
							})
							.setNegativeButton("NO", new OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.cancel();
								}
							})
							.create().show();
						return false;
					}
				});
            }
            return rootView;
        }
    }
	
	public void addnote(View v) {
		final EditText et = new EditText(this);
		et.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		new AlertDialog.Builder(this).setView(et)
			.setPositiveButton("K", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
		            LocalDatabaseHandler dbl = new LocalDatabaseHandler(DevNoteActivity.this);
		            dbl.smartInUp("dev_notes", "note", et.getText().toString(), "note", et.getText().toString());
				}
			})
			.setNegativeButton("NO", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			})
			.create().show();
	}
	
	public void home(View v) {
		startActivity(new Intent(this, ActivityMain.class));
	}
	
}