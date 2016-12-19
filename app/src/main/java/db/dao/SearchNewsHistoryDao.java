package db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import model.SearchHistoryBean;

import static constants.Constant.SEARCH_HISTORY_TABLE;

/**
 * Created by Administrator on 2016/12/16.
 */

public class SearchNewsHistoryDao {


    /**
     * 插入一条数据
     *
     * @param db
     * @param bean
     */
    public static boolean insertSearchNews(SQLiteDatabase db, SearchHistoryBean bean) {
          if (querySearchNews(db,bean)){
              ContentValues values=new ContentValues();
              values.put("key",bean.getKey());
              values.put("time",bean.getTime());
              db.update(SEARCH_HISTORY_TABLE,values,"key=?",new String[]{bean.getKey()});
              return false;
          }
        ContentValues values = new ContentValues();
        values.put("key", bean.getKey());
        db.insert(SEARCH_HISTORY_TABLE, null, values);
        return true;
    }

    /**
     * 查询所有
     *
     * @param db
     */
    public static boolean querySearchNews(SQLiteDatabase db,SearchHistoryBean bean) {
        //1，ture 表示去重复，2, 表名。
        Cursor cursor = db.query(true, SEARCH_HISTORY_TABLE, null, "key=?", new String[]{bean.getKey()}, null, null,null, null);
        if (cursor.moveToNext()){
           String key= cursor.getString(cursor.getColumnIndex("key"));
            Log.i("TAGrr", "querySearchNews: "+key);
            return key!=null?true:false;
        }
        return false;
    }


    /**
     * 查询所有
     *
     * @param db
     */
    public static List<SearchHistoryBean> querySearchNewsAll(SQLiteDatabase db) {
        List<SearchHistoryBean> list = new ArrayList<>();
        //1，ture 表示去重复，2, 表名。
        Cursor cursor = db.query(true, SEARCH_HISTORY_TABLE, null, null, null, null, null, "time", null);
        while (cursor.moveToNext()) {
           String key= cursor.getString(cursor.getColumnIndex("key"));
            int time=cursor.getInt(cursor.getColumnIndex("time"));
            SearchHistoryBean bean=new SearchHistoryBean(key,time);
            list.add(bean);
        }
        return list;
    }

    /**
     * 根据单个搜索历史删除
     *
     * @param db
     * @param bean
     * @return
     */
    public static boolean deleteSearchnews(SQLiteDatabase db, SearchHistoryBean bean) {

        int count = db.delete(SEARCH_HISTORY_TABLE, "key=?", new String[]{bean.getKey()});
        if (count > 0) {
            return true;
        }
        return false;
    }

    /**
     * 删除所有
     * @param db
     * @return
     */
    public static boolean deleteSearchNewsAll(SQLiteDatabase db) {

        int count = db.delete(SEARCH_HISTORY_TABLE, null, null);
        if (count > 0) {
            return true;
        }
        return false;
    }
}
