package model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/12/22.
 */

public class FeedBackBean extends BmobObject{
    private String feedBack_content;
    private String contactWays;

    public String getFeedBack_content() {
        return feedBack_content;
    }

    public void setFeedBack_content(String feedBack_content) {
        this.feedBack_content = feedBack_content;
    }

    public String getContactWays() {
        return contactWays;
    }

    public void setContactWays(String contactWays) {
        this.contactWays = contactWays;
    }

    @Override
    public String toString() {
        return "FeedBackBean{" +
                "feedBack_content='" + feedBack_content + '\'' +
                ", contactWays='" + contactWays + '\'' +
                '}';
    }
}
