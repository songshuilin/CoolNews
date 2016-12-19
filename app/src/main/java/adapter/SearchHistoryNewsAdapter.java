package adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import model.NewsSearchBean;
import model.SearchHistoryBean;

/**
 * Created by Administrator on 2016/12/6.
 */

public class SearchHistoryNewsAdapter extends RecyclerView.Adapter<SearchHistoryNewsAdapter.MyHodler>
        implements View.OnClickListener {
    private List<SearchHistoryBean> list;
    private Context context;
    private OnClickTextViewListener listener;//item的监听
    private OnClickImgviewListener clickListener;//delete的监听

    public void setListener(OnClickTextViewListener listener) {
        this.listener = listener;
    }

    public void setOnclickListener(OnClickImgviewListener listener) {
        this.clickListener = listener;
    }

    /**
     * 自定义回调的接口，
     */
    public interface OnClickTextViewListener {
        void OnClickTextView(View view, SearchHistoryBean bean);
    }


    /**
     * 自定义delete回调的接口，
     */
    public interface OnClickImgviewListener {
        void OnClickImgView(View view, SearchHistoryBean bean);
    }

    /**
     * item的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.history:
                if (listener != null) {
                    listener.OnClickTextView(v, (SearchHistoryBean) v.getTag());
                }
                break;
            case R.id.delete:

                if (clickListener != null) {
                    clickListener.OnClickImgView(v, (SearchHistoryBean) v.getTag());
                }
                break;
        }


    }

    public SearchHistoryNewsAdapter(List<SearchHistoryBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_hirstory_news_item, parent, false);
        MyHodler hodler = new MyHodler(view);
        hodler.hirtory.setOnClickListener(this);
        hodler.delete.setOnClickListener(this);
        return hodler;
    }

    @Override
    public void onBindViewHolder(MyHodler holder, int position) {
        holder.hirtory.setText(list.get(position).getKey());
        holder.delete.setImageResource(R.mipmap.delete);

        holder.hirtory.setTag(list.get(position));//为item设置tag,
        holder.delete.setTag(list.get(position));//为item设置tag,
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyHodler extends RecyclerView.ViewHolder {
        TextView hirtory;
        ImageView delete;

        public MyHodler(View itemView) {
            super(itemView);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            hirtory = (TextView) itemView.findViewById(R.id.history);
        }
    }
}
