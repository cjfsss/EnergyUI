package hos.ui.text;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;

import hos.ui.R;


/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/08/25
 *    desc   : 密码隐藏显示 EditText
 */
public final class PasswordEditText extends RegexEditText
        implements View.OnTouchListener,
                   View.OnFocusChangeListener,
        TextWatcher {

    private static final int TYPE_VISIBLE = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
    private static final int TYPE_INVISIBLE = InputType.TYPE_TEXT_VARIATION_PASSWORD;
    private int mShowPwdResId;
    private int mHidePwdResId;

    private Drawable mCurrentDrawable;
    private final Drawable mVisibleDrawable;
    private final Drawable mInvisibleDrawable;
    private boolean mPasswordEnable;

    private OnTouchListener mOnTouchListener;
    private OnFocusChangeListener mOnFocusChangeListener;

    public PasswordEditText(Context context) {
        this(context, null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    @SuppressWarnings("all")
    @SuppressLint("ClickableViewAccessibility")
    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PasswordEditText, defStyleAttr, 0);
        try {
            mShowPwdResId = typedArray.getResourceId(R.styleable.PasswordEditText_x_showPwdDrawable, -1);
            mHidePwdResId = typedArray.getResourceId(R.styleable.PasswordEditText_x_hidePwdDrawable, -1);
            if (mShowPwdResId == -1)
                mShowPwdResId = R.mipmap.x_et_svg_ic_show_password_24dp;
            if (mHidePwdResId == -1)
                mHidePwdResId = R.mipmap.x_et_svg_ic_hide_password_24dp;
            mVisibleDrawable = AppCompatResources.getDrawable(context, mHidePwdResId);
            mInvisibleDrawable = AppCompatResources.getDrawable(context, mShowPwdResId);
            mVisibleDrawable.setBounds(0, 0, mVisibleDrawable.getIntrinsicWidth(), mVisibleDrawable.getIntrinsicHeight());
            mInvisibleDrawable.setBounds(0, 0, mInvisibleDrawable.getIntrinsicWidth(), mInvisibleDrawable.getIntrinsicHeight());
        } finally {
            typedArray.recycle();
        }

        mCurrentDrawable = mVisibleDrawable;

        // 密码不可见
        addInputType(TYPE_INVISIBLE);
        // 密码输入规则
        setInputRegex(RegexContant.REGEX_NONNULL);

        setPasswordEnable(true);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setPasswordEnable(boolean enable){
        mPasswordEnable = enable;
        setDrawableVisible(false);
        if (enable) {

            setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            super.setOnTouchListener(this);
            super.setOnFocusChangeListener(this);
            super.addTextChangedListener(this);
        } else {
            setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
            super.setOnTouchListener(null);
            super.setOnFocusChangeListener(null);
            super.removeTextChangedListener(null);
        }
    }

    private void setDrawableVisible(boolean visible) {
        if (mCurrentDrawable.isVisible() == visible) {
            return;
        }

        mCurrentDrawable.setVisible(visible, false);
        Drawable[] drawables = getCompoundDrawables();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setCompoundDrawablesRelative(
                    drawables[0],
                    drawables[1],
                    visible ? mCurrentDrawable : null,
                    drawables[3]);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(drawables[0],
                    drawables[1],
                    visible ? mCurrentDrawable : null,
                    drawables[3]);
        }
    }

    private void refreshDrawableStatus() {
        Drawable[] drawables = getCompoundDrawables();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setCompoundDrawablesRelative(
                    drawables[0],
                    drawables[1],
                    mCurrentDrawable,
                    drawables[3]);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(drawables[0],
                    drawables[1],
                    mCurrentDrawable,
                    drawables[3]);
        }
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        mOnFocusChangeListener = onFocusChangeListener;
    }

    @Override
    public void setOnTouchListener(OnTouchListener onTouchListener) {
        mOnTouchListener = onTouchListener;
    }

    /**
     * {@link OnFocusChangeListener}
     */

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
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
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        if (mCurrentDrawable.isVisible() && x > getWidth() - getPaddingRight() - mCurrentDrawable.getIntrinsicWidth()) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if (mCurrentDrawable == mVisibleDrawable) {
                    mCurrentDrawable = mInvisibleDrawable;
                    // 密码可见
                    removeInputType(TYPE_INVISIBLE);
                    addInputType(TYPE_VISIBLE);
                    refreshDrawableStatus();
                } else if (mCurrentDrawable == mInvisibleDrawable) {
                    mCurrentDrawable = mVisibleDrawable;
                    // 密码不可见
                    removeInputType(TYPE_VISIBLE);
                    addInputType(TYPE_INVISIBLE);
                    refreshDrawableStatus();
                }
                Editable editable = getText();
                if (editable != null) {
                    setSelection(editable.toString().length());
                }
            }
            return true;
        }
        return mOnTouchListener != null && mOnTouchListener.onTouch(view, motionEvent);
    }

    /**
     * {@link TextWatcher}
     */

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!mPasswordEnable) {
            return;
        }
        if (isFocused()) {
            setDrawableVisible(s.length() > 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void afterTextChanged(Editable s) {}
}