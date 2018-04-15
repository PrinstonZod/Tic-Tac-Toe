package com.example.android.tic_tac_toe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    boolean usingAI;
    boolean unselected;
    Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.start_button);
        radioGroup = findViewById(R.id.size_of_board);
        usingAI = true;
        unselected = true;
        start.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(unselected)return;
                Intent i = new Intent();
                int game =radioGroup.getCheckedRadioButtonId();

                switch(game){

                    case R.id.radio_3x3:
                        i.setClass(getApplicationContext(),GameplayActivity.class);
                        break;
                    case R.id.radio_4x4:
                        i.setClass(getApplicationContext(),FourByFourGameplayActivity.class);
                        break;
                    case R.id.radio_5x5:
                        i.setClass(getApplicationContext(),FiveByFiveGameplayActivity.class);
            }
            i.putExtra("usingAI",usingAI);
            startActivity(i);
        }});
        radioGroup.check(R.id.radio_3x3);
        final Button p1vsp2 = findViewById(R.id.p1_vs_p2_button);
        final Button singlePlayer = findViewById(R.id.single_player_button);
        p1vsp2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                view.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                singlePlayer.setBackgroundColor(getResources().getColor(R.color.colorBrown));
                unselected = false;
                usingAI = false;
                start.setAlpha(1.0f);
            }
        });
        singlePlayer.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                view.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                p1vsp2.setBackgroundColor(getResources().getColor(R.color.colorBrown));
                unselected = false;
                usingAI = true;
                start.setAlpha(1.0f);
            }
        });

    }



}

