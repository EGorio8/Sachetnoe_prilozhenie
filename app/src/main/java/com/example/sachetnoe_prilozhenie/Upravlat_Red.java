package com.example.sachetnoe_prilozhenie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Upravlat_Red extends AppCompatActivity {

    TextView textEmail;
    private EditText Reating;
    private DatabaseHelper_Users_Merop dbHelper;
    public String fio_zav;
    public String fio_uch;
    public String fio;
    private LinearLayout layout;
    private LinearLayout layout2;
    public int _id_mer;
    private int userId;
    TextView textName;
     public String Name;

    @SuppressLint({"MissingInflatedId", "Range"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upravlat_red);

        Reating= findViewById(R.id.EditReating);

        textEmail = findViewById(R.id.EmailText);
        textName = findViewById(R.id.Name);

        Intent intent0 = getIntent();
        String email = intent0.getStringExtra("email");
        _id_mer = getIntent().getIntExtra("_id_mer", -2);
        fio = intent0.getStringExtra("fio");
        userId = intent0.getIntExtra("id", -2); // извлекаем значение id из intent

        textEmail.setText(email);

        // Находим родительский Layout
        layout = findViewById(R.id.leaner1);
        layout2 = findViewById(R.id.leaner2);

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
                    if (columnIndex > -1)
                    {
                        fio_zav = cursor2.getString(columnIndex);
                    }
                }
            String[] columns3 = {DatabaseHelper_Users_Merop.COLUMN_NAME};
            String selection3 = DatabaseHelper_Users_Merop.COLUMN_ID_MER + "=?";
            String[] selectionArgs3 = { String.valueOf(id_mer) };
            Cursor cursor4 = dbHelper.query(DatabaseHelper_Users_Merop.TABLE_M, columns3, selection3, selectionArgs3, null, null, null);
            if (cursor4.moveToFirst()) {
                int column1Index = cursor4.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_NAME);
                if (column1Index > -1) {
                    Name = cursor4.getString(column1Index);
                    textName.setText(Name);
                }
        }
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
                layout2.addView(textView);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) { String query = "SELECT * FROM " +
                            DatabaseHelper_Users_Merop.TABLE_UCHAV + " WHERE " +
                            DatabaseHelper_Users_Merop.COLUMN_ID_USER_UCH + "=" + id_user + " AND " +
                            DatabaseHelper_Users_Merop.COLUMN_ID_MER_UCH + "=" + id_mer;
                        SQLiteDatabase db = dbHelper.getWritableDatabase(); Cursor cursor = null;
                        try { cursor = db.rawQuery(query, null);
                            if (cursor.getCount() > 0) {
                                Toast.makeText(Upravlat_Red.this, "Этот пользователь уже учавствует в мероприятии", Toast.LENGTH_SHORT).show();
                            } else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put(DatabaseHelper_Users_Merop.COLUMN_ID_USER_UCH, id_user);
                                contentValues.put(DatabaseHelper_Users_Merop.COLUMN_ID_MER_UCH, id_mer);
                                long result = db.insert(DatabaseHelper_Users_Merop.TABLE_UCHAV, null, contentValues);
                                if (result == -1) {
                                    Toast.makeText(Upravlat_Red.this, "Ошибка при сохранении данных", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Upravlat_Red.this, "Участник одобрен!", Toast.LENGTH_SHORT).show();
                                    layout2.removeView(v);
                                }
                            }
                        } finally {
                            if (cursor != null) {
                                cursor.close();
                            } db.close();
                        }
                    }
                });
            }
        }
        Cursor cursor3 = dbHelper.getDataUCH();
        while (cursor3.moveToNext()) {
            int id_mer = cursor3.getInt(cursor3.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_ID_MER_UCH));
            String id_user = cursor3.getString(cursor3.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_ID_USER_UCH));
            if (id_mer != 0 && id_mer == _id_mer) {
                // Получение ФИО пользователя с заданным id из таблицы пользователей
                String[] columns = {DatabaseHelper_Users_Merop.COLUMN_FIO};
                String selection = DatabaseHelper_Users_Merop.COLUMN_ID + "=?";
                String[] selectionArgs = {id_user};
                Cursor cursor2 = dbHelper.query(DatabaseHelper_Users_Merop.TABLE_U, columns, selection, selectionArgs, null, null, null);
                if (cursor2.moveToFirst()) {
                    int columnIndex = cursor2.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_FIO);
                    if (columnIndex > -1) {
                        fio_uch = cursor2.getString(columnIndex);
                    }
                }
                cursor2.close();

                // Создание нового TextView
                TextView textView2 = new TextView(this);
                textView2.setText(fio_uch);
                textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                textView2.setTextColor(Color.WHITE);
                // Настройка параметров макета для каждого TextView
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 16, 0, 0);
                textView2.setLayoutParams(params);
                // Добавление TextView в родительский Layout
                layout.addView(textView2);
            }
        }
        // Закрытие курсора и базы данных
        cursor.close();
        cursor3.close();
        dbHelper.close();
    }

    @SuppressLint("Range")
    public void delete(View view) {
        dbHelper = new DatabaseHelper_Users_Merop(getApplicationContext());
        Cursor cursor = dbHelper.getData();
        while (cursor.moveToNext()) {
            Name = cursor.getString(cursor.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_NAME));
            if (Name != null) {
                String[] columns = {DatabaseHelper_Users_Merop.COLUMN_ID_MER};
                String selection = DatabaseHelper_Users_Merop.COLUMN_NAME + "=?";
                String[] selectionArgs = {Name};
                Cursor cursor2 = dbHelper.query(DatabaseHelper_Users_Merop.TABLE_M, columns, selection, selectionArgs, null, null, null);
                if (cursor2.moveToFirst()) {
                    int id_mer = cursor2.getInt(cursor2.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_ID_MER)); // получаем id текущей записи
                    if (id_mer == _id_mer) { // сравниваем с искомым id
                        dbHelper.deleteMer(_id_mer); //удаляем запись с использованием метода deleteMer
                        break; // выходим из цикла, т.к. нужная запись уже удалена
                    }
                }
            }
        }
        Intent intent1 = new Intent(this, Upravlat.class);
        intent1.putExtra("email", textEmail.getText().toString());
        intent1.putExtra("fio", fio);
        intent1.putExtra("id", userId);
        startActivity(intent1);
    }

    public void razdat(View view) {
        //создание экземпляра БД
        DatabaseHelper_Users_Merop dbHelper = new DatabaseHelper_Users_Merop(this); Intent intent = getIntent();
        _id_mer = intent.getIntExtra("_id_mer", -2);
        //проверка на введенное количество баллов
        if (Reating == null) { Toast.makeText(this, "Введите количество баллов", Toast.LENGTH_SHORT).show();
            return;
        }
        int points = Integer.parseInt(Reating.getText().toString());
        //открытие БД на запись
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereClause = DatabaseHelper_Users_Merop.COLUMN_ID + " IN (SELECT " + DatabaseHelper_Users_Merop.COLUMN_ID_USER_UCH + " FROM " + DatabaseHelper_Users_Merop.TABLE_UCHAV + " WHERE " + DatabaseHelper_Users_Merop.COLUMN_ID_MER_UCH + " = " + _id_mer + ")";
        Cursor cursor = db.query(DatabaseHelper_Users_Merop.TABLE_U, null, whereClause, null, null, null, null);
        if (cursor.moveToFirst()) {
            do
            {
                int oldPoints = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper_Users_Merop.COLUMN_REATING));
                int newPoints = oldPoints + points;
                if(newPoints>1000){
                    newPoints=1000;
                }
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper_Users_Merop.COLUMN_REATING, newPoints);
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper_Users_Merop.COLUMN_ID));
                String[] whereArgs = {String.valueOf(userId)};
                int count = db.update(DatabaseHelper_Users_Merop.TABLE_U, values, DatabaseHelper_Users_Merop.COLUMN_ID + "=?", whereArgs);
                //проверка на успешность выдачи баллов
                if (count <= 0) {
                    Toast.makeText(this, "Ошибка распределения баллов для пользователя" + userId, Toast.LENGTH_SHORT).show();
                }
            }
            while (cursor.moveToNext());
            Toast.makeText(this, "Баллы распределены успешно", Toast.LENGTH_SHORT).show();
        } else
        {
            Toast.makeText(this, "Пользователи не найдены", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        dbHelper.close();
        //запуск активности Upravlat
        Intent intent1 = new Intent(this, Upravlat.class);
        intent1.putExtra("email", textEmail.getText().toString());
        intent1.putExtra("fio", fio);
        intent1.putExtra("id", userId);
        startActivity(intent1);
    }

    public void uprav(View view) {
        Intent intent1 = new Intent(this, Upravlat.class);
        intent1.putExtra("email", textEmail.getText().toString());
        intent1.putExtra("fio", fio);
        intent1.putExtra("id", userId);

        startActivity(intent1);
    }
}