package com.example.lim_turnbasedgame;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtCharName, txtEnemyName, txtCharHP, txtEnemyHP, txtHerodps, txtEnemydps, txtCombatLog;
    Button btnEndTurn;
    ImageButton skill2;
    MediaPlayer player;

    //Hero Stats
    String CharName = "Aleg";
    int heroHP = 2000;
    int heroMinDamage = 80;
    int heroMaxDamage = 200;
    int critMinDamage = 300;
    int critMaxDamage = 500;

    //Enemy Stats
    String EnemyName = "Midget Slayer";
    int enemyHP = 5000;
    int enemyMinDamage = 30;
    int enemyMaxDamage = 50;
    int enemycritMinDamage = 150;
    int enemycritMaxDamage = 200;
    //Game Turn
    int turnNumber = 1;

    boolean burnstatus = false;
    int statuscounter = 0;
    int buttoncd = 0;
    int button2cd = 0;
    int burncounter =0;
    int prevdmgdealthero = 0;
    int prevdmgdealtenemy = 0;
    int burndamage = 30;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableFullscreen();
        setContentView(R.layout.activity_main);

        //New music
        player = MediaPlayer.create(this, R.raw.battlemusic);
        player.setLooping(true);
        player.setVolume(100, 100);
        player.start();

        //XML ids for text and button
        txtCharName = findViewById(R.id.txtCharName);
        txtEnemyName = findViewById(R.id.txtEnemyName);
        txtCharHP = findViewById(R.id.txtCharHP);
        txtEnemyHP = findViewById(R.id.txtEnemyHP);
        btnEndTurn = findViewById(R.id.btnEndTurn);
        txtHerodps = findViewById(R.id.txtHerodps);
        txtEnemydps = findViewById(R.id.txtEnemydps);

        txtCombatLog = findViewById(R.id.txtCombatLog);


        txtCharName.setText(CharName);
        txtCharHP.setText(String.valueOf(heroHP));

        txtEnemyName.setText(EnemyName);
        txtEnemyHP.setText(String.valueOf(enemyHP));

        txtHerodps.setText(String.valueOf(heroMinDamage) + " ~ " + String.valueOf(heroMaxDamage));
        txtEnemydps.setText(String.valueOf(enemyMinDamage) + " ~ " + String.valueOf(enemyMaxDamage));

        skill2 = findViewById(R.id.btnskill2);


        //button onClick Listener
        btnEndTurn.setOnClickListener(this);
        skill2.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {


        Random randomizer = new Random();
        int herodps = randomizer.nextInt(heroMaxDamage - heroMinDamage) + heroMinDamage;
        int enemydps = randomizer.nextInt(enemyMaxDamage - enemyMinDamage) + enemyMinDamage;
        int critdamage = randomizer.nextInt(critMaxDamage - critMinDamage) + critMinDamage;
        int enemycritdamage = randomizer.nextInt(enemycritMaxDamage - enemycritMinDamage) + enemycritMinDamage;
        int critchance = randomizer.nextInt(7);

        {
        }


        if (turnNumber % 2 != 1) { // ENEMY TURN
            skill2.setEnabled(false);
        } else if (turnNumber % 2 == 1) { // HERO TURN
            skill2.setEnabled(true);
        }
        if (button2cd > 0) {
            skill2.setEnabled(false);
            button2cd--;
        } else if (button2cd == 0) {
            skill2.setEnabled(true);
        }



        switch (v.getId()) {
            case R.id.btnskill2:

                enemyHP = Math.max(0, enemyHP - 150);
                turnNumber++;
                txtEnemyHP.setText(String.valueOf(enemyHP));

                txtCombatLog.setText("" + String.valueOf(CharName) + " used Burn! It dealt " + String.valueOf(150) + "! The enemy is burned for 5 turns.");
                btnEndTurn.setText("Your Turn (" + String.valueOf(turnNumber)+ ")");

                burnstatus = true;
                burncounter = 4;

                if (enemyHP == 0) {
                    txtCombatLog.setText("" + String.valueOf(CharName) + " killed " + String.valueOf(EnemyName) + "! You win.");
                    heroHP = 2000;
                    enemyHP = 5000;
                    turnNumber = 1;
                    btnEndTurn.setText("Next Game");

                }
                button2cd = 12;
                break;



            case R.id.btnEndTurn:
                //

                if (turnNumber % 2 == 1) { //odd HERO TURN

                    if (critchance == 1) {
                        enemyHP = Math.max(0, enemyHP - critdamage);
                        prevdmgdealthero = critdamage;
                        turnNumber++;
                        txtEnemyHP.setText(String.valueOf(enemyHP));
                        btnEndTurn.setText("Enemy Turn (" + String.valueOf(turnNumber) + ")");
                        txtCombatLog.setText("" + String.valueOf(CharName) + " dealt " + String.valueOf(critdamage) + " to " + String.valueOf(EnemyName) + ". A critical hit!");
                    } else {
                        enemyHP = Math.max(0, enemyHP - herodps);
                        prevdmgdealthero = herodps;
                        turnNumber++;
                        btnEndTurn.setText("Enemy Turn (" + String.valueOf(turnNumber) + ")");
                        txtEnemyHP.setText(String.valueOf(enemyHP));

                        txtCombatLog.setText("" + String.valueOf(CharName) + " dealt " + String.valueOf(herodps) + " to " + String.valueOf(EnemyName) + "!");
                        statuscounter--;
                    }


                    if (enemyHP == 0) {
                        txtCombatLog.setText("" + String.valueOf(CharName) + " killed " + String.valueOf(EnemyName) + "! You win.");
                        heroHP = 2000;
                        enemyHP = 5000;
                        turnNumber = 1;
                        btnEndTurn.setText("Next Game");
                    }

                    if (burncounter > 0) {
                        //if the enemy is still burned, reduce 30 per turn
                        enemyHP = (enemyHP - burndamage);
                        txtCombatLog.setText("" + String.valueOf(CharName) + " dealt " + String.valueOf(prevdmgdealthero) + " to " + String.valueOf(EnemyName) + "! The enemy is still burned! It dealt 30 damage.");
                        burncounter--;

                        if (burncounter == 0) {
                            burnstatus = false;
                            txtCombatLog.setText("" + String.valueOf(CharName) + " dealt " + String.valueOf(prevdmgdealthero) + " to " + String.valueOf(EnemyName) +"The enemy is no longer burned.");
                        }
                    }

                    buttoncd--;
                    button2cd--;

                } else if (turnNumber % 2 != 1) { //even ENEMY TURN

                    if (critchance == 1) {
                        heroHP = Math.max(0, heroHP - enemycritdamage);
                        prevdmgdealtenemy = enemycritdamage;
                        turnNumber++;
                        txtCharHP.setText(String.valueOf(heroHP));
                        btnEndTurn.setText("Your Turn (" + String.valueOf(turnNumber) + ")");
                        txtCombatLog.setText("" + String.valueOf(EnemyName) + " dealt " + String.valueOf(enemycritdamage) + " to " + String.valueOf(CharName) + ". A critical hit!");
                    } else {
                        heroHP = Math.max(0, heroHP - enemydps);
                        prevdmgdealtenemy = enemydps;
                        turnNumber++;
                        btnEndTurn.setText("Your Turn (" + String.valueOf(turnNumber) + ")");
                        txtCharHP.setText(String.valueOf(heroHP));
                        txtCombatLog.setText("" + String.valueOf(EnemyName) + " dealt " + String.valueOf(enemydps) + " to " + String.valueOf(CharName) + "!");
                        statuscounter--;
                    }


                    if (heroHP == 0) {
                        txtCombatLog.setText("" + String.valueOf(EnemyName) + " killed " + String.valueOf(CharName) + "! Game over.");
                        heroHP = 2000;
                        enemyHP = 5000;
                        turnNumber = 1;
                        btnEndTurn.setText("Next Game");

                    }
                        break;
                    }
                }
                buttoncd--;
        button2cd--;
        }
    private void enableFullscreen() {
        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            );
        }
    }
}