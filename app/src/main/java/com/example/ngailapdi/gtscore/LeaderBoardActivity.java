package com.example.ngailapdi.gtscore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LeaderBoardActivity extends AppCompatActivity {
    private ListView leaderBoard;
    private ArrayAdapter<String> namesArrayAdapter;
    private List<String> names;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        Intent intent = getIntent();
        names = new ArrayList<>();
        leaderBoard = (ListView) findViewById(R.id.leaderBoard);
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users/");
        userRef.orderByChild("num_wins").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                GenericTypeIndicator<Map<String, Object>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Object>>() {};
                final Map<String, Object> o = dataSnapshot.getValue(genericTypeIndicator);
                System.out.println("-----o:" + o.get("name"));
                if (o.containsKey("num_wins")) {
                    System.out.println("-----order: " + o.get("num_wins"));
                    namesArrayAdapter.add((String) o.get("name"));
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        namesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        leaderBoard.setAdapter(namesArrayAdapter);


    }
}
