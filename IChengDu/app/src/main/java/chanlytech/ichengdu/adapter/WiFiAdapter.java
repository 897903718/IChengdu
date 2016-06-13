package chanlytech.ichengdu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import chanlytech.ichengdu.R;
import chanlytech.ichengdu.entiy.WifiEntity;

/**
 * Created by Lyy on 2015/12/2.
 */
public class WiFiAdapter extends BaseAdapter {
    private Context mContext;
    private List<WifiEntity>mWifiEntities;
    public WiFiAdapter(Context context,List<WifiEntity> wifiEntities){
        this.mContext=context;
        this.mWifiEntities=wifiEntities;
    }
    @Override
    public int getCount() {
        return mWifiEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return mWifiEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder=null;
        WifiEntity wifiEntity = mWifiEntities.get(position);
        if (convertView==null){
            mViewHolder=new ViewHolder();
            convertView=View.inflate(mContext, R.layout.wifi_item,null);
            mViewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            mViewHolder.tv_ssid= (TextView) convertView.findViewById(R.id.tv_ssid);
            mViewHolder.iv_level= (ImageView) convertView.findViewById(R.id.iv_level);
            mViewHolder.iv_state= (ImageView) convertView.findViewById(R.id.wifi_state);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder= (ViewHolder) convertView.getTag();
        }
            mViewHolder.tv_ssid.setText(wifiEntity.getSsid());
            mViewHolder.tv_name.setText(wifiEntity.getTypename());
            showWifiLevel(wifiEntity.getLevel(),mViewHolder.iv_level);
            mViewHolder.iv_state.setBackgroundResource(R.mipmap.pic_free);
        return convertView;
    }

    class ViewHolder{
        TextView tv_ssid,tv_name;
        ImageView iv_level,iv_state;
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
