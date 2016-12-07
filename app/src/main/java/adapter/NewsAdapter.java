package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edu.coolnews.R;

import java.util.List;

import model.NewsBean;

/**
 * Created by Administrator on 2016/12/6.
 */

public class NewsAdapter extends RecyclerView.Adapter <NewsAdapter.MyHodler>{
    private List<NewsBean.NewslistBean> list;
   private Context context;
    public NewsAdapter(List<NewsBean.NewslistBean> list,Context context){
        this.list=list;
        this.context=context;
    }
    @Override
    public MyHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(context).inflate(R.layout.recyclerview_news_item,parent,false);
        MyHodler hodler=new MyHodler(view);
        return hodler;
    }

    @Override
    public void onBindViewHolder(MyHodler holder, int position) {
            holder.time.setText(list.get(position).getCtime());
            holder.desc.setText(list.get(position).getDescription());
            holder.title.setText(list.get(position).getTitle());
            holder.img.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHodler extends RecyclerView.ViewHolder{
        private TextView title;//新闻的标题
        private TextView desc;//新闻的描述
        private TextView time;//新闻的时间
        private ImageView img;//新闻的图片
        public MyHodler(View itemView) {
            super(itemView);
            img= (ImageView) itemView.findViewById(R.id.img);
            time= (TextView) itemView.findViewById(R.id.time);
            desc= (TextView) itemView.findViewById(R.id.desc);
            title= (TextView) itemView.findViewById(R.id.title);
        }
    }
}
