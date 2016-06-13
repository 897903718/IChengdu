package chanlytech.ichengdu.util;

import chanlytech.ichengdu.R;

/**
 * Created by Lyy on 2015/12/3.
 */
public class WifiUtils {
    private static int imgid;
    /**
     * 根据wifi信号强度返回不懂等级的图片
     * */

    public static int wifiLevel(int level){
        if (level > 100) {
            imgid=R.mipmap.pic_wifi_01;
        } else if (level > 80) {
            imgid=R.mipmap.pic_wifi_02;
        } else if (level> 70) {
            imgid=R.mipmap.pic_wifi_03;
        } else if (level> 60) {
            imgid=R.mipmap.pic_wifi_04;
        } else if (level > 50) {
            imgid=R.mipmap.pic_wifi_04;
        } else {
            imgid=R.mipmap.pic_wifi_04;
        }
        return imgid;
    }
}
