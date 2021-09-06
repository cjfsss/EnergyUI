package hos.base.simple;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;


import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import hos.ui.R;
import hos.util.utils.AppCompat;
import hos.util.utils.PairUtils;
import hos.util.utils.ResUtils;
import hos.util.utils.StringUtils;

/**
 * <p>Title: Simple </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/6/30 11:40
 */
public class Simple<DATA> implements Serializable {

    public int code;
    @NonNull
    public String message;
    @Nullable
    public DATA data;

    public Map<String, Object> param;

    private final int pageSize;

    private boolean isUpdate;

    @SafeVarargs
    public Simple(int code, @NonNull String message, @Nullable Pair<String, Object>... pairs) {
        this(code, message, null, AppCompat.getApp().getPageSize(), pairs);
    }

    @SafeVarargs
    public Simple(int code, @NonNull String message, boolean isUpdate, @Nullable Pair<String, Object>... pairs) {
        this(code, message, null, AppCompat.getApp().getPageSize(), isUpdate, pairs);
    }

    @SafeVarargs
    public Simple(int code, @NonNull String message, @Nullable DATA data, @Nullable Pair<String, Object>... pairs) {
        this(code, message, data, AppCompat.getApp().getPageSize(), pairs);
    }

    @SafeVarargs
    public Simple(int code, @NonNull String message, @Nullable DATA data, boolean isUpdate, @Nullable Pair<String, Object>... pairs) {
        this(code, message, data, AppCompat.getApp().getPageSize(), isUpdate, pairs);
    }

    @SafeVarargs
    public Simple(int code, @NonNull String message, @Nullable DATA data, int pageSize, @Nullable Pair<String, Object>... pairs) {
        this(code, message, data, pageSize, true, pairs);
    }

    @SafeVarargs
    public Simple(int code, @NonNull String message, @Nullable DATA data, int pageSize, boolean isUpdate, @Nullable Pair<String, Object>... pairs) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.pageSize = pageSize;
        this.isUpdate = isUpdate;
        this.param = PairUtils.toMap(pairs);
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public long count() {
        if (data == null) {
            return 0;
        }
        if (data instanceof List) {
            //noinspection rawtypes
            return ((List) data).size();
        }
        return 0;
    }

    public int pageSize() {
        return pageSize;
    }

    public boolean hasMore() {
        return count() >= pageSize();
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public boolean isEmpty() {
        if (data == null) {
            return true;
        }
        if (data instanceof String) {
            return StringUtils.toNULL((String) data) == null;
        }
        if (data instanceof List) {
            //noinspection rawtypes
            return ((List) data).isEmpty();
        }
        return code == 300;
    }

    /**
     * 成功
     */
    public boolean isSuccessFul() {
        return code == 200;
    }

    /**
     * 网络异常
     */
    public boolean isNetworkError() {
        return code == 100;
    }

    /**
     * 服务端异常
     */
    public boolean isError() {
        return code == 404;
    }

    @Override
    public String toString() {
        return "Simple{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", pageSize=" + pageSize +
                ", isUpdate=" + isUpdate +
                '}';
    }

    @SafeVarargs
    public static <DATA> Simple<DATA> success(@NonNull String message, @NonNull DATA data, @Nullable Pair<String, Object>... pairs) {
        return new Simple<>(200, message, data, pairs);
    }

    @SafeVarargs
    public static <DATA> Simple<DATA> success(@NonNull String message, @NonNull DATA data, boolean isUpdate, @Nullable Pair<String, Object>... pairs) {
        return new Simple<>(200, message, data, isUpdate, pairs);
    }

    @SafeVarargs
    public static <DATA> Simple<DATA> success(@NonNull String message, @NonNull DATA data, int pageSize, @Nullable Pair<String, Object>... pairs) {
        return new Simple<>(200, message, data, pageSize, pairs);
    }

    @SafeVarargs
    public static <DATA> Simple<DATA> success(@NonNull String message, @NonNull DATA data, int pageSize, boolean isUpdate, @Nullable Pair<String, Object>... pairs) {
        return new Simple<>(200, message, data, pageSize, isUpdate, pairs);
    }

    @SafeVarargs
    public static <DATA> Simple<DATA> empty(@NonNull String message, @Nullable Pair<String, Object>... pairs) {
        return new Simple<>(300, message, pairs);
    }

    @SafeVarargs
    public static <DATA> Simple<DATA> empty(@NonNull String message, boolean isUpdate, @Nullable Pair<String, Object>... pairs) {
        return new Simple<>(300, message, isUpdate, pairs);
    }

    @SafeVarargs
    public static <DATA> Simple<DATA> error(@NonNull String message, @Nullable Pair<String, Object>... pairs) {
        return new Simple<>(404, message, pairs);
    }

    @SafeVarargs
    public static <DATA> Simple<DATA> error(@NonNull String message, boolean isUpdate, @Nullable Pair<String, Object>... pairs) {
        return new Simple<>(404, message, isUpdate, pairs);
    }

    @SafeVarargs
    public static <DATA> Simple<DATA> error(@Nullable Pair<String, Object>... pairs) {
        return new Simple<>(404, ResUtils.getString(R.string.http_server_error), pairs);
    }

    @SafeVarargs
    public static <DATA> Simple<DATA> error(boolean isUpdate, @Nullable Pair<String, Object>... pairs) {
        return new Simple<>(404, ResUtils.getString(R.string.http_server_error), isUpdate, pairs);
    }

    @SafeVarargs
    public static <DATA> Simple<DATA> error(Throwable throwable, @Nullable Pair<String, Object>... pairs) {
        String message = throwable.getMessage();
        if (TextUtils.isEmpty(message)) {
            return Simple.error(pairs);
        }
        return new Simple<>(404, Objects.requireNonNull(message), pairs);
    }

    @SafeVarargs
    public static <DATA> Simple<DATA> error(Throwable throwable, boolean isUpdate, @Nullable Pair<String, Object>... pairs) {
        String message = throwable.getMessage();
        if (TextUtils.isEmpty(message)) {
            return Simple.error(pairs);
        }
        return new Simple<>(404, Objects.requireNonNull(message), isUpdate, pairs);
    }

    @SafeVarargs
    public static <DATA> Simple<DATA> net(Throwable throwable, @Nullable Pair<String, Object>... pairs) {
        String message = throwable.getMessage();
        if (TextUtils.isEmpty(message)) {
            return Simple.net(pairs);
        }
        return new Simple<>(100, Objects.requireNonNull(message), pairs);
    }

    @SafeVarargs
    public static <DATA> Simple<DATA> net(Throwable throwable, boolean isUpdate, @Nullable Pair<String, Object>... pairs) {
        String message = throwable.getMessage();
        if (TextUtils.isEmpty(message)) {
            return Simple.net(pairs);
        }
        return new Simple<>(100, Objects.requireNonNull(message), isUpdate, pairs);
    }

    @SafeVarargs
    public static <DATA> Simple<DATA> net(@Nullable Pair<String, Object>... pairs) {
        return new Simple<>(100, ResUtils.getString(R.string.http_network_error), pairs);
    }

    @SafeVarargs
    public static <DATA> Simple<DATA> net(boolean isUpdate, @Nullable Pair<String, Object>... pairs) {
        return new Simple<>(100, ResUtils.getString(R.string.http_network_error), isUpdate, pairs);
    }
}
