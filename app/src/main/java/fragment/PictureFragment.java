package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.edu.coolnews.R;

import java.util.ArrayList;
import java.util.List;

import adapter.PictureAdapter;
import base.LazyLoadFragment;
import constants.Constant;
import https.GetPictureAPI;
import model.PictureBean;
import view.ReadNewsActivity;

public class PictureFragment extends LazyLoadFragment implements OnRefreshListener, OnLoadMoreListener {
    List<PictureBean> list = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private SwipeToLoadLayout swipeToLoadLayout;
    private String type;
    private int next = 1;
    private PictureAdapter adapter;
    private String baseurl;

    private boolean isPrepared;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x123:
                    //设置layoutManager
                    mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    adapter = new PictureAdapter(list, getActivity());
                    mRecyclerView.setAdapter(adapter);
                    adapter.setListener(new PictureAdapter.OnClickItemListener() {
                        @Override
                        public void OnClickItem(View view, PictureBean pictureBean) {
                            // Toast.makeText(getActivity(),pictureBean.toString(),Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), ReadNewsActivity.class);
                            intent.putExtra("title", pictureBean.getPictureTitle());
                            intent.putExtra("imgurl", pictureBean.getPictureUrl());
                            startActivity(intent);
                        }
                    });
                    swipeToLoadLayout.setRefreshing(false);
                    break;
                case 0x1234:
                    adapter.notifyItemRangeInserted(list.size(), 20);
                   // adapter.notifyDataSetChanged();
//                    adapter=new PictureAdapter(list,getActivity());
//                    mRecyclerView.setAdapter(adapter);
//                    adapter.setListener(new PictureAdapter.OnClickItemListener() {
//                        @Override
//                        public void OnClickItem(View view, PictureBean pictureBean) {
//                            Toast.makeText(getActivity(),pictureBean.toString(),Toast.LENGTH_SHORT).show();
//                            Log.i("TAG", "OnClickItem: "+pictureBean.toString());
//                        }
//                    });
                    swipeToLoadLayout.setLoadingMore(false);
                    break;
            }
        }
    };


    public PictureFragment() {
    }

    @Override
    protected void lazyLoad() {
        if (isPrepared && isVisible) {
            Bundle bundle = getArguments();
            type = bundle.getString("type");
            baseurl = GetPictureAPI.getUrlForType(type);
            autoRefresh();
            isPrepared = false;
        }
    }
    @Override
    protected int getContentView() {
        return 0;
    }

    @Override
    protected void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.swipe_target);
        swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        isPrepared = true;
    }

    /**
     * 下拉刷新图片
     */
    public void onRefreshPicture() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                list.clear();
                list = GetPictureAPI.getPictureList(type);
                if (list == null) {
                    return;
                }
                handler.sendEmptyMessage(0x123);
            }
        }.start();
    }

    /**
     * 上拉加载图片
     */
    public void onLoadMorePicture() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String nextUrl = nextUrl(baseurl);
                List<PictureBean> nextList = GetPictureAPI.getPictureNextList(nextUrl);
                list.addAll(nextList);
                handler.sendEmptyMessage(0x1234);
                baseurl = nextUrl;
            }
        }.start();

    }

    @Override
    public void onLoadMore() {
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                onLoadMorePicture();
            }
        });
    }

    @Override
    public void onRefresh() {
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                // mAdapter.add("REFRESH:\n" + new Date());
                onRefreshPicture();
            }
        });
    }

    private void autoRefresh() {
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });
    }

    /**
     * 获取下一个url
     *
     * @param url
     * @return
     */
    public String nextUrl(String url) {
        //http://www.27270.com/ent/haibao/list_22_1.html

        //    .*[0-9]+.html   正则表达式匹配     数字.html
        //  http://www.xs8.cn/zongcai.html  如果该页url匹配不了.*[0-9]+.html则可以默认已加载到底了。
        //   http://www.xs8.cn/channel-3.html   如果该页url能匹配.*[0-9]+.html则可以上拉加载。

        if (url != null) {
            String[] strings = url.split("[0-9]+.html");
            String headUrl = strings[0];// http://www.xs8.cn/channel-
            String[] strings1 = url.split(headUrl);

            String[] strings2 = strings1[1].split(".html");//strings1[1] 是 3.html

            String number = strings2[0];//就是匹配的数字部分  [0-9]+
            next = Integer.valueOf(number);
            next++;
            return headUrl + next + ".html";
        }
        return null;
    }
}
