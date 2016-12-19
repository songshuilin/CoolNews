package model;

/**
 * Created by Administrator on 2016/12/16.
 */

public class SearchHistoryBean {
    private String key;
    private int time;

    public SearchHistoryBean() {
    }
    public SearchHistoryBean(String key,int time) {
        this.key = key;
        this.time=time;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "SearchHistoryBean{" +
                "key='" + key + '\'' +
                ", time=" + time +
                '}';
    }
}
