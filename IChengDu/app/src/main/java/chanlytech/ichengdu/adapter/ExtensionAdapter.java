package chanlytech.ichengdu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import chanlytech.ichengdu.R;
import chanlytech.ichengdu.entiy.ServerEntity;
import chanlytech.ichengdu.util.Imageload;
import chanlytech.ichengdu.util.PublicTool;
import chanlytech.ichengdu.weight.HalfRoundAngleImageView;

/**
 * Created by Lyy on 2015/11/18.
 */
public class ExtensionAdapter extends BaseAdapter {
    private Context mContext;
    private List<ServerEntity>mServerEntities;
    public ExtensionAdapter(Context context,List<ServerEntity>serverEntities){
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
        ViewHolder mViewHolder=null;
        int weight=PublicTool.getDeviceWidth();
        ServerEntity serverEntity = mServerEntities.get(position);
        if(convertView==null){
            mViewHolder=new ViewHolder();
            convertView=View.inflate(mContext, R.layout.extension_item,null);
            mViewHolder.imageView= (HalfRoundAngleImageView) convertView.findViewById(R.id.half_img);
            mViewHolder.textView= (TextView) convertView.findViewById(R.id.tv_name);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(weight/2-50, weight/2-50);
//            params.setMargins(5,5,5,0);
            mViewHolder.imageView.setLayoutParams(params);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder= (ViewHolder) convertView.getTag();
        }

        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(weight/2-50, weight/2-50);
//        params.setMargins(5,5,5,0);
        mViewHolder.imageView.setLayoutParams(params);
        mViewHolder.textView.setText(serverEntity.getName());
        Imageload.LoadImag(mContext, serverEntity.getImgurl(), mViewHolder.imageView);
        return convertView;
    }


    class ViewHolder{
        TextView textView;
        HalfRoundAngleImageView imageView;
    }
}
