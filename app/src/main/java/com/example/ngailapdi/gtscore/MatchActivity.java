package com.example.ngailapdi.gtscore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
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

        score1.setText(s1);
        score2.setText(s2);

        addOne = (Button) findViewById(R.id.add1);
        addOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scorep1++;
                score1.setText(scorep1 + "");
                DatabaseReference U11 =  database.child("Users/" + p1ID + "/games/" + matchID + "/score1/");
                U11.runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                        Integer s1 = scorep1;
                        mutableData.setValue(s1);
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

                    }
                });
                DatabaseReference U21 =  database.child("Users/" + p2ID + "/games/" + matchID + "/score2/");
                U11.runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                        Integer s1 = scorep1;
                        mutableData.setValue(s1);
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

                    }
                });

            }
        });
        addTwo = (Button) findViewById(R.id.add2);
        addTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scorep2++;
                score2.setText(scorep2 + "");
                DatabaseReference U11 =  database.child("Users/" + p1ID + "/games/" + matchID + "/score2/");
                U11.runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                        Integer s1 = scorep2;
                        mutableData.setValue(s1);
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

                    }
                });
                DatabaseReference U21 =  database.child("Users/" + p2ID + "/games/" + matchID + "/score1/");
                U11.runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                        Integer s1 = scorep2;
                        mutableData.setValue(s1);
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

                    }
                });
            }
        });

        minusOne = (Button) findViewById(R.id.minus1);
        minusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scorep1 > 0) {
                    scorep1 = scorep1 - 1;
                    score1.setText(scorep1 + "");
                    DatabaseReference U11 =  database.child("Users/" + p1ID + "/games/" + matchID + "/score1/");
                    U11.runTransaction(new Transaction.Handler() {
                        @NonNull
                        @Override
                        public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                            Integer s1 = scorep1;
                            mutableData.setValue(s1);
                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

                        }
                    });
                    DatabaseReference U21 =  database.child("Users/" + p2ID + "/games/" + matchID + "/score2/");
                    U11.runTransaction(new Transaction.Handler() {
                        @NonNull
                        @Override
                        public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                            Integer s1 = scorep1;
                            mutableData.setValue(s1);
                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

                        }
                    });
                }
            }
        });
        minusTwo = (Button) findViewById(R.id.minus2);
        minusTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scorep2 > 0) {
                    scorep2 = scorep2 - 1;
                    score2.setText(scorep2 + "");
                    DatabaseReference U11 =  database.child("Users/" + p1ID + "/games/" + matchID + "/score2/");
                    U11.runTransaction(new Transaction.Handler() {
                        @NonNull
                        @Override
                        public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                            Integer s1 = scorep2;
                            mutableData.setValue(s1);
                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

                        }
                    });
                    DatabaseReference U21 =  database.child("Users/" + p2ID + "/games/" + matchID + "/score1/");
                    U11.runTransaction(new Transaction.Handler() {
                        @NonNull
                        @Override
                        public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                            Integer s1 = scorep2;
                            mutableData.setValue(s1);
                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

                        }
                    });
                }
            }
        });



        player1.setText(player1Name);
        player2.setText(player2Name);

        saveScoreButton = (Button) findViewById(R.id.saveScore);
        saveScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder confirmSaveScoreBuilder = new AlertDialog.Builder(view.getContext());
                confirmSaveScoreBuilder.setMessage("Are you sure you want to end the game");
                confirmSaveScoreBuilder.setCancelable(true);

                confirmSaveScoreBuilder.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
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
                                if (Integer.parseInt(score1.getText().toString()) > Integer.parseInt(score2.getText().toString())) {
                                    System.out.println("GET HERE");
                                    final DatabaseReference winnerDatabase = database.child(("Users/" + p1ID + "/num_wins/"));
                                    winnerDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Integer numWins = 0;
                                            if (dataSnapshot.getValue() != null) {
                                                numWins = dataSnapshot.getValue(Integer.class);
                                            }
                                            numWins -= 1;
                                            System.out.println("------num wins: " + numWins);
                                            winnerDatabase.setValue(numWins);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                                Intent mainActivity = new Intent(MatchActivity.this, MainActivity.class);
                                mainActivity.putExtra("itemID","true");
                                startActivity(mainActivity);
                                dialog.cancel();
                            }
                        });

                confirmSaveScoreBuilder.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertConfirmSaveScore = confirmSaveScoreBuilder.create();
                alertConfirmSaveScore.show();


            }
        });

    }
}
