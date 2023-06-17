package com.example.sachetnoe_prilozhenie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Upravlat_Red extends AppCompatActivity {

    TextView textEmail;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upravlat_red);

        textEmail = findViewById(R.id.EmailText);

        Intent intent0 = getIntent();
        String email = intent0.getStringExtra("email");

        textEmail.setText(email);
    }
    public void uprav(View view) {
        Intent intent1 = new Intent(this, Upravlat.class);
        intent1.putExtra("email", textEmail.getText().toString());
        startActivity(intent1);
    }
}