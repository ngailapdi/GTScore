package com.example.ngailapdi.gtscore;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CreateMatchFragment extends Fragment {
    private DatabaseReference databaseUser;
    private FirebaseUser user;
    private EditText inputNameGame;
    private Button createMatch;
    private Spinner opponentSpinner;
    private List<User> friends;
    private List<String> friendsName;
    private User opponent;
    private ArrayAdapter<String> friendsNameAdapter;
    private Match matchOp;
    private  Match match;



    View inflatedView = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_tournament, container, false);
        inputNameGame = (EditText) inflatedView.findViewById(R.id.game_name);
        createMatch = (Button) inflatedView.findViewById(R.id.create_match);
        opponentSpinner = (Spinner) inflatedView.findViewById(R.id.opponent_list);
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseUser = FirebaseDatabase.getInstance().getReference();
        friendsName = new ArrayList<>();
        friends = new ArrayList<>();
        databaseUser.child("Users/" + user.getUid() + "/friends")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        User opponent = dataSnapshot.getValue(User.class);
                        System.out.println("hahah");
                        friends.add(opponent);

                        // Notify the ArrayAdapter that there was a change
                        friendsNameAdapter.add(opponent.getName());
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
//        createList();

        friendsNameAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, friendsName);
        opponentSpinner.setAdapter(friendsNameAdapter);
        opponentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // An item was selected. You can retrieve the selected item using
//                 String opponentName = (String) parent.getItemAtPosition(pos);
                String opponentName = friendsName.get(pos);
                System.out.println("---------" + opponentName);
                opponent = friends.get(pos);
                System.out.println("---------spinner"+opponent.getUid());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        createMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseMatch = databaseUser.child("Users/" + user.getUid() + "/games");
                DatabaseReference newMatch = databaseMatch.push();
                String matchID = newMatch.getKey();
                match = new Match(inputNameGame.getText().toString(),
                        user.getUid(), opponent.getUid(), user.getDisplayName(), opponent.getName());
                match.setMatchID(matchID);
                newMatch.setValue(match);
                System.out.println("---------"+opponent.getUid());

                databaseMatch = databaseUser.child("Users/" + opponent.getUid() + "/games/" + matchID);
                matchOp = new Match(inputNameGame.getText().toString(), opponent.getUid(),
                        user.getUid(), opponent.getName(), user.getDisplayName());
                matchOp.setMatchID(matchID);

                databaseMatch.setValue(matchOp);


                Log.d("Add match", "Add match success");

                updateUI();


            }
        });


        return inflatedView;
        

    }
    private void updateUI() {
        Intent matchActivity = new Intent(getActivity(), MatchActivity.class);
        matchActivity.putExtra("p1ID", match.getPlayer1ID());
        matchActivity.putExtra("p2ID", match.getPlayer2ID());
        matchActivity.putExtra("s1", match.getScore1());
        matchActivity.putExtra("s2", match.getScore2());
        matchActivity.putExtra("matchID", match.getMatchID());
        matchActivity.putExtra("p1Name", match.getPlayer1Name());
        matchActivity.putExtra("p2Name", match.getPlayer2Name());
        startActivity(matchActivity);

    }
}
