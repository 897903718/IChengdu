package chanlytech.ichengdu.module;

import android.content.Context;

import com.arialyy.frame.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;

import chanlytech.ichengdu.base.BaseModule;
import chanlytech.ichengdu.config.ResultCode;
import chanlytech.ichengdu.http.ServerUtil;
import chanlytech.ichengdu.util.SharedPreferData;

/**
 * Created by Lyy on 2015/12/17.
 */
public class MainModule extends BaseModule {
    private Context mContext;
    public MainModule(Context context) {
        super(context);
        mContext=context;
    }


    //获取wifi列表
    public void getWifiList(){
        ServerUtil.getWifiList(new HashMap<String, String>(),new HttpUtil.Response(){
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                callback(ResultCode.WIFI_LIST,data);
            }


            @Override
            public void onError(Object error) {
                super.onError(error);
                callback(ResultCode.EORROR_CODE,ResultCode.EORROR_MSG);
            }
        });
    }


    //有无更新查询
    public void isUpdateWifiList(String lasttime){
        Map<String,String>parmas=new HashMap<>();
        parmas.put("lasttime",lasttime);
        SharedPreferData.writeStringdata(mContext, "lasttime", System.currentTimeMillis()/1000+"");
        ServerUtil.upDateWifiList(parmas,new HttpUtil.Response(){
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                callback(ResultCode.UPDATE_WIFI, data);
            }

            @Override
            public void onError(Object error) {
                super.onError(error);
                callback(ResultCode.EORROR_CODE, ResultCode.EORROR_MSG);
            }
        });
    }
}
