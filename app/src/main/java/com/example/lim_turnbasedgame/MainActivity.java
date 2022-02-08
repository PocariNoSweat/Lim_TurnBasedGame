package com.example.lim_turnbasedgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtCharName,txtEnemyName,txtCharHP,txtEnemyHP,txtCharMP,txtEnemyMP;
    Button btnEndTurn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //XML ids for text and button
        txtCharName = findViewById(R.id.txtCharName);
        txtEnemyName = findViewById(R.id.txtEnemyName);
        txtCharHP = findViewById(R.id.txtCharHP);
        txtEnemyMP = findViewById(R.id.txtEnemyMP);
        txtCharMP = findViewById(R.id.txtCharMP);
        txtEnemyMP = findViewById(R.id.txtEnemyMP);
        btnEndTurn = findViewById(R.id.btnEndTurn);


        String CharName = "Aleg";
        int heroHP = 1000;
        int heroMP = 500;
        int heroMinDamage = 80;
        int heroMaxDamage = 130;

        String EnemyName = "Midget Slayer";
        int enemyHP = 2000;
        int enemyMP = 200;
        int enemyMinDamage = 30;
        int enemyMaxDamage = 50;

        txtCharName.setText(CharName);
        txtCharHP.setText(String.valueOf(heroHP));
        txtCharMP.setText(String.valueOf(heroMP));

        txtEnemyName.setText(EnemyName);
        txtEnemyHP.setText(String.valueOf(enemyHP));
        txtEnemyMP.setText(String.valueOf(enemyMP));




    }
}