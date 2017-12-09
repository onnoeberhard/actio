package actio.actio.functions;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class TouchController implements View.OnTouchListener {

	private SwipeInterface activity;
	static final int MIN_DISTANCE = 100;
	private float downX, downY, upX, upY;
	long time_down, time_elapsed;
	TimerTask tt;
	final Handler handler = new Handler();
	Timer t = new Timer();
	boolean longclicked = false;

	public TouchController(SwipeInterface activity) {
		this.activity = activity;
	}

	public void onRightToLeftSwipe(View v) {
		activity.right2left(v);
	}

	public void onLeftToRightSwipe(View v) {
		activity.left2right(v);
	}

	public void onTopToBottomSwipe(View v) {
		activity.top2bottom(v);
	}

	public void onBottomToTopSwipe(View v) {
		activity.bottom2top(v);
	}

	public void timer(final View v, final MotionEvent e, final float dx, final float dy) {
		tt = new TimerTask() {
			public void run() {
				handler.post(new Runnable() {
					public void run() {
						time_elapsed = System.nanoTime() - time_down;
						if (time_elapsed >= 1000000000) {
							time_elapsed = 0;
							if (!(dx - e.getX() > 50 || dy - e.getY() > 50)) {
								v.performLongClick();
								longclicked = true;
							}
							tt.cancel();
						}
					}
				});
			}
		};
		t.schedule(tt, 0, 200);
	}

	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				downX = event.getX();
				downY = event.getY();
				time_down = System.nanoTime();
				timer(v, event, downX, downY);
				longclicked = false;
				return true;
			}
			case MotionEvent.ACTION_UP: {
				upX = event.getX();
				upY = event.getY();
				if (longclicked) {
					tt.cancel();
					return true;
				}
				if(tt != null)
					tt.cancel();
	
				float deltaX = downX - upX;
				float deltaY = downY - upY;
	
				if (Math.abs(deltaX) > MIN_DISTANCE) {
					if (deltaX < 0) {
						this.onLeftToRightSwipe(v);
						return true;
					}
					if (deltaX > 0) {
						this.onRightToLeftSwipe(v);
						return true;
					}
				}
	
				if (Math.abs(deltaY) > MIN_DISTANCE) {
					if (deltaY < 0) {
						this.onTopToBottomSwipe(v);
						return true;
					}
					if (deltaY > 0) {
						this.onBottomToTopSwipe(v);
						return true;
					}
				} else {
					v.performClick();
				}
			}
		}
		return false;
	}
}