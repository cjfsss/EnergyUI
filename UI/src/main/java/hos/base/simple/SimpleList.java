package hos.base.simple;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;

import java.util.List;

import hos.ui.R;
import hos.util.utils.ResUtils;

/**
 * <p>Title: SimpleList </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/6/30 12:04
 */
public class SimpleList<ITEM> extends Simple<List<ITEM>> {

    @SafeVarargs
    public SimpleList(int code, @NonNull String message, @Nullable Pair<String, Object>... pairs) {
        super(code, message,pairs);
    }

    @SafeVarargs
    public SimpleList(int code, @NonNull String message, boolean isUpdate, @Nullable Pair<String, Object>... pairs) {
        super(code, message, isUpdate,pairs);
    }

    @SafeVarargs
    public SimpleList(int code, @NonNull String message, @Nullable List<ITEM> items, @Nullable Pair<String, Object>... pairs) {
        super(code, message, items,pairs);
    }

    @SafeVarargs
    public SimpleList(int code, @NonNull String message, @Nullable List<ITEM> items, boolean isUpdate, @Nullable Pair<String, Object>... pairs) {
        super(code, message, items, isUpdate,pairs);
    }

    @SafeVarargs
    public SimpleList(int code, @NonNull String message, @Nullable List<ITEM> items, int pageSize, @Nullable Pair<String, Object>... pairs) {
        super(code, message, items, pageSize,pairs);
    }

    @SafeVarargs
    public SimpleList(int code, @NonNull String message, @Nullable List<ITEM> items, int pageSize, boolean isUpdate, @Nullable Pair<String, Object>... pairs) {
        super(code, message, items, pageSize, isUpdate,pairs);
    }

    public long count() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    @SafeVarargs
    public static <ITEM> SimpleList<ITEM> successList(@NonNull String message, @NonNull List<ITEM> data, @Nullable Pair<String, Object>... pairs) {
        return new SimpleList<>(200, message, data,pairs);
    }

    @SafeVarargs
    public static <ITEM> SimpleList<ITEM> successList(@NonNull String message, @NonNull List<ITEM> data, boolean isUpdate, @Nullable Pair<String, Object>... pairs) {
        return new SimpleList<>(200, message, data, isUpdate,pairs);
    }

    @SafeVarargs
    public static <ITEM> SimpleList<ITEM> successList(@NonNull String message, @NonNull List<ITEM> data, int pageSize, @Nullable Pair<String, Object>... pairs) {
        return new SimpleList<>(200, message, data, pageSize,pairs);
    }

    @SafeVarargs
    public static <ITEM> SimpleList<ITEM> successList(@NonNull String message, @NonNull List<ITEM> data, int pageSize, boolean isUpdate, @Nullable Pair<String, Object>... pairs) {
        return new SimpleList<>(200, message, data, pageSize, isUpdate,pairs);
    }

    @SafeVarargs
    public static <ITEM> SimpleList<ITEM> emptyList(@NonNull String message, @Nullable Pair<String, Object>... pairs) {
        return new SimpleList<>(300, message,pairs);
    }

    @SafeVarargs
    public static <ITEM> SimpleList<ITEM> emptyList(@NonNull String message, boolean isUpdate, @Nullable Pair<String, Object>... pairs) {
        return new SimpleList<>(300, message, isUpdate,pairs);
    }

    @SafeVarargs
    public static <ITEM> SimpleList<ITEM> errorList(@NonNull String message, @Nullable Pair<String, Object>... pairs) {
        return new SimpleList<>(404, message,pairs);
    }

    @SafeVarargs
    public static <ITEM> SimpleList<ITEM> errorList(@NonNull String message, boolean isUpdate, @Nullable Pair<String, Object>... pairs) {
        return new SimpleList<>(404, message, isUpdate,pairs);
    }

    @SafeVarargs
    public static <ITEM> SimpleList<ITEM> errorList(@Nullable Pair<String, Object>... pairs) {
        return new SimpleList<>(404, ResUtils.getString(R.string.http_server_error),pairs);
    }

    @SafeVarargs
    public static <ITEM> SimpleList<ITEM> errorList(boolean isUpdate, @Nullable Pair<String, Object>... pairs) {
        return new SimpleList<>(404, ResUtils.getString(R.string.http_server_error), isUpdate,pairs);
    }

    @SafeVarargs
    public static <ITEM> SimpleList<ITEM> errorList(Throwable throwable, @Nullable Pair<String, Object>... pairs) {
        return new SimpleList<>(404, throwable.getMessage(),pairs);
    }

    @SafeVarargs
    public static <ITEM> SimpleList<ITEM> errorList(Throwable throwable, boolean isUpdate, @Nullable Pair<String, Object>... pairs) {
        return new SimpleList<>(404, throwable.getMessage(), isUpdate,pairs);
    }

    @SafeVarargs
    public static <ITEM> SimpleList<ITEM> netList(Throwable throwable, @Nullable Pair<String, Object>... pairs) {
        return new SimpleList<>(100, throwable.getMessage(),pairs);
    }

    @SafeVarargs
    public static <ITEM> SimpleList<ITEM> netList(Throwable throwable, boolean isUpdate, @Nullable Pair<String, Object>... pairs) {
        return new SimpleList<>(100, throwable.getMessage(), isUpdate,pairs);
    }

    @SafeVarargs
    public static <ITEM> SimpleList<ITEM> netList(@Nullable Pair<String, Object>... pairs) {
        return new SimpleList<>(100, ResUtils.getString(R.string.http_network_error),pairs);
    }

    @SafeVarargs
    public static <ITEM> SimpleList<ITEM> netList(boolean isUpdate, @Nullable Pair<String, Object>... pairs) {
        return new SimpleList<>(100, ResUtils.getString(R.string.http_network_error), isUpdate,pairs);
    }
}
