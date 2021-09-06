package hos.ui.scroll;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class AutoHorizontalScrollTextView extends AppCompatTextView {

    private boolean isMarquee = true;
    private boolean isStart = true;

    public AutoHorizontalScrollTextView(Context context) {
        this(context,null);
    }

    public AutoHorizontalScrollTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public AutoHorizontalScrollTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    @Override
    public boolean isFocused() {
        if (!isStart) {
            return super.isFocused();
        }
        return isMarquee;
    }

    public void onResume() {
        isMarquee = true;
    }

    public void onStop() {
        isMarquee = false;
    }
}
