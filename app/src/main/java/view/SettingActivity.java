package view;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.edu.coolnews.R;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.v3.BmobUser;
import event.FinishEvent;
import util.ToastUtil;

/**
 * 反馈
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView person_setting, about_me;
    private LinearLayout clear_cache, check_version;
    private ImageView back_img;
    private TextView chche_size, version;
    private TextView back_user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().hide();
        initView();
        getVersionCode();
    }

    private void initView() {
        chche_size = (TextView) findViewById(R.id.cache_size);
        version = (TextView) findViewById(R.id.version);
        person_setting = (TextView) findViewById(R.id.person_setting);
        about_me = (TextView) findViewById(R.id.about_me);
        clear_cache = (LinearLayout) findViewById(R.id.clear_cache);
        check_version = (LinearLayout) findViewById(R.id.check_version);
        back_img = (ImageView) findViewById(R.id.back_img);
        back_user = (TextView) findViewById(R.id.back_user);
        back_img.setOnClickListener(this);
        person_setting.setOnClickListener(this);
        about_me.setOnClickListener(this);
        clear_cache.setOnClickListener(this);
        check_version.setOnClickListener(this);
        back_user.setOnClickListener(this);
    }

    //获取本地的版本号
    private int getVersionCode() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            int versionCode = packageInfo.versionCode;
            version.setText("当前版本号 : "+versionCode);//设置当前版本号
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 为view注册点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_setting:
                Intent intent = new Intent(this, SettingUpadeUserActivity.class);
                startActivity(intent);
                break;
            case R.id.about_me:
                ToastUtil.MyToast(this, "about_me");
                Intent intent2 = new Intent(this, AboutActivity.class);
                startActivity(intent2);
                break;
            case R.id.check_version:
                ToastUtil.MyToast(this, "check_version");
                break;
            case R.id.clear_cache:
                ToastUtil.MyToast(this, "clear_cache");
                break;
            case R.id.back_user:
                BmobUser.logOut();   //清除缓存用户对象
                Intent intent1 = new Intent(this, LoginActivity.class);
                startActivity(intent1);
                EventBus.getDefault().post(new FinishEvent());
                finish();
                break;
            case R.id.back_img:
                finish();
                break;

        }
    }


}
