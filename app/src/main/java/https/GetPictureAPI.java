package https;


import android.os.Parcel;

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
            String url = getUrlForType(type);
            Document document = Jsoup.connect(url).get();
            Elements liboxElemnets =document.getElementsByClass("libox");

            if (liboxElemnets.size()>0) {
               list = new ArrayList<>();
           }

            for (int i = 10; i <liboxElemnets.size() ; i++) {
                PictureBean pictureBean = new PictureBean(Parcel.obtain());
                String imgUrl=null;
                if (liboxElemnets.get(i).getElementsByTag("img").hasAttr("lazysrc")){
                imgUrl= liboxElemnets.get(i).getElementsByTag("img").attr("lazysrc");
                }else {
                imgUrl= liboxElemnets.get(i).getElementsByTag("img").attr("src");
                }
                String imgTitle=liboxElemnets.get(i).getElementsByTag("p").text();
                pictureBean.setPictureUrl(imgUrl);
                pictureBean.setPictureTitle(imgTitle);
                list.add(pictureBean);
            }

//            Elements imgs = document.getElementsByTag("img");
//            if (imgs != null) {
//                list = new ArrayList<>();
//            }
//            for (int i = 10; i < imgs.size()-10; i++) {
//                PictureBean pictureBean = new PictureBean(Parcel.obtain());
//                if (imgs.get(i).hasAttr("alt")&&imgs.get(i).hasAttr("src")) {
//                    String imgTitle = imgs.get(i).attr("alt");//图片描述
//                    String imgUrl = imgs.get(i).attr("src");
//                    pictureBean.setPictureUrl(imgUrl);
//                    pictureBean.setPictureTitle(imgTitle);
//                    list.add(pictureBean);
//                }
//            }
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

            Elements liboxElemnets =document.getElementsByClass("libox");
            if (liboxElemnets.size()>0) {
                list = new ArrayList<>();
                return null;
            }

            for (int i = 0; i <liboxElemnets.size() ; i++) {
                PictureBean pictureBean = new PictureBean(Parcel.obtain());
                String imgUrl= liboxElemnets.get(i).getElementsByTag("img").attr("src");
                String imgTitle=liboxElemnets.get(i).getElementsByTag("p").text();
                pictureBean.setPictureUrl(imgUrl);
                pictureBean.setPictureTitle(imgTitle);
                list.add(pictureBean);
            }
//            Elements imgs = document.getElementsByTag("img");
//
//            if (imgs != null) {
//                list = new ArrayList<>();
//            }
//
//            for (int i = 0; i < imgs.size(); i++) {
//                PictureBean pictureBean = new PictureBean(Parcel.obtain());
//                if (imgs.get(i).hasAttr("alt")) {
//                    String imgTitle = imgs.get(i).attr("alt");//图片描述
//                    String imgUrl = imgs.get(i).attr("src");
//                    pictureBean.setPictureUrl(imgUrl);
//                    pictureBean.setPictureTitle(imgTitle);
//                    list.add(pictureBean);
//                }
//            }
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
