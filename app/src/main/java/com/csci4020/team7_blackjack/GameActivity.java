package com.csci4020.team7_blackjack;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    private TextView playerScore;
    private ImageView dealerCardOne, dealerCardTwo;
    private ImageView playerCardOne, getPlayerCardTwo;
    private String[] deckOfCards;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blackjack_activity);
        deckOfCards = {};
    }

    @Override
    public void onClick(View view) {

    }

    private class Deck {

    }
}
