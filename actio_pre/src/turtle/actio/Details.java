package turtle.actio;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class Details extends Activity {

	public static final String ITEM_ID = "item_id";
	
	int[] titlesid = {R.string.details}; 

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.details);
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(MainActivity.ma.products.get(MainActivity.productids.indexOf(extras.getInt(ITEM_ID))));
        ((TextView)findViewById(R.id.detailsid)).setText(Integer.toString(extras.getInt(ITEM_ID)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @SuppressWarnings("deprecation")
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = new Intent(this, MainActivity.class);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.from(this)
                            .addNextIntent(upIntent)
                            .startActivities();
                    finish();
                } else
                    NavUtils.navigateUpTo(this, upIntent);
                return true;
            case R.id.action_bookmark:
            	item.setIcon(android.R.drawable.star_on);
        }
        return super.onOptionsItemSelected(item);
    }
}
