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

    TextView txtCharName, txtEnemyName, txtCharHP, txtEnemyHP, txtCharMP, txtEnemyMP, txtHerodps, txtEnemydps, txtCombatLog;
    Button btnEndTurn;
    ImageButton skill1, skill2, skill3;
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

    boolean disabledstatus = false;
    int statuscounter = 0;
    int buttoncd = 0;

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

        skill1 = findViewById(R.id.btnskill1);
        skill2 = findViewById(R.id.btnskill2);
        skill3 = findViewById(R.id.btnskill3);


        //button onClick Listener
        btnEndTurn.setOnClickListener(this);
        skill1.setOnClickListener(this);
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

        if (turnNumber % 2 != 1) {
            skill1.setEnabled(false);
        } else if (turnNumber % 2 == 1) {
            skill1.setEnabled(true);
        }

        if (buttoncd > 0) {
            skill1.setEnabled(false);
            buttoncd--;
        } else if (buttoncd == 0) {
            skill1.setEnabled(true);
        }


        switch (v.getId()) {
            case R.id.btnskill1:

                enemyHP = Math.max(0, enemyHP - 100);
                turnNumber++;
                txtEnemyHP.setText(String.valueOf(enemyHP));


                txtCombatLog.setText("" + String.valueOf(CharName) + " used Stun! It dealt " + String.valueOf(100) + "! The enemy is stunned for 2 turns.");

                disabledstatus = true;
                statuscounter = 3;

                if (enemyHP == 0) {
                    txtCombatLog.setText("" + String.valueOf(CharName) + " killed " + String.valueOf(EnemyName) + "! You win.");
                    heroHP = 2000;
                    enemyHP = 5000;
                    turnNumber = 1;
                    btnEndTurn.setText("Next Game");
                }
                buttoncd = 8;


                break;
            case R.id.btnEndTurn:
                //

                if (turnNumber % 2 == 1) { //odd

                    if (critchance == 1) {
                        enemyHP = Math.max(0, enemyHP - critdamage);
                        turnNumber++;
                        txtEnemyHP.setText(String.valueOf(enemyHP));
                        btnEndTurn.setText("End Turn (" + String.valueOf(turnNumber) + ")");
                        txtCombatLog.setText("" + String.valueOf(CharName) + " dealt " + String.valueOf(critdamage) + " to " + String.valueOf(EnemyName) + ". A critical hit!");
                    }
                    else {
                        enemyHP = Math.max(0, enemyHP - herodps);
                        turnNumber++;
                        btnEndTurn.setText("End Turn (" + String.valueOf(turnNumber) + ")");
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
                    if (statuscounter > 0) {
                        ;//if the enemy is still stunned, reduce the stun for 1 turn
                        statuscounter--;
                        if (statuscounter == 0) {
                            disabledstatus = false;
                        }
                    }
                    buttoncd--;

                } else if (turnNumber % 2 != 1) { //even

                    if (critchance == 1) {
                        heroHP = Math.max(0, heroHP - enemycritdamage);
                        turnNumber++;
                        txtCharHP.setText(String.valueOf(heroHP));
                        btnEndTurn.setText("End Turn (" + String.valueOf(turnNumber) + ")");
                        txtCombatLog.setText("" + String.valueOf(EnemyName) + " dealt " + String.valueOf(enemycritdamage) + " to " + String.valueOf(CharName) + ". A critical hit!");
                    }

                    else {
                        heroHP = Math.max(0, heroHP - enemydps);
                        turnNumber++;
                        btnEndTurn.setText("End Turn (" + String.valueOf(turnNumber) + ")");
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