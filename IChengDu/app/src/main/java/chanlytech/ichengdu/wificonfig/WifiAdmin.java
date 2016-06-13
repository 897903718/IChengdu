package chanlytech.ichengdu.wificonfig;
import java.net.Inet4Address;
import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.util.Log;

public class WifiAdmin {
	//定义一个WifiManager对象
	private WifiManager mWifiManager;
	//定义一个WifiInfo对象
	private WifiInfo mWifiInfo;
	//扫描出的网络连接列表
	private List<ScanResult> mWifiList;
	//网络连接列表
	private List<WifiConfiguration> mWifiConfigurations;
	WifiLock mWifiLock;
	public Context context;
	public static final int WIFI_CONNECTED = 0x01;  
	public static final int WIFI_CONNECT_FAILED = 0x02;  
	public static final int WIFI_CONNECTING = 0x03;

	public WifiAdmin(Context context){
		//取得WifiManager对象
		mWifiManager=(WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		//取得WifiInfo对象
		mWifiInfo=mWifiManager.getConnectionInfo();
		this.context=context;
		mWifiManager.setWifiEnabled(true);
	}
	//打开wifi
	public boolean openWifi() {
		boolean bRet = true;
		if (!mWifiManager.isWifiEnabled()) {
			bRet = mWifiManager.setWifiEnabled(true);
		}
		return bRet;
	}
	//关闭wifi
	public void closeWifi(){
		if(!mWifiManager.isWifiEnabled()){
			mWifiManager.setWifiEnabled(false);
		}
	}

	//打开wifi
	public void openUserWifi(){
		if(!mWifiManager.isWifiEnabled()){
			mWifiManager.setWifiEnabled(true);
		}
	}


	// 检查当前wifi状态  
	public int checkState() {  
		return mWifiManager.getWifiState();  
	}  
	//锁定wifiLock
	public void acquireWifiLock(){
		mWifiLock.acquire();
	}
	//解锁wifiLock
	public void releaseWifiLock(){
		//判断是否锁定
		if(mWifiLock.isHeld()){
			mWifiLock.acquire();
		}
	}
	//创建一个wifiLock
	public void createWifiLock(){
		mWifiLock=mWifiManager.createWifiLock("test");
	}
	//得到配置好的网络
	public List<WifiConfiguration> getConfiguration(){
		return mWifiConfigurations;
	}
	//指定配置好的网络进行连接
	public void connetionConfiguration(int index){
		if(index>mWifiConfigurations.size()){
			return ;
		}
		//连接配置好指定ID的网络
		mWifiManager.enableNetwork(mWifiConfigurations.get(index).networkId, true);
	}
	public void startScan(){
		mWifiManager.startScan();
		//得到扫描结果
		mWifiList=mWifiManager.getScanResults();
		//得到配置好的网络连接
		mWifiConfigurations=mWifiManager.getConfiguredNetworks();
	}
	//得到网络列表
	public List<ScanResult> getWifiList(){
		return mWifiList;
	}
	//查看扫描结果
	public StringBuffer lookUpScan(){
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<mWifiList.size();i++){
			sb.append("Index_" + new Integer(i + 1).toString() + ":");
			// 将ScanResult信息转换成一个字符串包  
			//其中把包括：BSSID、SSID、capabilities、frequency、level  
			sb.append((mWifiList.get(i)).toString()).append("\n");
		}
		return sb;	
	}
	public String getMacAddress(){
		return (mWifiInfo==null)?"NULL":mWifiInfo.getMacAddress();
	}
	public String getBSSID(){
		return (mWifiInfo==null)?"NULL":mWifiInfo.getBSSID();
	}
	public int getIpAddress(){
		return (mWifiInfo==null)?0:mWifiInfo.getIpAddress();
	}
	//得到连接的ID
	public int getNetWordId(){
		return (mWifiInfo==null)?0:mWifiInfo.getNetworkId();
	}
	//得到wifiInfo的所有信息
	public String getWifiInfo(){
		return (mWifiInfo==null)?"NULL":mWifiInfo.toString();
	}
	//添加一个网络并连接
	public void addNetWork(WifiConfiguration configuration){
		int wcgId=mWifiManager.addNetwork(configuration);
		mWifiManager.enableNetwork(wcgId, true);
	}
	//断开指定ID的网络
	public void disConnectionWifi(int netId){
		mWifiManager.disableNetwork(netId);
		mWifiManager.disconnect();
	}
	//创建连接Configuration
	public WifiConfiguration CreateWifiConfig(String SSID, String Password, WifiConnect.WifiCipherType Type)
	{
		WifiConfiguration config = new WifiConfiguration();
		config.allowedAuthAlgorithms.clear();
		config.allowedGroupCiphers.clear();
		config.allowedKeyManagement.clear();
		config.allowedPairwiseCiphers.clear();
		config.allowedProtocols.clear();
		config.SSID = "\"" + SSID + "\"";
		//无密码
		if (Type == WifiConnect.WifiCipherType.WIFICIPHER_NOPASS) {
			config.wepKeys[0] = "";
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		//WEP加密
		if (Type == WifiConnect.WifiCipherType.WIFICIPHER_WEP) {
			config.preSharedKey = "\"" + Password + "\"";
			config.hiddenSSID = true;
			config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		//WPA加密
		if (Type == WifiConnect.WifiCipherType.WIFICIPHER_WPA) {
			config.preSharedKey = "\"" + Password + "\"";
			config.hiddenSSID = true;
		}else {
			return null;
		}
		return config;

	}
	//判断扫描结果是否连接上
	public boolean isConnect(ScanResult result) {
		if (result == null) {
			return false;
		}

		mWifiInfo = mWifiManager.getConnectionInfo();
//		String ssid = mWifiInfo.getSSID();
		String g2 = result.SSID;
//		String g2 = "\"" + result.SSID + "\"";
//		if (mWifiInfo.getSSID() != null && mWifiInfo.getSSID().endsWith(g2)) {
//			return true;
//		}
		if(mWifiInfo.getSSID()!=null&&mWifiInfo.getSSID().equals(g2)){
			return true;
		}
		return false;
	}
	//将int型的ip转换成字符串型的ip
	public String ipIntToString(int ip) {
		try {
			byte[] bytes = new byte[4];
			bytes[0] = (byte) (0xff & ip);
			bytes[1] = (byte) ((0xff00 & ip) >> 8);
			bytes[2] = (byte) ((0xff0000 & ip) >> 16);
			bytes[3] = (byte) ((0xff000000 & ip) >> 24);
			return Inet4Address.getByAddress(bytes).getHostAddress();
		} catch (Exception e) {
			return "";
		}
	}
	public int getConnNetId() {
		// result.SSID;
		mWifiInfo = mWifiManager.getConnectionInfo();
		return mWifiInfo.getNetworkId();
	}
	//信号强度转换为字符串   得到的值是一个0到-100的区间值，是一个int型数据，其中0到-50表示信号最好，-50到-70表示信号偏差，小于-70表示最差，有可能连接不上或者掉线。
	public static String singlLevToStr(int level) {

		String resuString = "无信号";

		if (Math.abs(level) > 100) {
		} else if (Math.abs(level) > 80) {
			resuString = "及弱";
		} else if (Math.abs(level) > 70) {
			resuString = "弱";
		} else if (Math.abs(level) > 60) {
			resuString = "较弱";
		} else if (Math.abs(level) > 50) {
			resuString = "强";
		} else {
			resuString = "极强";
		}
		return resuString;
	}
	/**
	 * Function: 提供一个外部接口，传入要连接的无线网 <br>
	 * 
	 * @author ZYT DateTime 2014-5-13 下午11:46:54<br>
	 * @param SSID
	 *            SSID
	 * @param Password
	 * @param Type
	 * <br>
	 *            没密码：{@linkplain WifiCipherType#WIFICIPHER_NOPASS}<br>
	 *            WEP加密： {@linkplain WifiCipherType#WIFICIPHER_WEP}<br>
	 *            WPA加密： {@linkplain WifiCipherType#WIFICIPHER_WPA}
	 * @return true:连接成功；false:连接失败<br>
	 */
	public boolean connect(String SSID, String Password, WifiConnect.WifiCipherType Type) {
		if (!this.openWifi()) {
			return false;
		}
		// 开启wifi功能需要一段时间(我在手机上测试一般需要1-3秒左右)，所以要等到wifi
		// 状态变成WIFI_STATE_ENABLED的时候才能执行下面的语句
		while (mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
			try {
				// 为了避免程序一直while循环，让它睡个100毫秒在检测……
				Thread.currentThread();
				Thread.sleep(100);
			} catch (InterruptedException ie) {
			}
		}

		System.out.println("WifiAdmin#connect==连接结束");

		WifiConfiguration wifiConfig = createWifiInfo(SSID, Password, Type);
		//
		if (wifiConfig == null) {
			return false;
		}

		WifiConfiguration tempConfig = this.isExsits(SSID);

	//	int tempId = wifiConfig.networkId;
		if (tempConfig != null) {
		//	tempId = tempConfig.networkId;
			mWifiManager.removeNetwork(tempConfig.networkId);
		}

		int netID = mWifiManager.addNetwork(wifiConfig);

		// 断开连接
		mWifiManager.disconnect();
		// 重新连接
		// netID = wifiConfig.networkId;
		// 设置为true,使其他的连接断开
		boolean bRet = mWifiManager.enableNetwork(netID, true);
		mWifiManager.reconnect();
		return bRet;
	}
	// 查看以前是否也配置过这个网络
	private WifiConfiguration isExsits(String SSID) {
		List<WifiConfiguration> existingConfigs = mWifiManager
				.getConfiguredNetworks();
		for (WifiConfiguration existingConfig : existingConfigs) {
			if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
				return existingConfig;
			}
		}
		return null;
	}
	// 然后是一个实际应用方法，只验证过没有密码的情况：
	public WifiConfiguration CreateWifiConfig(ScanResult scan, String Password) {
		// Password="ultrapower2013";
		// deleteExsits(info.ssid);
		WifiConfiguration config = new WifiConfiguration();
		config.hiddenSSID = false;
		config.status = WifiConfiguration.Status.ENABLED;

		if (scan.capabilities.contains("WEP")) {
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.allowedAuthAlgorithms
			.set(WifiConfiguration.AuthAlgorithm.OPEN);
			config.allowedGroupCiphers
			.set(WifiConfiguration.GroupCipher.WEP104);

			config.SSID = "\"" + scan.SSID + "\"";

			config.wepTxKeyIndex = 0;
			config.wepKeys[0] = Password;
			// config.preSharedKey = "\"" + SHARED_KEY + "\"";
		} else if (scan.capabilities.contains("PSK")) {
			//
			config.SSID = "\"" + scan.SSID + "\"";
			config.preSharedKey = "\"" + Password + "\"";
		} else if (scan.capabilities.contains("EAP")) {
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP);
			config.allowedAuthAlgorithms
			.set(WifiConfiguration.AuthAlgorithm.OPEN);
			config.allowedPairwiseCiphers
			.set(WifiConfiguration.PairwiseCipher.TKIP);
			config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
			config.SSID = "\"" + scan.SSID + "\"";
			config.preSharedKey = "\"" + Password + "\"";
		} else {
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

			config.SSID = "\"" + scan.SSID + "\"";
			// config.BSSID = info.mac;
			config.preSharedKey = null;
			//
		}

		return config;
	}

	public boolean connectSpecificAP(ScanResult scan) {
		List<WifiConfiguration> list = mWifiManager.getConfiguredNetworks();
		boolean networkInSupplicant = false;
		boolean connectResult = false;
		// 重新连接指定AP
		mWifiManager.disconnect();
		for (WifiConfiguration w : list) {
			// 将指定AP 名字转化
			// String str = convertToQuotedString(info.ssid);
			if (w.BSSID != null && w.BSSID.equals(scan.BSSID)) {
				connectResult = mWifiManager.enableNetwork(w.networkId, true);
				// mWifiManager.saveConfiguration();
				networkInSupplicant = true;
				break;
			}
		}
		if (!networkInSupplicant) {
			WifiConfiguration config = CreateWifiConfig(scan, "");
			connectResult = addNetwork(config);
		}

		return connectResult;
	}
	/**
	 * 添加到网络
	 * @param wcg
	 */
	public boolean addNetwork(WifiConfiguration wcg) {
		if (wcg == null) {
			return false;
		}
		//receiverDhcp = new ReceiverDhcp(ctx, mWifiManager, this, wlanHandler);
		//ctx.registerReceiver(receiverDhcp, new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION));
		int wcgID = mWifiManager.addNetwork(wcg);
		boolean b = mWifiManager.enableNetwork(wcgID, true);
		mWifiManager.saveConfiguration();
		System.out.println(b);
		return b;
	}
	public static boolean checkNetworkConnection(Context context)   
	{   
		final ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);    

		final NetworkInfo wifi =connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		final NetworkInfo mobile =connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		String extraInfo = wifi.getExtraInfo();
		Log.i("wifi", extraInfo+"/n"+wifi.describeContents()+"/n"+wifi.getSubtypeName()+"/n"+wifi.getReason()+"/n"+wifi.getType()+"/n"+wifi.getTypeName());
		if(wifi.isAvailable()||mobile.isAvailable())    
			return true;    
		else   
			return false;    
	}  
	/** 
		262.     * 判断wifi是否连接成功,不是network 
		263.     *  
		264.     * @param context 
		265.     * @return 
		266.     */  
	public int isWifiContected(Context context) {  
		ConnectivityManager connectivityManager = (ConnectivityManager) context  
				.getSystemService(Context.CONNECTIVITY_SERVICE);  
		NetworkInfo wifiNetworkInfo = connectivityManager  
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);  

		Log.i("TAG", "isConnectedOrConnecting = " + wifiNetworkInfo.isConnectedOrConnecting());  
		Log.i("TA", "wifiNetworkInfo.getDetailedState() = " + wifiNetworkInfo.getDetailedState());  
		if (wifiNetworkInfo.getDetailedState() == DetailedState.OBTAINING_IPADDR  
				|| wifiNetworkInfo.getDetailedState() == DetailedState.CONNECTING) {  
			return WIFI_CONNECTING;  
		} else if (wifiNetworkInfo.getDetailedState() == DetailedState.CONNECTED) {  
			return WIFI_CONNECTED;  
		} else {  
			Log.i("TAG", "getDetailedState() == " + wifiNetworkInfo.getDetailedState());  
			return WIFI_CONNECT_FAILED;  
		}  
	}  
	//指定ssid和密码连接wifi
	private WifiConfiguration createWifiInfo(String SSID, String Password,
			WifiConnect.WifiCipherType Type) {
		WifiConfiguration config = new WifiConfiguration();
		config.allowedAuthAlgorithms.clear();
		config.allowedGroupCiphers.clear();
		config.allowedKeyManagement.clear();
		config.allowedPairwiseCiphers.clear();
		config.allowedProtocols.clear();
		config.SSID = "\"" + SSID + "\"";
		if (Type == WifiConnect.WifiCipherType.WIFICIPHER_NOPASS) {
			config.wepKeys[0] = "";
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		if (Type == WifiConnect.WifiCipherType.WIFICIPHER_WEP) {
			config.preSharedKey = "\"" + Password + "\"";
			config.hiddenSSID = true;
			config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		if (Type == WifiConnect.WifiCipherType.WIFICIPHER_WPA) {
			// 修改之后配置
			config.preSharedKey = "\"" + Password + "\"";
			config.hiddenSSID = true;
			config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
			// config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
		} else {
			return null;
		}
		return config;
	}
	 
	/*设置要连接的热点的参数*/
	@SuppressWarnings("unused")
	private WifiConfiguration setWifiParams(String ssid,String password){
	WifiConfiguration apConfig=new WifiConfiguration();
	apConfig.SSID="\""+ssid+"\"";
	apConfig.preSharedKey="\"password\"";
	apConfig.hiddenSSID = true;
	apConfig.status = WifiConfiguration.Status.ENABLED;
	apConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
	apConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
//	apConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
	apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
	apConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
	//apConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
	apConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
//	apConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
	int netID = mWifiManager.addNetwork(apConfig);
	boolean bRet = mWifiManager.enableNetwork(netID, true);
	return apConfig;
	}

	
	 /**
     * 创建一个WifiConfig
     *
     * @param SSID
     * @param Password
     * @param Type
     * @return  config
     */
    public WifiConfiguration CreateWifiInfo(String SSID, String Password, int Type) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";
        WifiConfiguration tempConfig = this.isExsits1(SSID);
        if (tempConfig != null) {
            mWifiManager.removeNetwork(tempConfig.networkId);
        }
        if (Type == 1) //WIFICIPHER_NOPASS
            config.wepKeys[0] = "\"" + "\"";
        {
//
// config.wepKeys[0] = "";
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if (Type == 2) //WIFICIPHER_WEP
        {
            config.hiddenSSID = true;
            config.wepKeys[0] = "\"" + Password + "\"";
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if (Type == 3) //WIFICIPHER_WPA
        {
            config.preSharedKey = "\"" + Password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            //Config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }
        return config;
    }
//没有密码的连接
    private WifiConfiguration isExsits1(String SSID) {
        List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
                return existingConfig;
            }
        }
        return null;
    }
}

