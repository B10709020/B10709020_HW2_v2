package com.ehappy.b10709020_hw2_v2.data;


import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.ehappy.b10709020_hw2_v2.data.DBContract.*;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {
    public static void insertFakeData(SQLiteDatabase db){
        if(db == null){
            return;
        }
        //create a list of fake guests
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(DBEntry.COLUMN_NAME, "John");
        cv.put(DBEntry.COLUMN_PEOPLE, 12);
        list.add(cv);

        cv = new ContentValues();
        cv.put(DBEntry.COLUMN_NAME, "Tim");
        cv.put(DBEntry.COLUMN_PEOPLE, 2);
        list.add(cv);

        cv = new ContentValues();
        cv.put(DBEntry.COLUMN_NAME, "Jessica");
        cv.put(DBEntry.COLUMN_PEOPLE, 99);
        list.add(cv);

        cv = new ContentValues();
        cv.put(DBEntry.COLUMN_NAME, "Larry");
        cv.put(DBEntry.COLUMN_PEOPLE, 1);
        list.add(cv);

        cv = new ContentValues();
        cv.put(DBEntry.COLUMN_NAME, "Kim");
        cv.put(DBEntry.COLUMN_PEOPLE, 45);
        list.add(cv);

        //insert all guests in one transaction
        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (DBEntry.T_NAME,null,null);
            //go through the list and add one by one
            for(ContentValues c:list){
                db.insert(DBEntry.T_NAME, null, c);
            }
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            //too bad :(
        }
        finally
        {
            db.endTransaction();
        }
    }
}
