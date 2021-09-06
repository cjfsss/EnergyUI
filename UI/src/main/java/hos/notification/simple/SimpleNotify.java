package hos.notification.simple;

import hos.notification.ChannelNotify;
import hos.notification.listener.PendingIntentListener;
import hos.notification.view.BaseRemoteViews;
import hos.notification.view.ContentRemote;
import hos.ui.R;

/**
 * <p>Title: SimpleNotify </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2021/6/12 15:21
 */
public class SimpleNotify extends ChannelNotify<SimpleData> {
    public SimpleNotify(SimpleData data) {
        super(data);
        addContentRemoteViews(R.layout.notify_comm_layout);
    }

    public SimpleNotify(SimpleData data, PendingIntentListener listener) {
        this(data);
        addPendingIntentListener(listener);
    }

    public SimpleNotify(int notifyId, int icon, String title, String content, PendingIntentListener listener) {
        this(new SimpleData(notifyId, icon, title, content));
        addPendingIntentListener(listener);
    }

    @Override
    protected void convert(BaseRemoteViews mBaseRemoteViews, SimpleData data) {
        ContentRemote contentRemote = mBaseRemoteViews.contentRemote;
        if (contentRemote != null) {
            contentRemote
                    .setImageViewResource2(R.id.notify_head_img, data.getSmallIcon())
                    .setTextViewText2(R.id.notify_title, data.getContentTitle())
                    .setTextViewText2(R.id.notify_subtitle, data.getContentText())
                    .setOnClickPendingIntent2(getNotificationId(), R.id.notify_layout);
        }
    }


}
