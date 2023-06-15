package com.example.sachetnoe_prilozhenie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class My_World extends AppCompatActivity {


    private TextView textFio, textEmail, textStatus, textPol;
    private DatabaseHelper_Users dbHelper;

    @SuppressLint({"Range", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_world);

        dbHelper = new DatabaseHelper_Users(getApplicationContext());

        textEmail = findViewById(R.id.emailText);
        textFio = findViewById(R.id.fioText);
        textStatus = findViewById(R.id.statusText);
        textPol = findViewById(R.id.polText);

    }
    @Override
    protected void onResume() {
        super.onResume();
        showUserData();
    }
    @SuppressLint({"SetTextI18n", "Range"})
    private void showUserData() {
        Intent intent1 = getIntent();
        String email = intent1.getStringExtra("email");
        if (email != null) {
            String[] columns = {DatabaseHelper_Users.COLUMN_ID};
            String selection = DatabaseHelper_Users.COLUMN_EMAIL + "=?";
            String[] selectionArgs = {email};
            Cursor cursor = dbHelper.query(DatabaseHelper_Users.TABLE_U, columns, selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper_Users.COLUMN_ID));
                // здесь вы можете использовать id для выполнения каких-либо операций
                String[] columnsToReturn = {DatabaseHelper_Users.COLUMN_EMAIL, DatabaseHelper_Users.COLUMN_FIO, DatabaseHelper_Users.COLUMN_STATUS, DatabaseHelper_Users.COLUMN_POL};
                String selectionToReturn = DatabaseHelper_Users.COLUMN_ID + "=?";
                String[] selectionArgsToReturn = {String.valueOf(id)};
                Cursor cursorToReturn = dbHelper.query(DatabaseHelper_Users.TABLE_U, columnsToReturn, selectionToReturn, selectionArgsToReturn, null, null, null);
                if (cursorToReturn.moveToFirst()) {
                    String emailToReturn = cursorToReturn.getString(cursorToReturn.getColumnIndex(DatabaseHelper_Users.COLUMN_EMAIL));
                    String fioToReturn = cursorToReturn.getString(cursorToReturn.getColumnIndex(DatabaseHelper_Users.COLUMN_FIO));
                    String statusToReturn = cursorToReturn.getString(cursorToReturn.getColumnIndex(DatabaseHelper_Users.COLUMN_STATUS));
                    String polToReturn = cursorToReturn.getString(cursorToReturn.getColumnIndex(DatabaseHelper_Users.COLUMN_POL));
                    textEmail.setText(emailToReturn);
                    textFio.setText("ФИО: "+fioToReturn);
                    textStatus.setText("Статус: "+statusToReturn);
                    textPol.setText("Пол: "+polToReturn);
                } else {
                    textEmail.setText("");
                    textFio.setText("");
                    textStatus.setText("");
                    textPol.setText("");
                }
            } else {
                textEmail.setText("");
                textFio.setText("");
                textStatus.setText("");
                textPol.setText("");
            }
        } else {
            Toast.makeText(this, "Email is null", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Autor_Regis.class);
            startActivity(intent);
        }
    }


    public void go_out(View view) {
        Intent intent = new Intent(this, Autor_Regis.class);
        startActivity(intent);
    }

    public void go_to_menu(View view) {
        Intent intent0 = new Intent(this, Main_Menu.class);
        intent0.putExtra("email", textEmail.getText().toString());
        startActivity(intent0);
    }

    public void editData(View view) {
        Intent intent3 = new Intent(this, Redactor.class);
        intent3.putExtra("email", textEmail.getText().toString());
        startActivity(intent3);
    }

    public void go_to_my_merop(View view) {
        Intent intent0 = new Intent(this, My_Meropr.class);
        intent0.putExtra("email", textEmail.getText().toString());
        startActivity(intent0);
    }

    public void go_to_my_sobit(View view) {
        Intent intent0 = new Intent(this, My_Sobit.class);
        intent0.putExtra("email", textEmail.getText().toString());
        startActivity(intent0);
    }

}