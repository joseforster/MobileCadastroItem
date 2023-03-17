package com.example.unidade3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import model.ItemModel;

public class MeuSQLite extends SQLiteOpenHelper {

    public MeuSQLite(Context context, String dbname){
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create table if not exists item " +
                "      (id integer primary key autoincrement, descricao text, quantidade integer);" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
