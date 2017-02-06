package com.csci4020.team7_blackjack;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Pigott on 2/6/2017.
 */

//TODO set up scoring rules.
//TODO set up a YOU WON/YOU LOST dialog.
//TODO set up card counting to prevent duplicates.
//TODO set up dealer switch cases.
//TODO add money to bet along with an amount option.

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private int playerScoreInt; //anything over 21 will be considered a bust
    private int dealerScoreInt;
    private int playerMoneyInt;
    private TextView playerScore, playerMoney;
    private ImageView dealerCardOne, dealerCardTwo;
    private ImageView playerCardOne, playerCardTwo;
    private boolean playerStand = false, dealerStand = false, justStarted = true;
    private Deck deck;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blackjack_activity);

        startGame();

        Button b = (Button) findViewById(R.id.hit_button);
        b.setOnClickListener(this);  //game doesn't start until player clicks hit
    }

    @Override
    public void onClick(View view) {
        if(justStarted) {
            playerTakesAHit();
            playerTakesAHit();
            dealerTakesAHit();
            dealerTakesAHit();
            playerScore.setText(playerScoreInt + "");
        } else {
            playerTakesAHit();
            playerScore.setText(playerScoreInt + "");
            if(bust(playerScoreInt)) {
             while(!bust(dealerScoreInt)) {
                 dealerTakesAHit();
             }
            }
            dealerTakesAHit();
        }
    }

    private void playerTakesAHit() { //of that good good
        String drawableName = "cards"; //just in case for some reason something breaks or something.
        Resources res = getResources();
        drawableName = deck.drawCard();
        int resID = res.getIdentifier(drawableName, "drawable", getPackageName());
        playerCardTwo.setImageDrawable(playerCardOne.getDrawable()); //sets playerCardTwo to show what was previously in playerCardOne
        playerCardOne.setImageResource(resID);
        playerScoreInt += deck.getValue(drawableName);
    }

    private void dealerTakesAHit() { //of that good good
        String drawableName = "cards"; //just in case for some reason something breaks or something.
        Resources res = getResources();
        drawableName = deck.drawCard();
        int resID = res.getIdentifier(drawableName, "drawable", getPackageName());
        if(justStarted) {
            dealerCardOne.setImageResource(resID);
            justStarted = false;
        }
        dealerScoreInt += deck.getValue(drawableName);
    }

    private Boolean bust(int score) {
    if(score <= 21) {
        return false;
    } else {
        return true;
    }

    }

    private void startGame() {
        //initializing deck to draw from.
        deck = new Deck();
        deck.setDeck();

        //card declarations.
        dealerCardOne = (ImageView) findViewById(R.id.dealer_card2); //tee-hee
        dealerCardTwo = (ImageView) findViewById(R.id.dealer_card1);
        playerCardOne = (ImageView) findViewById(R.id.player_card1);
        playerCardTwo = (ImageView) findViewById(R.id.player_card2);

        //player's score is the only thing that updates on screen.
        playerScore = (TextView) findViewById(R.id.player_score);

        //making sure scores start off at 0.
        playerScoreInt = dealerScoreInt = 0;
        playerScore.setText(playerScoreInt + "");

        //setting money to defaulted 200, max should be about 9999 maybe?
        playerMoneyInt = 200;
    }

    private class Deck {
        private String[] deckOfCards;

        private void setDeck() {
            deckOfCards = new String[]{"c_two", "c_three", "c_four", "c_five", "c_six", "c_seven", "c_eight",
            /*Clubs*/                  "c_nine", "c_ten", "c_jack", "c_queen", "c_king", "c_ace",
                                       "d_two", "d_three", "d_four", "d_five", "d_six", "d_seven", "d_eight",
            /*Diamonds*/               "d_nine", "d_ten", "d_jack", "d_queen", "d_king", "d_ace",
                                       "h_two", "h_three", "h_four", "h_five", "h_six", "h_seven", "h_eight",
            /*Hearts*/                 "h_nine", "h_ten", "h_jack", "h_queen", "h_king", "h_ace",
                                       "s_two", "s_three", "s_four", "s_five", "s_six", "s_seven", "s_eight",
            /*Spades*/                 "s_nine", "s_ten", "s_jack", "s_queen", "s_king", "s_ace"};
            //  Clubs: 0-12 | Diamonds: 13-25 | Hearts: 26-38 | Spades: 39 - 51
        }

        private Deck() {
            setDeck();
        }

        private String drawCard() {
            String card = "cards";
            Random r = new Random();
            int i = r.nextInt((51 - 0)); //I mean the array is set up for 0-51 so dunno
            Log.d("Draw Error",Integer.toString(i));
            do {                            //TODO: Use something that doesn't eventually result in deadlock.
                i = r.nextInt((51 - 0));    //this will eventually result in a deadlock
                                            //but a single game of blackjack shouldn't cause it.
            } while(deckOfCards[i].equals("drawn"));
            card = deckOfCards[i];
            deckOfCards[i] = "drawn";
            return card;
        }

        private void resetDeck() {
            setDeck();
        }

        private int getValue(String card) {
            int v = 0;
            switch(card) {
                case "c_two":
                case "d_two":
                case "h_two":
                case "s_two":
                    v = 2;
                    break;
                case "c_three":
                case "d_three":
                case "h_three":
                case "s_three":
                    v = 3;
                    break;
                case "c_four":
                case "d_four":
                case "h_four":
                case "s_four":
                    v = 4;
                    break;
                case "c_five":
                case "d_five":
                case "h_five":
                case "s_five":
                    v = 5;
                    break;
                case "c_six":
                case "d_six":
                case "h_six":
                case "s_six":
                    v = 6;
                    break;
                case "c_seven":
                case "d_seven":
                case "h_seven":
                case "s_seven":
                    v = 7;
                    break;
                case "c_eight":
                case "d_eight":
                case "h_eight":
                case "s_eight":
                    v = 8;
                    break;
                case "c_nine":
                case "d_nine":
                case "h_nine":
                case "s_nine":
                    v = 9;
                    break;
                case "c_ten":
                case "d_ten":
                case "h_ten":
                case "s_ten":
                case "c_jack":
                case "d_jack":
                case "h_jack":
                case "s_jack":
                case "c_queen":
                case "d_queen":
                case "h_queen":
                case "s_queen":
                case "c_king":
                case "d_king":
                case "h_king":
                case "s_king":
                    v = 10;
                    break;
                case "c_ace":
                case "d_ace":
                case "h_ace":
                case "s_ace":
                    v = 11;
                    break;
                case "cards":
                    v = 0;
                    Log.d("Draw Error","Card wasn't drawn properly.");
                    break;
            }
            return v;
        }
    }
}