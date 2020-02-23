package com.example.lionortiger;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    enum Player {
        ONE, TWO
    }
    private Player currentPlayer = Player.TWO;
    private Player[] playerChoices = new Player[9];
    private int[][] winnerRowsColumns = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};
    private boolean gameOver = false;
    private Button btnReset;
    private GridLayout mGridLayout;
    private int currentTurn = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void imageViewIsTapped(View imageView) {
        ImageView tappedImageView = (ImageView) imageView;
        int tiTag = Integer.parseInt(tappedImageView.getTag().toString());
        btnReset = findViewById(R.id.btnReset);
        mGridLayout = findViewById(R.id.gridView);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restTheGame();
            }
        });


        //check if the view was tapped before.
        if (playerChoices[tiTag] == null && !gameOver) {
            currentTurn++;
            //show the image when user tap.
            tappedImageView.setTranslationX(-2000);
            //change the current player when the image is tapped.
            playerChoices[tiTag] = currentPlayer;
            if (currentPlayer == Player.ONE) {
                tappedImageView.setImageResource(R.drawable.lion);
                currentPlayer = Player.TWO;
            } else if (currentPlayer == Player.TWO) {
                tappedImageView.setImageResource(R.drawable.tiger);
                currentPlayer = Player.ONE;
            }
            tappedImageView.animate().translationXBy(2000).alpha(1).rotation(3600).setDuration(1000);
            Toast.makeText(this, tappedImageView.getTag().toString(), Toast.LENGTH_SHORT).show();


            //detect the condition of winning
            for (int[] winnerColumns : winnerRowsColumns) {
                if ((playerChoices[winnerColumns[0]] == Player.ONE &&
                        playerChoices[winnerColumns[1]] == Player.ONE &&
                        playerChoices[winnerColumns[2]] == Player.ONE)
                        || (playerChoices[winnerColumns[0]] == Player.TWO &&
                        playerChoices[winnerColumns[1]] == Player.TWO &&
                        playerChoices[winnerColumns[2]] == Player.TWO)
                ) {
                    gameOver = true;
                    AlertDialog.Builder winDialog = new AlertDialog.Builder(this);
                    String winnerOfGame = "";
                    if (currentPlayer == Player.ONE) {
                        winnerOfGame = "Player one wins!";
                    } else if (currentPlayer == Player.TWO) {
                        winnerOfGame = "Player two wins!";
                    }
                    winDialog.setMessage(winnerOfGame);
                    winDialog.setTitle("Win");
    //                winDialog.setPositiveButton("Finish the game", new DialogInterface.OnClickListener() {
    //                    @Override
    //                    public void onClick(DialogInterface dialog, int which) {
    //                        return;
    //                    }
    //                });
                    winDialog.show();
                }
            }
            //detect if all the images are tapped.
            if(gameOver || (currentTurn == 9)){
                btnReset.setVisibility(View.VISIBLE);
            }

        }

    }

    private void restTheGame() {
        for (int index = 0; index < mGridLayout.getChildCount(); index++){
            ImageView imageView = (ImageView) mGridLayout.getChildAt(index);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0f);
        }
        currentPlayer = Player.TWO;
        playerChoices = new Player[9];
        gameOver = false;
        currentTurn = 0;
        btnReset.setVisibility(View.INVISIBLE);
    }


}