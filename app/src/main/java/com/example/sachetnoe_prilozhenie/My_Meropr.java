package com.example.sachetnoe_prilozhenie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class My_Meropr extends AppCompatActivity {

    @Override // Переопределение родительского метода
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Создание активности
        setContentView(R.layout.my_meropr); // Устанавливаем макет пользовательского интерфейса "my_meropr" для этой активности
    }
   
    public void onBackPressed(View view) {  // Создаём метод для обработки события нажатия кнопки возвращения на предыдущую активность
        super.onBackPressed(); // Возвращаемся к предыдущей активности
    }
}
