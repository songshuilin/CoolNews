package view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.edu.coolnews.R;

import java.util.ArrayList;
import java.util.List;

import adapter.FragmentAdapter;
import constants.Constant;
import fragment.PictureFragment;
import fragment.NewsFragment;


public class ShowNewsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,View.OnClickListener

{
    private List<Fragment> fragments=new ArrayList<>();
    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private DrawerLayout mDl;
    private NavigationView mNv;
    private ViewPager newsViewPage;
    private ViewPager pictureViewpage;
    private ViewPager vedioViewpage;
    private ViewPager musicViewpage;
    private TabLayout newsTabLyout;
    private TabLayout pictureTabLyout;
    private TabLayout vedioTabLyout;
    private TabLayout musicTabLyout;
    private List<String> mTabText=new ArrayList<>();
    private FragmentAdapter adapter;
    private LinearLayout newsLinner;




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
        getNewsList();

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
        newsTabLyout= (TabLayout) findViewById(R.id.news_tab_title);
        pictureTabLyout= (TabLayout) findViewById(R.id.picture_tab_title);
        vedioTabLyout= (TabLayout) findViewById(R.id.vedio_tab_title);
        musicTabLyout= (TabLayout) findViewById(R.id.music_tab_title);
        newsViewPage= (ViewPager) findViewById(R.id.news_viewpager);
        pictureViewpage= (ViewPager) findViewById(R.id.picture_viewpager);
        vedioViewpage= (ViewPager) findViewById(R.id.vedio_viewpager);
        musicViewpage= (ViewPager) findViewById(R.id.music_viewpager);
        newsLinner= (LinearLayout) findViewById(R.id.content_show_news);
        mNv.setNavigationItemSelectedListener(this);
        mFab.setOnClickListener(this);
        setVisible(0);

        /**
         * 设置tablayout中的tab选中事件
         */
        newsTabLyout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(newsViewPage) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                Toast.makeText(ShowNewsActivity.this, tab.getText()+"==", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 指定显示哪个view
     * @param count
     */
    public void setVisible(int count) {
        for (int i = 0; i < newsLinner.getChildCount(); i++) {
            newsLinner.getChildAt(i).setVisibility(View.GONE);
        }
        newsLinner.getChildAt(count).setVisibility(View.VISIBLE);
        Log.i("TAGa", "initView: "+newsLinner.getChildCount());
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

    /**
     * 侧滑栏的点击事件
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
       switch (id){
           case R.id.menu_news:
                setVisible(0);
                getNewsList();
               mToolbar.setTitle("新闻简读");
               Toast.makeText(this,"menu_news",Toast.LENGTH_SHORT).show();
               break;
           case R.id.menu_pictures:
               setVisible(1);
               getPictureList();
               mToolbar.setTitle("图片浏览");
               Toast.makeText(this,"menu_pictures",Toast.LENGTH_SHORT).show();
               break;
           case R.id.menu_videos:
               setVisible(0);
               Toast.makeText(this,"menu_videos",Toast.LENGTH_SHORT).show();
               break;
           case R.id.menu_musics:
               setVisible(0);
               Toast.makeText(this,"menu_musics",Toast.LENGTH_SHORT).show();
               break;
           case R.id.menu_search:
               setVisible(0);
               Toast.makeText(this,"menu_search",Toast.LENGTH_SHORT).show();
               break;
           case R.id.menu_mode:
               setVisible(0);
               Toast.makeText(this,"menu_mode",Toast.LENGTH_SHORT).show();
               break;
           case R.id.menu_feedback:
               setVisible(0);
               Toast.makeText(this,"menu_feedback",Toast.LENGTH_SHORT).show();
               break;
           case R.id.menu_set:
               setVisible(0);
               Toast.makeText(this,"menu_set",Toast.LENGTH_SHORT).show();
               break;

       }
        mDl.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     *获取新闻数据列表
     */
    public void getPictureList() {
        fragments.clear();
        /**
         * 动态初始化fragmnet,并且从activity传值给对应的fragment
         */
        for (int j = 0; j <Constant.TAB_PICTURE.length; j++) {
            PictureFragment fragment = new PictureFragment();
            fragments.add(fragment);
            Bundle bundle=new Bundle();
            bundle.putString("type",Constant.TAB_PICTURE[j]);
            fragment.setArguments(bundle);
        }
        adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        pictureViewpage.setAdapter(adapter);
        pictureViewpage.setCurrentItem(0);//默认显示第一页
        pictureTabLyout.setupWithViewPager(pictureViewpage);//用的是tablayout，关联viewpage
        //为tablayout添加tab
        for (int i = 0; i <Constant.TAB_PICTURE.length; i++) {
            pictureTabLyout.getTabAt(i).setText(Constant.TAB_PICTURE[i]);
        }
    }

    /**
     *获取新闻数据列表
     */
    public void getNewsList() {
        fragments.clear();
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
        adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        newsViewPage.setAdapter(adapter);
        newsViewPage.setCurrentItem(0);//默认显示第一页
        newsTabLyout.setupWithViewPager(newsViewPage);//用的是tablayout，关联viewpage
        //为tablayout添加tab
        for (int i = 0; i <mTabText.size(); i++) {
            newsTabLyout.getTabAt(i).setText(mTabText.get(i));
        }

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
