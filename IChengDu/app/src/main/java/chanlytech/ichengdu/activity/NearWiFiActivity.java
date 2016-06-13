package chanlytech.ichengdu.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.util.show.T;
import com.chanlytech.ui.widget.NotScrollListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import chanlytech.ichengdu.R;
import chanlytech.ichengdu.adapter.OteherWifiAdapter;
import chanlytech.ichengdu.adapter.WiFiAdapter;
import chanlytech.ichengdu.base.BaseActivity;
import chanlytech.ichengdu.config.ResultCode;
import chanlytech.ichengdu.entiy.WifiEntity;
import chanlytech.ichengdu.module.NearWiFiModule;
import chanlytech.ichengdu.util.AESUtil;
import chanlytech.ichengdu.util.PublicTool;
import chanlytech.ichengdu.util.SharedPreferData;
import chanlytech.ichengdu.util.WifiUtils;
import chanlytech.ichengdu.wificonfig.WifiAdmin;

public class NearWiFiActivity extends BaseActivity<NearWiFiModule> implements View.OnClickListener {
    @InjectView(R.id.back)
    ImageView back;
    @InjectView(R.id.top_bar)
    RelativeLayout relativeLayout;
    @InjectView(R.id.free_wifi)
    NotScrollListView mFreewifilist;
    @InjectView(R.id.other_wifi)
    NotScrollListView mNotScrollListView;
    @InjectView(R.id.iv_wifi)
    ImageView iv_wifi;
    @InjectView(R.id.tv_ssid)
    TextView tv_ssid;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.rl_connect)
    RelativeLayout mLayout_connect;
    @InjectView(R.id.ll_free)
    LinearLayout mLayout_free;
    @InjectView(R.id.iv_connect)
    ImageView iv_connect;//一键直连按钮
    private WifiAdmin wifiAdmin;
    //认证接口	final String url = "http://app.skspruce.com/app?A&username="+userName+"&password="+password;
    private List<WifiEntity> mWifiEntities = new ArrayList<>();//获取到的wifi列表
    private List<WifiEntity> mShowwifiEntities = new ArrayList<>();//用于显示的wifi列表
    private List<ScanResult> wifiList = new ArrayList<>();//扫描得到的wifi列表
    private WiFiAdapter mWiFiAdapter;
    private WifiManager mWifiManager;
    private WifiInfo mWifiInfo;
    private WifiConfiguration wc;
    private WifiEntity mWifiEntity;
    private String action=WifiManager.NETWORK_STATE_CHANGED_ACTION;
    @Override
    public int setContentView() {
        return R.layout.activity_near_wi_fi;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        wifiAdmin = new WifiAdmin(this);
        wifiAdmin.startScan();
//
        if(SharedPreferData.readString(this,"wifidata")!=null&&SharedPreferData.readString(this,"wifidata").length()>0){
            try {
                String wifidata = AESUtil.decrypt(AESUtil.KEY, SharedPreferData.readString(this, "wifidata").toString());
                mWifiEntities = JSON.parseArray(wifidata, WifiEntity.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        initView();
        initLinster();
    }

    @Override
    public NearWiFiModule initModule() {
        return new NearWiFiModule(this);
    }

    private void initView() {
        int deviceWidth = PublicTool.getDeviceWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, deviceWidth * 2 / 3);
        relativeLayout.setLayoutParams(params);
        wifiList = wifiAdmin.getWifiList();
        if(wifiList.size()>1){
            //去重
            for(int i=0;i<wifiList.size();i++){
                for (int j=i+1;j<wifiList.size();j++){
                    if(wifiList.get(i).SSID.equals(wifiList.get(j).SSID)){
                        wifiList.remove(wifiList.get(j));
                    }
                }
            }
        }
        mNotScrollListView.setAdapter(new OteherWifiAdapter(this, wifiList));
        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        mWifiInfo = mWifiManager.getConnectionInfo();
        if (mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
            mLayout_connect.setVisibility(View.VISIBLE);
            tv_ssid.setText(mWifiInfo.getSSID().substring(1, mWifiInfo.getSSID().length() - 1));
            iv_wifi.setBackgroundResource(WifiUtils.wifiLevel(Math.abs(mWifiInfo.getRssi())));
        } else {
            mLayout_connect.setVisibility(View.GONE);
        }
        for (int i = 0; i < wifiList.size(); i++) {
            for (int j = 0; j < mWifiEntities.size(); j++) {
                if (wifiList.get(i).SSID.equals(mWifiEntities.get(j).getSsid())) {
                    WifiEntity wifiEntity = mWifiEntities.get(j);
                    wifiEntity.setLevel(Math.abs(wifiList.get(i).level));

                    mShowwifiEntities.add(wifiEntity);
                }
            }

        }

        if (mShowwifiEntities.size() > 0) {

            mLayout_free.setVisibility(View.VISIBLE);
            mWiFiAdapter = new WiFiAdapter(this, mShowwifiEntities);
            mFreewifilist.setAdapter(mWiFiAdapter);
        } else {
            mLayout_free.setVisibility(View.GONE);
        }




    }

    private void initLinster() {
        back.setOnClickListener(this);
        mNotScrollListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
//                intent.putExtra("extra_prefs_show_button_bar", true);
//                intent.putExtra("extra_prefs_set_next_text", "完成");
//                intent.putExtra("extra_prefs_set_back_text", "返回");
//                intent.putExtra("wifi_enable_next_on_connect", true);
                startActivity(intent);
            }
        });

        mFreewifilist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (mShowwifiEntities.size() > 0) {
                    mWifiEntity=mShowwifiEntities.get(position);
                    mWifiInfo = mWifiManager.getConnectionInfo();
                    wc = wifiAdmin.CreateWifiInfo(mWifiEntity.getSsid(), "", 1);
                    mWifiManager.disconnect();
                    mWifiManager.enableNetwork(mWifiManager.addNetwork(wc), true);
                    mWifiManager.saveConfiguration();
                    mWifiManager.reconnect();
                    T.showLong(NearWiFiActivity.this, "当前wifi正在连接认证中，请稍后....");
                    Log.i("认证账户", mWifiEntity.getAcc() + mWifiEntity.getPass());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getModule().authWifi(mWifiEntity.getAcc(), mWifiEntity.getPass());

                        }
                    }, 5000);
                }
            }
        });

        iv_connect.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_connect://一键直连
                int random = (int) (Math.random() * mShowwifiEntities.size());
                if (mShowwifiEntities.size() > 0) {
                    mWifiEntity=mShowwifiEntities.get(random);
                    mWifiInfo = mWifiManager.getConnectionInfo();
                    wc = wifiAdmin.CreateWifiInfo(mWifiEntity.getSsid(), "", 1);
                    mWifiManager.disconnect();
                    mWifiManager.enableNetwork(mWifiManager.addNetwork(wc), true);
                    mWifiManager.saveConfiguration();
                    mWifiManager.reconnect();
                    T.showLong(NearWiFiActivity.this, "当前wifi正在认证中，请稍后....");
                    Log.i("认证账户", mWifiEntity.getAcc() + mWifiEntity.getPass());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getModule().authWifi(mWifiEntity.getAcc(), mWifiEntity.getPass());

                        }
                    }, 5000);
                }
                break;
        }
    }

    @Override
    protected void dataCallback(int result, Object data) {
        super.dataCallback(result, data);
        switch (result) {
            case ResultCode.AUTH:
                String arg0 = data.toString();
                Log.i("认证结果", arg0);
                if (arg0.contains("0")) {
                    T.showLong(this, "wifi认证成功");
                    if(mWifiEntity!=null){
                        mLayout_connect.setVisibility(View.VISIBLE);
                        tv_ssid.setText(mWifiEntity.getSsid());
                        showWifiLevel(mWifiEntity.getLevel(), iv_wifi);
                        tv_name.setText(mWifiEntity.getTypename());
                    }
//                    Intent mIntent=new Intent(action);
//                    sendBroadcast(mIntent);
                } else if (arg0.contains("1")) {
                    T.showLong(this, "没有用户名");
                    mLayout_connect.setVisibility(View.GONE);
                } else if (arg0.contains("2")) {
                    T.showLong(this, "没有密码");
                    mLayout_connect.setVisibility(View.GONE);
                } else if (arg0.contains("5")) {
                    T.showLong(this, "找不到用户");
                    mLayout_connect.setVisibility(View.GONE);
                } else if (arg0.contains("6")) {
                    T.showLong(this, "用户密码出错");
                    mLayout_connect.setVisibility(View.GONE);
                } else if (arg0.contains("7")) {
                    T.showLong(this, "wifi认证成功");
                    if(mWifiEntity!=null){
                        mLayout_connect.setVisibility(View.VISIBLE);
                        tv_ssid.setText(mWifiEntity.getSsid());
                        showWifiLevel(mWifiEntity.getLevel(), iv_wifi);
                        tv_name.setText(mWifiEntity.getTypename());
                    }
//                    Intent mIntent=new Intent(action);
//                    sendBroadcast(mIntent);
                }
                break;
            case ResultCode.EORROR_CODE:
                T.showLong(this, "认证失败了");
                mLayout_connect.setVisibility(View.GONE);
                break;
        }
    }


    private void   showWifiLevel(int level,ImageView imageView){
        if (level > 100) {
            imageView.setBackgroundResource(R.mipmap.pic_wifi_01);
        } else if (level > 80) {
            imageView.setBackgroundResource(R.mipmap.pic_wifi_02);
        } else if (level> 70) {
            imageView.setBackgroundResource(R.mipmap.pic_wifi_03);
        } else if (level> 60) {
            imageView.setBackgroundResource(R.mipmap.pic_wifi_04);
        } else if (level > 50) {
            imageView.setBackgroundResource(R.mipmap.pic_wifi_04);
        } else {
            imageView.setBackgroundResource(R.mipmap.pic_wifi_04);
        }
    }



    //通过广播来接收wifi连接状态
   private BroadcastReceiver wifiReceiver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (null != parcelableExtra) {
                    NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                    NetworkInfo.State state = networkInfo.getState();
                    if(state== NetworkInfo.State.CONNECTED){  //WiFi连接上了
                        if(mWifiEntity!=null){
                            mLayout_connect.setVisibility(View.VISIBLE);
                            tv_ssid.setText(mWifiEntity.getSsid());
                            showWifiLevel(mWifiEntity.getLevel(), iv_wifi);
                            tv_name.setText(mWifiEntity.getTypename());
                        }
                    }else if( state==state.DISCONNECTED){//wifi断开

//					Toast.makeText(mContext, "WiFi断开了", Toast.LENGTH_LONG).show();
                    }

                }
            }
        }
    };

//    @Override
//    protected void onResume() {
//        super.onResume();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
//        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
//        registerReceiver(wifiReceiver, filter);
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unregisterReceiver(wifiReceiver);
//    }
}
