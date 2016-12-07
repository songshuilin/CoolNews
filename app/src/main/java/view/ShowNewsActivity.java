package view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edu.coolnews.R;

import java.util.ArrayList;
import java.util.List;

import adapter.NewsAdapter;
import adapter.NewsFragmentAdapter;
import constants.Constant;
import fragment.NewsFragment;
import https.GetNewsAPI;
import model.NewsBean;
import model.OnNetRequestListener;
import util.DividerItemDecoration;

public class ShowNewsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,View.OnClickListener{
    private List<Fragment> fragments;
    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private DrawerLayout mDl;
    private NavigationView mNv;
    private ViewPager mViewPage;
    private TabLayout mTabLyout;
    private List<String> mTabText=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);
        initView();//初始化各控件
        initData();
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDl, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDl.setDrawerListener(toggle);
        toggle.syncState();
        getNovelList();
    }

    private void initData() {
        for (int i = 0; i < Constant.TABS.length ; i++) {
            mTabText.add(Constant.TABS[i]);
        }
    }

    /**
     * 初始化各控件
     */
    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mDl=(DrawerLayout) findViewById(R.id.drawer_layout);
        mNv=(NavigationView) findViewById(R.id.nav_view);
        mTabLyout= (TabLayout) findViewById(R.id.song_tab_title);
        mViewPage= (ViewPager) findViewById(R.id.viewpager);
        mNv.setNavigationItemSelectedListener(this);
        mFab.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        if (mDl.isDrawerOpen(GravityCompat.START)) {
            mDl.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_news, menu);
        return true;
    }

    /**
     * 侧滑栏的点击事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
       switch (id){
           case R.id.nav_camera:
               break;
       }
        mDl.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getNovelList() {
        fragments = new ArrayList<>();
        /**
         * 动态初始化fragmnet,并且从activity传值给对应的fragment
         */
        for (int j = 0; j < mTabText.size(); j++) {
            NewsFragment fragment = new NewsFragment();
            fragments.add(fragment);
            Bundle bundle=new Bundle();
            bundle.putString("type",mTabText.get(j));
            fragment.setArguments(bundle);
        }
        NewsFragmentAdapter adapter = new NewsFragmentAdapter(getSupportFragmentManager(), fragments);
        mViewPage.setAdapter(adapter);
        mViewPage.setCurrentItem(0);//默认显示第一页
        mTabLyout.setupWithViewPager(mViewPage);//用的是tablayout，关联viewpage
        //为tablayout添加tab
        for (int i = 0; i <mTabText.size(); i++) {
            mTabLyout.getTabAt(i).setText(mTabText.get(i));
        }

        /**
         * 设置tablayout中的tab选中事件
         */
        mTabLyout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPage) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                Toast.makeText(ShowNewsActivity.this, tab.getText(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    /**
     * 按钮的点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab:
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            break;
        }

    }
}
