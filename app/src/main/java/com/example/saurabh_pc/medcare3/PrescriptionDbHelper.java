package com.example.saurabh_pc.medcare3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PrescriptionDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "medical_records.db";
    public static final String TABLE_NAME = "prescription_table";

    public static final String COL_PRES_1 = "id";
    public static final String COL_PRES_2 = "user_id";
    public static final String COL_PRES_3 = "date";
    public static final String COL_PRES_4 = "diagnosed_with";
    public static final String COL_PRES_5 = "blood_pressure";
    public static final String COL_PRES_6 = "things_to_follow";
    public static final String COL_PRES_7 = "physician_name";
    public static final String COL_PRES_8 = "registration_number";
    public static final String COL_PRES_9 = "drug_and_dosage";

    public PrescriptionDbHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRESCRIPTION_TABLE ="CREATE TABLE "+ TABLE_NAME +"("+COL_PRES_1+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_PRES_2 + " TEXT," + COL_PRES_3 + " TEXT," + COL_PRES_4 + " TEXT," + COL_PRES_5 + " TEXT," + COL_PRES_6 + " TEXT,"
                + COL_PRES_7 + " TEXT," + COL_PRES_8 + " TEXT," + COL_PRES_9 + " TEXT" + ")";
        db.execSQL(CREATE_PRESCRIPTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME );
        onCreate(db);
    }

    public boolean insertPresData(String user, String date, String diagnosed_with, String blood_pressure, String thingsfollow,
                                  String physicianName, String regNumber,String drug1,String drug2,String drug3,String drug4,String drug5){

        SQLiteDatabase db = this.getWritableDatabase();
        String drug = drug1+";"+drug2+";"+drug3+";"+drug4+";"+drug5;
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PRES_2,user);
        contentValues.put(COL_PRES_3,date);
        contentValues.put(COL_PRES_4,diagnosed_with);
        contentValues.put(COL_PRES_5,blood_pressure);
        contentValues.put(COL_PRES_6,thingsfollow);
        contentValues.put(COL_PRES_7,physicianName);
        contentValues.put(COL_PRES_8,regNumber);
        contentValues.put(COL_PRES_9,drug);
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

    public Cursor searchdata(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery(" SELECT * FROM "+ TABLE_NAME +" WHERE "+ COL_PRES_2 +" LIKE "+ " '"+ name +"' ",null);
        return cursor;
    }
}
