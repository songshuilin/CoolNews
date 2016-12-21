package view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import cn.bmob.v3.listener.SaveListener;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userBean=BmobUser.getCurrentUser(UserBean.class);
        if (userBean!=null){
            Intent intent=new Intent(this,ShowNewsActivity.class);
            startActivity(intent);
        }
        getSupportActionBar().hide();
        initView();
    }

    private void initView() {
        back_img = (ImageView) findViewById(R.id.back_img);
        username = (AutoCompleteTextView) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        regist = (Button) findViewById(R.id.regist);
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

                UserBean user = new UserBean();
                user.setUsername(name);
                user.setPassword(userpassword);
                user.login(new SaveListener<UserBean>() {
                    @Override
                    public void done(UserBean bean, BmobException e) {
                        if (e == null) {
                            if (bean.getEmailVerified()) {
                                ToastUtil.MyToast(LoginActivity.this, "登录成功:");
                                Intent intent=new Intent(LoginActivity.this,ShowNewsActivity.class);
                                startActivity(intent);
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
