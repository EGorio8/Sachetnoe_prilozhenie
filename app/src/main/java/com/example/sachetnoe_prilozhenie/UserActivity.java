package com.example.sachetnoe_prilozhenie;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    EditText nameBox;
    EditText vremaBox;
    EditText orgBox;
    MultiAutoCompleteTextView opisanieBox;
    Button delButton;
    Button saveButton;

    DatabaseHelper_Users sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    long userId=0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);

        nameBox = findViewById(R.id.name);
        vremaBox = findViewById(R.id.vrema);
        orgBox = findViewById(R.id.org);
        opisanieBox = findViewById(R.id.opis);
        delButton = findViewById(R.id.deleteButton);
        saveButton = findViewById(R.id.saveButton);

        sqlHelper = new DatabaseHelper_Users(this);
        db = sqlHelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }
        // если 0, то добавление
        if (userId > 0) {
            // получаем элемент по id из бд
            userCursor = db.rawQuery("select * from " + DatabaseHelper_Users.TABLE_M + " where " +
                    DatabaseHelper_Users.COLUMN_ID_MER + "=?", new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();
            nameBox.setText(userCursor.getString(1));
            vremaBox.setText(userCursor.getString(2));
            orgBox.setText(userCursor.getString(3));
            opisanieBox.setText(userCursor.getString(4));
            userCursor.close();
        } else {
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);
        }
    }

    public void save(View view){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper_Users.COLUMN_NAME, nameBox.getText().toString());
        cv.put(DatabaseHelper_Users.COLUMN_VREMA, vremaBox.getText().toString());
        cv.put(DatabaseHelper_Users.COLUMN_ORG, orgBox.getText().toString());
        cv.put(DatabaseHelper_Users.COLUMN_OPISANIE, opisanieBox.getText().toString());
        //cv.put(DatabaseHelper.COLUMN_VREMA, Integer.parseInt(vremaBox.getText().toString()));

        if (userId > 0) {
            db.update(DatabaseHelper_Users.TABLE_M, cv, DatabaseHelper_Users.COLUMN_ID_MER + "=" + userId, null);
        } else {
            db.insert(DatabaseHelper_Users.TABLE_M, null, cv);
        }
        goHome();
    }
    public void delete(View view){
        db.delete(DatabaseHelper_Users.TABLE_M, DatabaseHelper_Users.COLUMN_ID_MER + "=?", new String[]{String.valueOf(userId)});
        goHome();
    }
    private void goHome(){
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent = new Intent(this, Upravlat.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}