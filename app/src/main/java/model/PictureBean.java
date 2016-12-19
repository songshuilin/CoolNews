package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/12/11.
 */

public class PictureBean implements Parcelable {
    private String pictureUrl;
    private String pictureTitle;
    private String url;

    public PictureBean(Parcel in) {
        pictureUrl = in.readString();
        pictureTitle = in.readString();
        url = in.readString();
    }

    public static final Creator<PictureBean> CREATOR = new Creator<PictureBean>() {
        @Override
        public PictureBean createFromParcel(Parcel in) {
            return new PictureBean(in);
        }

        @Override
        public PictureBean[] newArray(int size) {
            return new PictureBean[size];
        }
    };

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPictureTitle() {
        return pictureTitle;
    }

    public void setPictureTitle(String pictureTitle) {
        this.pictureTitle = pictureTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pictureUrl);
        dest.writeString(pictureTitle);
        dest.writeString(url);
    }
    @Override
    public String toString() {
        return "PictureBean{" +
                "pictureUrl='" + pictureUrl + '\'' +
                ", pictureTitle='" + pictureTitle + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
