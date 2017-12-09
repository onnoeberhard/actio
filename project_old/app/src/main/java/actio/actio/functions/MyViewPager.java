package actio.actio.functions;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {

	private boolean mSwipeable = true;
//	private ViewPager mParentViewPager;
	
    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public void setSwipeable(boolean swipeable) {
    	mSwipeable = swipeable;
    }
    
    public boolean getSwipeable() {
    	return mSwipeable;
    }
    
//    public void setParentViewPager(ViewPager pager) {
//    	mParentViewPager = pager;
//    }
//    
//    public ViewPager getParentViewPager() {
//    	return mParentViewPager;
//    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if(mSwipeable)
        	return super.onInterceptTouchEvent(event);
//        return (mParentViewPager != null) ? mParentViewPager.onInterceptTouchEvent(event) : false;
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	if(mSwipeable)
    		return super.onTouchEvent(event);
//        return (mParentViewPager != null) ? mParentViewPager.onTouchEvent(event) : false;
    	return false;
    }
}