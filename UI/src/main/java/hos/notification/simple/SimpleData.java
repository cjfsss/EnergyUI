package hos.notification.simple;

import androidx.annotation.Nullable;

import hos.notification.BaseNotificationData;


/**
 * <p>Title: SimpleData </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/6/12 14:08
 */
public class SimpleData implements BaseNotificationData {

    private int notifyId;
    private int icon;
    private String title;

    private String content;

    public SimpleData(int notifyId, int icon, String title, String content) {
        this.notifyId = notifyId;
        this.icon = icon;
        this.title = title;
        this.content = content;
    }

    @Override
    public int getSmallIcon() {
        return icon;
    }

    @Nullable
    @Override
    public CharSequence getContentTitle() {
        return title;
    }

    @Nullable
    @Override
    public CharSequence getContentText() {
        return content;
    }

    @Override
    public int getNotifyId() {
        return notifyId;
    }
}
