package chanlytech.ichengdu.base;

import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;

import com.arialyy.frame.application.AbsApplication;
import com.arialyy.frame.util.show.L;

import java.util.ArrayList;
import java.util.List;

import chanlytech.ichengdu.entiy.CityEntity;
import chanlytech.ichengdu.entiy.UserEntity;
import chanlytech.ichengdu.http.ServerUtil;
import chanlytech.ichengdu.util.CrashHandler;


/**
 * Created by Lyy on 2015/8/31.
 */
public class BaseApplication extends AbsApplication {
    private static BaseApplication mInstance;
    private List<Activity> activities=new ArrayList<Activity>();
//    public LocationClient mLocationClient = null;
//    public BDLocationListener myListener;
//    public GeofenceClient mGeofenceClient;
    public Vibrator mVibrator;
//    private LocationClientOption.LocationMode tempMode = LocationClientOption.LocationMode.Hight_Accuracy;
    private CityEntity mCityEntity;
    @Override
    public void onCreate() {
        super.onCreate();
        // 在使用百度 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
//        SDKInitializer.initialize(getApplicationContext());
        ServerUtil.newInstance(getApplicationContext());//网络请求初始化
        AppManager.newInstance(getApplicationContext());
        mInstance = this;
        mCityEntity=new CityEntity();
//        myListener = new MyLocationListener();
//        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
//        mGeofenceClient = new GeofenceClient(getApplicationContext());
//        mLocationClient.registerLocationListener(myListener);
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
//        InitLocation();
        /**
         * 错误日志收集器
         * */
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
        creatUser();
    }
    public void addActivity(Activity activity){
        activities.add(activity);
    }

    public void exit(){
        for (Activity activity : activities) {
            activity.finish();
        }
        System.exit(0);
    }
    public static BaseApplication getAppLoctin() {
        return mInstance;
    }


    public  static BaseApplication getApp(){
        return  getAppLoctin();
    }

    /**
     * 创建游客
     * */
    private void creatUser(){
        if (!AppManager.isLogin()){
            UserEntity userEntity=new UserEntity();
            AppManager.saveUser(userEntity);
        }
    }
//
//    public class MyLocationListener implements BDLocationListener {
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            if (location == null)
//                return;
//            StringBuffer sb = new StringBuffer(256);
//            sb.append("time : ");
//            sb.append(location.getTime());
//            sb.append("\nerror code : ");
//            sb.append(location.getLocType());
//            sb.append("\nlatitude : ");
//            sb.append(location.getLatitude());
//            sb.append("\nlontitude : ");
//            sb.append(location.getLongitude());
//            sb.append("\nradius : ");
//            sb.append(location.getRadius());
//            sb.append("\ncity:");
//            sb.append(location.getCity());
//            sb.append("\ndistrict:");
//            sb.append(location.getDistrict());//获取区县
//            sb.append("\nfloor：");
//            sb.append(location.getFloor());//获取楼层信息仅限室内
//            if (location.getLocType() == BDLocation.TypeGpsLocation) {
//                sb.append("\nspeed : ");
//                sb.append(location.getSpeed());
//                sb.append("\nsatellite : ");
//                sb.append(location.getSatelliteNumber());//获取gps定位是锁定的卫星数
//            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
//                sb.append("\naddr : ");
//                sb.append(location.getAddrStr());
//            }
//            if(location.getLocType()==61){
//                mCityEntity.setCityId(location.getCityCode());
//                mCityEntity.setLatitude(location.getLatitude());
//                mCityEntity.setLongitude(location.getLongitude());
//
//            }else if(location.getLocType()==65){
//                mCityEntity.setCityId(location.getCityCode());
//                mCityEntity.setLatitude(location.getLatitude());
//                mCityEntity.setLongitude(location.getLongitude());
////                AppManager.saveCity(mCityEntity);
//            }else if(location.getLocType()==66){
//                mCityEntity.setCityId(location.getCityCode());
//                mCityEntity.setLatitude(location.getLatitude());
//                mCityEntity.setLongitude(location.getLongitude());
////                AppManager.saveCity(mCityEntity);
//            }else if(location.getLocType()==161){
//                mCityEntity.setCityId(location.getCityCode());
//                mCityEntity.setLatitude(location.getLatitude());
//                mCityEntity.setLongitude(location.getLongitude());
////                AppManager.saveCity(mCityEntity);
//            }
//            AppManager.saveCity(mCityEntity);
//
//            L.i("当前位置", location.getAddrStr() + sb.toString());
//        }
//
//
//    }

//    //配置定位类型
//    private void InitLocation() {
//        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(tempMode);//设置定位模式
//        option.setOpenGps(true);// 打开gps
//        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度，默认值gcj02
//        int span = 5000;
//        option.setScanSpan(span);//设置发起定位请求的间隔时间为5000ms
//        option.setIsNeedAddress(true);
//        mLocationClient.setLocOption(option);
//    }

}
