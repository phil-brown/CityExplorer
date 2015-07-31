package self.philbrown.cityexplorer.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * This custom ImageView overrides {@link #onMeasure(int, int)} to correctly scale the image
 * to use the full available width.
 *
 * @author Phil Brown
 * @since 10:20 PM Jul 30, 2015
 */
public class FullWidthImageView extends ImageView {

    public FullWidthImageView(Context context)
    {
        super(context);
    }

    public FullWidthImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public FullWidthImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
        else {
            int width = MeasureSpec.getSize(widthMeasureSpec);

            int height = width * drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth();
            setMeasuredDimension(width, height);
        }
    }
}