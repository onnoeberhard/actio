package onno.actio;

import android.os.Bundle;
import android.view.View;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        overridePendingTransition(0, 0);
        registerHideableHeaderView(findViewById(R.id.butter_bar));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        registerHideableHeaderView(findViewById(R.id.headerbar));
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_HOME;
    }

}