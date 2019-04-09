package com.example.ngailapdi.gtscore;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ProfileFragment extends Fragment {
    private DatabaseReference databaseUser;
    private TextView userDisplayNameView;
    private Button addGameButton;
    private ListView gameListView;
    private List<String> games;
    private List<Game> gameList;
    private ArrayAdapter<String> arrayAdapterGame;

    View inflatedView = null;
    FirebaseUser user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_profile,container,false);
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        userDisplayNameView = (TextView) inflatedView.findViewById(R.id.textView2);
//        userDisplayNameView.setText(user.getDisplayName());
//        gameList = new ArrayList<>();
//       // addGameButton = (Button) inflatedView.findViewById(R.id.buttonadd);
//        addGameButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("-----------------CLICK");
//                Intent addGameIntent = new Intent(getActivity(), AddGameActivity.class);
//                startActivityForResult(addGameIntent, 1);
//            }
//        });
//
//       //gameListView = (ListView) inflatedView.findViewById(R.id.listview);
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        databaseUser = FirebaseDatabase.getInstance().getReference();
//        games = new ArrayList<String>();
//        databaseUser.child("Users/" + user.getUid() + "/games")
//                .addChildEventListener(new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                        Game game = dataSnapshot.getValue(Game.class);
//                        System.out.println("hahah");
//                        gameList.add(game);
//
//                        // Notify the ArrayAdapter that there was a change
//                        arrayAdapterGame.add(game.getName());
//                    }
//
//                    @Override
//                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                    }
//
//                    @Override
//                    public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                    }
//
//                    @Override
//                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
////        createList();
//        arrayAdapterGame = new ArrayAdapter<>(this.getActivity(),android.R.layout.simple_list_item_1,games);
//        gameListView.setAdapter(arrayAdapterGame);
//        gameListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent gameActivity = new Intent(getActivity().getApplicationContext(), GameActivity.class);
//                gameActivity.putExtra("gameName", gameList.get(i).getName());
//                gameActivity.putExtra("gameID", gameList.get(i).getGameID());
//                gameActivity.putExtra("gameDescription", gameList.get(i).getDescription());
//
//                startActivity(gameActivity);
//            }
//        });

        return inflatedView;

    }

}
