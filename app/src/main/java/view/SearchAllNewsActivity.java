package view;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edu.coolnews.R;

import java.util.List;
import adapter.NewsAdapter;
import adapter.SearchHistoryNewsAdapter;
import db.SearchNewsHistoryHelp;
import db.dao.SearchNewsHistoryDao;
import https.GetNewsForSearch;
import model.NewsBean;
import model.SearchHistoryBean;
import util.DividerItemDecoration;

/**
 *
 */
public class SearchAllNewsActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView mHistoryRecyclerView;
    private AutoCompleteTextView autoCompleteTextView;
    private ImageView mSearch;
    private List<NewsBean.NewslistBean> list;
    private NewsAdapter adapter;
    private SearchHistoryNewsAdapter searchAdapter;
    private SQLiteDatabase db;
    private SearchNewsHistoryHelp serarchHelp;
    private String key;
    private List<SearchHistoryBean> historyBeanList;
    private ImageView backImg;
    private LinearLayout mLl;
    private TextView delete_all;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x123:
                    LinearLayoutManager layoutManager = new LinearLayoutManager(SearchAllNewsActivity.this);
                    mLl.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mRecyclerView.setLayoutManager(layoutManager);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    adapter = new NewsAdapter(list, SearchAllNewsActivity.this);
                    Log.i("TAG", "handleMessage: " + list);
                    adapter.setListener(new NewsAdapter.OnClickItemListener() {
                        @Override
                        public void OnClickItem(View view, NewsBean.NewslistBean newslistBean) {
                            NewsBean.NewslistBean bean = newslistBean;
                            Intent intent = new Intent(SearchAllNewsActivity.this, ReadNewsActivity.class);
                            intent.putExtra("url", newslistBean.getUrl());
                            intent.putExtra("title", newslistBean.getTitle());
                            intent.putExtra("imgurl", newslistBean.getPicUrl());
                            startActivity(intent);
                        }
                    });
                    mRecyclerView.setAdapter(adapter);


                    break;

                case 0x1234:
                    LinearLayoutManager historyLayoutManager = new LinearLayoutManager(SearchAllNewsActivity.this);
                    mRecyclerView.setVisibility(View.GONE);//看新闻的recycler隐藏
                    mLl.setVisibility(View.VISIBLE);
                    mHistoryRecyclerView.setLayoutManager(historyLayoutManager);
                    mHistoryRecyclerView.addItemDecoration(new DividerItemDecoration(SearchAllNewsActivity.this
                            , DividerItemDecoration.VERTICAL_LIST));
                    searchAdapter = new SearchHistoryNewsAdapter(historyBeanList, SearchAllNewsActivity.this);
                    mHistoryRecyclerView.setAdapter(searchAdapter);
                    searchAdapter.setListener(new SearchHistoryNewsAdapter.OnClickTextViewListener() {
                        @Override
                        public void OnClickTextView(View view, SearchHistoryBean bean) {
                           // Toast.makeText(SearchAllNewsActivity.this, bean.toString(), Toast.LENGTH_SHORT).show();
                            autoCompleteTextView.setText(bean.getKey());
                            searchHistory(bean.getKey());
                            SearchNewsHistoryDao.insertSearchNews(db,bean);
                            for (SearchHistoryBean history : historyBeanList) {
                                if (bean.getKey().equals(history.getKey())){
                                    historyBeanList.remove(history);
                                    break;
                                }
                            }
                            historyBeanList.add(bean);
                            mLl.setVisibility(View.GONE);
                            delete_all.setVisibility(View.VISIBLE);
                        }
                    });
                    searchAdapter.setOnclickListener(new SearchHistoryNewsAdapter.OnClickImgviewListener() {
                        @Override
                        public void OnClickImgView(View view, SearchHistoryBean bean) {
                            //Toast.makeText(SearchAllNewsActivity.this, "===" + bean.toString(), Toast.LENGTH_SHORT).show();
                            SearchNewsHistoryDao.deleteSearchnews(db, bean);
                            historyBeanList.remove(bean);
                            searchAdapter.notifyDataSetChanged();
                            if (historyBeanList.size()==0){
                                delete_all.setVisibility(View.GONE);
                            }

                        }
                    });
                    break;

            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_news);
        serarchHelp = new SearchNewsHistoryHelp(this, "history.db", null, 1);
        db = serarchHelp.getReadableDatabase();
        initView();
        getSupportActionBar().hide();
        //  mRecyclerView.setVisibility(View.GONE);
        query();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.searech_text);
        mLl= (LinearLayout) findViewById(R.id.ll);
        backImg = (ImageView) findViewById(R.id.back_img);
        backImg.setOnClickListener(this);
        delete_all= (TextView) findViewById(R.id.delete_all);
        delete_all.setOnClickListener(this);
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mRecyclerView.setVisibility(View.GONE);
                mLl.setVisibility(View.VISIBLE);
                searchAdapter.notifyDataSetChanged();
                if (historyBeanList.size()==0){
                    delete_all.setVisibility(View.GONE);
                }else {
                    delete_all.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSearch = (ImageView) findViewById(R.id.search);
        mSearch.setOnClickListener(this);
        mHistoryRecyclerView = (RecyclerView) findViewById(R.id.schear_recyclerview);
    }

    /**
     * 从数据库获取所有的数据
     */
    public void query() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                historyBeanList = SearchNewsHistoryDao.querySearchNewsAll(db);
                handler.sendEmptyMessage(0x1234);
            }
        }.start();


    }


    /**
     * 为view注册点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.delete_all:
                historyBeanList.clear();
                searchAdapter.notifyDataSetChanged();
                delete_all.setVisibility(View.GONE);
                break;
            case R.id.back_img:

                finish();
                break;
            case R.id.search:
                key = autoCompleteTextView.getText().toString();
                if (TextUtils.isEmpty(key)) {
                    Toast.makeText(SearchAllNewsActivity.this, "搜索不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                SearchHistoryBean bean = new SearchHistoryBean(key,(int)System.currentTimeMillis());
                searchHistory(key);
                SearchNewsHistoryDao.insertSearchNews(db,bean);
                for (SearchHistoryBean history : historyBeanList) {
                    if (key.equals(history.getKey())){
                        historyBeanList.remove(history);
                        break;
                    }
              }
                historyBeanList.add(bean);
                break;

        }
    }

    public void searchHistory(final String keyName) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                list = GetNewsForSearch.getAllNews(keyName, 1);
                if (list==null){
                    return;
                }
                handler.sendEmptyMessage(0x123);
            }
        }.start();

    }

}
