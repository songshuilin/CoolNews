package https;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import model.NewsBean;
import model.NewsSearchBean;

/**
 * Created by Administrator on 2016/12/15.
 */

public class GetNewsForSearch {

    public static List<NewsBean.NewslistBean> getAllNews(String key, int page) {
        List<NewsBean.NewslistBean> list = null;
        try {
//            Log.i("TAGww", "getAllNews: "+"http://news.baidu.com/ns?cl=" + page + "&rn=20&tn=news&word="
//                    + key);
           Document document = Jsoup.connect("http://news.baidu.com/ns?word="+ URLEncoder.encode(key, "UTF-8") +"&tn=news&from=news&cl="+page+"&rn=20&ct=0").get();
           // Document document = Jsoup.connect("http://news.baidu.com/ns?word=%E5%8C%97%E4%BA%AC&tn=news&from=news&cl=1&rn=20&ct=0").get();
            Element content = document.getElementById("content_left");
            Elements results = content.getElementsByClass("result");
           String nums= document.getElementsByClass("nums").get(0).text();
            Log.i("TAGaaaa", "getAllNews: "+nums);
            if (results.size() > 0) {
                list = new ArrayList<>();
            }

            for (int i = 0; i < results.size(); i++) {
                NewsBean.NewslistBean bean = new NewsBean.NewslistBean();
                Elements title = results.get(i).getElementsByClass("c-title");
                Element a = title.get(0).getElementsByTag("a").get(0);
                String url = a.attr("href");
                bean.setUrl(url);//新闻的url
                String newsTitle = a.html();
                newsTitle= newsTitle.replaceAll("<em>","<font size=22 color=red>");
                newsTitle= newsTitle.replaceAll("</em>","</font>");
                bean.setTitle(newsTitle);//新闻的标题
                if (results.get(i).getElementsByTag("img").size() == 0) {
                    bean.setPicUrl(null);//如果没有就为null;
                } else {
                    Element img = results.get(i).getElementsByClass("c-span6").get(0);
                    String imgUrl = img.getElementsByTag("img").get(0).attr("src");
                    bean.setPicUrl(imgUrl);//新闻的图片
                }
                String info = results.get(i).getElementsByClass("c-author").get(0).html();
              //  Log.i("TAGaa", "getAllNews: " + info);
                //  bean.setInfo(info);//新闻的信息
                String[] str = info.split("&nbsp;&nbsp;");
                if (str.length==1){
                    bean.setDescription("未知");
                    bean.setCtime(str[0]);
                }else {
                    bean.setDescription(str[0]);
                    bean.setCtime(str[1]);
                }
                list.add(bean);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


}
