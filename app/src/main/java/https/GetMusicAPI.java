package https;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import model.MusicLocalBean;

/**
 * Created by Administrator on 2016/12/15.
 */

public class GetMusicAPI {



    /**
     *获取本地的音乐
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

}
