package db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import model.CollectNewsBean;
import model.SearchHistoryBean;
import model.UserBean;

import static constants.Constant.COLLECT_NEWS_TABLE;
import static constants.Constant.SEARCH_HISTORY_TABLE;

/**
 * Created by Administrator on 2016/12/16.
 */

public class CollectNewsDao {

    /**
     * 插入一条数据
     *
     * @param db
     * @param bean
     */
    public static boolean insertCollectNews(SQLiteDatabase db, CollectNewsBean bean) {
        if (queryCollectNews(db, bean)) {
            return false;
        }
        ContentValues values = new ContentValues();
        values.put("username", bean.getUsername());
        values.put("url", bean.getUrl());
        values.put("title", bean.getTitle());
        values.put("imgurl", bean.getImgUrl());
        db.insert(COLLECT_NEWS_TABLE, null, values);
        return true;
    }

    /**
      * 更新用户
     * @param db
     * @param
     */
    public static void updateUsername(SQLiteDatabase db,String oldUsername,String newUsername){
        String sql="update "+COLLECT_NEWS_TABLE+" set username = '"+newUsername+"' where username = '"+oldUsername+"'";
        Log.i("TAG", "updateUsername: "+sql);
        db.execSQL(sql);
    }
    /**
     * 查询所有
     *
     * @param db
     */
    public static boolean queryCollectNews(SQLiteDatabase db, CollectNewsBean bean) {
        //1，ture 表示去重复，2, 表名。
        Cursor cursor = db.query(true, COLLECT_NEWS_TABLE, null, "url=? and username=? ", new String[]{bean.getUrl()
                , bean.getUsername()
        }, null, null, null, null);
        if (cursor.moveToNext()) {
            String url = cursor.getString(cursor.getColumnIndex("url"));
            return url != null ? true : false;
        }
        return false;
    }


    /**
     * 查询所有
     *
     * @param db
     */
    public static List<CollectNewsBean> queryCollectNewsAll(SQLiteDatabase db) {
        List<CollectNewsBean> list = new ArrayList<>();
        //1，ture 表示去重复，2, 表名。
        Cursor cursor = db.query(true, COLLECT_NEWS_TABLE, null, "username=?", new String[]{BmobUser.getCurrentUser(UserBean.class).getUsername()},
                null, null, null, null);
        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String imgurl = cursor.getString(cursor.getColumnIndex("imgurl"));

            CollectNewsBean bean = new CollectNewsBean(BmobUser.getCurrentUser(UserBean.class).getUsername(), url, imgurl, title);
            list.add(bean);

        }
        return list;
    }

    /**
     * 根据单个收藏删除
     *
     * @param db
     * @param bean
     * @return
     */
    public static boolean deleteCollectnews(SQLiteDatabase db, CollectNewsBean bean) {
        int count = db.delete(COLLECT_NEWS_TABLE, "url=?", new String[]{bean.getUrl()});
        if (count > 0) {
            return true;
        }
        return false;
    }

    /**
     * 删除所有
     *
     * @param db
     * @return
     */
    public static boolean deleteCollectNewsAll(SQLiteDatabase db) {
        int count = db.delete(COLLECT_NEWS_TABLE, null, null);
        if (count > 0) {
            return true;
        }
        return false;
    }
}
