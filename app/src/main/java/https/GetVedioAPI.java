package https;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.media.ThumbnailUtils;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;


import org.greenrobot.eventbus.EventBus;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import event.VedioEvent;
import model.VedioBean;
import model.VedioLocalBean;
import util.ToastUtil;

/**
 * Created by Administrator on 2016/12/13.
 */

public class GetVedioAPI {

    /**
     * 添加veido
     *
     * @param vedioBean
     * @param context
     */
    public static void addVedioBean(VedioBean vedioBean, final Context context) {
        VedioBean bean = new VedioBean();
        bean.setVedio_title(vedioBean.getVedio_title());
        bean.setVedio_img(vedioBean.getVedio_img());
        bean.setVedio_url(vedioBean.getVedio_url());
        bean.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    ToastUtil.MyToast(context, "创建数据成功：" + objectId);
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    /**
     * 查询所有
     *
     * @return
     */
    public static void queryVedioBeanAll() {

        BmobQuery<VedioBean> query = new BmobQuery<VedioBean>();
        //查询playerName叫“比目”的数据
        // query.addWhereEqualTo("playerName", "比目");
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        // query.setLimit(50);
        //执行查询方法
        query.findObjects(new FindListener<VedioBean>() {
            @Override
            public void done(List<VedioBean> object, BmobException e) {
                if (object != null) {
                    EventBus.getDefault().post(new VedioEvent(object));//发布事件
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }


    /**
     * 获取本地的所有视屏
     */
    public static List<VedioLocalBean> getLocalVedio(Context context) {

        List<VedioLocalBean> list = null;
        if (context != null) {
            list = new ArrayList<>();
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                //有sd卡的情况
                Cursor cursor = context.getContentResolver().query(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null,
                        null, null);

                while (cursor.moveToNext()) {
                    VedioLocalBean bean = new VedioLocalBean();
                    // 到视频文件的信息
                    String name = cursor.getString(cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));//得到视频的名字
                    bean.setName(name);
                    long size = cursor.getLong(cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));//得到视频的大小
                    bean.setSize(size);
                    long durantion = cursor.getLong(cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));//得到视频的时间长度
                    bean.setDuration(durantion);
                    String data = cursor.getString(cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));//得到视频的路径，可以转化为uri进行视频播放
                    bean.setData(data);
                    //使用静态方法获取视频的缩略图
                    Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(data, MediaStore.Video.Thumbnails.MINI_KIND);
                    bean.setThumbnail(thumbnail);
                    list.add(bean);
                }
            }
        }
        return list;
    }

    //Bitmap对象保存为图片文件
    public void saveBitmapFile(Bitmap bitmap) {
        File file = new File("/mnt/sdcard/pic/01.jpg");//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
