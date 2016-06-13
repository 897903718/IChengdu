package chanlytech.ichengdu.server;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import chanlytech.ichengdu.R;

/**
 * Created by Lyy on 2015/12/1.
 */
public class IchengDuServer extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Notifi(this,"服务启动");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void Notifi(Context context, String ss) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // 2构建通知
        Notification notification = new Notification(R.mipmap.ic_launcher,
                "测试离线",
                System.currentTimeMillis());
        Intent intent = new Intent();
        PendingIntent contentIntent = PendingIntent.getActivity(context, 100,
                intent, 0);
        notification.setLatestEventInfo(context,"测试离线", ss, contentIntent);
        notification.defaults = Notification.DEFAULT_SOUND;//功能：向通知添加声音、闪灯和振动效果的最简单、使用默认（defaults）属性，可以组合多个属性（和方法1中提示效果一样的） 声音默认
        notification.flags = Notification.FLAG_AUTO_CANCEL;// 提醒标志 点击通知之后自动消失
        notification.iconLevel = Notification.PRIORITY_HIGH;
        // 4发送通知
        nm.notify(100, notification);
    }
}
