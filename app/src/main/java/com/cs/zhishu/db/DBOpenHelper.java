package com.cs.zhishu.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by exbbefl on 7/15/2016.
 */
class DBOpenHelper extends SQLiteOpenHelper {
    private static DBOpenHelper instance;

    private DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, name, factory, version);
    }

    static DBOpenHelper getInstance(Context context) {

        if (instance == null) {
            synchronized (DBOpenHelper.class) {
                if (instance == null) {
                    instance = new DBOpenHelper(context.getApplicationContext(), DBConstant.DB_NAME, null, DBConstant.DB_VERSION);
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DBConstant.CREATE_TABLE_READ);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
