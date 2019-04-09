package com.example.ngailapdi.gtscore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    private TextView nameGame;
    private Button addMatchButton;
    private ListView matchListView;
    private DatabaseReference database;
    private ArrayAdapter<String> arrayAdapterMatch;
    private List<Match> matchList;
    private List<String> matchListName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        final String gameName = intent.getStringExtra("gameName");
        final String gameID = intent.getStringExtra("gameID");
        final String gameDescription = intent.getStringExtra("gameDescription");
        nameGame = (TextView) findViewById(R.id.nameGameText);
        nameGame.setText(gameName);
        matchList = new ArrayList<>();
        matchListName = new ArrayList<>();
        database = FirebaseDatabase.getInstance().getReference();
        matchListView = (ListView) findViewById(R.id.match_view);

//        database.child("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid()
//                + "/matches/").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                Match match = dataSnapshot.getValue(Match.class);
//                if (match.getGameID().equals(gameID)) {
//                    matchList.add(match);
//                    arrayAdapterMatch.add(match.getName());
//                }
//
//                // Notify the ArrayAdapter that there was a change
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
////        createList();
//        arrayAdapterMatch = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,matchListName);
//        matchListView.setAdapter(arrayAdapterMatch);
//        addMatchButton = (Button) findViewById(R.id.addMatchBtn);
//        addMatchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("-----------GAMEID: " + gameID);
//                Intent addMatchIntent = new Intent(GameActivity.this, AddMatchActivity.class);
//                addMatchIntent.putExtra("gameID", gameID);
//                addMatchIntent.putExtra("gameName", gameName);
//                addMatchIntent.putExtra("gameDescription", gameDescription);
//                startActivityForResult(addMatchIntent, 1);
//            }
//        });
//        matchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                System.out.println("---------------Length: " + matchList.size());
//                Intent matchActivity = new Intent(getApplicationContext(), MatchActivity.class);
//                matchActivity.putExtra("p1ID", matchList.get(i).getPlayer1ID());
//                matchActivity.putExtra("p2ID", matchList.get(i).getPlayer2ID());
//                matchActivity.putExtra("s1", matchList.get(i).getScore1());
//                matchActivity.putExtra("s2", matchList.get(i).getScore2());
//                matchActivity.putExtra("gameID", gameID);
//                matchActivity.putExtra("matchID", matchList.get(i).getMatchID());
//                matchActivity.putExtra("p1Name", matchList.get(i).getPlayer1Name());
//                matchActivity.putExtra("p2Name", matchList.get(i).getPlayer2Name());
//
//
//
//
//
//
//
//                startActivity(matchActivity);
//            }
//        });


    }
}
