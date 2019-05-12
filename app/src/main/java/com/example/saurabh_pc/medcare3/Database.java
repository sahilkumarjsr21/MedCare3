package com.example.saurabh_pc.medcare3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper
{

    public static String DatabaseName="data.db";
    public static final String PATH="/data/data/com.example.saurabh_pc.medcare3/databases/";
    public static int Version=1;
    Context context;
    public Database(Context context) {
        super(context, DatabaseName, null , Version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void onOPenDatabase()
    {
        SQLiteDatabase database=this.getReadableDatabase();
        String dbPath=context.getDatabasePath(DatabaseName).getPath();
        if(database!=null && database.isOpen())
        {
            return;
        }
        database=SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READWRITE);
    }
    public void closeDatabase()
    {
        SQLiteDatabase database= this.getReadableDatabase();
        if(database!=null)
        {
            database.close();
        }
    }
    public List<FirstAid> getNames()
    {
        SQLiteDatabase database= this.getReadableDatabase();
        Cursor cursor=database.rawQuery("SELECT name FROM first",null);
        List<FirstAid> name= new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do {
                FirstAid aid= new FirstAid();
                aid.setName(cursor.getString(cursor.getColumnIndex("name")));
                name.add(aid);
            }while(cursor.moveToNext());
        }
        return name;
    }
    public String getFileName(String name)
    {
        SQLiteDatabase database= this.getReadableDatabase();
        SQLiteQueryBuilder qb= new SQLiteQueryBuilder();
        qb.setTables("first");
        String fileName="";
        Cursor cursor=qb.query(database,new String[]{"file"},"name like ?",new String[]{"%"+name+"%"},null,null,null );
        if(cursor.moveToFirst())
        {
            do {
                FirstAid aid= new FirstAid();
                aid.setFile(cursor.getString(cursor.getColumnIndex("file")));
                fileName=aid.getFile();
            }while (cursor.moveToNext());
        }
        return fileName;
    }


}
