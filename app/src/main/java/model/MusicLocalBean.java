package model;


import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/12/13.
 */

public class MusicLocalBean {
    private String title;  //得到音乐的名字
    private String author;//得到音乐人的名字
    private long size;//得到音乐的大小
    private long duration;//得到音乐的时间长度
    private String data;//得到音乐的路径，可以转化为uri进行视频播放

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
        return "MusicLocalBean{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", size=" + size +
                ", duration=" + duration +
                ", data='" + data + '\'' +
                '}';
    }
}
