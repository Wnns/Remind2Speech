package wnns.remind2speech.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dominik on 2016-11-23.
 */

public class DBManager extends SQLiteOpenHelper {

    public DBManager(Context context) {
        super(context, "alarms.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table alarms(" +
                "alarmID integer primary key," +
                "alarmTitle text," +
                "alarmText text," +
                "alarmDate datetime);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addAlarm(String alarmTitle, String alarmText, Date alarmDate){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = simpleDateFormat.format(alarmDate);

        contentValues.put("alarmTitle", alarmTitle);
        contentValues.put("alarmText", alarmText);
        contentValues.put("alarmDate", formattedDate);

        sqLiteDatabase.insertOrThrow("alarms", null, contentValues);
    }

    public Cursor getAllAlarms(){

        String[] columns = {"alarmID", "alarmTitle", "alarmText", "alarmDate"};

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query("alarms", columns, null, null, null, null, "alarmDate DESC");
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor getAlarm(String alarmID){

        String[] columns = {"alarmID", "alarmTitle", "alarmText", "alarmDate"};

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query("alarms", columns, "alarmID = ?", new String[]{alarmID}, null, null, "alarmDate DESC");
        cursor.moveToFirst();
        return cursor;
    }

    public String getFreeID(){

        String[] columns = {"alarmID"};

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query("alarms", columns, null, null, null, null,"alarmID DESC", "1");

        if(cursor.getCount() == 0){

            return "1";
        }

        cursor.moveToFirst();

        String freeID = cursor.getString(cursor.getColumnIndex("alarmID"));
        return "" + (Integer.parseInt(freeID) + 1);
    }

    public void removeAlarm(String alarmID){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        sqLiteDatabase.delete("alarms", "alarmID = ?", new String[]{alarmID});
    }

}
