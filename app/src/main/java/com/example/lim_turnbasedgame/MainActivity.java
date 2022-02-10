package com.example.lim_turnbasedgame;

import androidx.appcompat.app.AppCompatActivity;

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

    //Hero Stats
    String CharName = "Aleg";
    int heroHP = 1000;
    int heroMP = 500;
    int heroMinDamage = 80;
    int heroMaxDamage = 150;

    //Enemy Stats
    String EnemyName = "Midget Slayer";
    int enemyHP = 2000;
    int enemyMP = 200;
    int enemyMinDamage = 20;
    int enemyMaxDamage = 50;
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
        //XML ids for text and button
        txtCharName = findViewById(R.id.txtCharName);
        txtEnemyName = findViewById(R.id.txtEnemyName);
        txtCharHP = findViewById(R.id.txtCharHP);
        txtEnemyHP = findViewById(R.id.txtEnemyHP);
        txtCharMP = findViewById(R.id.txtCharMP);
        txtEnemyMP = findViewById(R.id.txtEnemyMP);
        btnEndTurn = findViewById(R.id.btnEndTurn);
        txtHerodps = findViewById(R.id.txtHerodps);
        txtEnemydps = findViewById(R.id.txtEnemydps);

        txtCombatLog = findViewById(R.id.txtCombatLog);


        txtCharName.setText(CharName);
        txtCharHP.setText(String.valueOf(heroHP));
        txtCharMP.setText(String.valueOf(heroMP));

        txtEnemyName.setText(EnemyName);
        txtEnemyHP.setText(String.valueOf(enemyHP));
        txtEnemyMP.setText(String.valueOf(enemyMP));

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

        int critchance = randomizer.nextInt(6);

        if (critchance == 1) {
            //critical attack here
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

                enemyHP = enemyHP - 100;
                turnNumber++;
                txtEnemyHP.setText(String.valueOf(enemyHP));


                txtCombatLog.setText("" + String.valueOf(CharName) + " used Stun! It dealt " + String.valueOf(100) + "! The enemy is stunned for 2 turns.");

                disabledstatus = true;
                statuscounter = 3;

                if (enemyHP < 0) {
                    txtCombatLog.setText("" + String.valueOf(CharName) + " killed " + String.valueOf(EnemyName) + "! You win.");
                    heroHP = 1000;
                    enemyHP = 2000;
                    turnNumber = 1;
                    btnEndTurn.setText("Next Game");
                }
                buttoncd = 8;


                break;
            case R.id.btnEndTurn:
                //

                if (turnNumber % 2 == 1) { //odd
                    enemyHP = enemyHP - herodps;
                    turnNumber++;
                    txtEnemyHP.setText(String.valueOf(enemyHP));

                    txtCombatLog.setText("" + String.valueOf(CharName) + " dealt " + String.valueOf(herodps) + " to " + String.valueOf(EnemyName) + "!");
                    statuscounter--;

                    if (enemyHP < 0) {
                        txtCombatLog.setText("" + String.valueOf(CharName) + " killed " + String.valueOf(EnemyName) + "! You win.");
                        heroHP = 1000;
                        enemyHP = 2000;
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

                    if (disabledstatus == true) {
                        txtCombatLog.setText("" + String.valueOf(EnemyName) + " is still stunned for " + String.valueOf(statuscounter) + " turns! ");
                        statuscounter--;
                        if (statuscounter == 0) {
                            disabledstatus = false;
                        }

                    } else {
                        heroHP = heroHP - enemydps;
                        turnNumber++;
                        txtCharHP.setText(String.valueOf(heroHP));

                        txtCombatLog.setText("" + String.valueOf(EnemyName) + " dealt " + String.valueOf(enemydps) + " to " + String.valueOf(CharName) + "!");

                        if (heroHP < 0) {
                            txtCombatLog.setText("" + String.valueOf(CharName) + " killed " + String.valueOf(CharName) + "! Game over.");
                            heroHP = 1000;
                            enemyHP = 2000;
                            turnNumber = 1;
                            btnEndTurn.setText("Next Game");

                        }
                        break;
                    }
                }
                buttoncd--;
        }
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