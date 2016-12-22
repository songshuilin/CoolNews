package view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.edu.coolnews.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import io.vov.vitamio.utils.Log;
import model.UserBean;
import util.ToastUtil;

/**
 * 注册
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button login, regist;
    private AutoCompleteTextView username;
    private EditText password;
    private ImageView back_img;
    private UserBean userBean;
    private Button forget;
    private AlertDialog.Builder builder;
    private EditText mEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userBean = BmobUser.getCurrentUser(UserBean.class);
        if (userBean != null) {
            Intent intent = new Intent(this, ShowNewsActivity.class);
            startActivity(intent);
            finish();
        }
        getSupportActionBar().hide();
        initView();
    }

    private void initView() {
        mEt = new EditText(this);
        builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入邮箱...");
        builder.setView(mEt);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String email = mEt.getText().toString();
                BmobUser.resetPasswordByEmail(email, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            ToastUtil.MyToast(LoginActivity.this, "重置密码请求成功，请到" + email + "邮箱进行密码重置操作");
                        } else {
                            ToastUtil.MyToast(LoginActivity.this, "失败:" + e.getMessage());
                        }
                    }
                });
            }
        });

        builder.setPositiveButton("取消", null);

        back_img = (ImageView) findViewById(R.id.back_img);
        username = (AutoCompleteTextView) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        regist = (Button) findViewById(R.id.regist);
        forget = (Button) findViewById(R.id.forget);
        forget.setOnClickListener(this);
        back_img.setOnClickListener(this);
        login.setOnClickListener(this);
        regist.setOnClickListener(this);
    }


    /**
     * 为view注册点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forget:
                builder.show();
                break;

            case R.id.back_img:
                finish();
                break;
            case R.id.login:
                String name = username.getText().toString();
                String userpassword = password.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(userpassword)) {
                    ToastUtil.MyToast(LoginActivity.this, "你输入有为空，请检查");
                    return;
                }

                if (name.matches("^[A-Za-z0-9][\\\\w\\\\-\\\\.]{3,12}@([\\\\w\\\\-]+\\\\.)+[\\\\w]{2,3}$")) {
                    BmobUser.loginByAccount(name, userpassword, new LogInListener<UserBean>() {
                        @Override
                        public void done(UserBean user, BmobException e) {
                            if (user != null) {
                                ToastUtil.MyToast(LoginActivity.this, "登录成功:");
                            }
                        }
                    });
                    return;
                }
                UserBean user = new UserBean();
                user.setUsername(name);
                user.setPassword(userpassword);
                user.login(new SaveListener<UserBean>() {
                    @Override
                    public void done(UserBean bean, BmobException e) {
                        if (e == null) {
                            if (bean.getEmailVerified()) {
                                ToastUtil.MyToast(LoginActivity.this, "登录成功:");
                                Intent intent = new Intent(LoginActivity.this, ShowNewsActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                ToastUtil.MyToast(LoginActivity.this, "请去验证邮箱！");
                            }
                            //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                            //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                        } else {
                            ToastUtil.MyToast(LoginActivity.this, e.getMessage());
                        }
                    }
                });
                break;
            case R.id.regist:
                Intent intent = new Intent(this, RegistActivity.class);
                startActivity(intent);
                break;
        }
    }
}
