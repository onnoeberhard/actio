package actio.app.functions;

import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.GoogleAnalytics;

import android.app.Application;

import java.util.HashMap;

public class ActioApp extends Application {

    private static final String PROPERTY_ID = "UA-49452602-2";

    public static int GENERAL_TRACKER = 0;

    public enum TrackerName { APP_TRACKER }

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    public ActioApp() {
        super();
    }

    public synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = analytics.newTracker(PROPERTY_ID);
            mTrackers.put(trackerId, t);

        }
        return mTrackers.get(trackerId);
    }
}
