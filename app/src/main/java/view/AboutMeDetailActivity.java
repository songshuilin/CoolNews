package view;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.edu.coolnews.R;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import util.ToastUtil;


/**
 * 注册
 */
public class AboutMeDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ProgressDialog progressDialog;
    private ImageView back_img;
    private WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutme_detail);
        getSupportActionBar().hide();
        initView();
        showWebview(getIntent().getStringExtra("url"));
    }

    private void initView() {
        progressDialog = new ProgressDialog(AboutMeDetailActivity.this);
        back_img= (ImageView) findViewById(R.id.back_img);
        back_img.setOnClickListener(this);
        webView= (WebView) findViewById(R.id.webview);
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

    /**
     * 返回
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /**
         * webview.canGoBack()判断webview能否后退
         */
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            //webView后退
            webView.goBack();
        }

        return super.onKeyDown(keyCode, event);
    }
}