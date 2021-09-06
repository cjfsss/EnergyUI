package hos.ui.text;

import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Pattern;

/**
 * <p>Title: RegexDelegate </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/4/9 22:59
 */
public class RegexDelegate implements InputFilter,
         View.OnAttachStateChangeListener {

    /**
     * 正则表达式规则
     */
    private Pattern mPattern;

    private TextView mTextView;

    public RegexDelegate(TextView textView) {
        mTextView = textView;
    }

    /**
     * 是否有这个输入标记
     */
    public boolean hasInputType(int type) {
        if (mTextView == null) {
            return false;
        }
        return (mTextView.getInputType() & type) != 0;
    }

    /**
     * 添加一个输入标记
     */
    public void addInputType(int type) {
        if (mTextView == null) {
            return;
        }
        mTextView.setInputType(mTextView.getInputType() | type);
    }

    /**
     * 移除一个输入标记
     */
    public void removeInputType(int type) {
        if (mTextView == null) {
            return;
        }
        mTextView.setInputType(mTextView.getInputType() & ~type);
    }

    /**
     * 设置输入正则
     */
    public void setInputRegex(String regex) {
        if (mTextView == null) {
            return;
        }
        if (regex == null || "".equals(regex)) {
            return;
        }

        mPattern = Pattern.compile(regex);
        addFilters(this);
    }

    /**
     * 获取输入正则
     */
    public String getInputRegex() {
        if (mPattern == null) {
            return null;
        }
        return mPattern.pattern();
    }

    /**
     * 添加筛选规则
     */
    public void addFilters(InputFilter filter) {
        if (filter == null) {
            return;
        }
        final InputFilter[] newFilters;
        final InputFilter[] oldFilters = mTextView.getFilters();
        if (oldFilters != null && oldFilters.length > 0) {
            newFilters = new InputFilter[oldFilters.length + 1];
            // 复制旧数组的元素到新数组中
            System.arraycopy(oldFilters, 0, newFilters, 0, oldFilters.length);
            newFilters[oldFilters.length] = filter;
        } else {
            newFilters = new InputFilter[1];
            newFilters[0] = filter;
        }
        mTextView.setFilters(newFilters);
    }

    /**
     * {@link InputFilter}
     *
     * @param source 新输入的字符串
     * @param start  新输入的字符串起始下标，一般为0
     * @param end    新输入的字符串终点下标，一般为source长度-1
     * @param dest   输入之前文本框内容
     * @param dstart 原内容起始坐标，一般为0
     * @param dend   原内容终点坐标，一般为dest长度-1
     * @return 返回字符串将会加入到内容中
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if (mPattern == null) {
            return source;
        }

        // 拼接出最终的字符串
        String begin = dest.toString().substring(0, dstart);
        String over = dest.toString()
                          .substring(dstart + (dend - dstart), dstart + (dest.toString().length() - begin.length()));
        String result = begin + source + over;

        // 判断是插入还是删除
        if (dstart > dend - 1) {
            if (mPattern.matcher(result).matches()) {
                // 如果匹配就允许这个文本通过
                return source;
            }
        } else {
            if (!mPattern.matcher(result).matches()) {
                // 如果不匹配则不让删除（删空操作除外）
                if (!"".equals(result)) {
                    return dest.toString().substring(dstart, dend);
                }
            }
        }

        // 注意这里不能返回 null，否则会和 return source 效果一致
        return "";
    }

    @Override
    public void onViewAttachedToWindow(View v) {
        if (v instanceof TextView) {
            mTextView = (TextView) v;
        }
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        mTextView = null;
    }
}
