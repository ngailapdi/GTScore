package com.example.ngailapdi.gtscore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private boolean played = true;

    @Override
    public void onResume() {
        super.onResume();
        score1.setText(String.valueOf(scorep1));
        score2.setText(String.valueOf(scorep2));

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        Intent intent = getIntent();
        final String p1ID = intent.getStringExtra("p1ID");
        final String p2ID = intent.getStringExtra("p2ID");
        String s1 = intent.getStringExtra("s1");
        String s2 = intent.getStringExtra("s2");
        final String matchID = intent.getStringExtra("matchID");
        String player1Name = intent.getStringExtra("p1Name");
        String player2Name = intent.getStringExtra("p2Name");

        player1 = (TextView) findViewById(R.id.player1);
        player2 = (TextView) findViewById(R.id.player2);
        database = FirebaseDatabase.getInstance().getReference();
        score1 = (EditText) findViewById(R.id.score1);
        score2 = (EditText) findViewById(R.id.score2);
        addOne = (Button) findViewById(R.id.add1);
        final DatabaseReference databaseU1 = database.child("Users/" + p1ID + "/games/" + matchID);
        databaseU1.child("/score1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long s1 = (long) dataSnapshot.getValue();

                score1.setText(s1 + "");
                scorep1 = (int) s1;

            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });
        databaseU1.child("/score2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long s2 = (long) dataSnapshot.getValue();

                score2.setText(s2 + "");
                scorep2 = (int) s2;

            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });




        player1.setText(player1Name);
        player2.setText(player2Name);



    }
}
