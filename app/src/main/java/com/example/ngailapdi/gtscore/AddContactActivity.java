package com.example.ngailapdi.gtscore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddContactActivity extends AppCompatActivity {

    private DatabaseReference database;
    private FirebaseUser user;

    private ArrayList<String> userNameList;


    private ListView userListView;
    private View displayView;
    private ArrayAdapter<Map<String, String>> userNameArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        database = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userListView = findViewById(R.id.user_list);
        userNameList = new ArrayList<>();

        database.child("Users/").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                for (DataSnapshot dataValues : dataSnapshot.getChildren()) {
//                    System.out.println(dataValues.getValue(String.class));
////                  userArrayAdapter.add(dataValues.getValue(User.class));
//                }

                GenericTypeIndicator<Map<String, Object>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Object>>() {};
                final Map<String, Object> o = dataSnapshot.getValue(genericTypeIndicator);
                final Map<String, String> mapAdapter = new HashMap<>();
                mapAdapter.put("name", (String) o.get("name"));
                mapAdapter.put("uid", (String) o.get("uid"));
                if (!user.getUid().equals((String) o.get("uid"))) {
                    database.child("Users/" + user.getUid() + "/friends/").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            System.out.println("----------on data change");
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                User friend = data.getValue(User.class);
                                System.out.println("--------OUT");
                                System.out.println(friend.getUid());
                                System.out.println((String) o.get("uid"));
                                if (!friend.getUid().equals((String) o.get("uid"))) {
                                    System.out.println("GET IN HERE");
                                    System.out.println(friend.getUid());
                                    System.out.println((String) o.get("uid"));
                                    userNameArrayAdapter.add(mapAdapter);

                                }
                            }
//                            Object friend = dataSnapshot.getValue(Object.class);
//                            System.out.println("-------- Object: " + friend.getClass());
////                            if (!friend.getUid().equals((String) o.get("uid"))) {
//                                userNameArrayAdapter.add(mapAdapter);
//
////                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                System.out.println("hehehehehe ------------ " + o);
//                User appUser = dataSnapshot.getValue(User.class);
//                userArrayAdapter.add(appUser);
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

        userNameArrayAdapter = new UserListAdapter(this, R.layout.list_invite, userNameList, this);

        userListView.setAdapter(userNameArrayAdapter);
    }

    public void updateUI(String senderName, String senderID, String receiverID) {
        Intent addContactMessageActivity = new Intent(this, AddContactMessageActivity.class);
        addContactMessageActivity.putExtra("senderName", senderName);
        addContactMessageActivity.putExtra("senderID", senderID);
        addContactMessageActivity.putExtra("receiverID", receiverID);
        startActivityForResult(addContactMessageActivity, 1);

    }
}

class UserListAdapter extends ArrayAdapter<Map<String, String>> {
    int layout;
    AddContactActivity addContactActivity;

    protected UserListAdapter(Context context, int resources, List objects, AddContactActivity addContactActivity) {
        super(context, resources, objects);
        this.addContactActivity = addContactActivity;
        layout = resources;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder main = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.txt = (TextView) convertView.findViewById(R.id.txt);
            viewHolder.button = (Button) convertView.findViewById(R.id.bt);
            viewHolder.button.setText("Add");
            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addContactActivity.updateUI(
                            FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                            FirebaseAuth.getInstance().getCurrentUser().getUid(), getItem(position).get("uid"));

                }

            });
            convertView.setTag(viewHolder);
//                viewHolder.button.setText("Invited");
        }
        main = (ViewHolder) convertView.getTag();
        main.txt.setText(getItem(position).get("name"));

        return convertView;


    }

    public class ViewHolder{
        TextView txt;
        Button button;

    }

}

