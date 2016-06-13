package chanlytech.ichengdu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import chanlytech.ichengdu.R;
import chanlytech.ichengdu.adapter.BaiduZhidaAdapter;
import chanlytech.ichengdu.base.BaseActivity;
import chanlytech.ichengdu.entiy.ServerEntity;
import chanlytech.ichengdu.web.ServerWebActivity;
import chanlytech.ichengdu.weight.XListView;

public class BaiduZhidaActivity extends BaseActivity {
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.listview)
    XListView mXListView;
    private List<ServerEntity>mServerEntities=new ArrayList<>();
    private String title;
    @Override
    public int setContentView() {
        return R.layout.activity_baidu_zhida;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
        initLinster();
    }

    private void initView(){

        title=getIntent().getStringExtra("title");
        mTitle.setText(title);
        mXListView.setPullLoadEnable(false);
        mXListView.setPullRefreshEnable(false);
        if(title.equals("好吃嘴")){
            mServerEntities.add(new ServerEntity("1","海蓝宝海鲜","http://c.hiphotos.baidu.com/image/w%3D310/sign=a1fa2759d758ccbf1bbcb33b29d9bcd4/8694a4c27d1ed21b1cd0a00eab6eddc451da3f3b.jpg","http://hailanbaohaixianhuoguo.chanlytech.com:8088/hailanbaohaixianhuoguo/"));
            mServerEntities.add(new ServerEntity("2","久久丫","http://h.hiphotos.baidu.com/image/w%3D310/sign=a9e9c05298510fb378197196e932c893/377adab44aed2e73c4f008c78101a18b86d6fac4.jpg","http://jiujiuya.chanlytech.com:8088/jiujiuya/"));
            mServerEntities.add(new ServerEntity("3","嘉诚世纪","http://b.hiphotos.baidu.com/image/w%3D310/sign=8837c82b84025aafd33278cacbecab8d/9f2f070828381f30b0000342af014c086f06f0c4.jpg","http://jiachengshiji.chanlytech.com:8088/jiachengshiji/"));
            mXListView.setAdapter(new BaiduZhidaAdapter(this,mServerEntities));
        }else if (title.equals("好健康")){
            mServerEntities.add(new ServerEntity("1","劲键网球","http://f.hiphotos.baidu.com/image/w%3D310/sign=e45c84da273fb80e0cd167d606d32ffb/d009b3de9c82d158290ad15a860a19d8bd3e4255.jpg","http://jinjianwangqiu.chanlytech.com:8088/jinjianwangqiu"));
            mServerEntities.add(new ServerEntity("2","成都亚非牙科","http://h.hiphotos.baidu.com/image/w%3D310/sign=cab20f2045166d223877139576220945/342ac65c1038534364bb0fca9513b07ecb8088e1.jpg","http://yafeiyake.chanlytech.com:8088/yafeiyake"));
            mServerEntities.add(new ServerEntity("3","成都拜尔口腔","http://h.hiphotos.baidu.com/image/w%3D310/sign=407174e676cf3bc7e800cbede101babd/0e2442a7d933c895f63b1090d71373f083020097.jpg","http://baierkouqiang.chanlytech.com:8088/baierkouqiang/"));
            mXListView.setAdapter(new BaiduZhidaAdapter(this,mServerEntities));
        }else if (title.equals("好出游")){
            mServerEntities.add(new ServerEntity("1","川崎成都重庆","http://f.hiphotos.baidu.com/image/w%3D310/sign=baf40dcc793e6709be0043fe0bc69fb8/7a899e510fb30f245fd51de8ce95d143ac4b0389.jpg","http://chuanqichengdu.chanlytech.com:8088/chuanqichengdu/"));
            mServerEntities.add(new ServerEntity("2","成都中青旅","http://d.hiphotos.baidu.com/image/w%3D310/sign=8eb81513a186c91708035438f93c70c6/34fae6cd7b899e5114c1cbff44a7d933c9950d89.jpg","http://chengduzhongqinglv.chanlytech.com:8088/chengduzhongqinglv/"));
            mServerEntities.add(new ServerEntity("3","老车迷俱乐部","http://d.hiphotos.baidu.com/image/w%3D310/sign=8ecb1513a186c91708035438f93c70c6/34fae6cd7b899e5114b2cbff44a7d933c8950d18.jpg","http://laochemi.chanlytech.com:8088/laochemi"));
            mXListView.setAdapter(new BaiduZhidaAdapter(this,mServerEntities));
        }else if(title.equals("好生活")){
            mServerEntities.add(new ServerEntity("1","马立可汽车服务","http://f.hiphotos.baidu.com/image/w%3D310/sign=24d2c98eb519ebc4c0787098b224cf79/7af40ad162d9f2d30acfd204afec8a136227cc7b.jpg","http://malike.chanlytech.com:8088/malike"));
            mServerEntities.add(new ServerEntity("2","金柏居窗帘","http://f.hiphotos.baidu.com/image/w%3D310/sign=cc6e645dcf177f3e1034fa0c40ce3bb9/e7cd7b899e510fb31282817cdf33c895d0430c45.jpg","http://jinbaijuchuanglian.chanlytech.com:8088/jinbaijuchuanglian"));
            mServerEntities.add(new ServerEntity("3","望设计婚纱","http://f.hiphotos.baidu.com/image/w%3D310/sign=c119af5a860a19d8cb03820403fb82c9/d31b0ef41bd5ad6ed062920187cb39dbb7fd3c45.jpg","http://wangshejihunsha.chanlytech.com:8088/wangshejihunsha"));
            mXListView.setAdapter(new BaiduZhidaAdapter(this,mServerEntities));
        }



    }


    private void initLinster(){
        mXListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(BaiduZhidaActivity.this, ServerWebActivity.class);
                intent.putExtra("title",mServerEntities.get(position-1).getName());
                intent.putExtra("url",mServerEntities.get(position-1).getUrl());
                startActivity(intent);
            }
        });
    }


}
