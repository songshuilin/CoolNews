package fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.edu.coolnews.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import adapter.VedioAdapter;
import adapter.VedioLocalAdapter;
import constants.Constant;
import event.VedioEvent;
import https.GetVedioAPI;
import model.VedioBean;
import model.VedioLocalBean;
import util.DividerItemDecoration;
import util.ToastUtil;
import view.PlayVedioActivity;


public class VedioFragment extends Fragment {
    private List<VedioBean> list;
    private List<VedioLocalBean>localBeanList;
    private View view;
    private RecyclerView mRecyclerView;
    private VedioAdapter adapter;
    private VedioLocalAdapter vedioLocalAdapter;
    private String title;
    private String url;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //设置layoutManager
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(layoutManager);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
            switch (msg.what) {
                case 0x123:
                    Log.i("TAGaaaaaaaaaa", "handleMessage:.................... ");
                    adapter = new VedioAdapter(list, getActivity());
                    mRecyclerView.setAdapter(adapter);
                    adapter.setListener(new VedioAdapter.OnClickItemListener() {
                        @Override
                        public void OnClickItem(View view, VedioBean bean) {
                             title=bean.getVedio_title();
                             getUrl(bean.getVedio_url());//获取url
                        }
                    });
                    break;

                case 0x1234:
                    vedioLocalAdapter = new VedioLocalAdapter(localBeanList, getActivity());
                    mRecyclerView.setAdapter(vedioLocalAdapter);
                      vedioLocalAdapter.setListener(new VedioLocalAdapter.OnClickItemListener() {
                          @Override
                          public void OnClickItem(View view, VedioLocalBean bean) {
                              Intent intent=new Intent(getActivity(),PlayVedioActivity.class);
                              intent.putExtra("title",bean.getName());
                              intent.putExtra("url",bean.getData());
                              startActivity(intent);
                          }
                      });
                    break;

                case 0x12345:
                    Intent intent=new Intent(getActivity(),PlayVedioActivity.class);
                    intent.putExtra("title",title);
                    intent.putExtra("url",url);
                    startActivity(intent);
                    break;

            }
        }
    };

    /**
     * 获取视频url
     * @param vedio_url
     * @return
     */
    private void getUrl(final String vedio_url) {
          new Thread(){
              @Override
              public void run() {
                  super.run();
                  url=GetVedioAPI.getUrl(vedio_url);
                  handler.sendEmptyMessage(0x12345);
              }
          }.start();

    }

    public VedioFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            Bundle bundle=getArguments();
            String type= bundle.getString("type");
            Log.i("TAG", "onCreateView: "+type);
            view = inflater.inflate(R.layout.fragment_vedio, container, false);
            initView();
            if (Constant.VEDIO_ONLINE.equals(type)){
                EventBus.getDefault().register(this);
                getVedioOnLine();//获取在线视频
            }else if (Constant.VEDIO_UNLINE.equals(type)){
                getLocalVedio();//获取本地视频
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
     * 获取在线视频
     */
    public void getVedioOnLine(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                GetVedioAPI.queryVedioBeanAll();//获取在线视频
            }
        }.start();
    }
    /**
     * 初始化view
     */
    private void initView() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.swipe_target);
    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void getVedioList(VedioEvent event) {
        list = event.getList();
        Log.i("bbbbfff", "getVedioList: "+list);
        handler.sendEmptyMessage(0x123);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void getLocalVedio(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                localBeanList= GetVedioAPI.getLocalVedio(getActivity());
                handler.sendEmptyMessage(0x1234);
                Log.i("TAGVV", "run: "+localBeanList);
            }
        }.start();
    }

}
