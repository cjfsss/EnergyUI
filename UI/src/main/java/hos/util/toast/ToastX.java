package hos.util.toast;

import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * <p>Title: PrintToast </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/1/8 20:05
 */
public class ToastX {

    public interface ToastDelegate {

        /**
         * Set the gravity.
         *
         * @param gravity The gravity.
         * @param xOffset X-axis offset, in pixel.
         * @param yOffset Y-axis offset, in pixel.
         */
        void setGravity(final int gravity, final int xOffset, final int yOffset);

        /**
         * Set the color of background.
         *
         * @param backgroundColor The color of background.
         */
        void setBgColor(final int backgroundColor);

        /**
         * Set the resource of background.
         *
         * @param bgResource The resource of background.
         */
        void setBgResource(final int bgResource);

        /**
         * Set the color of message.
         *
         * @param msgColor The color of message.
         */
        void setMsgColor(final int msgColor);

        /**
         * Set the text size of message.
         *
         * @param textSize The text size of message.
         */
        void setMsgTextSize(final int textSize);

        /**
         * Show the sToast for a short period of time.
         *
         * @param text The text.
         */
        void showShort(final CharSequence text);

        /**
         * Show the sToast for a short period of time.
         *
         * @param resId The resource id for text.
         */
        void showShort(final int resId);

        /**
         * Show the sToast for a short period of time.
         *
         * @param resId The resource id for text.
         * @param args  The args.
         */
        void showShort(final int resId, final Object... args);

        /**
         * Show the sToast for a short period of time.
         *
         * @param format The format.
         * @param args   The args.
         */
        void showShort(final String format, final Object... args);

        /**
         * Show the sToast for a long period of time.
         *
         * @param text The text.
         */
        void showLong(final CharSequence text);

        /**
         * Show the sToast for a long period of time.
         *
         * @param resId The resource id for text.
         */
        void showLong(final int resId);

        /**
         * Show the sToast for a long period of time.
         *
         * @param resId The resource id for text.
         * @param args  The args.
         */
        void showLong(final int resId, final Object... args);

        /**
         * Show the sToast for a long period of time.
         *
         * @param format The format.
         * @param args   The args.
         */
        void showLong(final String format, final Object... args);

        /**
         * Show custom sToast for a short period of time.
         *
         * @param layoutId ID for an XML layout resource to load.
         */
        View showCustomShort(final int layoutId);

        /**
         * Show custom sToast for a long period of time.
         *
         * @param layoutId ID for an XML layout resource to load.
         */
        View showCustomLong(final int layoutId);

        /**
         * Cancel the sToast.
         */
        void cancel();
    }

    private static ToastDelegate DELEGETE = null;

    public static void setDelegete(ToastDelegate delegete) {
        DELEGETE = delegete;
    }

    /**
     * Set the gravity.
     *
     * @param gravity The gravity.
     * @param xOffset X-axis offset, in pixel.
     * @param yOffset Y-axis offset, in pixel.
     */
    public static void setGravity(final int gravity, final int xOffset, final int yOffset) {
        if (DELEGETE != null) {
            DELEGETE.setGravity(gravity, xOffset, yOffset);
        }
    }

    /**
     * Set the color of background.
     *
     * @param backgroundColor The color of background.
     */
    public static void setBgColor(@ColorInt final int backgroundColor) {
        if (DELEGETE != null) {
            DELEGETE.setBgColor(backgroundColor);
        }
    }

    /**
     * Set the resource of background.
     *
     * @param bgResource The resource of background.
     */
    public static void setBgResource(@DrawableRes final int bgResource) {
        if (DELEGETE != null) {
            DELEGETE.setBgResource(bgResource);
        }
    }

    /**
     * Set the color of message.
     *
     * @param msgColor The color of message.
     */
    public static void setMsgColor(@ColorInt final int msgColor) {
        if (DELEGETE != null) {
            DELEGETE.setMsgColor(msgColor);
        }
    }

    /**
     * Set the text size of message.
     *
     * @param textSize The text size of message.
     */
    public static void setMsgTextSize(final int textSize) {
        if (DELEGETE != null) {
            DELEGETE.setMsgTextSize(textSize);
        }
    }

    /**
     * Show the sToast for a short period of time.
     *
     * @param text The text.
     */
    public static void showShort(@NonNull final CharSequence text) {
        if (DELEGETE != null) {
            DELEGETE.showShort(text);
        }
    }

    /**
     * Show the sToast for a short period of time.
     *
     * @param resId The resource id for text.
     */
    public static void showShort(@StringRes final int resId) {
        if (DELEGETE != null) {
            DELEGETE.showShort(resId);
        }
    }

    /**
     * Show the sToast for a short period of time.
     *
     * @param resId The resource id for text.
     * @param args  The args.
     */
    public static void showShort(@StringRes final int resId, final Object... args) {
        if (DELEGETE != null) {
            DELEGETE.showShort(resId, args);
        }
    }

    /**
     * Show the sToast for a short period of time.
     *
     * @param format The format.
     * @param args   The args.
     */
    public static void showShort(@NonNull final String format, final Object... args) {
        if (DELEGETE != null) {
            DELEGETE.showShort(format, args);
        }
    }

    /**
     * Show the sToast for a long period of time.
     *
     * @param text The text.
     */
    public static void showLong(@NonNull final CharSequence text) {
        if (DELEGETE != null) {
            DELEGETE.showLong(text);
        }
    }

    /**
     * Show the sToast for a long period of time.
     *
     * @param resId The resource id for text.
     */
    public static void showLong(@StringRes final int resId) {
        if (DELEGETE != null) {
            DELEGETE.showLong(resId);
        }
    }

    /**
     * Show the sToast for a long period of time.
     *
     * @param resId The resource id for text.
     * @param args  The args.
     */
    public static void showLong(@StringRes final int resId, final Object... args) {
        if (DELEGETE != null) {
            DELEGETE.showLong(resId, args);
        }
    }

    /**
     * Show the sToast for a long period of time.
     *
     * @param format The format.
     * @param args   The args.
     */
    public static void showLong(@NonNull final String format, final Object... args) {
        if (DELEGETE != null) {
            DELEGETE.showLong(format, args);
        }
    }

    /**
     * Show custom sToast for a short period of time.
     *
     * @param layoutId ID for an XML layout resource to load.
     */
    public static View showCustomShort(@LayoutRes final int layoutId) {
        if (DELEGETE != null) {
            return DELEGETE.showCustomShort(layoutId);
        }
        return null;
    }

    /**
     * Show custom sToast for a long period of time.
     *
     * @param layoutId ID for an XML layout resource to load.
     */
    public static View showCustomLong(@LayoutRes final int layoutId) {
        if (DELEGETE != null) {
            return DELEGETE.showCustomLong(layoutId);
        }
        return null;
    }

    /**
     * Cancel the sToast.
     */
    public static void cancel() {
        if (DELEGETE != null) {
            DELEGETE.cancel();
        }
    }
}
