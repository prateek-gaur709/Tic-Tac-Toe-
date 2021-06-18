package com.example.gameconnect3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /*   Handler mHandler=new Handler();
         Runnable mRunnable= new  Runnable() {
           @Override
           public void run() {
                playAgainLayout.setVisibility(View.VISIBLE);
           }

       }
   */
    boolean gameIsActive=true;
    int[] gameState = {0,0,0,0,0,0,0,0,0};  //0 means unplayed--gamestate is determining whether counter is played or not,for every 9 counter
    int activePlayer = 1;
    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{2,4,6},{0,4,8}}; //winning positions of each counter

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        System.out.println(counter.getTag().toString());
        int taggedCounter = Integer.parseInt(counter.getTag().toString());
        if (gameState[taggedCounter] == 0 && gameIsActive==true) {

            gameState[taggedCounter] = activePlayer;  //played ...that counter is filled now.....very imp line..it is doing two jobs simultaneously
            //1.played or not
            //2.filling gamestate array with only two players(golden=2,blue=1) at the clicked counters.
            //after this,gamestate array be like={1,2,1,2,1,2,1,2,2}...randomly clicked on counters

            counter.setTranslationY(-1000f);
            if (activePlayer == 1) {
                counter.setImageResource(R.drawable.blue);
                activePlayer = 2;//golden
            } else {
                counter.setImageResource(R.drawable.goldenball);
                activePlayer = 1;//blue
            }

            counter.animate().translationYBy(1000f).rotation(1800).setDuration(300);

            for (int[] winningPosition : winningPositions)
            {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]]
                        && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                        && gameState[winningPosition[0]]!= 0) {

                    gameIsActive=false;
                    System.out.println(gameState[winningPosition[0]]);

                // this condition is checking whether
                // at the wiining positions,same color ball is present
                //or not
                //ex. Blue == Blue== Blue and not empty then won.

                    String winner = "Golden";
                    if (gameState[winningPosition[0]] == 1) {
                        winner = "Blue";
                    }

                    TextView winnerMsg = (TextView) findViewById(R.id.winnerMsg);
                    winnerMsg.setText(winner + " has won");

                    LinearLayout playAgainLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    playAgainLayout.animate().scaleX(1.25f).scaleY(1.25f).setDuration(7000);
                    // mHandler.postDelayed(mRunnable,4000);
                    playAgainLayout.setVisibility(View.VISIBLE);

                } else {
                    boolean gameIsOver=true;
                    for (int counterState : gameState){
                        if (counterState==0) gameIsOver=false;}

                    if (gameIsOver){
                        TextView winnerMsg = (TextView) findViewById(R.id.winnerMsg);
                        winnerMsg.setText("Its a draw");
                        LinearLayout playAgainLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                        playAgainLayout.setVisibility(View.VISIBLE);

                    }
                    }

                }
            }
        }

    public void playAgain(View view){
        gameIsActive=true;
        LinearLayout playAgain=(LinearLayout) findViewById(R.id.playAgainLayout);
        playAgain.setVisibility(View.INVISIBLE);

        activePlayer=1;
        for (int i=0;i<gameState.length;i++) {
            gameState[i] = 0;
        }

        GridLayout gridLayout=(GridLayout)findViewById(R.id.gridLayout);
        for (int i=0;i<gridLayout.getChildCount();i++){
            ImageView image=(ImageView) gridLayout.getChildAt(i);
            image.setImageResource(0);
        }

    }}
