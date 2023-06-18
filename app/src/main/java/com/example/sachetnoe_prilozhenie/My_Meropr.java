package com.example.sachetnoe_prilozhenie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class My_Meropr extends AppCompatActivity {

    private LinearLayout layout;
    private int userId;
    @SuppressLint("MissingInflatedId")
    @Override // Переопределение родительского метода
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Создание активности
        setContentView(R.layout.my_meropr); // Устанавливаем макет пользовательского интерфейса "my_meropr" для этой активности

        // Находим родительский Layout
        layout = findViewById(R.id.leaner);

        // Создание нового объекта DatabaseHelper
        DatabaseHelper_Users_Merop dbHelper = new DatabaseHelper_Users_Merop(this);

        Intent intent0 = getIntent();
        userId = intent0.getIntExtra("id", -2);

        // Получение курсора
        Cursor cursor = dbHelper.getDataUCH();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int _id_mer = cursor.getInt(cursor.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_ID_MER_UCH));
            @SuppressLint("Range") int id_user = cursor.getInt(cursor.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_ID_USER_UCH));
            @SuppressLint("Range") int id_uch = cursor.getInt(cursor.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_ID_UCH));
            if (userId != 0 && userId == id_user) {
                Cursor cursor2 = dbHelper.getData();
                while (cursor2.moveToNext()) {
                    @SuppressLint("Range") int id_mer = cursor2.getInt(cursor2.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_ID_MER));
                    @SuppressLint("Range") String name = cursor2.getString(cursor2.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_NAME));
                    @SuppressLint("Range") String vrema = cursor2.getString(cursor2.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_VREMA));
                    @SuppressLint("Range") String org = cursor2.getString(cursor2.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_ORG));
                    @SuppressLint("Range") String opisanie = cursor2.getString(cursor2.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_OPISANIE));
                    if (_id_mer != 0 && _id_mer == id_mer){
                        // Создание нового TextView
                        TextView textView = new TextView(this);
                        // Установка цвета фона
                        int r = (int) (Math.random() * 100);
                        int g = (int) (Math.random() * 100);
                        int b = (int) (Math.random() * 150);
                        textView.setBackgroundColor(Color.rgb(r, g, b));
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        textView.setTextColor(Color.WHITE);
                        textView.setId(_id_mer); // Установка идентификатора для TextView
                        textView.setText(_id_mer + ")" + " МЕРОПРИЯТИЕ: " + "\n" + name + "\n" + "ДАТА: " + vrema + "\n" + "ФИО ОРГАНИЗАТОРА: " + "\n" + org + "\n" + "ОПИСАНИЕ: " + "\n" + opisanie);
                        // Настройка параметров макета для каждого TextView
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(0, 16, 0, 0);
                        textView.setLayoutParams(params);

                        // Добавление TextView в родительский Layout
                        layout.addView(textView);
                    }
                }
                // Закрытие курсора и базы данных
                cursor2.close();
                dbHelper.close();
            }
        }
        // Закрытие курсора и базы данных
        cursor.close();
        dbHelper.close();
    }

    public void onBackPressed(View view) { // Создаём метод для обработки события нажатия кнопки возвращения на предыдущую активность
        super.onBackPressed(); // Возвращаемся к предыдущей активности
    }
}
