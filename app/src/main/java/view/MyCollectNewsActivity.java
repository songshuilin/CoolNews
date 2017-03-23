package view;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.edu.coolnews.R;

import java.util.Collections;
import java.util.List;

import adapter.CollectNewsAdapter;
import adapter.NewsAdapter;
import cn.bmob.v3.BmobUser;
import db.CollectNewsHelp;
import db.dao.CollectNewsDao;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import model.CollectNewsBean;
import model.NewsBean;
import model.UserBean;
import util.CustomMediaController;
import util.DividerItemDecoration;
import util.ToastUtil;

/**
 * 我的收藏
 */
public class MyCollectNewsActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private CollectNewsHelp help;
    private SQLiteDatabase db;
    private List<CollectNewsBean> list;
    private CollectNewsAdapter adapter;
    private ImageView back_img;
    private ItemTouchHelper itemTouchHelper;
    private UserBean bean;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x123:
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MyCollectNewsActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.addItemDecoration(new DividerItemDecoration(MyCollectNewsActivity.this, DividerItemDecoration.VERTICAL_LIST));
                    adapter = new CollectNewsAdapter(list, MyCollectNewsActivity.this);
                    adapter.setListener(new CollectNewsAdapter.OnClickItemListener() {
                        @Override
                        public void OnClickItem(View view, CollectNewsBean bean) {
                            Intent intent = new Intent(MyCollectNewsActivity.this, ReadNewsActivity.class);
                            intent.putExtra("url", bean.getUrl());
                            intent.putExtra("title", bean.getTitle());
                            intent.putExtra("imgurl", bean.getImgUrl());
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_news);
        getSupportActionBar().hide();
        bean= BmobUser.getCurrentUser(UserBean.class);
        initView();
        help = new CollectNewsHelp(this, "collect.db", null, 1);
        db = help.getReadableDatabase();
        queryCollectNewsAll();

        //0则不执行拖动或者滑动
        ItemTouchHelper.Callback mCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
            /**
             * @param recyclerView
             * @param viewHolder 拖动的ViewHolder
             * @param target 目标位置的ViewHolder
             * @return
             */
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
                int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
                if (fromPosition < toPosition) {
                    //分别把中间所有的item的位置重新交换
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(list, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(list, i, i - 1);
                    }
                }
                adapter.notifyItemMoved(fromPosition, toPosition);
                //返回true表示执行拖动
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                CollectNewsDao.deleteCollectnews(db,list.get(position));
                ToastUtil.MyToast(MyCollectNewsActivity.this,"删除成功...");
                list.remove(position);
                adapter.notifyItemRemoved(position);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    //滑动时改变Item的透明度
                    final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
                    viewHolder.itemView.setAlpha(alpha);
                    viewHolder.itemView.setTranslationX(dX);
                }
            }
        };
        itemTouchHelper = new ItemTouchHelper(mCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    //初始化控件
    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        back_img = (ImageView) findViewById(R.id.back_img);
        back_img.setOnClickListener(this);
    }

    /**
     * 从数据库中查取所有的收藏
     */
    public void queryCollectNewsAll() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                if (bean!=null){
                    list = CollectNewsDao.queryCollectNewsAll(db);
                    handler.sendEmptyMessage(0x123);
                }

            }
        }.start();
    }

    /**
     * view的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();//销毁页面
        }
    }
}
