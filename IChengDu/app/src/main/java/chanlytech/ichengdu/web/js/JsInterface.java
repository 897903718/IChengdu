package chanlytech.ichengdu.web.js;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSONObject;
import com.arialyy.frame.util.show.T;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.net.URISyntaxException;

import chanlytech.ichengdu.web.TestActivity;

/**
 * Created by Lyy on 2015/10/12.
 * 与js交互的方法
 */
public class JsInterface {
    private Context mContext;
    private Intent mIntent;
    private long mFirstClickTime = 0;

    public JsInterface(Context context) {
        this.mContext = context;

    }

    /**
     * 页面跳转()
     */
    @JavascriptInterface
    public void towebpage(String tourl) {
        long time = System.currentTimeMillis();
        if (time - mFirstClickTime < 2000) {

        } else {
            mIntent = new Intent(mContext, TestActivity.class);
            mIntent.putExtra("url", tourl);
            mContext.startActivity(mIntent);
        }
        mFirstClickTime = time;
        if (!isFastClick()) {
            mIntent = new Intent(mContext, TestActivity.class);
            mIntent.putExtra("url", tourl);
            mContext.startActivity(mIntent);
        }
    }

    /**
     * 导航
     */
    @JavascriptInterface
    public void tomapnavigation(double lat, double lng, String name, String addr) {
//        mIntent = new Intent(mContext, MapActivity.class);
//        mIntent.putExtra("lat", lat);
//        mIntent.putExtra("lng", lng);
//        mIntent.putExtra("name",name);
//        mIntent.putExtra("addr", addr);
//        mContext.startActivity(mIntent);
        if (isInstallByread("com.baidu.BaiduMap")) {
            try {//mode导航模式，固定为transit、driving、walking，分别表示公交、驾车和步行
                //region 城市名或县名 当给定region时，认为起点和终点都在同一城市，除非单独给定起点或终点的城市
                mIntent = Intent.getIntent("intent://map/direction?origin=latlng:+" + lng + ",+" + lat + "|name:我的位置&destination=" + addr + "&mode=driving®ion=成都&referer=Autohome|GasStation#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
//                        105.164957,31.638494
//                        mIntent = Intent.getIntent("intent://map/direction?origin=latlng:31.638494,105.164957|name:我的位置&destination="+addr+"&mode=driving&region=成都&src=千立网络科技有限公司|劳动社保#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            mContext.startActivity(mIntent);
        } else {
            T.showLong(mContext,"请下载最新版百度地图");
        }

    }

    /**
     * 向js提交区域位置
     */
//    @JavascriptInterface
//    public String getcurentarea() {
//
//        return AppManager.getUser().getAddress();
//
//    }

    /**
     * 向js提交经纬度
     */
    @JavascriptInterface
    public String getlocation() {
        JSONObject object = new JSONObject();
        object.put("lng", "104.059012");
        object.put("lat", "30.548485 ");
        return toJsonStr(object);
//         latitude : 30.548485  lontitude : 104.059012

    }
    /**
     * 获取用户信息
     */
//    @JavascriptInterface
//    public String getuserinfo() {
//        JSONObject object=new JSONObject();
//        if(AppManager.isLogin()){
//            object.put("userId",AppManager.getUser().getUserId());
//            object.put("nickName",AppManager.getUser().getNickName());
//            object.put("avatar",AppManager.getUser().getAvatar());
//            object.put("cityId",AppManager.getUser().getCityId());
//            object.put("telPhone",AppManager.getUser().getTelPhone());
//            object.put("address",AppManager.getUser().getAddress());
//            object.put("Mail", AppManager.getUser().getMail());
//            object.put("userType", AppManager.getUser().getUserType());
//        }else {
//            object.put("userId","");
//            object.put("nickName","");
//            object.put("avatar","");
//            object.put("cityId","");
//            object.put("telPhone","");
//            object.put("address","");
//            object.put("Mail", "");
//            object.put("userType", "");
//        }
//
//       return toJsonStr(object);
//    }

    /**
     * 返回上一页
     */

    @JavascriptInterface
    public void toprepage() {
        ((Activity) mContext).finish();
    }
    /**
     * 分享
     * */
//    @JavascriptInterface
//    public void jsshare(String title,String content,String imgurl,String url){
//        ShareHelper shareHelper=new ShareHelper(mContext);
//        shareHelper.showSharePop(title, url, content, imgurl);
////        T.showLong(mContext, title + content);
//    }

    /**
     * 跳转到登录页面
     *
     * */
//    @JavascriptInterface
//    public void tologinpage(){
//
//        mIntent = new Intent(mContext, LoginActivity.class);
//        mContext.startActivity(mIntent);
//    }
    /**
     * 返回首页
     * */
//    @JavascriptInterface
//    public void tophomepage(){
//
//        mIntent = new Intent(mContext, MainActivity.class);
//        mContext.startActivity(mIntent);
//        ((Activity) mContext).finish();
//    }

    /**
     * 返回Json字符串
     *
     * @param object
     * @return
     */
    public static String toJsonStr(Object object) {
        String result = "{}";
        if (object != null) {
            result = new GsonBuilder().create().toJson(object);
        }
        return result;
    }

    private boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - mFirstClickTime < 2000) {
            return true;
        }
        mFirstClickTime = time;
        return false;
    }

    /**
     *  
     *  * 判断是否安装目标应用 
     *  * @param packageName 目标应用安装后的包名 
     *  * @return 是否已安装目标应用 
     *  
     */
    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }


}
