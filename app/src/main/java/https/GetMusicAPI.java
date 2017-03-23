package https;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.MusicKrcBean;
import model.MusicLocalBean;
import model.MusicPlayInfoBean;
import model.MusicQueryBean;
import model.MusicSingerBean;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import server.MusicService;

import static constants.Constant.MUSIC_BASE_URL;

/**
 * Created by Administrator on 2016/12/15.
 */

public class GetMusicAPI {

    /**
     * 获取本地的音乐
     *
     * @param context
     * @return
     */
    public static List<MusicLocalBean> getMusicLocalList(Context context) {
        List<MusicLocalBean> list = null;
        if (context != null) {
            list = new ArrayList<>();
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                //有sd卡的情况
                Cursor cursor = context.getContentResolver().query(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                        MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

                while (cursor.moveToNext()) {
                    MusicLocalBean bean = new MusicLocalBean();
                    // 到视频文件的信息
                    String title = cursor.getString((cursor
                            .getColumnIndex(MediaStore.Audio.Media.TITLE)));//得到音乐的名字
                    bean.setTitle(title);

                    long size = cursor.getLong(cursor
                            .getColumnIndex(MediaStore.Audio.Media.SIZE));//得到音乐的大小
                    bean.setSize(size);

                    long durantion = cursor.getLong(cursor
                            .getColumnIndex(MediaStore.Audio.Media.DURATION));//得到音乐的时间长度
                    bean.setDuration(durantion);

                    String data = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.DATA));//得到音乐的路径，可以转化为uri进行视频播放
                    bean.setData(data);


                    String artist = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ARTIST));//艺术家

                    bean.setAuthor(artist);
                    list.add(bean);
                }
            }
        }
        return list;
    }

    /**
     * 获取歌词信息
     *
     * @param name 歌曲名称
     * @param hash 歌曲HASH
     * @param time 歌曲时间
     * @return
     */
    public static MusicKrcBean getMusicKrc(String name, String hash, String time) {

        MusicService musicService = getMusicService();

        Call<MusicKrcBean> musicKrc = musicService.getMusicKrc(name, hash, time);

        try {
            Response<MusicKrcBean> response = musicKrc.execute();

            if (response.isSuccessful()) {
                return response.body();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取歌手
     *
     * @param name 歌手名称
     */
    public static MusicSingerBean getMusicSinger(String name) {

        MusicService musicService = getMusicService();

         Call<MusicSingerBean> musicSinger= musicService.getMusicSinger(name);

        try {
            Response<MusicSingerBean> response = musicSinger.execute();
            if (response.isSuccessful()) {
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取播放地址
     * @param hash
     * @return
     */
    public static MusicPlayInfoBean getMusicPlayInfo(String hash){

        MusicService musicService = getMusicService();

        Call<MusicPlayInfoBean> musicSinger= musicService.getMusicPlayinfo(hash);

        try {
            Response<MusicPlayInfoBean> response = musicSinger.execute();
            if (response.isSuccessful()) {
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     *
     * @param s 关键字
     * @param size 条数
     * @param page 分页
     * @return
     */
    public static MusicQueryBean getMusicQuery(String s, String size, String page){

        MusicService musicService = getMusicService();

       Call<MusicQueryBean> musicQuery=musicService.getMusicQuery(s,size,page);
        try {
            Response<MusicQueryBean> response = musicQuery.execute();
            if (response.isSuccessful()) {
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static MusicService getMusicService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MUSIC_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(MusicService.class);
    }
}
