package com.example.ngailapdi.gtscore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MatchActivity extends AppCompatActivity {
    private TextView player1;
    private TextView player2;
    private EditText score1;
    private EditText score2;
    private Button saveScoreButton;
    private DatabaseReference database;
    private String player1Name;
    private String player2Name;
    private Button addOne;
    private Button addTwo;
    private Button minusOne;
    private Button minusTwo;
    private int scorep1 = 0;
    private int scorep2 = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        Intent intent = getIntent();
        final String p1ID = intent.getStringExtra("p1ID");
        final String p2ID = intent.getStringExtra("p2ID");
        String s1 = intent.getStringExtra("s1");
        String s2 = intent.getStringExtra("s2");
        final String gameID = intent.getStringExtra("gameID");
        final String matchID = intent.getStringExtra("matchID");
        String player1Name = intent.getStringExtra("p1Name");
        String player2Name = intent.getStringExtra("p2Name");

        player1 = (TextView) findViewById(R.id.player1);
        player2 = (TextView) findViewById(R.id.player2);
        database = FirebaseDatabase.getInstance().getReference();
        score1 = (EditText) findViewById(R.id.score1);
        score2 = (EditText) findViewById(R.id.score2);
        addOne = (Button) findViewById(R.id.add1);
        addOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scorep1 += 1;
                score1.setText(scorep1);
            }
        });
        addTwo = (Button) findViewById(R.id.add2);
        addTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scorep2 += 1;
                score2.setText(scorep2);
            }
        });

        minusOne = (Button) findViewById(R.id.minus1);
        minusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scorep1 -= 1;
                score1.setText(scorep1);
            }
        });
        minusTwo = (Button) findViewById(R.id.minus2);
        minusTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scorep2 -= 1;
                score2.setText(scorep2);
            }
        });


        player1.setText(player1Name);
        player2.setText(player2Name);
        saveScoreButton = (Button) findViewById(R.id.saveScore);
        saveScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseU11 = database.child("Users/" + p1ID + "/matches/" + matchID + "/score1/");
                DatabaseReference databaseU12 = database.child("Users/" + p1ID + "/matches/" + matchID + "/score2/");
                DatabaseReference databaseU21 = database.child("Users/" + p2ID + "/matches/" + matchID + "/score1/");
                DatabaseReference databaseU22 = database.child("Users/" + p2ID + "/matches/" + matchID + "/score2/");



                databaseU11.setValue(Integer.parseInt(score1.getText().toString()));
                databaseU21.setValue(Integer.parseInt(score1.getText().toString()));

                databaseU12.setValue(Integer.parseInt(score2.getText().toString()));
                databaseU22.setValue(Integer.parseInt(score2.getText().toString()));


            }
        });

    }
}
