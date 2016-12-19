package model;

/**
 * Created by Administrator on 2016/12/19.
 */

public class CollectNewsBean {
    private String url;
    private String imgUrl;
    private String title;

    public CollectNewsBean(String url, String imgUrl, String title) {
        this.url = url;
        this.imgUrl = imgUrl;
        this.title = title;
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
                "url='" + url + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
