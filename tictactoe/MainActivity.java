package com.example.tictactoe;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button[] buttons = new Button[9];
    private Button btnReset;
    private TextView tvTurn;

    private boolean isPlayerXTurn = true;
    private int moveCount = 0;
    private String[] board = new String[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTurn = findViewById(R.id.tvTurn);
        btnReset = findViewById(R.id.btnReset);

        for (int i = 0; i < 9; i++) {
            String buttonID = "button" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = findViewById(resID);
            final int index = i;

            buttons[i].setOnClickListener(view -> handleMove(index));
        }

        btnReset.setOnClickListener(view -> resetGame());
    }

    private void handleMove(int index) {
        if (board[index] != null) return;

        board[index] = isPlayerXTurn ? "X" : "O";
        buttons[index].setText(board[index]);
        moveCount++;

        if (checkWin()) {
            Toast.makeText(this, "Player " + board[index] + " wins!", Toast.LENGTH_LONG).show();
            disableButtons();
        } else if (moveCount == 9) {
            Toast.makeText(this, "It's a draw!", Toast.LENGTH_LONG).show();
        } else {
            isPlayerXTurn = !isPlayerXTurn;
            tvTurn.setText("Player " + (isPlayerXTurn ? "X" : "O") + " Turn");
        }
    }

    private boolean checkWin() {
        int[][] winConditions = {
                {0,1,2}, {3,4,5}, {6,7,8},
                {0,3,6}, {1,4,7}, {2,5,8},
                {0,4,8}, {2,4,6}
        };
        for (int[] condition : winConditions) {
            String a = board[condition[0]];
            String b = board[condition[1]];
            String c = board[condition[2]];

            if (a != null && a.equals(b) && a.equals(c)) return true;
        }
        return false;
    }

    private void disableButtons() {
        for (Button button : buttons) {
            button.setEnabled(false);
        }
    }

    private void resetGame() {
        for (int i = 0; i < 9; i++) {
            board[i] = null;
            buttons[i].setText("");
            buttons[i].setEnabled(true);
        }
        isPlayerXTurn = true;
        moveCount = 0;
        tvTurn.setText("Player X Turn");
    }
}