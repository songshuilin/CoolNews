package view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.edu.coolnews.R;
import com.facebook.drawee.view.SimpleDraweeView;

import cn.bmob.v3.BmobUser;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import db.CollectNewsHelp;
import db.dao.CollectNewsDao;
import model.CollectNewsBean;
import model.UserBean;


public class ReadContentForWIKIActivity extends AppCompatActivity implements View.OnClickListener {
    private WebView webView;
    private ProgressDialog progressDialog;
    private String url;//新闻的连接
    private CollapsingToolbarLayout mCollLayout;
    private ImageView backImg;
    private AlertDialog.Builder builder;

    /**
     * 初始化控件
     */
    private void initViews() {
        webView = (WebView) findViewById(R.id.webview);
        progressDialog = new ProgressDialog(ReadContentForWIKIActivity.this);
        mCollLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        backImg = (ImageView) findViewById(R.id.back_img);
        backImg.setOnClickListener(this);
        builder=new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("你还没有登录！, 请登录");
        builder.setNegativeButton("取消",null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(ReadContentForWIKIActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_news);
        url = getIntent().getStringExtra("url");
        initViews();
        showWebview();

    }

    /**
     * 点击进行跳转
     */

    public void showWebview() {
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

    /**
     * 按钮的点击事件
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
}
