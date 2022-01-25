package com.example.lim_turnbasedgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Methods can ask for values and give a return value except for void method types

        //Scenario you have a program that gives you a value of 9 if you provide it a string "Ebargs"


        value("Ebargs");

    }

    int value(String x){

        if(x=="Ebargs"){
            return 9;

        }

        return 1;
    }
}