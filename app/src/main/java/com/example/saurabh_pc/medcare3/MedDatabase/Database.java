package com.example.saurabh_pc.medcare3.MedDatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;


import com.example.saurabh_pc.medcare3.Model.Medicine;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="data.db";
    public static final String PATH="/data/data/com.example.saurabh_pc.medcare3/databases/";
    public static final int DB_VERSION=1;
    Context context;
    SQLiteDatabase database;

    public Database(Context context) {
        super(context, DATABASE_NAME,null,DB_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase()
    {
        String dbPath=context.getDatabasePath(DATABASE_NAME).getPath();
        if(database!=null && database.isOpen())
        {
            return ;
        }
        database=SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READWRITE);
    }
    public void closeDatabase()
    {
        if(database!=null)
            database.close();
    }
    public List<Medicine>getNames()
    {
        database=this.getReadableDatabase();
        Cursor cursor=database.rawQuery(" SELECT name From drug",null);
        List<Medicine> name= new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do {
                Medicine med=new Medicine();
                med.setName(cursor.getString(cursor.getColumnIndex("name")));
               // Log.d("DATABASE MEDICINE:",med.getName());
                name.add(med);
                //Log.d("Medicine Name",med.getName());
            }while (cursor.moveToNext());
        }
        return name;
    }
    public List<Medicine>getDetails(String name)
    {
        database=this.getReadableDatabase();
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
        qb.setTables("drug");
        String projectionIn[]={"name","about","use","how","side","precaution","interaction","missed","storage"};
        Cursor cursor=qb.query(database,projectionIn,"name LIKE ?",new String[]{"%"+name+"%"},null,null,null);

        List<Medicine>details= new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do
            {
                Medicine med= new Medicine();
                med.setName(cursor.getString(cursor.getColumnIndex("name")));
                med.setAbout(cursor.getString(cursor.getColumnIndex("about")));
                med.setUse(cursor.getString(cursor.getColumnIndex("use")));
                med.setSide(cursor.getString(cursor.getColumnIndex("side")));
                med.setHow(cursor.getString(cursor.getColumnIndex("how")));
                med.setPrecaution(cursor.getString(cursor.getColumnIndex("precaution")));
                med.setInteraction(cursor.getString(cursor.getColumnIndex("interaction")));
                med.setMiss(cursor.getString(cursor.getColumnIndex("missed")));
                med.setStorage(cursor.getString(cursor.getColumnIndex("storage")));
                details.add(med);
                Log.d("Details",med.getName()+med.getAbout()+med.getUse()+med.getSide()+med.getHow()+med.getInteraction()
                +med.getInteraction()+med.getMiss()+med.getStorage());
            }while (cursor.moveToNext());
        }
        return details;
    }
}
