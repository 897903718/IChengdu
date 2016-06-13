package chanlytech.ichengdu.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import chanlytech.ichengdu.R;
import chanlytech.ichengdu.adapter.OteherWifiAdapter;
import chanlytech.ichengdu.wificonfig.WifiAdmin;

public class TestActivity extends Activity {
private ListView mListView;
    private WifiAdmin wifiAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        mListView= (ListView) findViewById(R.id.list);
        wifiAdmin=new WifiAdmin(this);
        wifiAdmin.startScan();
        List<ScanResult> wifiList = wifiAdmin.getWifiList();
        mListView.setAdapter(new OteherWifiAdapter(this, wifiList));

    }


}
