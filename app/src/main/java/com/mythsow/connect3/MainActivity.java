package com.mythsow.connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button playAgainButton;
    TextView winnerTextView;
    GridLayout gridLayout;

    //0:No Coin, 1: Yellow, 2:Red
    int[] gameState = {0,0,0,0,0,0,0,0,0};

    int activePlayer = 1;
    boolean gameRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        winnerTextView = (TextView) findViewById(R.id.winnerTextView);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        restartGame();
    }

    private void restartGame() {
        playAgainButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);

        for(int i=0; i<gridLayout.getChildCount(); i++) {

            ImageView counter = (ImageView) gridLayout.getChildAt(i);

            counter.setImageDrawable(null);

        }

        for (int i=0; i<gameState.length; i++) {

            gameState[i] = 0;

        }

        activePlayer = 1;

        gameRunning = true;
    }


    public void dropCoin(View view) {

        ImageView coin = (ImageView) view;

        int droppedCoin = Integer.parseInt(coin.getTag().toString());

        if(!gameRunning) {
            Toast.makeText(getApplicationContext(),"Please restart the game to play again", Toast.LENGTH_LONG).show();
            return;
        }

        if(gameState[droppedCoin] == 0) {
            gameState[droppedCoin] = activePlayer;
            coin.setTranslationY(-1500);

            if(activePlayer == 1) {
                coin.setImageResource(R.drawable.yellow);
                activePlayer = 2;
            } else {
                coin.setImageResource(R.drawable.red);
                activePlayer = 1;
            }

            coin.animate().translationYBy(1500).rotation(3600).setDuration(300);


            boolean isgGameWon = checkIfGameWon(droppedCoin);

            if(isgGameWon) {
                String winner;
                gameRunning = false;

                if(activePlayer == 1) {
                    winner = "Red";
                } else {
                    winner = "Yellow";
                }

                winnerTextView.setText(winner + " has won");

                playAgainButton.setVisibility(View.VISIBLE);

                winnerTextView.setVisibility(View.VISIBLE);
            }

            boolean isGridFull = true;
            for(int i=0;i< gameState.length;i++) {
                if(gameState[i] == 0) {
                    isGridFull = false;
                }
            }

            if(isGridFull) {
                winnerTextView.setText("Draw Game");
                playAgainButton.setVisibility(View.VISIBLE);

                winnerTextView.setVisibility(View.VISIBLE);
            }


        } else {
            Toast.makeText(getApplicationContext(),"Please place coin in a new grid", Toast.LENGTH_LONG).show();
            return;
        }
    }

    private boolean checkIfGameWon(int droppedCoin) {

        boolean diagnolWon = checkIfDiagnolWon(droppedCoin);

        boolean horizontalWon = checkIfHorizontalWon(droppedCoin);

        boolean verticalWon = checkIfVerticalWon(droppedCoin);

        return diagnolWon || horizontalWon || verticalWon;
    }

    private boolean checkIfVerticalWon(int droppedCoin) {

        int up = -3;
        int down = 3;

        while(droppedCoin + up >=0) {

            if(gameState[droppedCoin + up] != gameState[droppedCoin]) {
                return false;
            }
            up = up-3;
        }

        while(droppedCoin + down < gameState.length) {

            if(gameState[droppedCoin + down] != gameState[droppedCoin]) {
                return false;
            }
            down = down+3;
        }

        return true;
    }

    private boolean checkIfHorizontalWon(int droppedCoin) {

        int right = 1;
        int left = -1;

        int max = ((droppedCoin/3) * 3) + 3;
        int min = ((droppedCoin/3) * 3) - 1;
        while(droppedCoin + right < max) {

            if(gameState[droppedCoin + right] != gameState[droppedCoin]) {
                return false;
            }
            right = right + 1;
        }

        while(droppedCoin + left > min) {

            if(gameState[droppedCoin + left] != gameState[droppedCoin]) {
                return false;
            }
            left = left - 1;
        }

        return true;
    }

    private boolean checkIfDiagnolWon(int droppedCoin) {

        if(droppedCoin % 4 == 0) {

            int rightDiag = 4;
            int leftDiag = -4;

            int max = gameState.length;
            int min = -1;
            while(droppedCoin + rightDiag < max) {

                if(gameState[droppedCoin + rightDiag] != gameState[droppedCoin]) {
                    return false;
                }
                rightDiag = rightDiag + 4;
            }

            while(droppedCoin + leftDiag > min) {

                if(gameState[droppedCoin + leftDiag] != gameState[droppedCoin]) {
                    return false;
                }
                leftDiag = leftDiag - 4;
            }

            return true;
        } else if(droppedCoin %4 == 2) {

            int rightDiag = -2;
            int leftDiag = 2;

            int max = ((gameState.length -1)/3)*3;
            int min = (gameState.length -1)/3;
            while(droppedCoin + rightDiag >= min) {

                if(gameState[droppedCoin + rightDiag] != gameState[droppedCoin]) {
                    return false;
                }
                rightDiag = rightDiag -2;
            }

            while(droppedCoin + leftDiag <= max) {

                if(gameState[droppedCoin + leftDiag] != gameState[droppedCoin]) {
                    return false;
                }
                leftDiag = leftDiag + 2;
            }

            return true;

        } else {
            return false;
        }
    }

    public void restartGame(View view) {
        restartGame();
    }
}
