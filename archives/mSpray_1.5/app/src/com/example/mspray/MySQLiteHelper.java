package com.example.mspray;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Lars Vogel, copyright 2010, 2011, 2012, http://www.vogella.com/articles/AndroidSQLite/article.html
 * @author Daniel Wu, September 2012, a few revisions based on Lars Vogel's model
 * An Android SQLite class that provides an interface with an SQL database.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String SQLTABLE = "sqlDataTable";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_COMMENT = "sqlDataEntry";

    private static final String DATABASE_NAME = "sqlDataTable.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + SQLTABLE + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_COMMENT
            + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + SQLTABLE);
        onCreate(db);
    }

}
