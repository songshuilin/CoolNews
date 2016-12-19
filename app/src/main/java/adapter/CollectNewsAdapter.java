package adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.edu.coolnews.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import model.CollectNewsBean;
import model.NewsBean;

/**
 * Created by Administrator on 2016/12/6.
 */

public class CollectNewsAdapter extends RecyclerView.Adapter<CollectNewsAdapter.MyHodler>
        implements View.OnClickListener {
    private List<CollectNewsBean> list;
    private Context context;
    private OnClickItemListener listener;//item的监听

    public void setListener(OnClickItemListener listener) {
        this.listener = listener;
    }

    /**
     * 自定义回调的接口，
     */
    public interface OnClickItemListener {
        void OnClickItem(View view, CollectNewsBean bean);
    }

    /**
     * item的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.OnClickItem(v, (CollectNewsBean) v.getTag());
        }
    }

    public CollectNewsAdapter(List<CollectNewsBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_collect_news_item, parent, false);
        MyHodler hodler = new MyHodler(view);
        view.setOnClickListener(this);
        return hodler;
    }

    @Override
    public void onBindViewHolder(MyHodler holder, int position) {
       holder.title.setText(list.get(position).getTitle());
        holder.draweeView.setImageURI(Uri.parse(list.get(position).getImgUrl()));
        holder.itemView.setTag(list.get(position));//为item设置tag,
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyHodler extends RecyclerView.ViewHolder {
        private TextView title;//新闻的标题
        private SimpleDraweeView draweeView;//新闻的图片

        public MyHodler(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.img);
        }
    }
}
