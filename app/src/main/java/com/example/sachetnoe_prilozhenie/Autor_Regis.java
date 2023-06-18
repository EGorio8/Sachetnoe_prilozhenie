package com.example.sachetnoe_prilozhenie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

// Класс для авторизации пользователей
public class Autor_Regis extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private DatabaseHelper_Users_Merop dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autor_regis);

        dbHelper = new DatabaseHelper_Users_Merop(this); // Создание экземпляра класса DatabaseHelper

        emailEditText = findViewById(R.id.fioText);
        passwordEditText = findViewById(R.id.password);
    }

    // Метод для выполнения входа в приложение
    public void enter(View view) {

        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if(!email.isEmpty() && !password.isEmpty()) { // Проверка заполнения полей логина и пароля

            SQLiteDatabase db = dbHelper.getReadableDatabase(); // Открытие базы данных в режиме только чтения

            String[] columns = {DatabaseHelper_Users_Merop.COLUMN_ID}; // Массив столбцов для выборки
            String selection = DatabaseHelper_Users_Merop.COLUMN_EMAIL + "=? AND " + DatabaseHelper_Users_Merop.COLUMN_PASSWORD + "=?"; // Условие выборки
            String[] selectionArgs = {email, password}; // Аргументы условия выборки

            Cursor cursor = db.query(DatabaseHelper_Users_Merop.TABLE_U, columns, selection, selectionArgs, null, null, null); // выполнение выборки

            if(cursor != null && cursor.getCount() > 0) { // Проверка наличия найденных записей в таблице пользователей
                Toast.makeText(this, "Вход выполнен", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Main_Menu.class); // Переход на экран главного меню
                startActivity(intent); // Запуск новой активности
                finish(); // Закрываем текущую активити
                Intent intent0 = new Intent(this, My_World.class); // Переход на экран личного кабинета
                intent0.putExtra("email", emailEditText.getText().toString()); // Добавляет данные почты для экрана личного кабинета
                startActivity(intent0); // Запуск новой активности My_World
            } else {
                Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show(); // Вывод сообщения об ошибке
            }

        } else {
            Toast.makeText(this, "Пожалуйста, введите логин и пароль", Toast.LENGTH_SHORT).show(); // Вывод сообщения об ошибке
        }
    }

    // Метод, вызываемый при нажатии на кнопку регистрации
    public void reg(View view) {
        Intent intent = new Intent(this, Registration.class); // Переход на экран регистрации нового пользователя
        startActivity(intent); // Запуск новой активности
    }

    // Если приложение вернется к этой активити из другой, например, когда пользователь нажмет на кнопку "назад"
    @Override
    protected void onResume() {
        super.onResume(); // Возобновляем активность

        emailEditText.setText(""); // Очищаем поля ввода логина
        passwordEditText.setText(""); // Очищаем поля ввода пароля
    }

    // Закрываем базу данных при уничтожении активности
    @Override // Переопределение родительского метода
    protected void onDestroy() {
        super.onDestroy(); // Закрываем активити
        dbHelper.close(); // Закрытие соединения с базой данных для освобождения ресурсов и избежания потенциальных проблем с безопасностью.
    }
}
