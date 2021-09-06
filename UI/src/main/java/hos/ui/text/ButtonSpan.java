package hos.ui.text;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

import hos.core.AppCompatApplication;


/**
 * <p>Title: ButtonSpan </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/9/10 12:03
 */
public class ButtonSpan extends ClickableSpan {

    private final View.OnClickListener onClickListener;
    private final int colorId;

    public ButtonSpan(View.OnClickListener onClickListener) {
        this(onClickListener, Color.WHITE);
    }

    public ButtonSpan(View.OnClickListener onClickListener, int colorId) {
        this.onClickListener = onClickListener;
        this.colorId = colorId;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(AppCompatApplication.getAppCompatApplication().getResources().getColor(colorId));
        ds.setTextSize(dip2px(16));
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(@NonNull View widget) {
        if (onClickListener != null) {
            onClickListener.onClick(widget);
        }
    }

    public static int dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
