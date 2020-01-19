package com.julia.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    boolean gameIsActive = true;
    // 1 - yellow, -1 - red
    int activePlayer = 1;
    int[] gameState = {0,0,0,0,0,0,0,0,0};
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    public void finishGame(int winningPlayer){
        TextView winningMessage = findViewById(R.id.winningMessage);
        if (winningPlayer == 1) {
            winningMessage.setText("Congratulations!\n\nYellow wins :)");
        } else if (winningPlayer == -1){
            winningMessage.setText("Congratulations!\n\nRed wins :)");
        } else {
            winningMessage.setText("That's a draw!");
        }
        LinearLayout playAgain = findViewById(R.id.playAgainLayout);
        playAgain.setVisibility(View.VISIBLE);
        gameIsActive = false;
    }

    public void dropIn(View view){
        if(gameIsActive){
            ImageView counter = (ImageView) view;
            int tappedCounter = Integer.parseInt(counter.getTag().toString());
            if (gameState[tappedCounter] == 0) {
                if (activePlayer == 1) {
                    counter.setImageResource(R.drawable.yellow);
                } else {
                    counter.setImageResource(R.drawable.red);
                }
                counter.animate().rotation(360).setDuration(300);
                gameState[tappedCounter] = activePlayer;
                activePlayer = activePlayer * (-1);
            }
            for (int[] winningPosition: winningPositions){
                if(gameState[winningPosition[0]] == gameState[winningPosition[1]]
                        && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                        && gameState[winningPosition[0]] != 0){
                    finishGame(gameState[winningPosition[0]]);
                } else {
                    boolean gameIsOver = true;
                    for (int counterState : gameState){
                        if(counterState == 0) {
                            gameIsOver = false;
                        }
                    }
                    if (gameIsOver) {
                        finishGame(0);
                    }
                }
            }
        }
    }

    public void startNewGame(View view) {
        gameIsActive = true;
        LinearLayout playAgain = findViewById(R.id.playAgainLayout);
        playAgain.setVisibility(View.INVISIBLE);
        activePlayer = 1;
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 0;
        }
        TableLayout grid = findViewById(R.id.tableLayout);
        for (int i = 0; i < grid.getChildCount(); i++) {
            TableRow row = (TableRow)grid.getChildAt(i);
            for(int j = 0; j < row.getChildCount(); j++){
                ((ImageView) row.getChildAt(j)).setImageResource(0);
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        }
    }
