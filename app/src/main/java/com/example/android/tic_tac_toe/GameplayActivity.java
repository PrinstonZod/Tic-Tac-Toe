package com.example.android.tic_tac_toe;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameplayActivity extends Activity {
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
        setContentView(R.layout.activity_gameplay);
        textview1 = findViewById(R.id.text_view_p1);
        myAI = new MyAI();
        player1Turn = true;
        roundCount = 0;
        player2_points = 0;
        usingAI = getIntent().getExtras().getBoolean("usingAI", true);
        player1_points = 0;
        textview2 = findViewById(R.id.text_view_p2);
        buttons = new Button[3][3];
        strings = new String[3][3];
        String buttonID;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
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
                        } else if (roundCount == 3 * 3) {
                            playerWins(0);
                        } else {
                            player1Turn = !player1Turn;
                        }
                        if (usingAI) {
                            //frozen = true;
                            //handler.postDelayed(new Thread(){
                            //public  void run()
                            //{
                            myAI.makeMove();
                            //frozen = false;
                            //}
                            //}, 500);
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
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
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
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                strings[i][j] = buttons[i][j].getText().toString();
            }
    }

    public boolean checkForWin() {
        resetStrings();
        if (strings[0][0].equals(strings[0][1]) && strings[0][0].equals(strings[0][2]) && !strings[0][0].equals("")) {
            return true;
        }
        if (strings[1][0].equals(strings[1][1]) && strings[1][0].equals(strings[1][2]) && !strings[1][0].equals("")) {
            return true;
        }
        if (strings[2][0].equals(strings[2][1]) && strings[2][0].equals(strings[2][2]) && !strings[2][0].equals("")) {
            return true;
        }
        if (strings[0][0].equals(strings[1][0]) && strings[0][0].equals(strings[2][0]) && !strings[0][0].equals("")) {
            return true;
        }
        if (strings[0][1].equals(strings[1][1]) && strings[0][1].equals(strings[2][1]) && !strings[0][1].equals("")) {
            return true;
        }
        if (strings[0][2].equals(strings[1][2]) && strings[0][2].equals(strings[2][2]) && !strings[0][2].equals("")) {
            return true;
        }
        if (strings[0][0].equals(strings[1][1]) && strings[0][0].equals(strings[2][2]) && !strings[0][0].equals("")) {
            return true;
        }
        if (strings[0][2].equals(strings[1][1]) && strings[0][2].equals(strings[2][0]) && !strings[0][2].equals("")) {
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

        // a clone button.onClick for AI
        public void click(View p1) {
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
            } else if (roundCount == 9) {
                playerWins(0);
            } else {
                player1Turn = !player1Turn;
            }
        }


        //check if player is about to win and block
        //check vertically
        private int getDangerV(int column) {
            int danger = 0;
            int empty = 0;
            if (strings[0][column].equals(comLetter)) return -1;
            else if (!strings[0][column].equals("")) danger++;
            else empty = 0;

            if (strings[1][column].equals(comLetter)) return -1;
            else if (!strings[1][column].equals("")) danger++;
            else empty = 1;
            if (strings[2][column].equals(comLetter)) return -1;
            else if (!strings[2][column].equals("")) danger++;
            else empty = 2;
            if (danger == 2) {
                return empty;
            }
            return -1;
        }

        //check horizontally
        private int getDangerH(int column) {
            int danger = 0;
            int empty = 0;
            if (strings[column][0].equals(comLetter)) return -1;
            else if (!strings[column][0].equals("")) danger++;
            else empty = 0;
            if (strings[column][1].equals(comLetter)) return -1;
            else if (!strings[column][1].equals("")) danger++;
            else empty = 1;
            if (strings[column][2].equals(comLetter)) return -1;
            else if (!strings[column][2].equals("")) danger++;
            else empty = 2;
            if (danger == 2) {
                return empty;
            }
            return -1;
        }

        //check diagonally
        private int getDangerCross() {
            int danger = 0;
            int empty = 0;
            if (strings[0][0].equals(comLetter)) {
                return -1;
            } else {
                if (!strings[0][0].equals("")) {
                    danger++;
                } else empty = 0;
            }
            if (strings[1][1].equals(comLetter)) {
                return -1;
            } else if (!strings[1][1].equals("")) {
                danger++;
            } else empty = 4;
            if (strings[2][2].equals(comLetter)) {
                return -1;
            } else if (!strings[2][2].equals("")) {
                danger++;
            } else empty = 8;
            if (danger == 2) {
                return empty;
            }
            danger = 0;
            if (strings[0][2].equals(comLetter)) return -1;
            else if (!strings[0][2].equals("")) danger++;
            else empty = 2;
            if (strings[1][1].equals(comLetter)) return -1;
            else if (!strings[1][1].equals("")) danger++;
            else empty = 4;
            if (strings[2][0].equals(comLetter)) return -1;
            else if (!strings[2][0].equals("")) danger++;
            else empty = 6;
            if (danger == 2) {
                return empty;
            }
            return -1;
        }

        private int getAdjacentBlock() {
            int[] blocks = new int[9];
            int bl_index = 0;
            int cells = 0;
            int cell;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    //for each cell; if there is a letter adjacent
                    if (strings[i][j] == comLetter) {
                        //check row
                        for (int k = 0; k < 3; k++) {
                            if (strings[k][j].equals("") || k == i)
                                cells += 1;
                        }
                        if (cells == 3) {
                            //free row, add all the free cells to block pool
                            for (int k = 0; k < 3; k++)
                                if (k != i) {
                                    cell = k * 3 + j;
                                    //see if the cell is already in the pool
                                    for (int l = 0; l < bl_index; l++) {
                                        if (blocks[l] == cell) {
                                            //the block will give two ways
                                            return cell;
                                        }
                                    }
                                    blocks[bl_index] = cell;
                                    bl_index += 1;
                                }
                        }
                        cells = 0;
                        //otherwise ignore row
                        //check column
                        for (int k = 0; k < 3; k++) {
                            if (strings[i][k].equals("") || k == j)
                                cells += 1;
                        }
                        if (cells == 3) {
                            //free column, add all the free cells to block pool
                            for (int k = 0; k < 3; k++)
                                if (k != j) {
                                    cell = i * 3 + k;
                                    //see if the cell is already in the pool
                                    for (int l = 0; l < bl_index; l++) {
                                        if (blocks[l] == cell) {
                                            //the block will give two ways
                                            return cell;
                                        }
                                    }
                                    blocks[bl_index] = cell;
                                    bl_index += 1;
                                }
                        }
                        cells = 0;
                        //otherwise ignore column
                    }
                    //next cell
                }
            }
            if (bl_index > 0) {
                return blocks[0];
            }
            return -1;
        }

        public void makeMove() {
            comLetter = player1Turn ? "o" : "x";
            //a very quick hack to get the Ai to try to win. Basically by changing comLetter I trick it into
            //believing it is player1 trying to block itself and in essence winning.
            //realised that this should come first ie block yourself before blocking your opponent
            for (int i = 0; i < 3; i++) {
                if (getDangerV(i) > -1) {
                    click(buttons[getDangerV(i)][i]);
                    return;
                }
                if (getDangerH(i) > -1) {
                    click(buttons[i][getDangerH(i)]);
                    return;
                }
                if (getDangerCross() > -1) {
                    click(buttons[getDangerCross() / 3][getDangerCross() % 3]);
                    return;
                }
            }

            comLetter = player1Turn ? "x" : "o";
            for (int i = 0; i < 3; i++) {
                if (getDangerV(i) > -1) {
                    click(buttons[getDangerV(i)][i]);
                    return;
                }
                if (getDangerH(i) > -1) {
                    click(buttons[i][getDangerH(i)]);
                    return;
                }
                if (getDangerCross() > -1) {
                    click(buttons[getDangerCross() / 3][getDangerCross() % 3]);
                    return;
                }
            }
            if (getAdjacentBlock() > -1) {
                click(buttons[getAdjacentBlock() / 3][getAdjacentBlock() % 3]);
                return;
            }
            if (strings[1][1].equals("")) {
                click(buttons[1][1]);
                return;
            }
            for (int i = 2; i > -1; i--)
                for (int j = 0; j < 3; j++) {
                    if (strings[i][j].equals("")) {
                        click(buttons[i][j]);
                        return;
                    }
                }
        }
    }
}
