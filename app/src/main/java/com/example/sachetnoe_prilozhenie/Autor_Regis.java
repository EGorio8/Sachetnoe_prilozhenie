package com.example.sachetnoe_prilozhenie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Autor_Regis extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private DatabaseHelper_Users dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autor_regis);

        dbHelper = new DatabaseHelper_Users(this); // создание экземпляра класса DatabaseHelper

        emailEditText = findViewById(R.id.fioText);
        passwordEditText = findViewById(R.id.password);
    }

    // метод для выполнения входа в приложение
    public void enter(View view) {

        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if(!email.isEmpty() && !password.isEmpty()) { // проверка заполнения полей логина и пароля

            SQLiteDatabase db = dbHelper.getReadableDatabase(); // открытие базы данных в режиме только чтения

            String[] columns = {DatabaseHelper_Users.COLUMN_ID}; // массив столбцов для выборки
            String selection = DatabaseHelper_Users.COLUMN_EMAIL + "=? AND " + DatabaseHelper_Users.COLUMN_PASSWORD + "=?"; // условие выборки
            String[] selectionArgs = {email, password}; // аргументы условия выборки

            Cursor cursor = db.query(DatabaseHelper_Users.TABLE_U, columns, selection, selectionArgs, null, null, null); // выполнение выборки

            if(cursor != null && cursor.getCount() > 0) { // проверка наличия найденных записей в таблице пользователей
                Toast.makeText(this, "Вход выполнен", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Main_Menu.class); // переход на экран главного меню
                startActivity(intent); // запуск новой активности
                finish(); // закрываем текущую активити
                Intent intent0 = new Intent(this, Main_Menu.class);
                intent0.putExtra("email", emailEditText.getText().toString());
                startActivity(intent0); // запуск новой активности My_World
            } else {
                Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Пожалуйста, введите логин и пароль", Toast.LENGTH_SHORT).show();
        }
    }

    public void reg(View view) {
        Intent intent = new Intent(this, Registration.class); // переход на экран регистрации нового пользователя
        startActivity(intent); // запуск новой активности
    }

    // если приложение вернется к этой активити из другой, например, когда пользователь нажмет на кнопку "назад"
    @Override
    protected void onResume() {
        super.onResume();

        emailEditText.setText(""); // очищаем поля ввода логина и пароля
        passwordEditText.setText("");
    }

    // закрываем базу данных при уничтожении активности
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close(); // закрытие базы данных
    }
}