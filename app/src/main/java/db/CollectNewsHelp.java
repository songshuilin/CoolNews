package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import constants.Constant;

/**
 * Created by Administrator on 2016/12/19.
 */

public class CollectNewsHelp extends SQLiteOpenHelper {

    //建表语句
    private String sql = "create table " + Constant.COLLECT_NEWS_TABLE
            + " ( id integer primary key autoincrement ," +
            " username ,title ,imgurl,url ) ";

    public CollectNewsHelp(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
