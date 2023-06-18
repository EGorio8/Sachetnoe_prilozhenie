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

// Создание класса для регистрации пользователя
public class Registration extends AppCompatActivity {

    private EditText fioEditText, emailEditText, passwordEditText; // Переменные для ФИО, логина и пароля
    private CheckBox statusCheckBox; // Переменная для статуса
    private Switch PolSwitch; // Переменная дла пола
    private DatabaseHelper_Users_Merop dbHelper; // Объект для создания и управления базой данных SQLite

    @SuppressLint("MissingInflatedId") // Заглушка для предупреждения android-studio
    @Override // Переопределение родительского метода
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Создание активности
        setContentView(R.layout.registration); // Устанавливаем макет пользовательского интерфейса "user_activity" для этой активности

        dbHelper = new DatabaseHelper_Users_Merop(this); // Создание объекта dbHelper класса DatabaseHelper_Users_Merop

        // Получаем соответствующие формы ввода из XML 
        fioEditText = findViewById(R.id.org); ; // Связываем переменную ФИО с текстовым полем для ввода ФИО
        emailEditText = findViewById(R.id.fioText); ; // Связываем переменную логина с текстовым полем для ввода логина
        passwordEditText = findViewById(R.id.password); ; // Связываем переменную пароля с текстовым полем для ввода пароля
        statusCheckBox = findViewById(R.id.statusCheckBox); // Связываем переменную статуса полем для ввода статуса
        PolSwitch = findViewById(R.id.polSwitch); // Связываем переменную пола полем для ввода пола

        PolSwitch.setChecked(false); // Мужской пол по умолчанию
    }

    public void savereg(View view) {
        String fio = fioEditText.getText().toString().trim(); // Получаем значение ФИО пользователя из соответствующего текстового поля
        String email = emailEditText.getText().toString().trim(); // Получаем значение логина пользователя из соответствующего текстового поля
        String password = passwordEditText.getText().toString().trim(); // Получаем значение пароля пользователя из соответствующего текстового поля
        String status = ""; // Оставляем статус пустым
        int reating = 0; // Задаём изначальное количество рейтинга пользователя

        if (statusCheckBox.isChecked()) { // Проверяем состояние флажка статуса
            status = "Организатор"; // Присвоение статуса согласно значению флажка
        } else {
            status = "Волонтёр"; // Присвоение статуса согласно значению флажка
        }
        String pol = ""; // Переменная пола пользователя
        if (PolSwitch.isChecked()) { // Проверяем состояние тоггл-кнопки пола
            pol = "Женский"; // Присвоение статуса согласно значению кнопки
        } else {
            pol = "Мужской";// Присвоение статуса согласно значению кнопки
        }

        // Проверяем, что все текстовые поля заполнены
        if (!fio.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper_Users_Merop.COLUMN_FIO, fio);
            values.put(DatabaseHelper_Users_Merop.COLUMN_EMAIL, email);
            values.put(DatabaseHelper_Users_Merop.COLUMN_PASSWORD, password);
            values.put(DatabaseHelper_Users_Merop.COLUMN_POL, pol);
            values.put(DatabaseHelper_Users_Merop.COLUMN_STATUS, status);
            values.put(DatabaseHelper_Users_Merop.COLUMN_REATING, reating);

            // Проверяем уникальность email
            boolean exists = dbHelper.checkUser(email);
            while (exists) {
                // Если этот email уже занят, запрашиваем у пользователя новый email
                Toast.makeText(this, "Пользователь с таким email уже существует", Toast.LENGTH_SHORT).show();
                emailEditText.requestFocus(); // Устанавливаем фокус на поле ввода email
                return; // Выходим из метода и ждем, пока пользователь введет уникальный email
            }

            // Добавляем нового пользователя в базу данных
            boolean success = dbHelper.insertData(email, password, fio, status, pol, reating);
            if (success) { // Если регистрация прошла успешно, то
                Toast.makeText(this, "Пользователь зарегистрирован", Toast.LENGTH_SHORT).show(); //  Уведомляем об этом пользователя
                Intent intent = new Intent(this, Autor_Regis.class); // Переходим на экран личного кабинета
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Ошибка при регистрации пользователя", Toast.LENGTH_SHORT).show(); // Вывод сообщения об ошибке
            }
        } else {
            Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show(); // Вывод сообщения об ошибке
        }
    }

    // Закрываем базу данных при уничтожении активности
    @Override  // Переопределение родительского метода
    protected void onDestroy() {
        super.onDestroy(); // Закрываем активность
        dbHelper.close(); // Закрываем соединения с базой данных для освобождения ресурсов и избежания потенциальных проблем с безопасностью
    }
}
