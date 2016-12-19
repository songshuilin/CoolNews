package constants;

/**
 * Created by Administrator on 2016/12/6.
 */

public interface Constant {
    // 新闻
    String BASE_URL = "http://api.tianapi.com/";//新闻
    String API_KEY = "b917ce35b6958f7199810f9348fc33ef";
    String SOCIAL_NEWS = "社会";
    String GUONEI_NEWS = "国内";
    String WORLD_NEWS = "国际";
    String HUABIAN_NEWS = "娱乐花边";
    String TIYU_NEWS = "体育";
    String NBA_NEWS = "NBA";
    String FOOTBALL_NEWS = "足球";
    String KEJI_NEWS = "科技";
    String MOBILE_NEWS = "移动互联";
    String VR_NEWS = "VR";
    String STARTUP_NEWS = "创业";
    String IT_NEWS = "IT";
    String APPLE_NEWS = "苹果";
    String HEALTH_NEWS = "健康";
    String[] TABS = {SOCIAL_NEWS, GUONEI_NEWS,
            WORLD_NEWS, HUABIAN_NEWS,
            TIYU_NEWS, NBA_NEWS,
            FOOTBALL_NEWS, KEJI_NEWS,
            MOBILE_NEWS, VR_NEWS,
            STARTUP_NEWS, IT_NEWS,
            APPLE_NEWS, HEALTH_NEWS,
    };

    //图片
    String PICTURE_URL_STAR = "http://www.27270.com/ent/mingxingtuku/list_10_3.html";//明星
    String PICTURE_URL_CAR = "http://www.27270.com/beautiful/qichetuku/list_15_1.html";//汽车
    String PICTURE_URL_ANIMAL = "http://www.27270.com/word/dongwushijie/list_8_1.html";//动物
    String PICTURE_URL_FUN = "http://www.27270.com/word/gaoxiaoqutu/list_7_1.html";//搞笑
    String PICTURE_URL_LOVELY =  "http://www.27270.com/qita/mengtu/list_27_1.html";//可爱
    String PICTURE_URL_POSTER =  "http://www.27270.com/ent/haibao/list_22_1.html";//海报

    String PICTURE_STAR = "明星";
    String PICTURE_CAR= "汽车";
    String PICTURE__ANIMAL = "动物";
    String PICTURE_FUN = "搞笑";
    String PICTURE_LOVELY="可爱";
    String PICTURE_POSTER="海报";
    String TAB_PICTURE[] = {PICTURE_STAR,PICTURE_CAR,PICTURE__ANIMAL,PICTURE_FUN
    ,PICTURE_LOVELY,PICTURE_POSTER
    };


    /**
     * 视频
     */
    //tring VEDIO_URL="http://v.jxntv.cn/so/?fr=pg&ch=138&ca=138&pg=1";
     String VEDIO_URL="http://v.jxntv.cn/so/?fr=pg&ch=138&ca=138&pg=1#";

    String  VEDIO_ONLINE="在线视频";
    String   VEDIO_UNLINE="离线视频";

    String TAB_VEDIO[]={VEDIO_ONLINE,VEDIO_UNLINE};


    /**
     * 音乐
     */

    String  MUSIC_ONLINE="在线音乐";
    String   MUSIC_UNLINE="离线音乐";

    String TAB_MUSIC[]={MUSIC_ONLINE,MUSIC_UNLINE};

    /**
     * 新闻的搜索
     */
    String SEARCH_NEWS="http://news.baidu.com/ns?cl=2&rn=20&tn=news&word=美女";


    /**
     * 表名
     */

    String SEARCH_HISTORY_TABLE="SEARCH_HISTORY_TABLE";

    String COLLECT_NEWS_TABLE="COLLECT_NEWS_TABLE";
}
