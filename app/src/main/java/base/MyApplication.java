package base;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import cn.bmob.v3.Bmob;
import cn.sharesdk.framework.ShareSDK;
import io.vov.vitamio.Vitamio;

/**
 * Created by Administrator on 2016/12/7.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        //必须写这个，初始化加载库文件
        Vitamio.isInitialized(this);
        ShareSDK.initSDK(this, "19bc82ba113d8");
        //第一：默认初始化
        Bmob.initialize(this, "1273b4298603e20842ba91504c936403");


        //Beta.autoCheckUpgrade = false;//设置不自动检查
        Bugly.init(this, "252ef731ff", false);


        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);    }
    }
}
