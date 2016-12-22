package view;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
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

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import db.CollectNewsHelp;
import db.dao.CollectNewsDao;
import model.CollectNewsBean;
import model.UserBean;

public class ReadNewsActivity extends AppCompatActivity implements View.OnClickListener {
    private WebView webView;
    private ProgressDialog progressDialog;
    private String url;//新闻的连接
    private String title;//新闻标题
    private String imgUrl;//图片连接
    private CollapsingToolbarLayout mCollLayout;
    private Toolbar mToolbar;
    private SimpleDraweeView mImg;
    private ImageView backImg, rightImg,collectImg;
    private SQLiteDatabase db;
    private CollectNewsHelp help;
    private UserBean bean;
    /**
     * 初始化控件
     */
    private void initViews() {
        webView = (WebView) findViewById(R.id.webview);
        progressDialog = new ProgressDialog(ReadNewsActivity.this);
        mCollLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mImg = (SimpleDraweeView) findViewById(R.id.img_readnews);
        backImg = (ImageView) findViewById(R.id.back_img);
        rightImg = (ImageView) findViewById(R.id.right_img);
        backImg.setOnClickListener(this);
        rightImg.setOnClickListener(this);
        collectImg= (ImageView) findViewById(R.id.collect_img);
        collectImg.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_news);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        imgUrl = getIntent().getStringExtra("imgurl");
        initViews();
        showWebview();
         help=new CollectNewsHelp(this,"collect.db",null,1);
         db=help.getReadableDatabase();
        mCollLayout.setTitle(Html.fromHtml(title));
        if (imgUrl!=null){
            Uri uri = Uri.parse(imgUrl);
            mImg.setImageURI(uri);
        }else {
            mImg.setImageURI("");
        }

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

            case R.id.right_img:
                showShare();
                break;
            case R.id.collect_img:
               // finish();
                CollectNewsDao.insertCollectNews(db,new CollectNewsBean(
                    BmobUser.getCurrentUser(UserBean.class).getUsername(),
                    url,imgUrl,title));
                Toast.makeText(ReadNewsActivity.this,"收藏成功...",Toast.LENGTH_SHORT).show();
            break;
        }
    }


    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(title);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl(imgUrl);
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("小宋程序员");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);

        // 启动分享GUI
        oks.show(this);
    }
}
