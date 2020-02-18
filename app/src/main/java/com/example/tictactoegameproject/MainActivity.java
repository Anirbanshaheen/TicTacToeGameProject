package com.example.tictactoegameproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean playerOneTurn = true;
    private int roundCount;
    private int playerOnePoints;
    private int playerTwoPoints;

//    private TextView textView;

    private ImageView imageViewReset;

    private TextView textViewPlayerOne;
    private TextView textViewPlayerTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayerOne = findViewById(R.id.textViewPlayerOne);
        textViewPlayerTwo = findViewById(R.id.textViewPlayerTwo);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                String buttonID = "button_" + i + j;
                int resId = getResources().getIdentifier(buttonID, "id", getPackageName());

                buttons[i][j] = findViewById(resId);
                buttons[i][j].setOnClickListener(this);

            }
        }

        imageViewReset = findViewById(R.id.reset);
        imageViewReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });


    }

    @Override
    public void onClick(View v) {
        // Here checked, button was clicked and return this
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (playerOneTurn) {
            ((Button) v).setTextSize(30);
            ((Button) v).setText("X");
        } else {
            ((Button) v).setTextSize(30);
            ((Button) v).setText("O");
        }

        roundCount++;

        if (checkForWin()) {
            if (playerOneTurn) {
                playerOneWins();
            } else {
                playerTwoWins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            playerOneTurn = !playerOneTurn;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        // Initialize all button in field String
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        // For row validation check
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {

                return true;
            }
        }

        // For column validation check
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {

                return true;
            }
        }

        // For diagonal left to right
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {

            return true;
        }

        // For diagonal right to left
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {

            return true;
        }

        return false;
    }

    private void playerOneWins() {
        playerOnePoints++;
        Toast.makeText(this, "Player One Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();

    }

    private void playerTwoWins() {
        playerTwoPoints++;
        Toast.makeText(this, "Player Two Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();

    }

    private void draw() {
        Toast.makeText(this, "Draw !", Toast.LENGTH_LONG).show();
        resetBoard();

    }

    @SuppressLint("SetTextI18n")
    private void updatePointsText() {
        textViewPlayerOne.setText("Player One " + playerOnePoints);
        textViewPlayerTwo.setText("Player Two " + playerTwoPoints);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        playerOneTurn = true;

    }

    private void resetGame() {
        playerOnePoints = 0;
        playerTwoPoints = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("playerOnePoints", playerOnePoints);
        outState.putInt("playerTwoPoints", playerTwoPoints);
        outState.putBoolean("playerOne", playerOneTurn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        playerOnePoints = savedInstanceState.getInt("playerOnePoints");
        playerTwoPoints = savedInstanceState.getInt("playerTwoPoints");
        playerOneTurn = savedInstanceState.getBoolean("playerOne");
    }
}
