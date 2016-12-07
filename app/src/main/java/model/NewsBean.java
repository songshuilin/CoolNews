package model;

import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 */

public class NewsBean {

    /**
     * code : 200
     * msg : success
     * newslist : [{"ctime":"2016-12-06 13:01","title":"北京一宝马车深夜冲撞公交站乘客 至少1人遇难","description":"搜狐社会","picUrl":"http://photocdn.sohu.com/20161206/Img475058014_ss.jpeg","url":"http://news.sohu.com/20161206/n475058013.shtml"},{"ctime":"2016-12-06 13:10","title":"网红孵化试验：义乌在高校设电商模特班","description":"搜狐社会","picUrl":"http://photocdn.sohu.com/20161206/Img475058551_ss.jpeg","url":"http://news.sohu.com/20161206/n475058550.shtml"},{"ctime":"2016-12-06 13:37","title":"徐翔、王巍、竺勇操纵证券市场案一审公开开庭审理","description":"搜狐社会","picUrl":"http://photocdn.sohu.com/20161206/Img475061931_ss.jpeg","url":"http://news.sohu.com/20161206/n475060834.shtml"},{"ctime":"2016-12-06 13:49","title":"男子拖着火煤气罐狂奔：要牺牲就我一个人飞上天","description":"搜狐社会","picUrl":"http://photocdn.sohu.com/20161206/Img475061931_ss.jpeg","url":"http://news.sohu.com/20161206/n475061930.shtml"},{"ctime":"2016-12-06 13:50","title":"记者周口鹿邑法院采访被打 当地回应已成立调查组","description":"搜狐社会","picUrl":"http://photocdn.sohu.com/20161206/Img475061992_ss.jpg","url":"http://news.sohu.com/20161206/n475061990.shtml"},{"ctime":"2016-12-06 12:01","title":"与同学发生碰撞被判赔偿 男孩心生委屈自断手指","description":"搜狐社会","picUrl":"http://photocdn.sohu.com/20161206/Img475049296_ss.png","url":"http://news.sohu.com/20161206/n475051709.shtml"},{"ctime":"2016-12-06 12:45","title":"山东东平警方回应民警医院闹事：涉事者是辅警","description":"搜狐社会","picUrl":"http://photocdn.sohu.com/20161206/Img475050576_ss.jpeg","url":"http://news.sohu.com/20161206/n475057273.shtml"},{"ctime":"2016-12-06 11:25","title":"王石：万科实现万亿目标只要6年","description":"搜狐社会","picUrl":"http://photocdn.sohu.com/20161206/Img475042135_ss.jpeg","url":"http://news.sohu.com/20161206/n475046922.shtml"},{"ctime":"2016-12-06 11:29","title":"这道不分文理后的\u201c高考题\u201d惊呆网友：幸亏毕业早","description":"搜狐社会","picUrl":"http://photocdn.sohu.com/20161206/Img475042166_ss.jpeg","url":"http://news.sohu.com/20161206/n475047422.shtml"},{"ctime":"2016-12-06 11:37","title":"春运抢票大战即将打响 秘诀速看","description":"搜狐社会","picUrl":"http://photocdn.sohu.com/20161206/Img475046923_ss.jpeg","url":"http://news.sohu.com/20161206/n475048369.shtml"}]
     */

    private int code;
    private String msg;
    private List<NewslistBean> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class NewslistBean {
        /**
         * ctime : 2016-12-06 13:01
         * title : 北京一宝马车深夜冲撞公交站乘客 至少1人遇难
         * description : 搜狐社会
         * picUrl : http://photocdn.sohu.com/20161206/Img475058014_ss.jpeg
         * url : http://news.sohu.com/20161206/n475058013.shtml
         */

        private String ctime;
        private String title;
        private String description;
        private String picUrl;
        private String url;

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "NewslistBean{" +
                    "ctime='" + ctime + '\'' +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", picUrl='" + picUrl + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewsBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", newslist=" + newslist +
                '}';
    }
}