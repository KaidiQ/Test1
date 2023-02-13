package com.example.student;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

    private static final String DB_NAME = "student.db";
    private static final String DB_TABLE = "peopleinfo";
    private static final int DB_VERSION = 1;

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_BANJI = "banji";
    public static final String KEY_XUEHAO = "xuehao";
    public static final String KEY_XUEYUAN = "xueyuan";
    public static final String KEY_ZHENGZHI = "zhengzhi";

    private SQLiteDatabase db;
    private final Context context;
    private DBOpenHelper dbOpenHelper;

    public DBAdapter(Context _context) {
        context = _context;
    }

    public void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }
    //异常捕获
    public void open() throws SQLiteException {
        dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_VERSION);
        try {
            db = dbOpenHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbOpenHelper.getReadableDatabase();
        }
    }


    public long insert(People people) {
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_NAME, people.Name);
        newValues.put(KEY_BANJI, people.Banji);
        newValues.put(KEY_XUEHAO, people.Xuehao);
        newValues.put(KEY_XUEYUAN, people.xueyuan);
        newValues.put(KEY_ZHENGZHI, people.zhengzhi);

        return db.insert(DB_TABLE, null, newValues);
    }

    //查询所有的数据
    public People[] queryAllData() {
        Cursor results = db.query(DB_TABLE, new String[]{KEY_ID, KEY_NAME, KEY_BANJI, KEY_XUEHAO, KEY_XUEYUAN, KEY_ZHENGZHI},
                null, null, null, null, null);
        return ConvertToPeople(results);
    }

    //    查询单条数据
    public People[] queryOneData(long id) {
        Cursor results = db.query(DB_TABLE, new String[]{KEY_ID, KEY_NAME, KEY_BANJI, KEY_XUEHAO, KEY_XUEYUAN, KEY_ZHENGZHI},
                KEY_ID + "=" + id, null, null, null, null);
        return ConvertToPeople(results);
    }

    @SuppressLint("Range")
    private People[] ConvertToPeople(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        People[] peoples = new People[resultCounts];
        for (int i = 0; i < resultCounts; i++) {
            peoples[i] = new People();
            peoples[i].ID = cursor.getInt(0);
            peoples[i].Name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            peoples[i].Banji = cursor.getString(cursor.getColumnIndex(KEY_BANJI));
            peoples[i].Xuehao = cursor.getString(cursor.getColumnIndex(KEY_XUEHAO));
            peoples[i].xueyuan = cursor.getString(cursor.getColumnIndex(KEY_XUEYUAN));
            peoples[i].zhengzhi = cursor.getString(cursor.getColumnIndex(KEY_ZHENGZHI));
            cursor.moveToNext();
        }
        return peoples;
    }

    public long deleteAllData() {
        return db.delete(DB_TABLE, null, null);
    }

    public long deleteOneData(long id) {
        return db.delete(DB_TABLE, KEY_ID + "=" + id, null);
    }

    public long updateOneData(long id, People people) {
        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_NAME, people.Name);
        updateValues.put(KEY_BANJI, people.Banji);
        updateValues.put(KEY_XUEHAO, people.Xuehao);
        updateValues.put(KEY_XUEYUAN, people.xueyuan);
        updateValues.put(KEY_ZHENGZHI, people.zhengzhi);

        return db.update(DB_TABLE, updateValues, KEY_ID + "=" + id, null);
    }
    //创建数据库和数据表 监听设置在MainActivity中
    private static class DBOpenHelper extends SQLiteOpenHelper {

        public DBOpenHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }//构造函数

        private static final String DB_CREATE = "create table " +
                DB_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " +
                KEY_NAME + " text not null, " + KEY_BANJI + " text not null," + KEY_XUEHAO + " text not null," + KEY_XUEYUAN + " text not null," + KEY_ZHENGZHI + " text not null);";

        //表字段定义
        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
            _db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(_db);
        }
    }
}
