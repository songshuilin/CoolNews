package fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.edu.coolnews.R;

import java.util.List;

import adapter.MusicLocalAdapter;
import adapter.VedioLocalAdapter;
import constants.Constant;
import https.GetMusicAPI;
import model.MusicLocalBean;
import model.VedioLocalBean;
import util.DividerItemDecoration;
import view.PlayVedioActivity;

public class MusicFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private View view;
    private List<MusicLocalBean> localBeanList;
    private MusicLocalAdapter localAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //设置layoutManager
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(layoutManager);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
            switch (msg.what) {
                case 0x123://展示本地的视频
                    localAdapter = new MusicLocalAdapter(localBeanList, getActivity());
                    mRecyclerView.setAdapter(localAdapter);
                    localAdapter.setListener(new MusicLocalAdapter.OnClickItemListener() {
                        @Override
                        public void OnClickItem(View view, MusicLocalBean bean) {
                            Intent intent = new Intent(getActivity(), PlayVedioActivity.class);
                            intent.putExtra("title", bean.getTitle());
                            intent.putExtra("url", bean.getData());
                            startActivity(intent);
                        }
                    });
                    break;


            }

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_music, container, false);
            initView();
            Bundle bundle = getArguments();
            String type = bundle.getString("type");
            if (Constant.MUSIC_ONLINE.equals(type)) {

            } else if (Constant.MUSIC_UNLINE.equals(type)) {
                getLocalMusicList();//获取本地的视频
            }

        }

        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }

        return view;

    }

    /**
     * 初始化view
     */
    private void initView() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
    }

    /**
     * 获取本地的音乐
     */
    private void getLocalMusicList() {

        new Thread() {
            @Override
            public void run() {
                super.run();
                localBeanList = GetMusicAPI.getMusicLocalList(getActivity());
                handler.sendEmptyMessage(0x123);
            }
        }.start();

    }
}
