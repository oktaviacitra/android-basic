package com.example.expression;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView textViewEmotion;
    Button btnSenang, btnBiasa, btnSedih;
    Animation bounce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewEmotion = findViewById(R.id.textViewEmotion);
        btnSenang = findViewById(R.id.btnSenang);
        btnBiasa = findViewById(R.id.btnBiasa);
        btnSedih = findViewById(R.id.btnSedih);

        bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);

        btnSenang.setOnClickListener(view -> {
            view.startAnimation(bounce);
            textViewEmotion.setText("Kamu sedang senang 😊");
        });

        btnBiasa.setOnClickListener(view -> {
            view.startAnimation(bounce);
            textViewEmotion.setText("Kamu merasa biasa saja 😐");
        });

        btnSedih.setOnClickListener(view -> {
            view.startAnimation(bounce);
            textViewEmotion.setText("Kamu sedang sedih 😢");
        });
    }
}