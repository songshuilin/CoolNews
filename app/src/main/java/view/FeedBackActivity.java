package view;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edu.coolnews.R;

import java.util.List;

import adapter.NewsAdapter;
import adapter.SearchHistoryNewsAdapter;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import db.SearchNewsHistoryHelp;
import db.dao.SearchNewsHistoryDao;
import https.GetNewsForSearch;
import model.FeedBackBean;
import model.NewsBean;
import model.SearchHistoryBean;
import util.DividerItemDecoration;
import util.ToastUtil;

/**
 * 反馈
 */
public class FeedBackActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView back_img;
    private TextView send;
    private EditText mEtContent, mContact_ways;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        getSupportActionBar().hide();
        initView();
    }

    private void initView() {
        back_img = (ImageView) findViewById(R.id.back_img);
        send = (TextView) findViewById(R.id.send);
        mContact_ways = (EditText) findViewById(R.id.contact_ways);
        mEtContent = (EditText) findViewById(R.id.content);
        send.setOnClickListener(this);
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
            case R.id.send:
                String content=mEtContent.getText().toString();
                String contact=mContact_ways.getText().toString();
                if (TextUtils.isEmpty(contact)||TextUtils.isEmpty(content)){
                    ToastUtil.MyToast(FeedBackActivity.this,"输入有空，请检查。");
                    return;
                }
                FeedBackBean feedBack = new FeedBackBean();
                feedBack.setContactWays(contact);
                feedBack.setFeedBack_content(content);
                //注意：不能调用gameScore.setObjectId("")方法
                feedBack.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if (e == null) {
                            ToastUtil.MyToast(FeedBackActivity.this, "反馈成功");
                            finish();
                        } else {
                            ToastUtil.MyToast(FeedBackActivity.this, "反馈失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });

                break;
        }
    }


}
