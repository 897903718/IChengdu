package chanlytech.ichengdu.adapter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import chanlytech.ichengdu.R;

/**
 * Created by Lyy on 2015/12/2.
 */
public class OteherWifiAdapter extends BaseAdapter {
    private Context mContext;
    private List<ScanResult>mScanResults;
    private int imgId;
    public OteherWifiAdapter(Context context,List<ScanResult>scanResults){
        this.mContext=context;
        this.mScanResults=scanResults;
    }
    @Override
    public int getCount() {
        return mScanResults.size();
    }

    @Override
    public Object getItem(int position) {
        return mScanResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder=null;
        ScanResult scanResult = mScanResults.get(position);
        if(convertView==null){
            mViewHolder=new ViewHolder();
            convertView=View.inflate(mContext, R.layout.other_wifi_item,null);
            mViewHolder.tv_ssid= (TextView) convertView.findViewById(R.id.tv_ssid);
            mViewHolder.iv_level= (ImageView) convertView.findViewById(R.id.iv_level);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder= (ViewHolder) convertView.getTag();
        }
       int level=scanResult.level;
        int imgId = R.mipmap.pic_wifi_01;
        if (Math.abs(level) > 80) {
            imgId =R.mipmap.pic_wifi_01;
        } else if (Math.abs(level) > 70) {
            imgId = R.mipmap.pic_wifi_02;
        } else if (Math.abs(level) >60) {
            imgId = R.mipmap.pic_wifi_03;
        } else if (Math.abs(level) > 50) {
            imgId =R.mipmap.pic_wifi_04;
        } else if (Math.abs(level) > 40) {
            imgId = R.mipmap.pic_wifi_04;
        }
       mViewHolder.iv_level.setBackgroundResource(imgId);
        mViewHolder.tv_ssid.setText(scanResult.SSID);
        return convertView;
    }

    class ViewHolder{
        TextView tv_ssid;
        ImageView iv_level;
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
}
