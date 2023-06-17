package com.example.sachetnoe_prilozhenie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Upravlat extends AppCompatActivity {
    TextView textEmail;

    private LinearLayout layout;
    private EditText searchEditText;
    String status;
    public String fio;
    public int userId;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upravlat);
        textEmail = findViewById(R.id.EmailText);

        searchEditText = findViewById(R.id.searchEvent);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterEvents(s.toString()); // вызов функции фильтрации мероприятий по введенному тексту
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Находим родительский Layout
        layout = findViewById(R.id.leaner);

        // Создание нового объекта DatabaseHelper
        DatabaseHelper_Users_Merop dbHelper = new DatabaseHelper_Users_Merop(this);

        // Получение курсора
        Cursor cursor = dbHelper.getData();
        Intent intent1 = getIntent();
        String email = intent1.getStringExtra("email");
        status = intent1.getStringExtra("status");
        fio = intent1.getStringExtra("fio");
        userId = getIntent().getIntExtra("id", -2);

        textEmail.setText(email);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int _id_mer = cursor.getInt(cursor.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_ID_MER));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_NAME));
            @SuppressLint("Range") String vrema = cursor.getString(cursor.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_VREMA));
            @SuppressLint("Range") String org = cursor.getString(cursor.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_ORG));
            @SuppressLint("Range") String opisanie = cursor.getString(cursor.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_OPISANIE));
            if (fio != null && fio.equals(org)) { // Проверка на равенство строк org и fio
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
                textView.setText(_id_mer + ")" + " МЕРОПРИЯТИЕ: " + "\n" + name + "\n" + "ДАТА: " + vrema + "\n" + "ФИО ОРГАНИЗАТОРА: " + "\n" + fio + "\n" + "ОПИСАНИЕ: " + "\n" + opisanie);
                // Настройка параметров макета для каждого TextView
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 16, 0, 0);
                textView.setLayoutParams(params);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Действия при нажатии на TextView
                        Intent intent0 = new Intent(getApplicationContext(), Upravlat_Red.class);
                        intent0.putExtra("_id_mer", _id_mer);
                        intent0.putExtra("email", textEmail.getText().toString());
                        intent0.putExtra("status", status);
                        intent0.putExtra("fio", fio);
                        intent0.putExtra("id", userId); // передаем id в intent0
                        startActivity(intent0);
                    }
                });
                // Добавление TextView в родительский Layout
                layout.addView(textView);
            }
        }

        // Закрытие курсора и базы данных
        cursor.close();
        dbHelper.close();
    }

    private void filterEvents(String searchText) {
        // Создание нового объекта DatabaseHelper
        DatabaseHelper_Users_Merop dbHelper = new DatabaseHelper_Users_Merop(this);

        // Получение курсора с данными
        Cursor cursor = dbHelper.getData();

        // Очистка родительского Layout перед добавлением отфильтрованных TextView
        layout.removeAllViews();

        // Перебор всех записей в курсоре
        while (cursor.moveToNext()) {
            // Получение данных из курсора
            @SuppressLint("Range") int _id_mer = cursor.getInt(cursor.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_ID_MER));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_NAME));
            @SuppressLint("Range") String vrema = cursor.getString(cursor.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_VREMA));
            @SuppressLint("Range") String org = cursor.getString(cursor.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_ORG));
            @SuppressLint("Range") String opisanie = cursor.getString(cursor.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_OPISANIE));
            if (fio != null && fio.equals(org)) { // Проверка на равенство строк org и fio
                // Если поисковый запрос не пустой и название мероприятия не содержит запрос, то пропускаем это мероприятие
                if (!searchText.isEmpty() && !name.toLowerCase().contains(searchText.toLowerCase())) {
                    continue;
                }

                // Создание нового TextView
                TextView textView = new TextView(this);

                // Генерация случайного цвета фона
                int r = (int) (Math.random() * 100);
                int g = (int) (Math.random() * 100);
                int b = (int) (Math.random() * 150);

                // Установка цвета фона, размера и цвета текста TextView
                textView.setBackgroundColor(Color.rgb(r, g, b));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                textView.setTextColor(Color.WHITE);

                textView.setId(_id_mer); // Установка идентификатора для TextView
                textView.setText(_id_mer + ")" + " МЕРОПРИЯТИЕ: " + "\n" + name + "\n" + "ДАТА: " + vrema + "\n" + "ФИО ОРГАНИЗАТОРА: " + "\n" + fio + "\n" + "ОПИСАНИЕ: " + "\n" + opisanie);

                // Настройка параметров макета для каждого TextView
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 16, 0, 0);
                textView.setLayoutParams(params);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Действия при нажатии на TextView
                        Intent intent0 = new Intent(getApplicationContext(), Upravlat_Red.class);
                        intent0.putExtra("_id_mer", _id_mer);
                        intent0.putExtra("email", textEmail.getText().toString());
                        intent0.putExtra("fio", fio);
                        intent0.putExtra("id", userId); // передаем id в intent0
                        startActivity(intent0);
                    }
                });

                // Добавление TextView в родительский Layout
                layout.addView(textView);
            }
        }

        // Закрытие курсора и базы данных
        cursor.close();
        dbHelper.close();
    }

    public void add(View view) {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    public void menu(View view) {
        Intent intent0 = new Intent(this, Main_Menu.class);
        intent0.putExtra("email", textEmail.getText().toString());
        intent0.putExtra("fio", fio);
        startActivity(intent0);
    }
}