package hos.util.utils;

import android.animation.StateListAnimator;
import android.app.Application;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.AnimatorRes;
import androidx.annotation.ArrayRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;


import hos.core.AppCompatApplication;
import hos.util.compat.ResAttributeContext;
import hos.util.compat.ResContext;


/**
 * <p>Title: ResUtils </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/1/2 9:44
 */
public class ResUtils {

    private static Application getApplication() {
        return AppCompatApplication.getAppCompatApplication();
    }


    /**
     * 获取colorPrimary的颜色,需要V7包的支持
     *
     * @return 0xAARRGGBB
     */
    public static int getColorPrimary() {
        return ResAttributeContext.getColorPrimary(getApplication());
    }

    /**
     * 判断是否是平板
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isTablet(Context context) {
        if (context == null) {
            context = getApplication();
        }
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


    /**
     * 获取字符串
     */
    public static String getString(@StringRes int id) {
        return ResContext.getString(getApplication(), id);
    }

    /**
     * 获取字符串
     *
     * @return
     */
    public static String getString(@StringRes int id, Object... formatArgs) {
        return ResContext.getString(getApplication(), id, formatArgs);
    }

    public static CharSequence[] getTextArray(@ArrayRes int id) {
        return ResContext.getTextArray(getApplication(), id);
    }

    public static String[] getStringArray(@ArrayRes int id) {
        return ResContext.getStringArray(getApplication(), id);
    }

    public static int[] getIntArray(@ArrayRes int id) {
        return ResContext.getIntArray(getApplication(), id);
    }

    public static int[] getMipmapArray(@ArrayRes int id) {
        return ResContext.getMipmapArray(getApplication(), id);
    }

    @Nullable
    public static Drawable getDrawable(@DrawableRes int id) {
        return ResContext.getDrawable(getApplication(), id);
    }

    @Nullable
    public static ColorStateList getColorStateList(@ColorRes int id) {
        return ResContext.getColorStateList(getApplication(), id);
    }

    @ColorInt
    public static int getColor(@ColorRes int id) {
        return ResContext.getColor(getApplication(), id);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static StateListAnimator getStateListAnimator(@AnimatorRes int id) {
        return ResContext.getStateListAnimator(getApplication(), id);
    }

    /**
     * 判断是否是平板
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isTablet() {
        return (getApplication().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
