package com.example.sachetnoe_prilozhenie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    private EditText fioEditText, emailEditText, passwordEditText;
    private CheckBox statusCheckBox;
    private Switch PolSwitch;
    private DatabaseHelper_Users_Merop dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        dbHelper = new DatabaseHelper_Users_Merop(this);

        fioEditText = findViewById(R.id.org);
        emailEditText = findViewById(R.id.fioText);
        passwordEditText = findViewById(R.id.password);
        statusCheckBox = findViewById(R.id.statusCheckBox);
        PolSwitch = findViewById(R.id.polSwitch);

        // Устанавливаем мужской пол по умолчанию
        PolSwitch.setChecked(false);
    }

    public void savereg(View view) {
        // Получаем значения полей со страницы регистрации
        String fio = fioEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String status = "";
        int reating = 0;
        //здесь был Гиша

        if (statusCheckBox.isChecked()) {
            status = "Организатор";
        } else {
            status = "Волонтёр";
        }
        String pol = "";
        if (PolSwitch.isChecked()) {
            pol = "Женский";
        } else {
            pol = "Мужской";
        }

        // Проверяем, что все поля заполнены
        if (!fio.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper_Users_Merop.COLUMN_FIO, fio);
            values.put(DatabaseHelper_Users_Merop.COLUMN_EMAIL, email);
            values.put(DatabaseHelper_Users_Merop.COLUMN_PASSWORD, password);
            values.put(DatabaseHelper_Users_Merop.COLUMN_POL, pol);
            values.put(DatabaseHelper_Users_Merop.COLUMN_STATUS, status);
            values.put(DatabaseHelper_Users_Merop.COLUMN_REATING, reating);

            // Проверяем наличие пользователя с указанным email
            boolean exists = dbHelper.checkUser(email);
            while (exists) {
                // Этот email уже занят, запрашиваем у пользователя новый email
                Toast.makeText(this, "Пользователь с таким email уже существует", Toast.LENGTH_SHORT).show();
                emailEditText.requestFocus(); // устанавливаем фокус на поле ввода email
                return; // выходим из метода и ждем, пока пользователь введет уникальный email
            }

            // Добавляем нового пользователя в базу данных
            boolean success = dbHelper.insertData(email, password, fio, status, pol, reating);
            if (success) {
                Toast.makeText(this, "Пользователь зарегистрирован", Toast.LENGTH_SHORT).show();
                // Переходим на экран личного кабинета
                Intent intent = new Intent(this, Autor_Regis.class);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Ошибка при регистрации пользователя", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
        }
    }

    // закрываем базу данных при уничтожении активности
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}