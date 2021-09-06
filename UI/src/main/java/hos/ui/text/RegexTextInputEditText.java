package hos.ui.text;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;

import hos.ui.R;


/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/06/29
 * desc   : 正则输入限制编辑框
 */
class RegexTextInputEditText extends TextInputEditText {

    private RegexDelegate mRegexDelegate;

    public RegexTextInputEditText(Context context) {
        this(context, null);
    }

    public RegexTextInputEditText(Context context, AttributeSet attrs) {
        this(context, attrs, com.google.android.material.R.attr.editTextStyle);
    }

    public RegexTextInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRegexDelegate = new RegexDelegate(this);
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RegexTextInputEditText, defStyleAttr, 0);
        try {
            if (array.hasValue(R.styleable.RegexTextInputEditText_inputRegex)) {
                setInputRegex(array.getString(R.styleable.RegexTextInputEditText_inputRegex));
            } else {
                if (array.hasValue(R.styleable.RegexTextInputEditText_regexType)) {
                    int regexType = array.getInt(R.styleable.RegexTextInputEditText_regexType, 0);
                    switch (regexType) {
                        case 0x01:
                            setInputRegex(RegexContant.REGEX_MOBILE);
                            break;
                        case 0x02:
                            setInputRegex(RegexContant.REGEX_CHINESE);
                            break;
                        case 0x03:
                            setInputRegex(RegexContant.REGEX_ENGLISH);
                            break;
                        case 0x04:
                            setInputRegex(RegexContant.REGEX_COUNT);
                            break;
                        case 0x05:
                            setInputRegex(RegexContant.REGEX_NAME);
                            break;
                        case 0x06:
                            setInputRegex(RegexContant.REGEX_NONNULL);
                            break;
                        case 0x07:
                            setInputRegex(RegexContant.REGEX_ENGLISH_NUMBER);
                            break;
                        case 0x08:
                            setInputRegex(RegexContant.REGEX_ID_CARD);
                            break;
                        default:
                            break;
                    }
                }
            }
        } finally {
            array.recycle();
        }
        addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                mRegexDelegate.onViewAttachedToWindow(v);
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                mRegexDelegate.onViewDetachedFromWindow(v);
            }
        });
    }

    @NonNull
    public RegexDelegate getRegexDelegate() {
        if (mRegexDelegate == null) {
            mRegexDelegate = new RegexDelegate(this);
        }
        return mRegexDelegate;
    }

    /**
     * 是否有这个输入标记
     */
    public boolean hasInputType(int type) {
        return getRegexDelegate().hasInputType(type);
    }

    /**
     * 添加一个输入标记
     */
    public void addInputType(int type) {
        getRegexDelegate().addInputType(type);
    }

    /**
     * 移除一个输入标记
     */
    public void removeInputType(int type) {
        getRegexDelegate().removeInputType(type);
    }

    /**
     * 设置输入正则
     */
    public void setInputRegex(String regex) {
        getRegexDelegate().setInputRegex(regex);
    }
}