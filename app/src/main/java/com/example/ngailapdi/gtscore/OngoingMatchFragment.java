package com.example.ngailapdi.gtscore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OngoingMatchFragment extends Fragment {
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



    View inflatedView = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_profile, container, false);
        String compare = getArguments().getString("Allow");

        if(compare.equalsIgnoreCase("yes")) {
            final String p1ID = getArguments().getString("p1ID");
            final String p2ID = getArguments().getString("p2ID");
            String s1 = getArguments().getString("s1");
            String s2 = getArguments().getString("s2");
            final String matchID = getArguments().getString("matchID");
            String player1Name = getArguments().getString("p1Name");
            String player2Name = getArguments().getString("p2Name");

            player1 = (TextView) inflatedView.findViewById(R.id.player1);
            player2 = (TextView) inflatedView.findViewById(R.id.player2);
            database = FirebaseDatabase.getInstance().getReference();
            score1 = (EditText) inflatedView.findViewById(R.id.score1);
            score2 = (EditText) inflatedView.findViewById(R.id.score2);
            //addOne = (Button) inflatedView.findViewById(R.id.add1);
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
            addOne = (Button) inflatedView.findViewById(R.id.add1);
            addOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scorep1++;
                    score1.setText(scorep1 + "");
                }
            });
            addTwo = (Button) inflatedView.findViewById(R.id.add2);
            addTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scorep2++;
                    score2.setText(scorep2 + "");
                }
            });

            minusOne = (Button) inflatedView.findViewById(R.id.minus1);
            minusOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (scorep1 > 0) {
                        scorep1 = scorep1 - 1;
                        score1.setText(scorep1 + "");
                    }
                }
            });
            minusTwo = (Button) inflatedView.findViewById(R.id.minus2);
            minusTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (scorep2 > 0) {
                        scorep2 = scorep2 - 1;
                        score2.setText(scorep2 + "");
                    }
                }
            });


            player1.setText(player1Name);
            player2.setText(player2Name);


            saveScoreButton = (Button) inflatedView.findViewById(R.id.saveScore);
            saveScoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseReference databaseU11 = database.child("Users/" + p1ID + "/games/" + matchID + "/score1/");
                    DatabaseReference databaseU12 = database.child("Users/" + p1ID + "/games/" + matchID + "/score2/");
                    DatabaseReference databaseU21 = database.child("Users/" + p2ID + "/games/" + matchID + "/score1/");
                    DatabaseReference databaseU22 = database.child("Users/" + p2ID + "/games/" + matchID + "/score2/");
                    DatabaseReference databaseU13 = database.child("Users/" + p1ID + "/games/" + matchID + "/played/");
                    DatabaseReference databaseU23 = database.child("Users/" + p2ID + "/games/" + matchID + "/played/");

                    databaseU11.setValue(Integer.parseInt(score1.getText().toString()));
                    databaseU21.setValue(Integer.parseInt(score1.getText().toString()));
                    databaseU12.setValue(Integer.parseInt(score2.getText().toString()));
                    databaseU22.setValue(Integer.parseInt(score2.getText().toString()));
                    databaseU13.setValue(true);
                    databaseU23.setValue(true);


                }
            });
        }
        else
        {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(inflatedView.getContext());
            builder1.setMessage("You don't have any ongoing match");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
        return inflatedView;


        

    }


}
