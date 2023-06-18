package com.example.sachetnoe_prilozhenie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint; import android.content.Intent; import android.database.Cursor; import android.os.Bundle; import android.view.View; import android.widget.ImageView; import android.widget.TextView; import android.widget.Toast;

public class UserProfileScreen extends AppCompatActivity {

    private TextView textFio, textEmail, textStatus, textPol, textReating;
    private ImageView ImageIcon;
    private DatabaseHelper_Users_Merop dbHelper;
    private int id;
    public int userId;

    @SuppressLint({"Range", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_world);

        dbHelper = new DatabaseHelper_Users_Merop(getApplicationContext());

        textEmail = findViewById(R.id.emailText);
        textFio = findViewById(R.id.fioText);
        textStatus = findViewById(R.id.statusText);
        textPol = findViewById(R.id.polText);
        textReating = findViewById(R.id.reatingText);
        ImageIcon = findViewById(R.id.icon_reating);

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
        userId = intent1.getIntExtra("id", -2);
        if (email != null) {
            String[] columns = {DatabaseHelper_Users_Merop.COLUMN_ID};
            String selection = DatabaseHelper_Users_Merop.COLUMN_EMAIL + "=?";
            String[] selectionArgs = {email};
            Cursor cursor = dbHelper.query(DatabaseHelper_Users_Merop.TABLE_U, columns, selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {
                id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_ID));
                // здесь мы можем использовать id для выполнения каких-либо операций
                String[] columnsToReturn = {DatabaseHelper_Users_Merop.COLUMN_ID,DatabaseHelper_Users_Merop.COLUMN_EMAIL, DatabaseHelper_Users_Merop.COLUMN_FIO, DatabaseHelper_Users_Merop.COLUMN_STATUS, DatabaseHelper_Users_Merop.COLUMN_POL, DatabaseHelper_Users_Merop.COLUMN_REATING};
                String selectionToReturn = DatabaseHelper_Users_Merop.COLUMN_ID + "=?";
                String[] selectionArgsToReturn = {String.valueOf(id)};
                Cursor cursorToReturn = dbHelper.query(DatabaseHelper_Users_Merop.TABLE_U, columnsToReturn, selectionToReturn, selectionArgsToReturn, null, null, null);
                if (cursorToReturn.moveToFirst()) {
                    String idToReturn = cursorToReturn.getString(cursorToReturn.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_ID));
                    String emailToReturn = cursorToReturn.getString(cursorToReturn.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_EMAIL));
                    String fioToReturn = cursorToReturn.getString(cursorToReturn.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_FIO));
                    String statusToReturn = cursorToReturn.getString(cursorToReturn.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_STATUS));
                    String polToReturn = cursorToReturn.getString(cursorToReturn.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_POL));
                    int reatingToReturn = cursorToReturn.getInt(cursorToReturn.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_REATING));
                    id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper_Users_Merop.COLUMN_ID));
                    textEmail.setText(emailToReturn);
                    textFio.setText(fioToReturn);
                    textStatus.setText("Статус: "+statusToReturn);
                    textPol.setText("Пол: "+polToReturn);
                    if(reatingToReturn<201){
                        textReating.setText("Медь");
                        ImageIcon.setImageResource(R.drawable.copper);
                    }else if(reatingToReturn<401 && reatingToReturn>200){
                        textReating.setText("Бронза");
                        ImageIcon.setImageResource(R.drawable.bronze);
                    }else if(reatingToReturn<601 && reatingToReturn>400){
                        textReating.setText("Серебро");
                        ImageIcon.setImageResource(R.drawable.silver_1);
                    }else if(reatingToReturn<801 && reatingToReturn>600){
                        textReating.setText("Золото");
                        ImageIcon.setImageResource(R.drawable.gold);
                    }else if(reatingToReturn<1001 && reatingToReturn>800){
                        textReating.setText("Бриллиант");
                        ImageIcon.setImageResource(R.drawable.diamond);
                    }
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
        intent0.putExtra("status", textStatus.getText().toString());
        intent0.putExtra("id", id);
        intent0.putExtra("fio", textFio.getText().toString());
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
        intent0.putExtra("id", id); // передаем id в intent0
        startActivity(intent0);
    }

    public void go_to_my_sobit(View view) {
        Intent intent0 = new Intent(this, My_Sobit.class);
        intent0.putExtra("email", textEmail.getText().toString());
        intent0.putExtra("id", id); // передаем id в intent0
        startActivity(intent0);
    }
}



