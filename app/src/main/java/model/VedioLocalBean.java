package model;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/12/14.
 */

public class VedioLocalBean {

    private String name;  //得到视频的名字
    private long size;//得到视频的大小
    private long duration;//得到视频的时间长度
    private String data;//得到视频的路径，可以转化为uri进行视频播放
    private Bitmap thumbnail;

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "VedioLocalBean{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", duration=" + duration +
                ", data='" + data + '\'' +
                ", thumbnail=" + thumbnail +
                '}';
    }
}
