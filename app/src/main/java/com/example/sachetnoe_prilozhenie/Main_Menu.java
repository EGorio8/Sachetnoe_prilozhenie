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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        textEmail = findViewById(R.id.EmailText);
        ButtonControl = findViewById(R.id.button16);

        meropList = findViewById(R.id.list);

        Intent intent0 = getIntent();
        String email = intent0.getStringExtra("email");
        status = intent0.getStringExtra("status");
        fio = intent0.getStringExtra("fio");
        userId = getIntent().getIntExtra("id", -2);
        int[] colors = new int[]{
                Color.parseColor("#202630"),
                Color.parseColor("#313741"),};

        textEmail.setText(email);

        if (status != null && status.equals("Статус: Волонтёр")) {
            ButtonControl.setVisibility(View.GONE); // скрыть кнопку
        } else {
            ButtonControl.setVisibility(View.VISIBLE); // показать кнопку
        }

        meropList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int meropId = (int) id;

                // изменяем цвет фона выбранного мероприятия
                view.setBackgroundColor(colors[position % colors.length]);

                // остальной код обработки нажатия на мероприятие
                String query = "SELECT * FROM " + DatabaseHelper_Users_Merop.TABLE_Z + " WHERE " +
                        DatabaseHelper_Users_Merop.COLUMN_ID_USER_Z + "=" + userId + " AND " +
                        DatabaseHelper_Users_Merop.COLUMN_ID_MER_Z + "=" + meropId;
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                Cursor cursor = null;
                try {
                    cursor = db.rawQuery(query, null);
                    if (cursor.getCount() > 0) {
                        Toast.makeText(Main_Menu.this, "Вы уже зарегистрированы на это мероприятие", Toast.LENGTH_SHORT).show();
                    } else {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DatabaseHelper_Users_Merop.COLUMN_ID_USER_Z, userId);
                        contentValues.put(DatabaseHelper_Users_Merop.COLUMN_ID_MER_Z, meropId);
                        long result = db.insert(DatabaseHelper_Users_Merop.TABLE_Z, null, contentValues);
                        if (result == -1) {
                            Toast.makeText(Main_Menu.this, "Ошибка при сохранении данных", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Main_Menu.this, "Заявка на мероприятие сохранена", Toast.LENGTH_SHORT).show();
                        }
                    }
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                    db.close();
                }
            }
        });


        databaseHelper = new DatabaseHelper_Users_Merop(getApplicationContext());
        db = databaseHelper.getReadableDatabase(); // инициализируем базу данных в onCreate()
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {
        super.onResume();

// закрываем курсор, если он не закрыт
        if (userCursor != null && !userCursor.isClosed()) {
            userCursor.close();
        }

//открываем базу данных
        db = databaseHelper.getReadableDatabase();

//получаем данные из бд в виде курсора
        userCursor = db.rawQuery("select * from " + DatabaseHelper_Users_Merop.TABLE_M, null);
// определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DatabaseHelper_Users_Merop.COLUMN_NAME, DatabaseHelper_Users_Merop.COLUMN_VREMA, DatabaseHelper_Users_Merop.COLUMN_ORG, DatabaseHelper_Users_Merop.COLUMN_OPISANIE};
// создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        meropList.setAdapter(userAdapter);
// сбрасываем цвета фонов мероприятий
        for (int i = 0; i < meropList.getChildCount(); i++) {
            View child = meropList.getChildAt(i);
            child.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    // добавляем метод для кнопки "На главную"
    public void home(View view) {
        Intent intent1 = new Intent(this, My_World.class);
        intent1.putExtra("email", textEmail.getText().toString());
        intent1.putExtra("status", status);
        intent1.putExtra("fio", fio);
        intent1.putExtra("id", userId);
        startActivity(intent1);
    }

    // добавляем метод для кнопки "Управление"
    public void use(View view) {
        Intent intent1 = new Intent(this, Upravlat.class);
        intent1.putExtra("email", textEmail.getText().toString());
        intent1.putExtra("status", status);
        intent1.putExtra("fio", fio);
        intent1.putExtra("id", userId);
        startActivity(intent1);
    }

    @Override
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