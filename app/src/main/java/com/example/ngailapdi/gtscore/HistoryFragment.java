package com.example.ngailapdi.gtscore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private ListView hisList;
    private View inflateredView = null;
    private List<Match> matchList;
    private ArrayList<String> matchname;
    private DatabaseReference database;
    private ArrayAdapter adapter;

    FirebaseUser user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        inflateredView = inflater.inflate(R.layout.fragment_history,container,false);
        hisList = (ListView) inflateredView.findViewById(R.id.his_list);
        user = FirebaseAuth.getInstance().getCurrentUser();
        matchList = new ArrayList<>();
        matchname = new ArrayList<>();
        database = FirebaseDatabase.getInstance().getReference();
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,matchname);

        database.child("Users/" + user.getUid()
                + "/games").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Match match = dataSnapshot.getValue(Match.class);
                if(match.getPlayed())
                {
                    matchList.add(match);
                    adapter.add(match.getName());
                    adapter.notifyDataSetChanged();

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

        hisList.setAdapter(adapter);
        hisList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("---------------Length: " + matchList.size());
                    Intent matchActivity = new Intent(getActivity(), MatchActivity.class);
                matchActivity.putExtra("p1ID", matchList.get(i).getPlayer1ID());
                matchActivity.putExtra("p2ID", matchList.get(i).getPlayer2ID());
                matchActivity.putExtra("s1", matchList.get(i).getScore1());
                matchActivity.putExtra("s2", matchList.get(i).getScore2());
                matchActivity.putExtra("matchID", matchList.get(i).getMatchID());
                matchActivity.putExtra("p1Name", matchList.get(i).getPlayer1Name());
                matchActivity.putExtra("p2Name", matchList.get(i).getPlayer2Name());







                startActivity(matchActivity);
            }
        });


        return inflateredView;
    }
}
