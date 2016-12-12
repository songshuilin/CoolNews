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
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import model.NewsBean;

/**
 * Created by Administrator on 2016/12/6.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyHodler>
        implements View.OnClickListener {
    private List<NewsBean.NewslistBean> list;
    private Context context;
    private OnClickItemListener listener;//item的监听

    public void setListener(OnClickItemListener listener) {
        this.listener = listener;
    }

    /**
     * 自定义回调的接口，
     */
    public interface OnClickItemListener {
        void OnClickItem(View view, NewsBean.NewslistBean newslistBean);
    }

    /**
     * item的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.OnClickItem(v, (NewsBean.NewslistBean) v.getTag());
        }
    }

    public NewsAdapter(List<NewsBean.NewslistBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_news_item, parent, false);
        MyHodler hodler = new MyHodler(view);
        view.setOnClickListener(this);
        return hodler;
    }

    @Override
    public void onBindViewHolder(MyHodler holder, int position) {
        holder.time.setText(list.get(position).getCtime());
        holder.desc.setText(list.get(position).getDescription());
        holder.title.setText(list.get(position).getTitle());

        if (TextUtils.isEmpty(list.get(position).getPicUrl())){
            holder.draweeView.setVisibility(View.GONE);
        }else {
            Uri uri = Uri.parse(list.get(position).getPicUrl());
            holder.draweeView.setImageURI(uri);
        }

        holder.itemView.setTag(list.get(position));//为item设置tag,
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyHodler extends RecyclerView.ViewHolder {
        private TextView title;//新闻的标题
        private TextView desc;//新闻的描述
        private TextView time;//新闻的时间
        private SimpleDraweeView draweeView;//新闻的图片

        public MyHodler(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.time);
            desc = (TextView) itemView.findViewById(R.id.desc);
            title = (TextView) itemView.findViewById(R.id.title);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.img);
        }
    }
}
