package chanlytech.ichengdu.web;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.arialyy.frame.util.NetUtils;

import butterknife.InjectView;
import chanlytech.ichengdu.R;
import chanlytech.ichengdu.base.BaseActivity;
import chanlytech.ichengdu.web.js.JsInterface;

public class TestActivity extends BaseActivity {
    @InjectView(R.id.title)
    TextView mTextView;
    @InjectView(R.id.webview)
    WebView mWebView;
    @InjectView(R.id.back)
    ImageView mImageView_back;
    private static final String APP_CACAHE_DIRNAME = "/webcache";


    @Override
    public int setContentView() {
        return R.layout.activity_test;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();

    }

    private void initView() {
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
        //      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
        Log.i("", "cacheDirPath=" + cacheDirPath);
        //设置数据库缓存路径
        mWebView.getSettings().setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        mWebView.getSettings().setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        mWebView.getSettings().setAppCacheEnabled(true);
//        mWebView.getSettings().setUserAgentString("laodongjianguan/" + AppManager.getUser().getUserId() + "/" + mWebView.getSettings().getUserAgentString());
//       T.showLong(this,mWebView.getSettings().getUserAgentString());
        mWebView.loadUrl(getIntent().getStringExtra("url"));
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                } else {
                    view.loadUrl(url);
                }
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
        mWebView.setWebChromeClient(new WebChromeClient() {
                                        @Override
                                        public void onReceivedTitle(WebView view, String title) {
                                            super.onReceivedTitle(view, title);
                                            mTextView.setText(title);
                                        }
                                    }

        );
        mWebView.addJavascriptInterface(new JsInterface(this), "AppJsInterface");
    }
}
