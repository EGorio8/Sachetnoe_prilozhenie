package com.example.sachetnoe_prilozhenie;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper_Users_Merop extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_U = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIO = "fio";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_POL = "pol";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_REATING = "reating";
    public static final String COLUMN_EMAIL = "email";

    public static final String TABLE_M = "merop";
    public static final String COLUMN_ID_MER = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_VREMA = "vreme";
    public static final String COLUMN_ORG = "org";
    public static final String COLUMN_OPISANIE = "opisanie";


    public DatabaseHelper_Users_Merop(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_U + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIO + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_STATUS + " TEXT, " +
                COLUMN_EMAIL + " TEXT ," +
                COLUMN_POL + " TEXT, " +
                COLUMN_REATING + " INT"
                + ")");

        db.execSQL("CREATE TABLE " + TABLE_M + " (" +
                COLUMN_ID_MER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_VREMA + " TEXT, " +
                COLUMN_ORG + " TEXT, " +
                COLUMN_OPISANIE + " TEXT"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_U);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_U);
        onCreate(db);
    }

    public Cursor query(String tableName, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy);
        return cursor;
    }

    public boolean checkUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_U,
                new String[]{COLUMN_ID},
                COLUMN_EMAIL + "=?",
                new String[]{email},
                null, null, null, null);
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public boolean insertData(String email, String password, String fio, String status, String pol, int reating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_FIO, fio);
        contentValues.put(COLUMN_STATUS, status);
        contentValues.put(COLUMN_POL, pol);
        contentValues.put(COLUMN_REATING, reating);
        long result = db.insert(TABLE_U, null, contentValues);
        return result != -1;
    }

    public int update(String table, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = this.getWritableDatabase();
        int count = db.update(table, values, selection, selectionArgs);
        db.close();
        return count;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_M, null);
    }

}