package base;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Administrator on 2016/12/7.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        ShareSDK.initSDK(this,"19bc82ba113d8");
    }
}
