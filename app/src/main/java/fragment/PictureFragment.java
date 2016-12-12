package fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.edu.coolnews.R;

import java.util.List;

import adapter.PictureAdapter;
import constants.Constant;
import https.GetPictureAPI;
import model.PictureBean;

public class PictureFragment extends Fragment {
    List<PictureBean>list=null;
    private RecyclerView mRecyclerView;
    private View view;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x123:
                    //设置layoutManager
                    mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                    PictureAdapter adapter=new PictureAdapter(list,getActivity());
                    mRecyclerView.setAdapter(adapter);
                    adapter.setListener(new PictureAdapter.OnClickItemListener() {
                        @Override
                        public void OnClickItem(View view, PictureBean pictureBean) {
                            Toast.makeText(getActivity(),pictureBean.toString(),Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
            }
        }
    };

    public PictureFragment(){}

    /**
     * 初始化各view
     */
    private void initView() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view=inflater.inflate(R.layout.fragment_picture, container, false);
            initView();
            Bundle bundle= getArguments();
            String type=  bundle.getString("type");
            getPictures(type);
        }

        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    public void getPictures(final String type){
        new Thread(){
            @Override
            public void run() {
                super.run();

                list=GetPictureAPI.getPictureList(type);
                handler.sendEmptyMessage(0x123);
            }
        }.start();
    }
}
