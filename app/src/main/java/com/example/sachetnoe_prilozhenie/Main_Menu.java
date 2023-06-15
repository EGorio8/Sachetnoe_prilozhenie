package com.example.sachetnoe_prilozhenie;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


public class Main_Menu extends AppCompatActivity {
    TextView textEmail;

    ListView userList;
    DatabaseHelper_Users_Merop databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        textEmail = findViewById(R.id.EmailText);

        userList = findViewById(R.id.list);
        Intent intent0 = getIntent();
        String email = intent0.getStringExtra("email");

        textEmail.setText(email);


        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        databaseHelper = new DatabaseHelper_Users_Merop(getApplicationContext());
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.getReadableDatabase();

        //получаем данные из бд в виде курсора
        userCursor = db.rawQuery("select * from " + DatabaseHelper_Users_Merop.TABLE_M, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DatabaseHelper_Users_Merop.COLUMN_NAME, DatabaseHelper_Users_Merop.COLUMN_VREMA, DatabaseHelper_Users_Merop.COLUMN_ORG, DatabaseHelper_Users_Merop.COLUMN_OPISANIE};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        userList.setAdapter(userAdapter);
    }

    // по нажатию на кнопку запускаем UserActivity для добавления данных

    public void home(View view) {
        Intent intent1 = new Intent(this, My_World.class);
        intent1.putExtra("email", textEmail.getText().toString());
        startActivity(intent1);
    }

    public void use(View view) {
        Intent intent1 = new Intent(this, Upravlat.class);
        intent1.putExtra("email", textEmail.getText().toString());
        startActivity(intent1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }
}