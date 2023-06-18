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

    EditText nameBox; // Переменная для отображения текстового поля ???
    EditText vremaBox; // Переменная для отображения текстового поля ???
    EditText orgBox; // Переменная для отображения текстового поля ???
    MultiAutoCompleteTextView opisanieBox; // Переменная для отображения текстового поля ???
    Button delButton; // Переменная для кнопки удаления
    Button saveButton;  // Переменная для кнопки сохранения
    public String email;  // Переменная для хранения логина
    public int userId; // Переменная для хранения пользовательского id

    DatabaseHelper_Users_Merop sqlHelper; // Объект для создания и управления базой данных SQLite
    SQLiteDatabase db; // Объект для выполнения запросов к базе данных и управления ее структурой
    Cursor userCursor; // Объект для работы с результатами выборки данных из базы данных SQLite
    long Id = 0; // Переменная для хранения Id строки в базе данных
    
    @SuppressLint("MissingInflatedId") // Заглушка для предупреждения android-studio
    @Override  // Переопределение родительского метода
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  // Создание активности
        setContentView(R.layout.user_activity);  // Устанавливаем макет пользовательского интерфейса "user_activity" для этой активности
        Intent intent0 = getIntent(); // Создаём объект для передачи данных между классами
        email = intent0.getStringExtra("email");  // Получаем значение логина
        userId = intent0.getIntExtra("id", -2); // извлекаем значение id из intent

        nameBox = findViewById(R.id.name); // Связываем  переменную ??? с ???
        vremaBox = findViewById(R.id.vrema); // Связываем  переменную ??? с ???
        orgBox = findViewById(R.id.org); // // Связываем  переменную данных о организаторе с с текстовым полем данных об организаторе
        opisanieBox = findViewById(R.id.opis); // Связываем  переменную текстового поля описания мероприятия с текстовым полем описания мероприятия
        delButton = findViewById(R.id.deleteButton); // Связываем  переменную кнопки удаления с кнопкой для удаления
        saveButton = findViewById(R.id.saveButton); // Связываем  переменную кнопки сохранения с кнопкой для сохранения

        sqlHelper = new DatabaseHelper_Users_Merop(this); // Создание объекта sqlHelper класса DatabaseHelper_Users_Merop
        db = sqlHelper.getWritableDatabase();  // Получаем экземпляр БД в режиме "для изменения"

        Bundle extras = getIntent().getExtras(); // Извлекаем дополнительные данные из интента, полученного при запуске этой активности
        if (extras != null) { // Проверка наличия дополнительных данных в интенте, если они есть, то
            Id = extras.getLong("id"); // Переменной Id присваивается значение из интента по ключу "id"
        }
        
        if (Id > 0) {  // Если существует строка с заданным Id в БД, то
            userCursor = db.rawQuery("select * from " + DatabaseHelper_Users_Merop.TABLE_M + " where " +  // Записываем в переменную курсора все строки таблицы "DatabaseHelper_Users_Merop.TABLE_M"
                    DatabaseHelper_Users_Merop.COLUMN_ID_MER + "=?", new String[]{String.valueOf(Id)}); // Обозначаем массив названий столбцов, в которых нужно отображать данные, и массив ресурсов пользовательского интерфейса
            userCursor.moveToFirst(); // Помещаем курсор на первую строку в результате запроса
            nameBox.setText(userCursor.getString(1)); // Загружаем данные певого столбца в соответствующее TextView
            vremaBox.setText(userCursor.getString(2)); // Загружаем данные второго столбца в соответствующее TextView
            orgBox.setText(userCursor.getString(3)); // Загружаем данные третьего столбца в соответствующее TextView
            opisanieBox.setText(userCursor.getString(4)); // Загружаем данные четвертого столбца в соответствующее TextView
            userCursor.close(); // Если открыт - закрываем
        } else { // Иначе
            delButton.setVisibility(View.GONE); // Скрываем кнопку удаления
        }
    }
    
    // Создаём метод, вызываемый при сохранении данных в базе данных
    public void save(View view){
        ContentValues cv = new ContentValues(); // Создает объект ContentValues и добавляет в его содержимое данные, введенные пользователем в текстовые поля nameBox, vremaBox, orgBox и opisanieBox
        cv.put(DatabaseHelper_Users_Merop.COLUMN_NAME, nameBox.getText().toString());
        cv.put(DatabaseHelper_Users_Merop.COLUMN_VREMA, vremaBox.getText().toString());
        cv.put(DatabaseHelper_Users_Merop.COLUMN_ORG, orgBox.getText().toString());
        cv.put(DatabaseHelper_Users_Merop.COLUMN_OPISANIE, opisanieBox.getText().toString());

        if (Id > 0) { // Если существует строка с заданным Id в БД, то
            db.update(DatabaseHelper_Users_Merop.TABLE_M, cv, DatabaseHelper_Users_Merop.COLUMN_ID_MER + "=" + Id, null); // Обновляем строку в БД, удовлетворяющую заданному условию
        } else { // Иначе
            db.insert(DatabaseHelper_Users_Merop.TABLE_M, null, cv); // Вставляем эту строку в БД
        }
        goHome(); // Переводим пользователя на другую активность
    }
    
    public void delete(View view){
        db.delete(DatabaseHelper_Users_Merop.TABLE_M, DatabaseHelper_Users_Merop.COLUMN_ID_MER + "=?", new String[]{String.valueOf(Id)}); // Удаляем строку из БД по написанному условию
        goHome(); // Переводим пользователя на другую активность
    }
    
    private void goHome(){ // Метод для перевода пользователя на другую активность
        db.close(); // Закрываем соединения с базой данных для освобождения ресурсов и избежания потенциальных проблем с безопасностью
        Intent intent0 = new Intent(this, Main_Menu.class);  // Переход на экран ???
        intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP); // Добавляем два флагов для объекта Intent: удаляем иные активности из стека задач; создаем целевую активность, если она находиться не сверху стека активностей
        intent0.putExtra("email", email); // Добавляем данные почты для экрана ???
        intent0.putExtra("id", userId); // Добавляет данные пользователького идентификатора для экрана ???
        startActivity(intent0); // Запуск новой активности в окне ???
    }
}
