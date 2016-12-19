package https;


import android.os.Parcel;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.PictureBean;

import static constants.Constant.*;

/**
 * Created by Administrator on 2016/12/11.
 */

public class GetPictureAPI {

    public static List<PictureBean> getPictureList(String type) {
        List<PictureBean> list = null;
        try {
            String url=getUrlForType(type);
            Document document = Jsoup.connect(url).get();
            String str=document.toString();
            Elements meinv = document.getElementsByClass("listUll");
            Elements meinvs = meinv.get(1).getElementsByClass("libox");

            if (meinvs != null) {
                list = new ArrayList<>();
            }

            for (int i = 0; i < meinvs.size(); i++) {
                PictureBean pictureBean = new PictureBean(Parcel.obtain());
                String imgUrl = meinvs.get(i).getElementsByTag("img").attr("lazysrc");//图片url
                String imgTitle = meinvs.get(i).getElementsByTag("p").text();//图片描述
                String htmlUrl=meinvs.get(i).getElementsByTag("a").attr("href");
                pictureBean.setUrl(htmlUrl);
                pictureBean.setPictureTitle(imgTitle);
                pictureBean.setPictureUrl(imgUrl);
                list.add(pictureBean);
            }

            return list;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }


    public static List<PictureBean> getPictureNextList(String nextUrl) {
        List<PictureBean> list = null;
        try {
            Document document = Jsoup.connect(nextUrl).get();
            String str=document.toString();
            Elements meinv = document.getElementsByClass("listUll");
            Elements meinvs = meinv.get(1).getElementsByClass("libox");

            if (meinvs != null) {
                list = new ArrayList<>();
            }

            for (int i = 0; i < meinvs.size(); i++) {
                PictureBean pictureBean = new PictureBean(Parcel.obtain());
                String imgUrl = meinvs.get(i).getElementsByTag("img").attr("lazysrc");//图片url
                String imgTitle = meinvs.get(i).getElementsByTag("p").text();//图片描述
                pictureBean.setPictureTitle(imgTitle);
                pictureBean.setPictureUrl(imgUrl);
                list.add(pictureBean);
            }

            return list;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static String getUrlForType(String type) {
        switch (type) {
            case PICTURE_STAR:
                return PICTURE_URL_STAR;

            case PICTURE_CAR:
                return PICTURE_URL_CAR;

            case PICTURE__ANIMAL:
                return PICTURE_URL_ANIMAL;

            case PICTURE_FUN:
                return PICTURE_URL_FUN;

            case PICTURE_LOVELY:
                return PICTURE_URL_LOVELY;

            case PICTURE_POSTER:
                return PICTURE_URL_POSTER;
        }

        return null;
    }

}
