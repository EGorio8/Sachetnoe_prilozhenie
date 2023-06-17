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

public class Upravlat_Red extends AppCompatActivity {

    TextView textEmail;
    public String fio_zav;
    public String fio;
    private LinearLayout layout;
    private int _id_mer;
    public int userId;

    @SuppressLint({"MissingInflatedId", "Range"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upravlat_red);

        textEmail = findViewById(R.id.EmailText);

        Intent intent0 = getIntent();
        String email = intent0.getStringExtra("email");
        _id_mer = getIntent().getIntExtra("_id_mer", -2);
        fio = intent0.getStringExtra("fio");
        userId = getIntent().getIntExtra("id", -2);

        textEmail.setText(email);

        // Находим родительский Layout
        layout = findViewById(R.id.leaner2);

        // Создание нового объекта DatabaseHelper
        DatabaseHelper_Users_Merop dbHelper = new DatabaseHelper_Users_Merop(this);

        // Получение курсора
        Cursor cursor = dbHelper.getDataZ();

        while (cursor.moveToNext()) {
            int id_mer = cursor.getInt(cursor.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_ID_MER_Z));
            String id_user = cursor.getString(cursor.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_ID_USER_Z));
            if (id_mer != 0 && id_mer == _id_mer) {
                // Получение ФИО пользователя с заданным id из таблицы пользователей
                String[] columns = {DatabaseHelper_Users_Merop.COLUMN_FIO};
                String selection = DatabaseHelper_Users_Merop.COLUMN_ID + "=?";
                String[] selectionArgs = { id_user };
                Cursor cursor2 = dbHelper.query(DatabaseHelper_Users_Merop.TABLE_U, columns, selection, selectionArgs, null, null, null);
                if (cursor2.moveToFirst()) {
                    int columnIndex = cursor2.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_FIO);
                    if (columnIndex > -1) {
                        fio_zav = cursor2.getString(columnIndex);
                    }
                }
                cursor2.close();

                // Создание нового TextView
                TextView textView = new TextView(this);
                textView.setText(fio_zav);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                textView.setTextColor(Color.WHITE);
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
        cursor.close();
        dbHelper.close();
    }

    public void uprav(View view) {
        Intent intent1 = new Intent(this, Upravlat.class);
        intent1.putExtra("email", textEmail.getText().toString());
        intent1.putExtra("fio", fio);
        intent1.putExtra("id", userId); // передаем id в intent0

        startActivity(intent1);
    }
}