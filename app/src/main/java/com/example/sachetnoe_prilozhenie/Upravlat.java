package com.example.sachetnoe_prilozhenie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Upravlat extends AppCompatActivity {
    TextView textEmail;

    private LinearLayout layout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upravlat);
        textEmail = findViewById(R.id.EmailText);

        // Находим родительский Layout
        layout = findViewById(R.id.leaner);

        // Создание нового объекта DatabaseHelper
        DatabaseHelper_Users dbHelper = new DatabaseHelper_Users(this);

        // Получение курсора
        Cursor cursor = dbHelper.getData();
        Intent intent1 = getIntent();
        String email = intent1.getStringExtra("email");

        textEmail.setText(email);

        while (cursor.moveToNext()) {
            // Получение данных из курсора
            @SuppressLint("Range") int _id_mer = cursor.getInt(cursor.getColumnIndex(DatabaseHelper_Users.COLUMN_ID_MER));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper_Users.COLUMN_NAME));
            @SuppressLint("Range") String vrema = cursor.getString(cursor.getColumnIndex(DatabaseHelper_Users.COLUMN_VREMA));
            @SuppressLint("Range") String fio = cursor.getString(cursor.getColumnIndex(DatabaseHelper_Users.COLUMN_ORG));
            @SuppressLint("Range") String opisanie = cursor.getString(cursor.getColumnIndex(DatabaseHelper_Users.COLUMN_OPISANIE));

            // Создание нового TextView
            TextView textView = new TextView(this);

            int r = (int) (Math.random() * 100);
            int g = (int) (Math.random() * 100);
            int b = (int) (Math.random() * 150);

            // Установка цвета фона
            textView.setBackgroundColor(Color.rgb(r, g, b));

            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            textView.setTextColor(Color.WHITE);

            textView.setId(_id_mer); // Установка идентификатора для TextView
            textView.setText(_id_mer + ")" + " МЕРОПРИЯТИЕ: " + "\n" + name + "\n" + "ДАТА: " + vrema + "\n" + "ФИО ОРГАНИЗАТОРА: " + "\n" + fio + "\n" + "ОПИСАНИЕ: " + "\n" + opisanie);

            // Настройка параметров макета для каждого TextView
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 16, 0, 0);
            textView.setLayoutParams(params);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Действия при нажатии на TextView
                    Intent intent = new Intent(getApplicationContext(), Upravlat_Red.class);
                    intent.putExtra("_id_mer", _id_mer);
                    startActivity(intent);
                }
            });


            // Добавление TextView в родительский Layout
            layout.addView(textView);
        }

        // Закрытие курсора и базы данных
        cursor.close();
        dbHelper.close();
    }

    public void upr(View view) {
        Intent intent = new Intent(this, Upravlat_Red.class);
        startActivity(intent);
    }

    public void add(View view) {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    public void menu(View view) {
        Intent intent0 = new Intent(this, Main_Menu.class);
        intent0.putExtra("email", textEmail.getText().toString());
        startActivity(intent0);
    }
}