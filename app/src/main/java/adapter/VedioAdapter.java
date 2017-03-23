package adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.edu.coolnews.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import model.NewsBean;
import model.VedioBean;

/**
 * Created by Administrator on 2016/12/6.
 */

public class VedioAdapter extends RecyclerView.Adapter<VedioAdapter.MyHodler>
        implements View.OnClickListener {

    private List<VedioBean> list;
    private Context context;
    private OnClickItemListener listener;//item的监听

    public void setListener(OnClickItemListener listener) {
        this.listener = listener;
    }

    /**
     * 自定义回调的接口，
     */
    public interface OnClickItemListener {
        void OnClickItem(View view, VedioBean bean);
    }

    /**
     * item的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.OnClickItem(v,  (VedioBean)v.getTag());
        }
    }

    public VedioAdapter(List<VedioBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_vedio_item, parent, false);
        MyHodler hodler = new MyHodler(view);
        view.setOnClickListener(this);
        return hodler;
    }

    @Override
    public void onBindViewHolder(MyHodler holder, int position) {
         holder.vedioTime.setText(list.get(position).getTime());
        holder.vedioInfo.setText(list.get(position).getVedio_title());
        holder.draweeView.setImageURI(Uri.parse(list.get(position).getVedio_img()));
        holder.itemView.setTag(list.get(position));//为item设置tag,
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyHodler extends RecyclerView.ViewHolder {

        private TextView vedioInfo;//视频的描述
        private SimpleDraweeView draweeView;//视频的图片
        private TextView vedioTime;//视频的时间
        public MyHodler(View itemView) {
            super(itemView);
            vedioInfo= (TextView) itemView.findViewById(R.id.vedio_info);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.img);
            vedioTime= (TextView) itemView.findViewById(R.id.vedio_time);
        }
    }
}
