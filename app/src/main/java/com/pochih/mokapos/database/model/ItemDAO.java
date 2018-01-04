package com.pochih.mokapos.database.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pochih.mokapos.database.MokaPosDBHelper;
import com.pochih.mokapos.entity.Item;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by A-Po on 2018/01/04.
 */

public class ItemDAO {

    public static final String TABLE_NAME = "item";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ALBUM_ID = "albumId";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_THUMBNAIL_URL = "thumbnailUrl";
    public static final String COLUMN_PRICE = "price";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_ALBUM_ID + " INTEGER, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_URL + " TEXT, " +
                    COLUMN_THUMBNAIL_URL + " TEXT, " +
                    COLUMN_PRICE + " INTEGER)";

    private SQLiteDatabase db;

    public ItemDAO(Context context) {
        db = MokaPosDBHelper.getDatabase(context);
    }

    public void close() {
        db.close();
    }

    public void insert(Item item) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_ID, item.getId());
            cv.put(COLUMN_ALBUM_ID, item.getAlbumId());
            cv.put(COLUMN_TITLE, item.getTitle());
            cv.put(COLUMN_URL, item.getUrl());
            cv.put(COLUMN_THUMBNAIL_URL, item.getThumbnailUrl());
            cv.put(COLUMN_PRICE, item.getPrice());
            long id = db.insert(TABLE_NAME, null, cv);
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    public boolean delete(int id) {
        String where = COLUMN_ID + "=" + id;
        return db.delete(TABLE_NAME, where, null) > 0;
    }

    public List<Item> getAll() {
        List<Item> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        return result;
    }

    public Item getRecord(Cursor cursor) {
        Item result = new Item();
        result.setId(cursor.getInt(0));
        result.setAlbumId(cursor.getInt(1));
        result.setTitle(cursor.getString(2));
        result.setUrl(cursor.getString(3));
        result.setThumbnailUrl(cursor.getString(4));
        result.setPrice(cursor.getInt(5));
        return result;
    }
}
