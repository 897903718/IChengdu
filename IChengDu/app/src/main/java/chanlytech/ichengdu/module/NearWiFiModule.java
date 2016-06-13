package chanlytech.ichengdu.module;

import android.content.Context;

import com.loopj.android.http.RequestParams;

import chanlytech.ichengdu.base.BaseModule;
import chanlytech.ichengdu.config.ResultCode;
import chanlytech.ichengdu.http.LoadDatahandler;
import chanlytech.ichengdu.http.MyAsyncHttpResponseHandler;
import chanlytech.ichengdu.http.MyHttpClient;

/**
 * Created by Lyy on 2015/12/17.
 */
public class NearWiFiModule extends BaseModule {
    private Context mContext;
//    private String authUrl="http://app.skspruce.com/app?A&username=";
    public NearWiFiModule(Context context) {
        super(context);
        this.mContext=context;
    }


    //wifi认证接口
    public void authWifi(String admin,String pass){
//        RequestParams params=new RequestParams();
//        params.put("userName",admin);
//        params.put("password", pass);
         String authUrl="http://app.skspruce.com/app?A&username="+admin+"&password="+pass;
        MyHttpClient.getInstance(mContext).get(authUrl,new MyAsyncHttpResponseHandler(new LoadDatahandler() {
            @Override
            public void onSuccess(String data) {
                super.onSuccess(data);
                callback(ResultCode.AUTH, data);
            }

            @Override
            public void onFailure(String error, String message) {
                super.onFailure(error, message);
                callback(ResultCode.EORROR_CODE, ResultCode.EORROR_MSG);
            }
        }));
    }
}
