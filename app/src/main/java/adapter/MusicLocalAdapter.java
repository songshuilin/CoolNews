package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.edu.coolnews.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import model.MusicLocalBean;
import model.VedioLocalBean;
import util.TimeUtil;

/**
 * Created by Administrator on 2016/12/6.
 */

public class MusicLocalAdapter extends RecyclerView.Adapter<MusicLocalAdapter.MyHodler>
        implements View.OnClickListener {

    private List<MusicLocalBean> list;
    private Context context;
    private OnClickItemListener listener;//item的监听

    public void setListener(OnClickItemListener listener) {
        this.listener = listener;
    }

    /**
     * 自定义回调的接口，
     */
    public interface OnClickItemListener {
        void OnClickItem(View view, MusicLocalBean bean);
    }

    /**
     * item的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.OnClickItem(v,  (MusicLocalBean)v.getTag());
        }
    }

    public MusicLocalAdapter(List<MusicLocalBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_music_local_item, parent, false);
        MyHodler hodler = new MyHodler(view);
        view.setOnClickListener(this);
        return hodler;
    }

    @Override
    public void onBindViewHolder(MyHodler holder, int position) {
        holder.musicAuthor.setText(list.get(position).getAuthor());
        String data=TimeUtil.formatDuring(list.get(position).getDuration());
        holder.musicTime.setText(data);
        float size= (float) (list.get(position).getSize()/(1024.0*1024.0));
        float  a  =   (float)(Math.round(size*100))/100;
        holder.musicSize.setText(a+"M");
        if (position<10){
            holder.sort.setText("0"+position);
        }else {
            holder.sort.setText(position+"");
        }

        holder.musicTitle.setText(list.get(position).getTitle());
        holder.itemView.setTag(list.get(position));//为item设置tag,
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyHodler extends RecyclerView.ViewHolder {

        private TextView musicTitle;//音乐的描述
        private TextView musicAuthor;//音乐的作者
        private TextView sort;//音乐的图片
        private TextView musicSize;//音乐的大小
        private TextView musicTime;//音乐的时间

        public MyHodler(View itemView) {
            super(itemView);
            musicSize= (TextView) itemView.findViewById(R.id.size);
            musicTime= (TextView) itemView.findViewById(R.id.time);
            musicTitle= (TextView) itemView.findViewById(R.id.title);
            musicAuthor= (TextView) itemView.findViewById(R.id.author);
            sort = (TextView) itemView.findViewById(R.id.sort);
        }
    }
}
