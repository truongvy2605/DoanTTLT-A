package com.com.flag;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "flag.db"; //Tên Database
    private static final int DATABASE_VERSION = 1; //Version của Database

    private static String TABLE_NAME = "flag"; //Tên bảng
    private static String COLUMN_ID = "flag"; //Tên cột id
    private static String COLUMN_NAME = "flag"; //Tên cột Name
    private static String COLUMN_IMAGE = "flag"; // Tên cột image

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tạo Bảng
        String script = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT,"
                + COLUMN_IMAGE + " TEXT" + ")";
        db.execSQL(script);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // xóa bảng cũ nếu tồn tại
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // và tạo lại
        onCreate(db);
    }

    // thêm 1 record vào database

    public void addFlag(Flag flag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, flag.getName());
        values.put(COLUMN_IMAGE, flag.getImage());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // lấy 1 record từ database
    public Flag getFlag(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_ID,
                        COLUMN_NAME, COLUMN_IMAGE }, COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Flag flag = new Flag(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        return flag;
    }

    // lấy tất cả record trong database
    public List<Flag> getAllFlags() {
        List<Flag> flagList = new ArrayList<Flag>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Flag flag = new Flag();
                flag.setId(Integer.parseInt(cursor.getString(0)));
                flag.setName(cursor.getString(1));
                flag.setImage(cursor.getString(2));
                flagList.add(flag);
            } while (cursor.moveToNext());
        }
        return flagList;
    }

    // lấy số lượng record trong database
    public int getFlagsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    // cập nhật 1 record trong database
    public int updateFlag(Flag flag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, flag.getName());
        values.put(COLUMN_IMAGE, flag.getImage());
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[] { String.valueOf(flag.getId()) });
    }

    // xóa 1 record trong database
    public void deleteFlag(Flag flag) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[] { String.valueOf(flag.getId()) });
        db.close();
    }

    // xóa tất cả record trong database
    public void deleteAllFlags() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}
