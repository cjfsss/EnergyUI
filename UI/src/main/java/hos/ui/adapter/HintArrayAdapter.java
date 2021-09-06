package hos.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.List;

import hos.ui.R;

/**
 * <p>Title: FirstArrayAdapter </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/3/24 20:46
 */
public class HintArrayAdapter extends ArrayAdapter<String> {

    private final String mFirstHint;
    private final String mFirstDropdownText;

    public HintArrayAdapter(@NonNull Context context, @NonNull List<String> dataList, String firstHint) {
        this(context, android.R.layout.simple_spinner_item, dataList, firstHint, null);
    }

    public HintArrayAdapter(@NonNull Context context, @NonNull List<String> dataList, String firstHint, String firstDropdownText) {
        this(context, android.R.layout.simple_spinner_item, dataList, firstHint, firstDropdownText);
    }

    public HintArrayAdapter(@NonNull Context context, int layoutId, @NonNull List<String> dataList, String firstHint, String firstDropdownText) {
        this(context, layoutId, R.layout.support_simple_spinner_dropdown_item, dataList, firstHint, firstDropdownText);
    }

    public HintArrayAdapter(@NonNull Context context, int layoutId, @NonNull List<String> dataList, String firstHint) {
        this(context, layoutId, R.layout.support_simple_spinner_dropdown_item, dataList, firstHint, null);
    }

    public HintArrayAdapter(@NonNull Context context, @LayoutRes int layoutId, @LayoutRes int dropdownLayoutId, @NonNull List<String> dataList, String firstHint) {
        this(context, layoutId, dropdownLayoutId, dataList, firstHint, null);
    }

    public HintArrayAdapter(@NonNull Context context, @LayoutRes int layoutId, @LayoutRes int dropdownLayoutId, @NonNull List<String> dataList, String firstHint, String firstDropdownText) {
        super(context, layoutId, dataList);
        setDropDownViewResource(dropdownLayoutId);
        mFirstHint = firstHint;
        mFirstDropdownText = firstDropdownText;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View textView = super.getView(position, convertView, parent);
        if (position == 0) {
            if (textView instanceof TextView && !TextUtils.isEmpty(mFirstHint)) {
                ((TextView) textView).setText(mFirstHint);
                return textView;
            }
        }
        return textView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View dropDownView = super.getDropDownView(position, convertView, parent);
        if (position == 0) {
            if (dropDownView instanceof TextView && !TextUtils.isEmpty(mFirstDropdownText)) {
                ((TextView) dropDownView).setText(mFirstDropdownText);
                return dropDownView;
            }
        }
        return dropDownView;
    }
}
