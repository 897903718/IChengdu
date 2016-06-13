package chanlytech.ichengdu.activity;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.util.NetUtils;
import com.arialyy.frame.util.show.T;
import com.chanlytech.ui.widget.NotScrollGridView;
import com.igexin.sdk.PushManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;
import chanlytech.ichengdu.R;
import chanlytech.ichengdu.adapter.BannerAdapter;
import chanlytech.ichengdu.adapter.ExtensionAdapter;
import chanlytech.ichengdu.adapter.ServerAdapter;
import chanlytech.ichengdu.base.BaseActivity;
import chanlytech.ichengdu.base.BaseApplication;
import chanlytech.ichengdu.base.BaseEntity;
import chanlytech.ichengdu.config.ResultCode;
import chanlytech.ichengdu.entiy.BannerEntity;
import chanlytech.ichengdu.entiy.ServerEntity;
import chanlytech.ichengdu.entiy.WifiEntity;
import chanlytech.ichengdu.module.MainModule;
import chanlytech.ichengdu.util.AESUtil;
import chanlytech.ichengdu.util.PublicTool;
import chanlytech.ichengdu.util.SharedPreferData;
import chanlytech.ichengdu.web.ServerWebActivity;

public class MainActivity extends BaseActivity<MainModule> implements Runnable, View.OnClickListener {
    @InjectView(R.id.ad_vp)
    ViewPager mViewPager;
    @InjectView(R.id.ll_add_four)
    LinearLayout mLayout;
    @InjectView(R.id.tv_user)
    TextView tv_user;
    @InjectView(R.id.people_life)
    NotScrollGridView notScrollGridView;
    @InjectView(R.id.happy_life)
    NotScrollGridView scrollGridView;
    @InjectView(R.id.grid_view)
    NotScrollGridView gridView;
    @InjectView(R.id.rl_ad)
    RelativeLayout relativeLayout;
    private BannerAdapter bannerAdapter;
    private List<ImageView> imageViews = new ArrayList<ImageView>();// 用来装小圆点的集合
    private Timer mTimer = new Timer();
    private boolean mIsUserTouched = false;
    private int mBannerPosition = 0;
    private static final String TAG = "MainActivity";
    private List<ServerEntity> mServerEntities1 = new ArrayList<>();
    private List<ServerEntity> mServerEntities2 = new ArrayList<>();
    private List<ServerEntity> mServerEntities3 = new ArrayList<>();
    private List<BannerEntity> mBannerEntities = new ArrayList<>();
    private Intent mIntent;
//    private String wifiData=null;
    @Override
    public int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        BaseApplication.getAppLoctin().addActivity(this);
        PushManager.getInstance().initialize(this.getApplicationContext());
        mTimer.schedule(mTimerTask, 5000, 5000);
        initView();
        initLinster();
//        //如果缓存里面有直接读取缓存数据，否则从网络获取
//        if(SharedPreferData.readString(this,"wifidata").length()>0){
//         }else {
//
//        }

        if(NetUtils.isConnected(this)){
            //网络连接
            getModule().getWifiList();
            if(SharedPreferData.readString(this,"lasttime").length()==0){
                getModule().isUpdateWifiList("0");
            }else {
                getModule().isUpdateWifiList(SharedPreferData.readString(this,"lasttime").toString());
            }
        }

    }

    @Override
    public MainModule initModule() {
        return new MainModule(this);
    }

    private void initView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, PublicTool.getDeviceWidth() / 2);
        relativeLayout.setLayoutParams(params);
        mBannerEntities.add(new BannerEntity("1", "黄哥江油肥肠", "http://f.hiphotos.baidu.com/image/w%3D310/sign=a9dd4884cabf6c81f7372ae98c3fb1d7/a5c27d1ed21b0ef42e8a34b2dbc451da80cb3eff.jpg", "http://huanggejiangyoufeichang.chanlytech.com:8088/huanggejiangyoufeichang/"));
        mBannerEntities.add(new BannerEntity("2", "原视觉婚纱摄影", "http://f.hiphotos.baidu.com/image/w%3D310/sign=08aa79229f25bc312b5d07996ede8de7/9e3df8dcd100baa1e84eeb574110b912c9fc2eff.jpg", "http://yuanshijue.chanlytech.com:8088/yuanshijue/"));
        mBannerEntities.add(new BannerEntity("3", "黄老五花生酥", "http://b.hiphotos.baidu.com/image/w%3D310/sign=caffefd54d36acaf59e090fd4cd88d03/5fdf8db1cb13495408334488504e9258d1094a28.jpg", "http://huanglaowu.chanlytech.com/huanglaowu/"));
        bannerAdapter = new BannerAdapter(this, mBannerEntities, mViewPager);
        mViewPager.setAdapter(bannerAdapter);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN
                        || action == MotionEvent.ACTION_MOVE) {
                    mIsUserTouched = true;
                } else if (action == MotionEvent.ACTION_UP) {
                    mIsUserTouched = false;
                }
                return false;
            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                mBannerPosition = arg0;
                setIndicator(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        initFod(mBannerEntities);
        //http://m.8684.cn/chengdu_bus
        mServerEntities1.add(new ServerEntity("2", "公交查询", "http://e.hiphotos.baidu.com/image/w%3D310/sign=d1c79421a951f3dec3b2bf65a4ecf0ec/94cad1c8a786c91733ef0ed2cf3d70cf3ac75768.jpg", "http://map.baidu.com/"));
        mServerEntities1.add(new ServerEntity("3", "政务导航", "http://h.hiphotos.baidu.com/image/w%3D310/sign=bc0fdd548a5494ee872209181df7e0e1/c8177f3e6709c93d3f1e521c993df8dcd0005468.jpg", "http://laodongjianguan.chanlytech.com:8088/laodongjianguan/index.php/daohang/daohang?showgps"));
        mServerEntities1.add(new ServerEntity("4", "快递查询", "http://e.hiphotos.baidu.com/image/w%3D310/sign=81d63159b4b7d0a27bc9029cfbed760d/2cf5e0fe9925bc3170825c7358df8db1ca137079.jpg", "http://m.kuaidi100.com/index_all.html"));
        mServerEntities1.add(new ServerEntity("1", "周边wi-fi", "http://d.hiphotos.baidu.com/image/w%3D310/sign=19dbbf53d23f8794d3ff4e2fe21a0ead/f636afc379310a553887821fb14543a9832610bd.jpg",null));
        notScrollGridView.setAdapter(new ServerAdapter(this, mServerEntities1));
        mServerEntities2.add(new ServerEntity("1", "好吃嘴", "http://d.hiphotos.baidu.com/image/w%3D310/sign=85caf4cdfbfaaf5184e387bebc5694ed/241f95cad1c8a78682c616e56109c93d71cf5068.jpg"));
        mServerEntities2.add(new ServerEntity("2", "好健康", "http://f.hiphotos.baidu.com/image/w%3D310/sign=dc8b7c1530d3d539c13d09c20a86e927/37d12f2eb9389b505aa3bcb98335e5dde6116ef8.jpg"));
        mServerEntities2.add(new ServerEntity("3", "好出游", "http://c.hiphotos.baidu.com/image/w%3D310/sign=ed3ea7c09b16fdfad86cc0ef848d8cea/314e251f95cad1c8ed57a0cc793e6709c83d5168.jpg"));
        mServerEntities2.add(new ServerEntity("4", "好生活", "http://a.hiphotos.baidu.com/image/w%3D310/sign=4b50aa26fa039245a1b5e70eb795a4a8/aa64034f78f0f736275d10ea0c55b319eac413bd.jpg"));
        scrollGridView.setAdapter(new ServerAdapter(this, mServerEntities2));
        mServerEntities3.add(new ServerEntity("1", "嘉诚世纪", "http://d.hiphotos.baidu.com/image/w%3D310/sign=e0c533d89445d688a302b4a594c37dab/024f78f0f736afc35a09c58eb519ebc4b64512d7.jpg", "http://jiachengshiji.chanlytech.com:8088/jiachengshiji/"));
        mServerEntities3.add(new ServerEntity("2", "思凡沉香", "http://g.hiphotos.baidu.com/image/w%3D310/sign=6cf888cbbf12c8fcb4f3f0cccc0292b4/5bafa40f4bfbfbedb517cc947ef0f736aec31f8a.jpg", "http://sifanchenxiang.chanlytech.com:8088/sifanchenxiang/"));
        gridView.setAdapter(new ExtensionAdapter(this, mServerEntities3));
    }

    private void initLinster() {
        tv_user.setOnClickListener(this);
        notScrollGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mServerEntities1.get(position).getUrl() != null) {
                    mIntent = new Intent(MainActivity.this, ServerWebActivity.class);
                    mIntent.putExtra("title", mServerEntities1.get(position).getName());
                    mIntent.putExtra("url", mServerEntities1.get(position).getUrl());
                    startActivity(mIntent);
                } else {
                    mIntent = new Intent(MainActivity.this, NearWiFiActivity.class);
//                    mIntent.putExtra("wifidata",wifiData);
                    startActivity(mIntent);
                }

            }
        });
        scrollGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mIntent = new Intent(MainActivity.this, BaiduZhidaActivity.class);
                mIntent.putExtra("title", mServerEntities2.get(position).getName());
                startActivity(mIntent);
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mIntent = new Intent(MainActivity.this, ServerWebActivity.class);
                mIntent.putExtra("title", mServerEntities3.get(position).getName());
                mIntent.putExtra("url", mServerEntities3.get(position).getUrl());
                startActivity(mIntent);
            }
        });

    }

    private void initFod(List<BannerEntity> bannerEntities) {
        if (mLayout != null) {
            mLayout.removeAllViews();
            for (int i = 0; i < bannerEntities.size(); i++) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(12, 0, 12, 0);
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(params);
                imageView.setBackgroundResource(R.drawable.ic_dot_hollow);
                mLayout.addView(imageView);
                imageViews.add(imageView);

            }
        }
        if (imageViews.size() > 0) {
            imageViews.get(0).setImageResource(R.drawable.ic_dot_solid);
        }

    }

    //定时器
    private TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            if (!mIsUserTouched) {
                mBannerPosition = (mBannerPosition + 1) % 100;
                runOnUiThread(MainActivity.this);
                Log.d(TAG, "tname:" + Thread.currentThread().getName());
            }
        }
    };

    //切换小圆点
    private void setIndicator(int position) {
        if (mBannerEntities.size() > 0) {
            position %= mBannerEntities.size();
            for (ImageView indicator : imageViews) {
                indicator.setImageResource(R.drawable.ic_dot_hollow);
            }
            imageViews.get(position).setImageResource(R.drawable.ic_dot_solid);
        }

    }

    @Override
    public void run() {
        if (mBannerPosition == 100 - 1) {
            mViewPager.setCurrentItem(mBannerEntities.size() - 1, false);
        } else {
            mViewPager.setCurrentItem(mBannerPosition);
        }
    }


    @Override
    protected void onDestroy() {
        mTimer.cancel();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_user:
                T.showLong(this,"正在开发中");
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void dataCallback(int result, Object data) {
        super.dataCallback(result, data);
        switch (result){
            case ResultCode.UPDATE_WIFI://检查wifi更新
                BaseEntity baseEntity1 = JSON.parseObject(data.toString(), BaseEntity.class);
                if(baseEntity1.getStatus()==1){
                    WifiEntity wifiEntity=JSON.parseObject(baseEntity1.getData(), WifiEntity.class);
                    if(wifiEntity.getIsupdate()==1){
                        getModule().getWifiList();
                    }
                }
                break;
            case ResultCode.WIFI_LIST://获取wifi列表
                BaseEntity baseEntity = JSON.parseObject(data.toString(), BaseEntity.class);
                if(baseEntity.getStatus()==1){
//                    wifiData=baseEntity.getData();
                    //进行加密
                    try {
                        String encrypt = AESUtil.encrypt(AESUtil.KEY, baseEntity.getData());
                        SharedPreferData.writeStringdata(this,"wifidata",encrypt);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                    JSON.parseObject(baseEntity.getData(), WifiEntity.class);
                }
                break;

        }
    }
}
