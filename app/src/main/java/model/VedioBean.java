package model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/12/13.
 */

public class VedioBean extends BmobObject {
    private String vedio_url;
    private String vedio_img;
    private String vedio_title;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVedio_url() {
        return vedio_url;
    }

    public void setVedio_url(String vedio_url) {
        this.vedio_url = vedio_url;
    }

    public String getVedio_img() {
        return vedio_img;
    }

    public void setVedio_img(String vedio_img) {
        this.vedio_img = vedio_img;
    }

    public String getVedio_title() {
        return vedio_title;
    }

    public void setVedio_title(String vedio_title) {
        this.vedio_title = vedio_title;
    }

    @Override
    public String toString() {
        return "VedioBean{" +
                "vedio_url='" + vedio_url + '\'' +
                ", vedio_img='" + vedio_img + '\'' +
                ", vedio_title='" + vedio_title + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
