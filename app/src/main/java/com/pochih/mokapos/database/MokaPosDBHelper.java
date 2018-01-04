package com.pochih.mokapos.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pochih.mokapos.database.model.ItemDAO;

/**
 * Created by A-Po on 2018/01/04.
 */

public class MokaPosDBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "mokapos.db";
    private static SQLiteDatabase database;

    public MokaPosDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public MokaPosDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                           int version) {
        super(context, name, factory, version);
    }

    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = new MokaPosDBHelper(context, DB_NAME, null, DB_VERSION).getWritableDatabase();
        }

        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ItemDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ItemDAO.TABLE_NAME);
        onCreate(db);
    }
}
