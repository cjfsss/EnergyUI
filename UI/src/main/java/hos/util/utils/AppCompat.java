package hos.util.utils;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import hos.core.AppCompatApplication;
import hos.util.compat.IntentCompat;

/**
 * <p>Title: AppCompat </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/7/12 14:46
 */
public class AppCompat {

    public static AppCompatApplication getApp() {
        return AppCompatApplication.getAppCompatApplication();
    }

    @Nullable
    public static Activity getCurrentActivity() {
        return getApp().getCurrentActivity();
    }

    @NonNull
    public static Context getContext() {
        Activity currentActivity = getCurrentActivity();
        if (currentActivity != null) {
            return currentActivity;
        }
        return getApp();
    }

    public static boolean launchUrl(@NonNull String url) {
        try {
            getContext().startActivity(IntentCompat.getLaunchUrl(url));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean launchUrl(@NonNull Context context, @NonNull String url) {
        try {
            context.startActivity(IntentCompat.getLaunchUrl(url));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean launchMarket(@NonNull String packageName) {
        try {
            getContext().startActivity(IntentCompat.getLaunchMarket(packageName));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean launchMarket(@NonNull Context context, @NonNull String packageName) {
        try {
            context.startActivity(IntentCompat.getLaunchMarket(packageName));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
