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
    public String email;
    public int userId;

    DatabaseHelper_Users_Merop sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    long Id=0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
        Intent intent0 = getIntent();
        email = intent0.getStringExtra("email");
        userId = intent0.getIntExtra("id", -2); // извлекаем значение id из intent

        nameBox = findViewById(R.id.name);
        vremaBox = findViewById(R.id.vrema);
        orgBox = findViewById(R.id.org);
        opisanieBox = findViewById(R.id.opis);
        delButton = findViewById(R.id.deleteButton);
        saveButton = findViewById(R.id.saveButton);

        sqlHelper = new DatabaseHelper_Users_Merop(this);
        db = sqlHelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Id = extras.getLong("id");
        }
        // если 0, то добавление
        if (Id > 0) {
            // получаем элемент по id из бд
            userCursor = db.rawQuery("select * from " + DatabaseHelper_Users_Merop.TABLE_M + " where " +
                    DatabaseHelper_Users_Merop.COLUMN_ID_MER + "=?", new String[]{String.valueOf(Id)});
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
        cv.put(DatabaseHelper_Users_Merop.COLUMN_NAME, nameBox.getText().toString());
        cv.put(DatabaseHelper_Users_Merop.COLUMN_VREMA, vremaBox.getText().toString());
        cv.put(DatabaseHelper_Users_Merop.COLUMN_ORG, orgBox.getText().toString());
        cv.put(DatabaseHelper_Users_Merop.COLUMN_OPISANIE, opisanieBox.getText().toString());

        if (Id > 0) {
            db.update(DatabaseHelper_Users_Merop.TABLE_M, cv, DatabaseHelper_Users_Merop.COLUMN_ID_MER + "=" + Id, null);
        } else {
            db.insert(DatabaseHelper_Users_Merop.TABLE_M, null, cv);
        }
        goHome();
    }
    public void delete(View view){
        db.delete(DatabaseHelper_Users_Merop.TABLE_M, DatabaseHelper_Users_Merop.COLUMN_ID_MER + "=?", new String[]{String.valueOf(Id)});
        goHome();
    }
    private void goHome(){
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent0 = new Intent(this, Main_Menu.class);
        intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent0.putExtra("email", email);
        intent0.putExtra("id", userId); // передаем id в intent0
        startActivity(intent0);
    }
}