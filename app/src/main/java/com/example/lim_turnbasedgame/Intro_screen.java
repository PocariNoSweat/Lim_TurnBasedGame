package com.example.lim_turnbasedgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Intro_screen extends AppCompatActivity {
    private Button startgame;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_screen);
        startgame = findViewById(R.id.btnstartgame);

        player = MediaPlayer.create(this, R.raw.intromusic);

        player.setLooping(true);
        player.setVolume(100,100);
        player.start();


        startgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intro_screen.this, MainActivity.class);
                player.stop();
                startActivity(intent);




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



        });
    }
}