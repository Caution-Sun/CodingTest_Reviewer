package com.example.codingtest_reviewe;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class TaskDatabase {
    private static final String TAG = "TaskDatabase";

    private static TaskDatabase database;

    public static String DATABASE_NAME = "task.db";
    public static String TABLE_USERS = "USERS";
    public static int DATABASE_VERSION = 1;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;

    private TaskDatabase(Context context){
        this.context = context;
    }

    public static TaskDatabase getInstance(Context context){
        if(database == null){
            database = new TaskDatabase(context);
        }

        return database;
    }

    public boolean open() {
        Log.d(TAG,"opening database [" + DATABASE_NAME + "]");

        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

        return true;
    }

    public void close() {
        Log.d(TAG,"closing database [" + DATABASE_NAME + "]");

        db.close();

        database = null;
    }

    public Cursor rawQuery(String SQL){
        Log.d(TAG, "executeQuery called");

        Cursor cursor = null;

        try {
            cursor = db.rawQuery(SQL, null);
            Log.d(TAG, "Cursor count : " + cursor.getCount());
        }catch (Exception ex){
            Log.e(TAG, "Exception in excuteQuery", ex);
        }

        return cursor;
    }

    public boolean execSQL(String SQL){
        Log.d(TAG, "execute called");

        try {
            Log.d(TAG, "SQL : " + SQL);
            db.execSQL(SQL);
        }catch (Exception ex){
            Log.e(TAG, "Exception in excuteQuery", ex);
            return false;
        }

        return true;
    }

    private class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(TAG, "creating database [" + DATABASE_NAME + "]");

            Log.d(TAG, "creating table [" + TABLE_USERS + "]");

            String DROP_SQL = "drop table if exists " + TABLE_USERS;
            try{
                db.execSQL(DROP_SQL);
            }catch (Exception ex){
                Log.e(TAG, "Exception in DROP_SQL", ex);
            }

            String CREATE_SQL = "CREATE TABLE " + TABLE_USERS + "("
                    + " _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + " NAME TASK DEFAULT '', "
                    + " DATE TEXT DEFAULT '' "
                    + ")";
            try{
                db.execSQL(CREATE_SQL);
            } catch (Exception ex){
                Log.e(TAG, "Exception in CREATE_SQL", ex);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(TAG, "Upgrading database from vestion " + oldVersion + " to " + newVersion + ".");
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            Log.d(TAG, "opened database [" + DATABASE_NAME + "]");
        }
    }
}