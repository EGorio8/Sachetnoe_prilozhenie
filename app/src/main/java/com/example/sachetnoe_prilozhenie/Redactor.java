package com.example.sachetnoe_prilozhenie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class Redactor extends AppCompatActivity {

    private EditText textFio, textEmail, textPassword;
    private CheckBox textStatus;
    private Switch textPol;
    private DatabaseHelper_Users_Merop dbHelper;

    @SuppressLint({"Range", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redactor);

        dbHelper = new DatabaseHelper_Users_Merop(getApplicationContext());

        textFio = findViewById(R.id.red_Fio);
        textEmail = findViewById(R.id.red_Email);
        textStatus = findViewById(R.id.red_Status);
        textPol = findViewById(R.id.red_Pol);
        textPassword = findViewById(R.id.red_Password);

    }

    @Override
    protected void onResume() {
        super.onResume();
        showUserData();
    }

    @SuppressLint({"SetTextI18n", "Range"})
    private void showUserData() {
        Intent intent3 = getIntent();
        String email = intent3.getStringExtra("email");
        if (email != null) {
            String[] columns = {DatabaseHelper_Users_Merop.COLUMN_ID};
            String selection = DatabaseHelper_Users_Merop.COLUMN_EMAIL + "=?";
            String[] selectionArgs = {email};
            Cursor cursor = dbHelper.query(DatabaseHelper_Users_Merop.TABLE_U, columns, selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_ID));

                String[] columnsToReturn = {DatabaseHelper_Users_Merop.COLUMN_EMAIL, DatabaseHelper_Users_Merop.COLUMN_FIO, DatabaseHelper_Users_Merop.COLUMN_STATUS, DatabaseHelper_Users_Merop.COLUMN_POL, DatabaseHelper_Users_Merop.COLUMN_PASSWORD};
                String selectionToReturn = DatabaseHelper_Users_Merop.COLUMN_ID + "=?";
                String[] selectionArgsToReturn = {String.valueOf(id)};
                Cursor cursorToReturn = dbHelper.query(DatabaseHelper_Users_Merop.TABLE_U, columnsToReturn, selectionToReturn, selectionArgsToReturn, null, null, null);
                if (cursorToReturn.moveToFirst()) {
                    String emailToReturn = cursorToReturn.getString(cursorToReturn.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_EMAIL));
                    String fioToReturn = cursorToReturn.getString(cursorToReturn.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_FIO));
                    String statusToReturn = cursorToReturn.getString(cursorToReturn.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_STATUS));
                    String polToReturn = cursorToReturn.getString(cursorToReturn.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_POL));
                    String passwordToReturn = cursorToReturn.getString(cursorToReturn.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_PASSWORD));
                    textEmail.setText(emailToReturn);
                    textFio.setText(fioToReturn);
                    textPassword.setText(passwordToReturn);
                    textStatus.setChecked(statusToReturn.equals("Организатор"));
                    textPol.setChecked(polToReturn.equals("Женский"));
                } else {
                    textEmail.setText("");
                    textFio.setText("");
                    textStatus.setChecked(false);
                    textPol.setChecked(false);
                }
            } else {
                textEmail.setText("");
                textFio.setText("");
                textStatus.setChecked(false);
                textPol.setChecked(false);
            }
        } else {
            Toast.makeText(this, "Email is null", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Autor_Regis.class);
            startActivity(intent);
        }
    }

    public void saveUserData(View view) {
        Intent intent3 = getIntent();
        String email = intent3.getStringExtra("email");
        if (email != null) {
            String new_email = textEmail.getText().toString();
            String fio = textFio.getText().toString();
            String password = textPassword.getText().toString();
            String status = textStatus.isChecked() ? "Организатор" : "Волонтёр";
            String pol = textPol.isChecked() ? "Женский" : "Мужской";

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper_Users_Merop.COLUMN_EMAIL, new_email);
            values.put(DatabaseHelper_Users_Merop.COLUMN_FIO, fio);
            values.put(DatabaseHelper_Users_Merop.COLUMN_STATUS, status);
            values.put(DatabaseHelper_Users_Merop.COLUMN_POL, pol);
            values.put(DatabaseHelper_Users_Merop.COLUMN_PASSWORD, password);



            String selection = DatabaseHelper_Users_Merop.COLUMN_EMAIL + "=?";
            String[] selectionArgs = {email};

            int count = dbHelper.update(DatabaseHelper_Users_Merop.TABLE_U, values, selection, selectionArgs);
            if (count > 0) {
                Toast.makeText(this, "Ваши данные успешно сохранены", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Autor_Regis.class);
                startActivity(intent);

            } else {
                Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Email is null", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Autor_Regis.class);
            startActivity(intent);
        }
    }

    public void onBackPressed(View view) {
        super.onBackPressed();
    }
}