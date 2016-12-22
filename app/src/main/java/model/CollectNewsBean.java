package model;

/**
 * Created by Administrator on 2016/12/19.
 */

public class CollectNewsBean {
    private String username;
    private String url;
    private String imgUrl;
    private String title;

    public CollectNewsBean(String username,String url, String imgUrl, String title) {
        this.url = url;
        this.imgUrl = imgUrl;
        this.title = title;
        this.username=username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "CollectNewsBean{" +
                "username='" + username + '\'' +
                ", url='" + url + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
