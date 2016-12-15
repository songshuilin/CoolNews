package adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edu.coolnews.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import model.VedioBean;
import model.VedioLocalBean;
import util.TimeUtil;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by Administrator on 2016/12/6.
 */

public class VedioLocalAdapter extends RecyclerView.Adapter<VedioLocalAdapter.MyHodler>
        implements View.OnClickListener {

    private List<VedioLocalBean> list;
    private Context context;
    private OnClickItemListener listener;//item的监听

    public void setListener(OnClickItemListener listener) {
        this.listener = listener;
    }

    /**
     * 自定义回调的接口，
     */
    public interface OnClickItemListener {
        void OnClickItem(View view, VedioLocalBean bean);
    }

    /**
     * item的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.OnClickItem(v,  (VedioLocalBean)v.getTag());
        }
    }

    public VedioLocalAdapter(List<VedioLocalBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_vedio_local_item, parent, false);
        MyHodler hodler = new MyHodler(view);
        view.setOnClickListener(this);
        return hodler;
    }

    @Override
    public void onBindViewHolder(MyHodler holder, int position) {
        holder.vedioTitle.setText(list.get(position).getName());
        String data=TimeUtil.formatDuring(list.get(position).getDuration());
        holder.vedioTime.setText(data);
        float size= (float) (list.get(position).getSize()/(1024.0*1024.0));
        holder.vedioSize.setText(size+"M");
        holder.draweeView.setImageBitmap(list.get(position).getThumbnail());
        holder.itemView.setTag(list.get(position));//为item设置tag,
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyHodler extends RecyclerView.ViewHolder {

        private TextView vedioTitle;//视频的描述
        private SimpleDraweeView draweeView;//视频的图片
        private TextView vedioSize;//视频的大小
        private TextView vedioTime;//视频的时间

        public MyHodler(View itemView) {
            super(itemView);
            vedioSize= (TextView) itemView.findViewById(R.id.size);
            vedioTime= (TextView) itemView.findViewById(R.id.time);
            vedioTitle= (TextView) itemView.findViewById(R.id.title);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.img);
        }
    }
}
