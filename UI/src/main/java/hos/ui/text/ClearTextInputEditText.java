package hos.ui.text;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import hos.ui.R;


/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 带清除按钮的 EditText
 */
public final class ClearTextInputEditText extends RegexTextInputEditText
        implements View.OnTouchListener,
        View.OnFocusChangeListener, TextWatcher {

    private Drawable mClearDrawable;

    private OnTouchListener mOnTouchListener;
    private OnFocusChangeListener mOnFocusChangeListener;

    public ClearTextInputEditText(Context context) {
        this(context, null);
    }

    public ClearTextInputEditText(Context context, AttributeSet attrs) {
        this(context, attrs, com.google.android.material.R.attr.editTextStyle);
    }

    @SuppressLint("ClickableViewAccessibility")
    public ClearTextInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClearTextInputEditText, defStyleAttr, 0);
        try {
            int cdId = typedArray.getResourceId(R.styleable.ClearTextInputEditText_x_clearDrawable, -1);
            if (cdId == -1)
                cdId = R.mipmap.x_et_svg_ic_clear_24dp;
            mClearDrawable = AppCompatResources.getDrawable(context, cdId);
            if (mClearDrawable != null) {
                mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
                if (cdId == R.mipmap.x_et_svg_ic_clear_24dp)
                    DrawableCompat.setTint(mClearDrawable, getCurrentHintTextColor());
            }
        } finally {
            typedArray.recycle();
        }
        setDrawableVisible(false);
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        super.addTextChangedListener(this);
    }

    private void setDrawableVisible(final boolean visible) {
        if (mClearDrawable.isVisible() == visible) {
            return;
        }

        mClearDrawable.setVisible(visible, false);
        final Drawable[] drawables = getCompoundDrawables();
        setCompoundDrawables(
                drawables[0],
                drawables[1],
                visible ? mClearDrawable : null,
                drawables[3]);
    }

    @Override
    public void setOnFocusChangeListener(final OnFocusChangeListener onFocusChangeListener) {
        mOnFocusChangeListener = onFocusChangeListener;
    }

    @Override
    public void setOnTouchListener(final OnTouchListener onTouchListener) {
        mOnTouchListener = onTouchListener;
    }

    /**
     * {@link OnFocusChangeListener}
     */

    @Override
    public void onFocusChange(final View view, final boolean hasFocus) {
        if (hasFocus && getText() != null) {
            setDrawableVisible(getText().length() > 0);
        } else {
            setDrawableVisible(false);
        }
        if (mOnFocusChangeListener != null) {
            mOnFocusChangeListener.onFocusChange(view, hasFocus);
        }
    }

    /**
     * {@link OnTouchListener}
     */

    @Override
    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        final int x = (int) motionEvent.getX();
        if (mClearDrawable.isVisible() && x > getWidth() - getPaddingRight() - mClearDrawable.getIntrinsicWidth()) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                setText("");
            }
            return true;
        }
        return mOnTouchListener != null && mOnTouchListener.onTouch(view, motionEvent);
    }

    /**
     * {@link TextWatcher}
     */

    @Override
    public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
        if (isFocused()) {
            setDrawableVisible(s.length() > 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void afterTextChanged(Editable s) {}
}