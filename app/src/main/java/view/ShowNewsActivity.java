package view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edu.coolnews.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import adapter.FragmentAdapter;
import cn.bmob.v3.BmobUser;
import constants.Constant;
import custom.FloatingActionMenu;
import fragment.MusicFragment;
import fragment.PictureFragment;
import fragment.NewsFragment;
import fragment.VedioFragment;
import https.GetMusicAPI;
import https.GetNewsForSearch;
import https.GetVedioAPI;
import model.UserBean;
import model.VedioBean;
import util.ToastUtil;


public class ShowNewsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener

{
    private List<Fragment> fragments = new ArrayList<>();
    private Toolbar mToolbar;
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
    private List<String> mTabText = new ArrayList<>();
    private FragmentAdapter adapter;
    private LinearLayout newsLinner;
    private FloatingActionMenu floatingActionMenu;
    private SharedPreferences.Editor editor;
    private boolean isDay = true;
    private AppCompatDelegate ad;
    private View handView;
    private SimpleDraweeView userImg;
    private LinearLayout my_favorites;
    private LinearLayout download;
    private UserBean curUser;
    private TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);
        curUser= BmobUser.getCurrentUser(UserBean.class);
        initView();//初始化各控件
        initData();
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDl, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDl.setDrawerListener(toggle);
        toggle.syncState();
        getNewsList();
        editor = getSharedPreferences("day_night_mode", MODE_PRIVATE).edit();
    }

    private void initData() {
        username.setText(curUser.getUsername());
        userImg.setImageURI(Uri.parse(curUser.getImage().getFileUrl()));
        Log.i("TAGss", "initData: "+Uri.parse(curUser.getImage().getFileUrl()));
        for (int i = 0; i < Constant.TABS.length; i++) {
            mTabText.add(Constant.TABS[i]);
        }
    }

    /**
     * 初始化各控件
     */
    private void initView() {

        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.floating_actions_menu);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDl = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNv = (NavigationView) findViewById(R.id.nav_view);
        newsTabLyout = (TabLayout) findViewById(R.id.news_tab_title);
        pictureTabLyout = (TabLayout) findViewById(R.id.picture_tab_title);
        vedioTabLyout = (TabLayout) findViewById(R.id.vedio_tab_title);
        musicTabLyout = (TabLayout) findViewById(R.id.music_tab_title);
        newsViewPage = (ViewPager) findViewById(R.id.news_viewpager);
        pictureViewpage = (ViewPager) findViewById(R.id.picture_viewpager);
        vedioViewpage = (ViewPager) findViewById(R.id.vedio_viewpager);
        musicViewpage = (ViewPager) findViewById(R.id.music_viewpager);
        newsLinner = (LinearLayout) findViewById(R.id.content_show_news);
        handView = mNv.getHeaderView(0);
        username= (TextView) handView.findViewById(R.id.username);
        download = (LinearLayout) handView.findViewById(R.id.download);
        my_favorites = (LinearLayout) handView.findViewById(R.id.my_favorites);
        userImg = (SimpleDraweeView) handView.findViewById(R.id.usernameImg);
        my_favorites.setOnClickListener(this);
        download.setOnClickListener(this);
        userImg.setOnClickListener(this);
        mNv.setNavigationItemSelectedListener(this);
        addPopMenuView();//添加菜单
        setVisible(0);

        /**
         * 设置tablayout中的tab选中事件
         */
        newsTabLyout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(newsViewPage) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                // Toast.makeText(ShowNewsActivity.this, tab.getText() + "==", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 指定显示哪个view
     *
     * @param count
     */
    public void setVisible(int count) {
        for (int i = 0; i < newsLinner.getChildCount(); i++) {
            newsLinner.getChildAt(i).setVisibility(View.GONE);
        }
        newsLinner.getChildAt(count).setVisibility(View.VISIBLE);
        Log.i("TAGa", "initView: " + newsLinner.getChildCount());
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
     * 选项菜单点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    // GetNewsForSearch.getAllNews("美女",1);
                }
            }.start();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 侧滑栏的点击事件
     *
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_news:
                setVisible(0);
                getNewsList();
                mToolbar.setTitle("新闻简读");
                //  Toast.makeText(this, "menu_news", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_pictures:
                setVisible(1);
                getPictureList();
                mToolbar.setTitle("图片浏览");
                // Toast.makeText(this, "menu_pictures", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_videos:
                setVisible(2);
                getVedioList();
                mToolbar.setTitle("视频爽看");
                //Toast.makeText(this, "menu_videos", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_musics:
                setVisible(3);
                getMusicList();
                mToolbar.setTitle("音乐轻听");
                // Toast.makeText(this, "menu_musics", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_search:
                Intent intent = new Intent(this, SearchAllNewsActivity.class);
                startActivity(intent);
                //Toast.makeText(this, "menu_search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_mode:
                isDay = getSharedPreferences("day_night_mode", MODE_PRIVATE)
                        .getBoolean("mode", true);
                isDay = !isDay;
                ad = getDelegate();
                if (isDay) {
//                    item.setIcon(R.mipmap.menu_theme_day);
//                    item.setTitle("日间模式");
                    ad.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
//                    item.setIcon(R.mipmap.menu_theme_night);
//                    item.setTitle("夜间模式");
                    ad.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    recreate();
                }

                editor.putBoolean("mode", isDay);
                editor.commit();
                // Toast.makeText(this, "menu_mode", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_feedback:
                Intent intent1 = new Intent(this, FeedBackActivity.class);
                startActivity(intent1);
                // Toast.makeText(this, "menu_feedback", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_set:
                Intent intent2 = new Intent(this, SettingActivity.class);
                startActivity(intent2);
                // Toast.makeText(this, "menu_set", Toast.LENGTH_SHORT).show();
                break;

        }
        mDl.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * 获取新闻数据列表
     */
    public void getPictureList() {
        fragments.clear();
        /**
         * 动态初始化fragmnet,并且从activity传值给对应的fragment
         */
        for (int j = 0; j < Constant.TAB_PICTURE.length; j++) {
            PictureFragment fragment = new PictureFragment();
            fragments.add(fragment);
            Bundle bundle = new Bundle();
            bundle.putString("type", Constant.TAB_PICTURE[j]);
            fragment.setArguments(bundle);
        }
        adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        pictureViewpage.setAdapter(adapter);
        pictureViewpage.setCurrentItem(0);//默认显示第一页
        pictureTabLyout.setupWithViewPager(pictureViewpage);//用的是tablayout，关联viewpage
        //为tablayout添加tab
        for (int i = 0; i < Constant.TAB_PICTURE.length; i++) {
            pictureTabLyout.getTabAt(i).setText(Constant.TAB_PICTURE[i]);
        }
    }

    /**
     * 增加菜单并添加点击事件
     */
    public void addPopMenuView() {
        View newsView = LayoutInflater.from(this).inflate(R.layout.pop_news, null);
        View pictureView = LayoutInflater.from(this).inflate(R.layout.pop_picture, null);
        View vedioVview = LayoutInflater.from(this).inflate(R.layout.pop_vedio, null);
        View musciView = LayoutInflater.from(this).inflate(R.layout.pop_music, null);
        floatingActionMenu.addActionsView(newsView);
        floatingActionMenu.addActionsView(pictureView);
        floatingActionMenu.addActionsView(vedioVview);
        floatingActionMenu.addActionsView(musciView);
        newsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisible(0);
                getNewsList();
                mToolbar.setTitle("新闻简读");
                floatingActionMenu.collapse();
            }
        });
        pictureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisible(1);
                getPictureList();
                mToolbar.setTitle("图片浏览");
                floatingActionMenu.collapse();
            }
        });
        vedioVview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisible(2);
                getVedioList();
                mToolbar.setTitle("视频爽看");
                // Toast.makeText(ShowNewsActivity.this, "3", Toast.LENGTH_SHORT).show();
                floatingActionMenu.collapse();
            }
        });
        musciView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisible(3);
                getMusicList();
                mToolbar.setTitle("音乐轻听");
                // Toast.makeText(ShowNewsActivity.this, "4", Toast.LENGTH_SHORT).show();
                floatingActionMenu.collapse();
            }
        });

    }


    /**
     * 获取数据音乐列表
     */
    public void getMusicList() {
        fragments.clear();
        /**
         * 动态初始化fragmnet,并且从activity传值给对应的fragment
         */
        for (int j = 0; j < Constant.TAB_MUSIC.length; j++) {
            MusicFragment fragment = new MusicFragment();
            fragments.add(fragment);
            Bundle bundle = new Bundle();
            bundle.putString("type", Constant.TAB_MUSIC[j]);
            fragment.setArguments(bundle);
        }
        adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        musicViewpage.setAdapter(adapter);
        musicViewpage.setCurrentItem(0);//默认显示第一页
        musicTabLyout.setupWithViewPager(musicViewpage);//用的是tablayout，关联viewpage
        //为tablayout添加tab
        for (int i = 0; i < Constant.TAB_MUSIC.length; i++) {
            musicTabLyout.getTabAt(i).setText(Constant.TAB_MUSIC[i]);
        }
    }

    /**
     * 获取视频数据列表
     */
    public void getVedioList() {
        fragments.clear();
        /**
         * 动态初始化fragmnet,并且从activity传值给对应的fragment
         */
        for (int j = 0; j < Constant.TAB_VEDIO.length; j++) {
            VedioFragment fragment = new VedioFragment();
            fragments.add(fragment);
            Bundle bundle = new Bundle();
            bundle.putString("type", Constant.TAB_VEDIO[j]);
            fragment.setArguments(bundle);
        }
        adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        vedioViewpage.setAdapter(adapter);
        vedioViewpage.setCurrentItem(0);//默认显示第一页
        vedioTabLyout.setupWithViewPager(vedioViewpage);//用的是tablayout，关联viewpage
        //为tablayout添加tab
        for (int i = 0; i < Constant.TAB_VEDIO.length; i++) {
            vedioTabLyout.getTabAt(i).setText(Constant.TAB_VEDIO[i]);
        }
    }


    /**
     * 获取新闻数据列表
     */
    public void getNewsList() {
        fragments.clear();
        /**
         * 动态初始化fragmnet,并且从activity传值给对应的fragment
         */
        for (int j = 0; j < mTabText.size(); j++) {
            NewsFragment fragment = new NewsFragment();
            fragments.add(fragment);
            Bundle bundle = new Bundle();
            bundle.putString("type", mTabText.get(j));
            fragment.setArguments(bundle);
        }
        adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        newsViewPage.setAdapter(adapter);
        newsViewPage.setCurrentItem(0);//默认显示第一页
        newsTabLyout.setupWithViewPager(newsViewPage);//用的是tablayout，关联viewpage
        //为tablayout添加tab
        for (int i = 0; i < mTabText.size(); i++) {
            newsTabLyout.getTabAt(i).setText(mTabText.get(i));
        }

    }


    /**
     * 按钮的点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.download:
                ToastUtil.MyToast(this, "download");
                break;
            case R.id.usernameImg:
                ToastUtil.MyToast(this, "usernameImg");
                break;
            case R.id.my_favorites:
                //  ToastUtil.MyToast(this,"my_favorites");
                Intent intent = new Intent(this, MyCollectNewsActivity.class);
                startActivity(intent);
                break;
        }
    }


}
