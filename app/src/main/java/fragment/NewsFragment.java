package fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.edu.coolnews.R;

import adapter.NewsAdapter;
import https.GetNewsAPI;
import model.NewsBean;
import model.OnNetRequestListener;
import view.ReadNewsActivity;


public class NewsFragment extends Fragment {
    private View view;
    private RecyclerView mRecyclerView;
    private String type;
    private NewsAdapter adapter;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_news, container, false);
            initView();
            Bundle bundle = getArguments();
            type = bundle.getString("type");
            GetNewsAPI.getNews(type, 20, null, 1, new OnNetRequestListener() {

                @Override
                public void onSuccess(NewsBean data) {
                      if (data!=null){
                          LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                          mRecyclerView.setLayoutManager(layoutManager);
                          layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                          adapter = new NewsAdapter(data.getNewslist(), getActivity());
                         adapter.setListener(new NewsAdapter.OnClickItemListener() {
                             @Override
                             public void OnClickItem(View view, NewsBean.NewslistBean newslistBean) {
                                 Intent intent=new Intent(getActivity(), ReadNewsActivity.class);
                                 intent.putExtra("url",newslistBean.getUrl());
                                 intent.putExtra("title",newslistBean.getTitle());
                                 intent.putExtra("imgurl",newslistBean.getPicUrl());
                                 startActivity(intent);
                             }
                         });
                          mRecyclerView.setAdapter(adapter);
                      }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    /**
     * 初始化各view
     */
    private void initView() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

    }

}
