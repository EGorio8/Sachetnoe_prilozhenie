package com.example.sachetnoe_prilozhenie;

import android.annotation.SuppressLint; import android.content.ContentValues; import android.content.Intent; import android.database.Cursor; import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build; import android.os.Bundle; import android.view.View; import android.widget.AdapterView; import android.widget.Button; import android.widget.ListView; import android.widget.SimpleCursorAdapter; import android.widget.TextView; import android.widget.Toast;

import androidx.annotation.RequiresApi; import androidx.appcompat.app.AppCompatActivity;

public class Main_Menu extends AppCompatActivity {

    TextView textEmail;
    Button ButtonControl;
    ListView meropList;
    DatabaseHelper_Users_Merop databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    String status;
    String fio;
    public int userId;

    // Создание активности страницы главного меню
    @SuppressLint("MissingInflatedId") // Заглушка для предупреждения android-studio
    @Override // Переопределение родительского метода
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Создание активности
        setContentView(R.layout.main_menu); // Устанавливаем макет пользовательского интерфейса "main_menu" для этой активности

        textEmail = findViewById(R.id.EmailText); // Связываем  переменную логина с текстовым полем для ввода логина
        ButtonControl = findViewById(R.id.button16); // Связываем переменную кнопки с кнопкой для управления мероприятиями

        meropList = findViewById(R.id.list); // Связываем переменную листа мероприятий со списком мероприятий

        Intent intent0 = getIntent(); // Создаём объект для передачи данных между классами
        String email = intent0.getStringExtra("email"); // Получаем значение логина
        status = intent0.getStringExtra("status"); // Получаем значение статуса
        fio = intent0.getStringExtra("fio"); // Получаем значение ФИО
        userId = getIntent().getIntExtra("id", -2); // Получаем значение уникального идентификатора пользователя, если значение не найдено, возвращаем "-2"
        int[] colors = new int[]{ // Создаём массив из двух цветов(для чередования цветов списка доступных мероприятий)
                Color.parseColor("#202630"), // Тёмно-серый
                Color.parseColor("#202630"), // Светло-серый
        };

        textEmail.setText(email); // Значение переменной "email" устанавливается для текстового поля логина.

        if (status != null && status.equals("Статус: Волонтёр")) { // Если статус пользователя = волонтер
            ButtonControl.setVisibility(View.GONE); // Скрываем кнопку управления мероприятиями
        } else { // Если статус пользователя = организатор
            ButtonControl.setVisibility(View.VISIBLE); // Отображаем кнопку
        }

        meropList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // Создаём слушатель событий нажатий на элементы списка мероприятий
            @Override  // Переопределение родительского метода
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { // Метод, который вызызывается по нажатию на элемент списка
                int meropId = (int) id; // Создаем переменную под id мероприятия

                view.setBackgroundColor(colors[position % colors.length]); // Изменяем цвет фона выбранного мероприятия

                String query = "SELECT * FROM " + DatabaseHelper_Users_Merop.TABLE_Z + " WHERE " +
                        DatabaseHelper_Users_Merop.COLUMN_ID_USER_Z + "=" + userId + " AND " +
                        DatabaseHelper_Users_Merop.COLUMN_ID_MER_Z + "=" + meropId; // Переменная для запроса к БД
                SQLiteDatabase db = databaseHelper.getWritableDatabase(); // Получаем экземпляр с в режиме "для изменения"
                Cursor cursor = null; // Создаем объект для получения доступа к результатам запроса в базе данных
                try { // Блок кода для сохранения пользовательской заявки на мероприятие в БД
                    cursor = db.rawQuery(query, null); //Получаем данные от запроса к БД 
                    if (cursor.getCount() > 0) { // Проверяем наличие записей в БД, если такая запиь уже есть, то
                        Toast.makeText(Main_Menu.this, "Вы уже зарегистрированы на это мероприятие", Toast.LENGTH_SHORT).show(); // Выводим сообщение о статусе регистрации волонтера
                    } else { // Иначе
                        ContentValues contentValues = new ContentValues(); // Создаем новую запись в базе данных
                        contentValues.put(DatabaseHelper_Users_Merop.COLUMN_ID_USER_Z, userId); // Добавляем значение пользовательного идентификатора в соответствующий столбец в таблице БД
                        contentValues.put(DatabaseHelper_Users_Merop.COLUMN_ID_MER_Z, meropId); // Добавляем  значение идентификатора мероприятий в соответствующий столбец в таблице БД
                        long result = db.insert(DatabaseHelper_Users_Merop.TABLE_Z, null, contentValues); // Добавляем полученную запись в БД
                        if (result == -1) { // Если данные в БД не были сохранены
                            Toast.makeText(Main_Menu.this, "Ошибка при сохранении данных", Toast.LENGTH_SHORT).show(); // Выводим сообщение об ошибке
                        } else { // Иначе
                            Toast.makeText(Main_Menu.this, "Заявка на мероприятие сохранена", Toast.LENGTH_SHORT).show(); // Выводим сообщение об успешном выполнении добавления мероприятия
                        }
                    }
                } finally { // Независимо от успешности операции добавления мероприятия в БД
                    if (cursor != null) { // Проверка открытости курсора
                        cursor.close(); // Если открыт - закрываем
                    }
                    db.close(); // Закрытие соединения с базой данных для освобождения ресурсов и избежания потенциальных проблем с безопасностью
                }
            }
        });


        databaseHelper = new DatabaseHelper_Users_Merop(getApplicationContext());  // Создание экземпляра класса БД для этой активности
        db = databaseHelper.getReadableDatabase(); // Открываем БД в режиме "только чтения"
    }
    
     // Если приложение вернется к этой активности из другой, например, когда пользователь нажмет на кнопку "назад"
    @RequiresApi(api = Build.VERSION_CODES.O) // Приложение не будет выполняться на устройствах с более старыми версиями Android, которые не поддерживают следующие функции
    @Override // Переопределение родительского метода
    public void onResume() {
        super.onResume();  // Возобновляем активность

        if (userCursor != null && !userCursor.isClosed()) { // Проверка открытости курсора
            userCursor.close(); // Если открыт - закрываем
        }

        db = databaseHelper.getReadableDatabase(); // Открываем БД в режиме "только чтения"

        userCursor = db.rawQuery("select * from " + DatabaseHelper_Users_Merop.TABLE_M, null);  // Записываем в переменную курсора все строки таблицы "DatabaseHelper_Users_Merop.TABLE_M"
        String[] headers = new String[]{DatabaseHelper_Users_Merop.COLUMN_NAME, DatabaseHelper_Users_Merop.COLUMN_VREMA, DatabaseHelper_Users_Merop.COLUMN_ORG, DatabaseHelper_Users_Merop.COLUMN_OPISANIE}; // Выбираем из БД столбцы , которые будут выводиться в ListView
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, // Связываем результаты запроса к базе данных с пользовательским интерфейсом
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0); // Обозначаем массив названий столбцов, в которых нужно отображать данные, и массив ресурсов пользовательского интерфейса
        meropList.setAdapter(userAdapter); // Устанавливаем адаптер userAdapter для списка meropList
        for (int i = 0; i < meropList.getChildCount(); i++) { // Создаём цикл, который перебирает все элементы в списке мероприятий
            View child = meropList.getChildAt(i); // Получаем элементы списка для дальнейшего взаимодействия с ними
            child.setBackgroundColor(Color.TRANSPARENT); // Устанавливаем фон элементов списка мероприятий
        }
    }

    // Создаём метод для кнопки "На главную"
    public void home(View view) {
        Intent intent1 = new Intent(this, My_World.class);
        intent1.putExtra("email", textEmail.getText().toString());
        intent1.putExtra("status", status);
        intent1.putExtra("fio", fio);
        intent1.putExtra("id", userId);
        startActivity(intent1);
    }

    // Создаём метод для кнопки "Управление"
    public void use(View view) {
        Intent intent1 = new Intent(this, Upravlat.class);
        intent1.putExtra("email", textEmail.getText().toString());
        intent1.putExtra("status", status);
        intent1.putExtra("fio", fio);
        intent1.putExtra("id", userId);
        startActivity(intent1);
    }

    @Override // Переопределение родительского метода
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        if (db != null) {
            db.close();
        }
        if (userCursor != null) {
            userCursor.close();
        }
    }
}
