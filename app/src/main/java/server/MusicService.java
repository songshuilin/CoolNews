package server;

import model.MusicKrcBean;
import model.MusicPlayInfoBean;
import model.MusicQueryBean;
import model.MusicSingerBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/12/23.
 */

public interface MusicService {


    /**
     * 歌词
     *
     * @param name 歌曲名称
     * @param hash  歌曲HASH
     * @param time 歌曲时间
     */
    @Headers({
            "apikey : 3cb75d497967c823fde8f85f2ef473c4",
    })
    @GET("krc")
    Call<MusicKrcBean> getMusicKrc(@Query("name") String name, @Query("hash") String hash
            , @Query("time") String time
    );

    /**
     * 歌手信息
     * @param name 歌手名称
     */
    @Headers({
            "apikey : 3cb75d497967c823fde8f85f2ef473c4",
    })
    @GET("singer")
    Call<MusicSingerBean> getMusicSinger(@Query("name") String name);


    /**
     * 音乐播放地址
     * @param hash 歌手hash
     */
    @Headers({
            "apikey : 3cb75d497967c823fde8f85f2ef473c4",
    })
    @GET("playinfo")
    Call<MusicPlayInfoBean> getMusicPlayinfo(@Query("hash") String hash);


    /**
     * 音乐搜索
     * @param s 关键字
     * @param size 条数
     * @param page 分页
     */
    @Headers({
            "apikey : 3cb75d497967c823fde8f85f2ef473c4",
    })
    @GET("query")
    Call<MusicQueryBean> getMusicQuery(@Query("s") String s, @Query("size") String size
            , @Query("page") String page);
}
