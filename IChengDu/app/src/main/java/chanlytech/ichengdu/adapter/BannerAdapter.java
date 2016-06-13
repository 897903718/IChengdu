package chanlytech.ichengdu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import chanlytech.ichengdu.R;
import chanlytech.ichengdu.entiy.BannerEntity;
import chanlytech.ichengdu.util.Imageload;
import chanlytech.ichengdu.web.ServerWebActivity;

/**
 * Created by Lyy on 2015/11/11.
 */
public class BannerAdapter extends PagerAdapter {
    private static final String TAG = "MainActivity";
    private Context mContext;
    private List<BannerEntity> mBannerEntities;
    private ViewPager mViewPager;
    private final int FAKE_BANNER_SIZE = 100;

    public BannerAdapter(Context context, List<BannerEntity> bannerEntities, ViewPager viewPager) {
        this.mContext = context;
        this.mBannerEntities = bannerEntities;
        this.mViewPager = viewPager;
    }

    @Override
    public int getCount() {
        return FAKE_BANNER_SIZE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mBannerEntities.size() > 0) {
            position %= mBannerEntities.size();
            View view = View.inflate(mContext, R.layout.item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            Imageload.LoadImag(mContext, mBannerEntities.get(position).getImgurl(), imageView);
            // imageView.setImageResource(mImagesSrc[position]);
            final int pos = position;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent=new Intent(mContext, ServerWebActivity.class);
                    mIntent.putExtra("title",mBannerEntities.get(pos).getName());
                    mIntent.putExtra("url",mBannerEntities.get(pos).getUrl());
                    mContext.startActivity(mIntent);
//                    Toast.makeText(mContext, "click banner item :" + pos,
//                            Toast.LENGTH_SHORT).show();
                }
            });
            container.addView(view);
            return view;
        } else {
            ImageView imageView = new ImageView(mContext);
            return imageView;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void finishUpdate(ViewGroup container) {
        int position = mViewPager.getCurrentItem();
        Log.d(TAG, "finish update before, position=" + position);
        if (position == 0) {
            position = mBannerEntities.size();
            mViewPager.setCurrentItem(position, false);
        } else if (position == FAKE_BANNER_SIZE - 1) {
            position = mBannerEntities.size() - 1;
            mViewPager.setCurrentItem(position, false);
        }
        Log.d(TAG, "finish update after, position=" + position);
    }
}
