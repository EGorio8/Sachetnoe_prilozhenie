package com.example.sachetnoe_prilozhenie;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper_Mer extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "merop.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_M = "merop";
    public static final String COLUMN_ID_MER = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_VREMA = "vreme";
    public static final String COLUMN_ORG = "org";
    public static final String COLUMN_OPISANIE = "opisanie";

    public DatabaseHelper_Mer(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_M + " (" +
                COLUMN_ID_MER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_VREMA + " TEXT, " +
                COLUMN_ORG + " TEXT, " +
                COLUMN_OPISANIE + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_M);
        onCreate(db);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_M, null);
    }
}