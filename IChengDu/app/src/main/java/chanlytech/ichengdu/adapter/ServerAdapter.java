package chanlytech.ichengdu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import chanlytech.ichengdu.R;
import chanlytech.ichengdu.entiy.ServerEntity;
import chanlytech.ichengdu.util.Imageload;

/**
 * Created by Lyy on 2015/11/12.
 */
public class ServerAdapter extends BaseAdapter {
    private Context mContext;
    private List<ServerEntity>mServerEntities;
    public ServerAdapter(Context context,List<ServerEntity>serverEntities) {
        this.mContext = context;
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
        ServerEntity serverEntity = mServerEntities.get(position);
        if (convertView == null) {
            mViewHolder=new ViewHolder();
            convertView = View.inflate(mContext, R.layout.server_item, null);
            mViewHolder.mImageView= (ImageView) convertView.findViewById(R.id.iv_img);
            mViewHolder.mTextView= (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder= (ViewHolder) convertView.getTag();
        }
        mViewHolder.mTextView.setText(serverEntity.getName());
        Imageload.LoadImag(mContext,serverEntity.getImgurl(),mViewHolder.mImageView);
        return convertView;
    }


    class ViewHolder {
        ImageView mImageView;
        TextView mTextView;
    }
}
