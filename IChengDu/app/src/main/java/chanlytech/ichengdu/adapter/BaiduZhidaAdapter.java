package chanlytech.ichengdu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import chanlytech.ichengdu.R;
import chanlytech.ichengdu.entiy.ServerEntity;
import chanlytech.ichengdu.util.Imageload;
import chanlytech.ichengdu.util.PublicTool;

/**
 * Created by Lyy on 2015/11/11.
 */
public class BaiduZhidaAdapter extends BaseAdapter {
    private Context mContext;
    private List<ServerEntity>mServerEntities;
    public BaiduZhidaAdapter(Context context,List<ServerEntity>serverEntities){
        this.mContext=context;
        this.mServerEntities=serverEntities;
    }
    @Override
    public int getCount() {
        return mServerEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return mServerEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ServerEntity serverEntity = mServerEntities.get(position);
        ViewHolder mViewHolder=null;
        int weight= PublicTool.getDeviceWidth();
        if(convertView==null){
            mViewHolder=new ViewHolder();
            convertView=View.inflate(mContext, R.layout.baiduzhida_item,null);
            mViewHolder.mImageView= (ImageView) convertView.findViewById(R.id.iv_zhidaimag);
            mViewHolder.textView= (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder= (ViewHolder) convertView.getTag();
        }
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, weight/2);
        mViewHolder.textView.setText(serverEntity.getName());
        mViewHolder.mImageView.setLayoutParams(params);
        Imageload.LoadImag(mContext,serverEntity.getImgurl(),mViewHolder.mImageView);
        return convertView;
    }

    class ViewHolder{
        ImageView mImageView;
        TextView textView;
    }
}
