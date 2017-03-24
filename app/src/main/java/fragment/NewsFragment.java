package fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.edu.coolnews.R;

import java.util.List;

import adapter.NewsAdapter;
import base.LazyLoadFragment;
import https.GetNewsAPI;
import model.NewsBean;
import model.OnNetRequestListener;
import view.ReadNewsActivity;


public class NewsFragment extends LazyLoadFragment implements OnRefreshListener, OnLoadMoreListener {
    private RecyclerView mRecyclerView;
    private String type;
    private NewsAdapter adapter;
    private SwipeToLoadLayout swipeToLoadLayout;
    private int count = 1;
    private List<NewsBean.NewslistBean> list;
    private boolean isPrepared;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    protected void lazyLoad() {
        if (isPrepared && isVisible) {
            Bundle bundle = getArguments();
            type = bundle.getString("type");
            autoRefresh();
            onRefreshNews();
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
     * 上拉加载
     */
    public void onLoadMoreNews(int page) {
        GetNewsAPI.getNews(type, 20, null, page, new OnNetRequestListener() {
            @Override
            public void onSuccess(NewsBean data) {
                if (data != null) {
                    list.addAll(data.getNewslist());
                    adapter.notifyDataSetChanged();
                    swipeToLoadLayout.setLoadingMore(false);
                }

            }

            @Override
            public void onFailure(Throwable t) {

            }

        });
    }

    /**
     * 下拉刷新
     */
    public void onRefreshNews() {
        GetNewsAPI.getNews(type, 20, null, 1, new OnNetRequestListener() {
            @Override
            public void onSuccess(NewsBean data) {
                if (data != null) {
                    list = data.getNewslist();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    mRecyclerView.setLayoutManager(layoutManager);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    adapter = new NewsAdapter(list, getActivity());
                    adapter.setListener(new NewsAdapter.OnClickItemListener() {
                        @Override
                        public void OnClickItem(View view, NewsBean.NewslistBean newslistBean) {
                            Intent intent = new Intent(getActivity(), ReadNewsActivity.class);
                            intent.putExtra("url", newslistBean.getUrl());
                            intent.putExtra("title", newslistBean.getTitle());
                            intent.putExtra("imgurl", newslistBean.getPicUrl());
                            startActivity(intent);
                        }
                    });
                    mRecyclerView.setAdapter(adapter);
                    swipeToLoadLayout.setRefreshing(false);
                }

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


    @Override
    public void onLoadMore() {
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {

                // mAdapter.add("LOAD MORE:\n" + new Date());
                count++;
                onLoadMoreNews(count);
            }
        });
    }

    @Override
    public void onRefresh() {
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                // mAdapter.add("REFRESH:\n" + new Date());
                if (list != null)
                    list.clear();//清空
                onRefreshNews();
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
}
