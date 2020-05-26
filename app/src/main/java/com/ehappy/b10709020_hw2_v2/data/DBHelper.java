package com.ehappy.b10709020_hw2_v2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.ehappy.b10709020_hw2_v2.data.DBContract.*;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "list.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = "CREATE TABLE " + DBEntry.T_NAME + " (" +
                DBEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                DBEntry.COLUMN_PEOPLE + " INTEGER NOT NULL, " +
                DBEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        // COMPLETED (7) Execute the query by calling execSQL on sqLiteDatabase and pass the string query SQL_CREATE_WAITLIST_TABLE
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBEntry.T_NAME);
        onCreate(db);
    }
}
