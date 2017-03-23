package view;



import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import com.example.edu.coolnews.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import constants.Constant;


/**
 * 注册
 */
public class SearchForWIKIActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView search;
    private EditText input_mEt;
    private ImageView back_img;
    private WebView webView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki_search_aboutme);
        getSupportActionBar().hide();
        initView();
    }

    private void initView() {
        progressDialog = new ProgressDialog(SearchForWIKIActivity.this);
        search= (ImageView) findViewById(R.id.search);
        input_mEt= (EditText) findViewById(R.id.searech_text);
        back_img= (ImageView) findViewById(R.id.back_img);
        webView= (WebView) findViewById(R.id.webview);
        search.setOnClickListener(this);
        back_img.setOnClickListener(this);
    }


    /**
     * 为view注册点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.search:
                String key = input_mEt.getText().toString();
                try {
                    showWebview(Constant.SEARCH_WIKI_URL+ URLEncoder.encode(key, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 点击进行跳转
     */

    public void showWebview(String url) {
        //获取websetings 设置
        WebSettings settings = webView.getSettings();
        settings.setSupportZoom(true);
        //设置浏览器支持javaScript
        settings.setJavaScriptEnabled(true);
        //设置打开自带缩放按钮
        settings.setBuiltInZoomControls(true);
        //设置 缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        // 进行跳转用户输入的url地址
        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            //速度正在改变
            public void onProgressChanged(WebView view, int newProgress) {
                progressDialog.setMessage("加载" + newProgress);
                Log.d("1507", "5");
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            // 显示读渠道的内容
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Log.d("1507", "3");
                return true;
            }

            /**
             * 页面开始的时候 回调此方法
             * @param view
             * @param url
             * @param favicon
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (progressDialog == null) {
                    progressDialog.setMessage("加载中。。。。。。。。。。。");
                }
                Log.d("1507", "1");
                progressDialog.show();
            }

            /**
             * 页面结束的时候 回调此方法
             * @param view
             * @param url
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();
//                Log.d("1507", "1");
            }
        });
    }
}