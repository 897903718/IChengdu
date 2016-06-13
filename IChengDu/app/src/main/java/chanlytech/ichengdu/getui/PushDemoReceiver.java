package chanlytech.ichengdu.getui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.arialyy.frame.util.show.T;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;

import chanlytech.ichengdu.R;


public class PushDemoReceiver extends BroadcastReceiver {

    /**
     * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)
     */
    public static StringBuilder payloadData = new StringBuilder();
    private Intent mIntent;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));
        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                // 获取透传数据
                // String appid = bundle.getString("appid");
                byte[] payload = bundle.getByteArray("payload");
                String taskid = bundle.getString("taskid");
                String messageid = bundle.getString("messageid");
                // smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
                boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
                System.out.println("第三方回执接口调用" + (result ? "成功" : "失败"));
                if (payload != null) {
                    String data = new String(payload);
                    Log.d("GetuiSdkDemo透传消息", "receiver payload : " + data);
                    //接收透传消息
                    Notifi(context,data);
                    payloadData.append(data);
                    payloadData.append("\n");


//                    if (GetuiSdkDemoActivity.tLogView != null) {
//                        GetuiSdkDemoActivity.tLogView.append(data + "\n");
//                    }
                }
                break;

            case PushConsts.GET_CLIENTID:
                // 获取ClientID(CID)
                // 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
                String cid = bundle.getString("clientid");
                break;

            case PushConsts.THIRDPART_FEEDBACK:
                /*
                 * String appid = bundle.getString("appid"); String taskid =
                 * bundle.getString("taskid"); String actionid = bundle.getString("actionid");
                 * String result = bundle.getString("result"); long timestamp =
                 * bundle.getLong("timestamp");
                 * 
                 * Log.d("GetuiSdkDemo", "appid = " + appid); Log.d("GetuiSdkDemo", "taskid = " +
                 * taskid); Log.d("GetuiSdkDemo", "actionid = " + actionid); Log.d("GetuiSdkDemo",
                 * "result = " + result); Log.d("GetuiSdkDemo", "timestamp = " + timestamp);
                 */
                break;

            default:
                break;
        }
    }



    @SuppressWarnings("deprecation")
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
