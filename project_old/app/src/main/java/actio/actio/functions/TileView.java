package actio.actio.functions;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class TileView extends RelativeLayout {

	public TileView(final Context context) {
		super(context);
	}

	public TileView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	public TileView(final Context context, final AttributeSet attrs,
			final int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}

}