package com.example.saurabh_pc.medcare3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class imageDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "REPORTS.db";
    public static final String TABLE_NAME = "imageTable";

    public static final String COL_PRES_1 = "user_id";
    public static final String COL_PRES_2 = "date";
    public static final String COL_PRES_3 = "image";

    public imageDbHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRESCRIPTION_TABLE ="CREATE TABLE "+ TABLE_NAME +"("+COL_PRES_1+" TEXT," +
                COL_PRES_2 + " TEXT," + COL_PRES_3 + " BLOB" + ")";
        db.execSQL(CREATE_PRESCRIPTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME );
        onCreate(db);
    }

    public boolean insertImage(String userId, String date, byte[] img){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PRES_1, userId);
        contentValues.put(COL_PRES_2, date);
        contentValues.put(COL_PRES_3, img);
        long result = db.insert(TABLE_NAME,null,contentValues);

        if(result!=-1)
            return true;
        else
            return false;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        return res;
    }

    public Cursor searchdata(String userid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery(" SELECT * FROM "+ TABLE_NAME +" WHERE "+ COL_PRES_1 +" LIKE "+ " '"+ userid +"' ",null);
        return cursor;
    }

    public void ResetTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME); //delete all rows in a table
        db.close();
    }
}
