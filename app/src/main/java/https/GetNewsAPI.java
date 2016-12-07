package https;


import model.NewsBean;
import model.OnNetRequestListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import server.NewsService;

import static constants.Constant.API_KEY;
import static constants.Constant.APPLE_NEWS;
import static constants.Constant.BASE_URL;
import static constants.Constant.FOOTBALL_NEWS;
import static constants.Constant.GUONEI_NEWS;
import static constants.Constant.HEALTH_NEWS;
import static constants.Constant.HUABIAN_NEWS;
import static constants.Constant.IT_NEWS;
import static constants.Constant.NBA_NEWS;
import static constants.Constant.SOCIAL_NEWS;
import static constants.Constant.STARTUP_NEWS;
import static constants.Constant.TIYU_NEWS;
import static constants.Constant.VR_NEWS;
import static constants.Constant.WORLD_NEWS;
import static constants.Constant.KEJI_NEWS;
import static constants.Constant.MOBILE_NEWS;

/**
 * Created by Administrator on 2016/12/6.
 */

public class GetNewsAPI {

    /**
     * 获取社会新闻
     *
     * @param num
     * @param word
     * @param page
     * @param listListener
     */
    public static void getNews(String type, int num, String word, int page, final OnNetRequestListener listListener) {
        //1.创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL).build();
        //2.创建访问API的请求
        NewsService service = retrofit.create(NewsService.class);
        //根据类型返回哪个新闻接口
        Call<NewsBean> call = getServiceMethod(service, type, num, word, page);

        call.enqueue(new Callback<NewsBean>() {
            @Override
            public void onResponse(Call<NewsBean> call, Response<NewsBean> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        //  Log.i("TAGA", "onResponse: "+response.body().getNewslist().toString());
                        listListener.onSuccess(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsBean> call, Throwable t) {
                listListener.onFailure(new Exception());
            }
        });
    }

    /**
     * 根据类型返回哪个新闻接口
     *
     * @param service
     * @param type
     * @param num
     * @param word
     * @param page
     * @return
     */
    private static Call<NewsBean> getServiceMethod(NewsService service, String type, int num, String word, int page) {
        Call<NewsBean> call = null;
        switch (type) {
            case SOCIAL_NEWS:
                call = service.getSocialNews(API_KEY, num, word, page);
                break;
            case GUONEI_NEWS:
                call = service.getGuoNeiNews(API_KEY, num, word, page);
                break;
            case WORLD_NEWS:
                call = service.getWorldNews(API_KEY, num, word, page);
                break;
            case HUABIAN_NEWS:
                call = service.getHuaBianNews(API_KEY, num, word, page);
                break;
            case TIYU_NEWS:
                call = service.getTiYuNews(API_KEY, num, word, page);
                break;
            case NBA_NEWS:
                call = service.getNBANews(API_KEY, num, word, page);
                break;
            case FOOTBALL_NEWS:
                call = service.getFootballNews(API_KEY, num, word, page);
                break;
            case KEJI_NEWS:
                call = service.getKeJiNews(API_KEY, num, word, page);
                break;
            case MOBILE_NEWS:
                call = service.getMobileNews(API_KEY, num, word, page);
                break;
            case VR_NEWS:
                call = service.getVrNews(API_KEY, num, word, page);
                break;
            case STARTUP_NEWS:
                call = service.getStartUpNews(API_KEY, num, word, page);
                break;
            case IT_NEWS:
                call = service.getItNews(API_KEY, num, word, page);
                break;
            case APPLE_NEWS:
                call = service.getAppleNews(API_KEY, num, word, page);
                break;
            case HEALTH_NEWS:
                call = service.getHealthNews(API_KEY, num, word, page);
                break;
        }

        return call;
    }

}
