package com.example.android.tic_tac_toe;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class FiveByFiveGameplayActivity extends Activity {
    //our buttons and their values
    public Button[][] buttons;
    public String[][] strings;
    //a counter
    public int roundCount;
    //player score board
    TextView textview1, textview2;
    //booleans to show which player's turn
    boolean player1Turn;
    int player1_points, player2_points;
    //toggle two players or ai
    boolean usingAI;
    //used to schedule tasks
    Handler handler;
    //used to suspend input
    boolean frozen;
    //the AI object
    MyAI myAI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        setContentView(R.layout.activity_five_by_five_gameplay);
        textview1 = findViewById(R.id.text_view_p1);
        myAI = new MyAI();
        player1Turn = true;
        roundCount = 0;
        player2_points = 0;
        usingAI = getIntent().getExtras().getBoolean("usingAI", true);
        player1_points = 0;
        textview2 = findViewById(R.id.text_view_p2);
        buttons = new Button[5][5];
        strings = new String[5][5];
        String buttonID;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                buttonID = "button_" + ((Integer) i).toString() + ((Integer) j).toString();
                buttons[i][j] = findViewById(getResources().getIdentifier(buttonID, "id", getPackageName()));
                buttons[i][j].setText("");
                buttons[i][j].setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View p1) {
                        if (frozen) {
                            return;
                        }
                        if (!((Button) p1).getText().toString().equals("")) {
                            return;
                        }
                        roundCount++;
                        if (player1Turn) {
                            ((Button) p1).setText("x");
                        } else {
                            ((Button) p1).setText("o");
                        }
                        if (checkForWin()) {
                            if (player1Turn) {
                                playerWins(1);
                            } else playerWins(2);
                        } else if (roundCount == 25) {
                            playerWins(0);
                        } else {
                            player1Turn = !player1Turn;

                            if (usingAI) {
                                myAI.makeMove();
                            }
                        }
                    }
                });
            }
        }
        (findViewById(R.id.button_reset)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View p1) {
                player1_points = 0;
                player2_points = 0;
                updateText();
                resetBoard();
            }
        });
    }

    private void playerWins(int p0) {
        switch (p0) {
            case 1:
                player1_points++;
                Toast.makeText(this, "Player 1 wins", Toast.LENGTH_SHORT).show();
                updateText();
                break;
            case 2:
                player2_points++;
                Toast.makeText(this, "Player 2 wins", Toast.LENGTH_SHORT).show();
                updateText();
                break;
            case 0:
                Toast.makeText(this, "Ooops. It is a draw", Toast.LENGTH_SHORT).show();
        }
        frozen = true;
        handler.postDelayed(new Thread() {
            public void run() {
                resetBoard();
                frozen = false;
            }
        }, 1000);
    }

    private void resetBoard() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                buttons[i][j].setText("");
            }
            roundCount = 0;
            player1Turn = true;
        }
    }

    private void updateText() {
        textview1.setText("Player 1: " + ((Integer) player1_points).toString());
        textview2.setText("Player 2: " + ((Integer) player2_points).toString());
    }

    private void resetStrings() {
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++) {
                strings[i][j] = buttons[i][j].getText().toString();
            }
    }

    public boolean checkForWin() {
        resetStrings();
        if (strings[0][0].equals(strings[0][1]) && strings[0][0].equals(strings[0][2]) && strings[0][0].equals(strings[0][3]) && strings[0][0].equals(strings[0][4]) && !strings[0][0].equals("")) {
            return true;
        }
        if (strings[1][0].equals(strings[1][1]) && strings[1][0].equals(strings[1][2]) && strings[1][0].equals(strings[1][3]) && strings[1][0].equals(strings[1][4]) && !strings[1][0].equals("")) {
            return true;
        }
        if (strings[2][0].equals(strings[2][1]) && strings[2][0].equals(strings[2][2]) && strings[2][0].equals(strings[2][3]) && strings[2][0].equals(strings[2][4]) && !strings[2][0].equals("")) {
            return true;
        }
        if (strings[3][0].equals(strings[3][1]) && strings[3][0].equals(strings[3][2]) && strings[3][0].equals(strings[3][3]) && strings[3][0].equals(strings[3][4]) && !strings[3][0].equals("")) {
            return true;
        }
        if (strings[4][0].equals(strings[4][1]) && strings[4][0].equals(strings[4][2]) && strings[4][0].equals(strings[4][3]) && strings[4][0].equals(strings[4][4]) && !strings[4][0].equals("")) {
            return true;
        }


        if (strings[0][0].equals(strings[1][0]) && strings[0][0].equals(strings[2][0]) && strings[0][0].equals(strings[3][0]) && strings[0][0].equals(strings[4][0]) && !strings[0][0].equals("")) {
            return true;
        }
        if (strings[0][1].equals(strings[1][1]) && strings[0][1].equals(strings[2][1]) && strings[0][1].equals(strings[3][1]) && strings[0][1].equals(strings[4][1]) && !strings[0][1].equals("")) {
            return true;
        }
        if (strings[0][2].equals(strings[1][2]) && strings[0][2].equals(strings[2][2]) && strings[0][2].equals(strings[3][2]) && strings[0][2].equals(strings[4][2]) && !strings[0][2].equals("")) {
            return true;
        }
        if (strings[0][3].equals(strings[1][3]) && strings[0][3].equals(strings[2][3]) && strings[0][3].equals(strings[3][3]) && strings[0][3].equals(strings[4][3]) && !strings[0][3].equals("")) {
            return true;
        }
        if (strings[0][4].equals(strings[1][4]) && strings[0][4].equals(strings[2][4]) && strings[0][4].equals(strings[3][4]) && strings[0][4].equals(strings[4][4]) && !strings[0][4].equals("")) {
            return true;
        }


        if (strings[0][0].equals(strings[1][1]) && strings[0][0].equals(strings[2][2]) && strings[0][0].equals(strings[3][3]) && strings[0][0].equals(strings[4][4]) && !strings[0][0].equals("")) {
            return true;
        }
        if (strings[0][4].equals(strings[1][3]) && strings[0][4].equals(strings[2][2]) && strings[0][4].equals(strings[3][1]) && strings[0][4].equals(strings[4][0]) && !strings[0][4].equals("")) {
            return true;
        }
        return false;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("usingAI", usingAI);
        outState.putInt("roundCount", roundCount);
        outState.putBoolean("frozen", frozen);
        outState.putBoolean("player1Turn", player1Turn);
        outState.putInt("player2_points", player2_points);
        outState.putInt("player1_points", player1_points);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        usingAI = savedInstanceState.getBoolean("usingAI", usingAI);
        frozen = savedInstanceState.getBoolean("frozen", frozen);
        player1Turn = savedInstanceState.getBoolean("player1Turn", player1Turn);
        roundCount = savedInstanceState.getInt("roundCount", roundCount);
        player1_points = savedInstanceState.getInt("player1_points", player1_points);
        player2_points = savedInstanceState.getInt("player2_points", player2_points);
    }

    class MyAI {
        //which letter is the AI using
        String comLetter;
        Random gen;

        public MyAI() {
            gen = new Random();
        }


        // a clone button.onClick for AI
        public void click(View p1) {
            if (frozen) return;
            if (!((Button) p1).getText().toString().equals("")) {
                return;
            }
            roundCount++;
            if (player1Turn) {
                ((Button) p1).setText("x");
            } else {
                ((Button) p1).setText("o");
            }
            if (checkForWin()) {
                if (player1Turn) {
                    playerWins(1);
                } else playerWins(2);
            } else if (roundCount == 25) {
                playerWins(0);
            } else {
                player1Turn = !player1Turn;
            }
        }

        /*Hierarchy of Danger
         *Four cells about to win
         *Three cells and empty cell gives three ways
         *Three cells and empty cell gives two ways and another three cells
         *Three cells and empty cell gives four cells and two other three cells
         *Three cells and empty cell gives two ways
         *Three cells about to get four
         *Two cells and empty cell gives three ways
         *Two cells and empty cell gives two ways
         *Two cells and about to get three
         *One cell and empty cell gives three ways
         *One cell and empty cell gives two ways
         *Free row
         *A formula for danger would be
         *n = number of cells
         *a = number of ways to be completed to n
         *b = forplayer?1:0
         *E = summation(a*10^n)+ab
         *Danger = E<200?E:0
         */
        private int getBestCell() {
            int opposing;
            int supporting;
            int ncol, nrow, ncross, ncross2, maxhelp;
            int bestCell = -1;
            int bestDanger = -1;
            int danger;
            for (int i = 0; i < 5; i++)
                for (int j = 0; j < 5; j++) {
                    //for each cell check the benefits of picking it
                    if (!strings[i][j].equals("")) continue;
                    danger = 0;
                    opposing = 0;
                    supporting = 0;
                    nrow = 0;
                    ncol = 0;
                    ncross = 0;
                    ncross2 = 0;
                    maxhelp = 0;
                    for (int k = 0; k < 5; k++) {
                        //check the column to get number of opposing/supporting cells
                        if (strings[k][j].equals(comLetter)) supporting++;
                        else if (!strings[k][j].equals("")) opposing++;
                    }
                    //a mixed up column will not win;
                    if (opposing * supporting == 0) ncol = opposing + supporting;
                    if (!(opposing < supporting)) maxhelp = ncol > maxhelp ? ncol : maxhelp;

                    opposing = 0;
                    supporting = 0;
                    for (int k = 0; k < 5; k++) {
                        //check the row to get number of opposing/supporting cells
                        if (strings[i][k].equals(comLetter)) supporting++;
                        else if (!strings[i][k].equals("")) opposing++;
                    }
                    //a mixed up row will not win;
                    if (opposing * supporting == 0) nrow = opposing + supporting;
                    if (!(opposing < supporting)) maxhelp = nrow > maxhelp ? nrow : maxhelp;

                    opposing = 0;
                    supporting = 0;
                    if (i == j) {
                        //it is on a major diagonal
                        for (int k = 0; k < 5; k++) {
                            //check the diagonal to get number of opposing/supporting cells
                            if (strings[k][k].equals(comLetter)) supporting++;
                            else if (!strings[k][k].equals("")) opposing++;
                        }
                        //a mixed up diagonal will not win;
                        if (opposing * supporting == 0) ncross = opposing + supporting;
                        if (!(opposing < supporting)) maxhelp = ncross > maxhelp ? ncross : maxhelp;
                    }

                    opposing = 0;
                    supporting = 0;
                    if (4 - i == j) {
                        //it is on a major diagonal
                        for (int k = 0; k < 5; k++) {
                            //check the diagonal to get number of opposing/supporting cells
                            if (strings[4 - k][k].equals(comLetter)) supporting++;
                            else if (!strings[4 - k][k].equals("")) opposing++;
                        }
                        //a mixed up diagonal will not win;
                        if (opposing * supporting == 0) ncross2 = opposing + supporting;
                        if (!(opposing < supporting))
                            maxhelp = ncross2 > maxhelp ? ncross2 : maxhelp;
                    }

                    int a;
                    for (int k = 0; k < 5; k++) {
                        a = 0;
                        if (nrow == k) a++;
                        if (ncol == k) a++;
                        if (ncross == k) a++;
                        if (ncross2 == k) a++;
                        danger += a * Math.pow(10, k);
                    }
                    danger += maxhelp;
                    if (danger > bestDanger) {
                        bestCell = 5 * i + j;
                        bestDanger = danger;
                    }
                    //be random in picking between the options
                    else if (danger == bestDanger) {
                        boolean use = gen.nextInt() > gen.nextInt();
                        bestDanger = use ? danger : bestDanger;
                        bestCell = use ? i * 5 + j : bestCell;
                    }
                }
            return bestCell;
        }

        public void makeMove() {
            comLetter = player1Turn ? "o" : "x";
            int cell = getBestCell();
            click(buttons[cell / 5][cell % 5]);
        }
    }

}

