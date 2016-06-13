package chanlytech.ichengdu.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import chanlytech.ichengdu.server.IchengDuServer;

/**
 * Created by Lyy on 2015/12/1.
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, IchengDuServer.class);
        context.startService(service);
        //启动应用，参数为需要自动启动的应用的包名 
//        Intent intent1 =context.getPackageManager().getLaunchIntentForPackage("chanlytech.ichengdu");
//        context.startActivity(intent1);
    }
}
