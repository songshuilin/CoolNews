package server;

import model.NewsBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/12/6.
 */

public interface NewsService {
    /**
     * 社会新闻
     * @param key
     * @param num
     * @param word
     * @param page
     * @return
     */
    @GET("social")
    Call<NewsBean> getSocialNews(@Query("key") String key, @Query("num") int num ,
                                 @Query("word") String word,
                                 @Query("page") int page
    );

    /**
     * 国内新闻
     * @param key
     * @param num
     * @param word
     * @param page
     * @return
     */
    @GET("guonei")
    Call<NewsBean> getGuoNeiNews(@Query("key") String key, @Query("num") int num ,
                                 @Query("word") String word,
                                 @Query("page") int page
    );
    /**
     * 国际新闻
     * @param key
     * @param num
     * @param word
     * @param page
     * @return
     */
    @GET("world")
    Call<NewsBean> getWorldNews(@Query("key") String key, @Query("num") int num ,
                                 @Query("word") String word,
                                 @Query("page") int page
    );
    /**
     * 娱乐花边新闻
     * @param key
     * @param num
     * @param word
     * @param page
     * @return
     */
    @GET("huabian")
    Call<NewsBean> getHuaBianNews(@Query("key") String key, @Query("num") int num ,
                                 @Query("word") String word,
                                 @Query("page") int page
    );
    /**
     * 体育新闻
     * @param key
     * @param num
     * @param word
     * @param page
     * @return
     */
    @GET("tiyu")
    Call<NewsBean> getTiYuNews(@Query("key") String key, @Query("num") int num ,
                                 @Query("word") String word,
                                 @Query("page") int page
    );
    /**
     * NBA新闻
     * @param key
     * @param num
     * @param word
     * @param page
     * @return
     */
    @GET("nba")
    Call<NewsBean> getNBANews(@Query("key") String key, @Query("num") int num ,
                                 @Query("word") String word,
                                 @Query("page") int page
    );
    /**
     * 足球新闻
     * @param key
     * @param num
     * @param word
     * @param page
     * @return
     */
    @GET("football")
    Call<NewsBean> getFootballNews(@Query("key") String key, @Query("num") int num ,
                              @Query("word") String word,
                              @Query("page") int page
    );
    /**
     * 科技新闻
     * @param key
     * @param num
     * @param word
     * @param page
     * @return
     */
    @GET("keji")
    Call<NewsBean> getKeJiNews(@Query("key") String key, @Query("num") int num ,
                              @Query("word") String word,
                              @Query("page") int page
    );
    /**
     * 移动新闻
     * @param key
     * @param num
     * @param word
     * @param page
     * @return
     */
    @GET("mobile")
    Call<NewsBean> getMobileNews(@Query("key") String key, @Query("num") int num ,
                              @Query("word") String word,
                              @Query("page") int page
    );  /**
     * vr新闻
     * @param key
     * @param num
     * @param word
     * @param page
     * @return
     */
    @GET("vr")
    Call<NewsBean> getVrNews(@Query("key") String key, @Query("num") int num ,
                              @Query("word") String word,
                              @Query("page") int page
    );
    /**
     * 创业新闻
     * @param key
     * @param num
     * @param word
     * @param page
     * @return
     */
    @GET("startup")
    Call<NewsBean> getStartUpNews(@Query("key") String key, @Query("num") int num ,
                              @Query("word") String word,
                              @Query("page") int page
    );

    /**
     * IT新闻
     * @param key
     * @param num
     * @param word
     * @param page
     * @return
     */
    @GET("it")
    Call<NewsBean> getItNews(@Query("key") String key, @Query("num") int num ,
                                  @Query("word") String word,
                                  @Query("page") int page
    );
    /**
     * 苹果新闻
     * @param key
     * @param num
     * @param word
     * @param page
     * @return
     */
    @GET("apple")
    Call<NewsBean> getAppleNews(@Query("key") String key, @Query("num") int num ,
                                  @Query("word") String word,
                                  @Query("page") int page
    );
    /**
     * 健康新闻
     * @param key
     * @param num
     * @param word
     * @param page
     * @return
     */
    @GET("health")
    Call<NewsBean> getHealthNews(@Query("key") String key, @Query("num") int num ,
                                  @Query("word") String word,
                                  @Query("page") int page
    );
}
