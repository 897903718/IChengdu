package chanlytech.ichengdu.web;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.arialyy.frame.util.NetUtils;

import butterknife.InjectView;
import chanlytech.ichengdu.R;
import chanlytech.ichengdu.base.BaseActivity;
import chanlytech.ichengdu.util.Constants;
import chanlytech.ichengdu.web.js.JsInterface;

public class ServerWebActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.webview)
    WebView mWebView;
    @InjectView(R.id.back)
    ImageView mImageView_back;
    private static final String APP_CACAHE_DIRNAME = "/webcache";
//dd3662d2155661753bcf1aa17bbd8bde  /data/data/chanlytech.ichengdu/files/webcache

    @Override
    public int setContentView() {
        return R.layout.activity_server_web;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
        initLinster();

    }

    @SuppressLint("JavascriptInterface")
    private void initView(){
        title.setText(getIntent().getStringExtra("title"));
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (NetUtils.isConnected(this)) {
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);  //设置 缓存模式
        } else {
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        // 开启 DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能
        mWebView.getSettings().setDatabaseEnabled(true);
        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
//        String cacheDirPath = Constants.getSDPath() + APP_CACAHE_DIRNAME;
        //      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
        Log.i("", "cacheDirPath=" + cacheDirPath);
        //设置数据库缓存路径
        mWebView.getSettings().setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        mWebView.getSettings().setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        mWebView.getSettings().setAppCacheEnabled(true);

        mWebView.loadUrl(getIntent().getStringExtra("url"));
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if(url.startsWith("tel:")){
//                    Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse(url));
//                    startActivity(intent);
//                }else {
//                    view.loadUrl(url);
//                }
                view.loadUrl(url);
                return true;
//                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                openAnimation();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                if (animationDrawable != null && animationDrawable.isRunning()) {
//                    animationDrawable.stop();
//                    iv_load.setVisibility(View.GONE);
//                }
            }
        });
        mWebView.addJavascriptInterface(new JsInterface(this), "AppJsInterface");
    }

    private void initLinster(){
        mImageView_back.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.back:
                if(mWebView.canGoBack()){
                    mWebView.goBack();
                }else {
                    finish();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mWebView.canGoBack()){
            mWebView.goBack();
        }else {
            finish();
        }
    }
}
