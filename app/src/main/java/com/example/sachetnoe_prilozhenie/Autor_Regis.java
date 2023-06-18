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

    private EditText emailEditText, passwordEditText; // Создание переменных под логин и пароль пользователя
    private DatabaseHelper_Users_Merop dbHelper; // Создание переменной под БД

    // Создание активности страницы авторизации / регистрации пользователя
    @Override // Переопределение родительского метода
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Создание активности
        setContentView(R.layout.autor_regis); // Устанавливаем макет пользовательского интерфейса "autor_regis" для этой активности

        dbHelper = new DatabaseHelper_Users_Merop(this); // Создание экземпляра класса БД для этой активности

        emailEditText = findViewById(R.id.fioText); // Связываем переменную логина с текстовым полем для ввода логина
        passwordEditText = findViewById(R.id.password); // Связываем переменную пароля с текстовым полем для ввода пароля
    }

    // Метод для выполнения входа в приложение(код, выполняющийся по нажатию на кнопку "Войти")
    public void enter(View view) {

        String email = emailEditText.getText().toString().trim(); // Получаем значение электронной почты пользователя из соответствующего текстового поля
        String password = passwordEditText.getText().toString().trim(); // Получаем значение пароля пользователя из соответствующего текстового поля

        if(!email.isEmpty() && !password.isEmpty()) { // Проверка полей логина и пароля на то, что они не пустые

            SQLiteDatabase db = dbHelper.getReadableDatabase(); // Открываем БД в режиме "только чтения"

            String[] columns = {DatabaseHelper_Users_Merop.COLUMN_ID}; // задаём массив столбцов, которые будут запрашиваться в базе данных
            String selection = DatabaseHelper_Users_Merop.COLUMN_EMAIL + "=? AND " + DatabaseHelper_Users_Merop.COLUMN_PASSWORD + "=?"; // Определяем условия поиска в БД(ищем логин и парорь)
            String[] selectionArgs = {email, password}; // Задаём массив аргументов для выборки записи, подходящих условию поиска

            Cursor cursor = db.query(DatabaseHelper_Users_Merop.TABLE_U, columns, selection, selectionArgs, null, null, null); // Выполняем запрос(выборку) к БД

            if(cursor != null && cursor.getCount() > 0) { // Проверка наличия найденных записей в таблице пользователей
                Toast.makeText(this, "Вход выполнен", Toast.LENGTH_SHORT).show(); // Вывод сообщения об удачном входе в аккаунт
                Intent intent = new Intent(this, Main_Menu.class); // Переход на экран главного меню
                startActivity(intent); // Запуск новой активности
                finish(); // Закрываем текущую активности
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
    @Override // Переопределение родительского метода
    protected void onResume() {
        super.onResume(); // Возобновляем активность

        emailEditText.setText(""); // Очищаем поля ввода логина
        passwordEditText.setText(""); // Очищаем поля ввода пароля
    }

    // Закрываем базу данных при уничтожении активности
    @Override // Переопределение родительского метода
    protected void onDestroy() {
        super.onDestroy(); // Закрываем активности
        dbHelper.close(); // Закрытие соединения с базой данных для освобождения ресурсов и избежания потенциальных проблем с безопасностью
    }
}
