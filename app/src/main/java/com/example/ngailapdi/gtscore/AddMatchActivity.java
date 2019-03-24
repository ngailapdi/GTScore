package com.example.ngailapdi.gtscore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddMatchActivity extends AppCompatActivity {
    private EditText inputName;
    private EditText opEmail;
    private Button save;
    private Button cancel;

    private DatabaseReference database;
    private FirebaseUser user;
    private Spinner userSpinner;
    private List<User> friends;
    private List<String> friendsName;
    private User opponent;
    private ArrayAdapter<String> friendsNameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_match);
        inputName = (EditText) findViewById(R.id.nameMatchText);
        save = (Button) findViewById(R.id.saveAddMatchBtn);
        cancel = (Button) findViewById(R.id.cancelAddMatchBtn);
        database = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        opEmail = (EditText) findViewById(R.id.op_email);
        friends = new ArrayList<>();
        friendsName = new ArrayList<>();

        database.child("Users/" + user.getUid() + "/friends")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        User friend = dataSnapshot.getValue(User.class);

                        // Notify the ArrayAdapter that there was a change
                        friends.add(friend);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        Intent intent = getIntent();
        final String gameID = intent.getStringExtra("gameID");
        final String gameName = intent.getStringExtra("gameName");
        final String gameDescription = intent.getStringExtra("gameDescription");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (attemptSave()) {
                    System.out.println("_------------gameID: " + gameID);
                    for (User friend : friends) {
                        if (opEmail.getText().toString().equals(friend.getEmail())) {
                            opponent = friend;
                        }
                    }
                    if (opponent == null) {
                        System.out.println("NOT FOUND");
                        Log.d("error not found", "This person is not in your contact list");
                    }
//
                    DatabaseReference databaseMatch = database.child("Users/" + user.getUid() + "/matches");
                    DatabaseReference newMatch = databaseMatch.push();
                    String matchID = newMatch.getKey();
                    Match match = new Match(inputName.getText().toString(),
                            user.getUid(), opponent.getUid(), user.getDisplayName(), opponent.getName(), gameID);
                    match.setMatchID(matchID);

                    newMatch.setValue(match);

                    Game g2 = new Game(gameName, gameDescription);
                    g2.setGameID(gameID);
                    database.child("Users/" + opponent.getUid() + "/games/" + gameID).setValue(g2);

                    databaseMatch = database.child("Users/" + opponent.getUid() + "/matches/" + matchID);
                    Match matchOp = new Match(inputName.getText().toString(), opponent.getUid(),
                            user.getUid(), opponent.getName(), user.getDisplayName(), gameID);
                    matchOp.setMatchID(matchID);

                    databaseMatch.setValue(matchOp);


                    Log.d("Add match", "Add match success");
                    updateUI(user);

                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        Intent returnIntent = new Intent();
        if (user != null) {
            setResult(Activity.RESULT_OK, returnIntent);
        } else {
            setResult(Activity.RESULT_CANCELED, returnIntent);
        }
        finish();

    }

    private boolean attemptSave() {
        return true;
    }

}

