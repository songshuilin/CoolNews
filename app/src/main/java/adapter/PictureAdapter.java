package adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edu.coolnews.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import model.NewsBean;
import model.PictureBean;

/**
 * Created by Administrator on 2016/12/6.
 */

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.MyHodler>
        implements View.OnClickListener {
    private List<PictureBean> list;
    private Context context;
    private OnClickItemListener listener;//item的监听
    List<Integer> mheight;

    public void setListener(OnClickItemListener listener) {
        this.listener = listener;
    }

    /**
     * 自定义回调的接口，
     */
    public interface OnClickItemListener {
        void OnClickItem(View view,PictureBean pictureBean);
    }

    /**
     * item的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.OnClickItem(v, (PictureBean) v.getTag());
        }
    }

    public PictureAdapter(List<PictureBean> list, Context context) {
        this.list = list;
        this.context = context;
        //随机高度集合
//        mheight=new ArrayList<Integer>();
//            for(int i=0;i<list.size();i++){
//                mheight.add((int)(250+Math.random()*400));
//        }
    }

    @Override
    public MyHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_picture_item, parent, false);
        MyHodler hodler = new MyHodler(view);
        view.setOnClickListener(this);
        return hodler;
    }

    @Override
    public void onBindViewHolder(MyHodler holder, int position) {
        ViewGroup.LayoutParams lp = holder.draweeView.getLayoutParams();

        lp.height =(int)(250+Math.random()*400);
        holder.draweeView.setLayoutParams(lp);

        Uri uri = Uri.parse(list.get(position).getPictureUrl());
       // holder.draweeView.setImageURI(uri);
        holder.imgTitle.setText(list.get(position).getPictureTitle());

        PipelineDraweeController controller =
                (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setOldController(holder.draweeView.getController())
                        .setAutoPlayAnimations(true) //自动播放gif动画
                        .build();
        holder.draweeView.setController(controller);
        holder.itemView.setTag(list.get(position));//为item设置tag,
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyHodler extends RecyclerView.ViewHolder {

        private TextView imgTitle;//图片的描述
        private SimpleDraweeView draweeView;//新闻的图片

        public MyHodler(View itemView) {
            super(itemView);
            imgTitle = (TextView) itemView.findViewById(R.id.picture_item_title);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.picture_item_img);
        }
    }
}
