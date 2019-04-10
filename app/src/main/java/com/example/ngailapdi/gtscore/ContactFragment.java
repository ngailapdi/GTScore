package com.example.ngailapdi.gtscore;

import android.content.Context;
import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {
    @Nullable
    private ArrayList<User> contactList;
    private static DatabaseReference database;


    private FirebaseUser user;
    private ListView contactListView;
    private View displayView;
    private ArrayAdapter<User> arrayAdapterName;
    private Button addUser;

    public void updateUI(String senderID, String receiverID) {
        Intent createMessage = new Intent(getActivity(), CreateMessage.class);
        createMessage.putExtra("senderID", senderID);
        createMessage.putExtra("receiverID", receiverID);
        startActivityForResult(createMessage, 1);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        displayView = inflater.inflate(R.layout.fragment_contact,container,false);
        contactListView = (ListView) displayView.findViewById(R.id.list_contact);
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
        contactList = new ArrayList<User>();
        addUser = (Button) displayView.findViewById(R.id.add_contact_btn);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("-----------------ADD USER");
                Intent signUpIntent = new Intent(getActivity(), AddContactActivity.class);
                startActivityForResult(signUpIntent, 1);
            }
        });
        database.child("Users/" + user.getUid() + "/friends")
                .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                final User friend = dataSnapshot.getValue(User.class);
                database.child("Users/" + friend.getUid() + "/deviceToken/")
                        .addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                friend.setDeviceToken(dataSnapshot.getValue(String.class));
                                database.child("Users/" + user.getUid() + "/friends/" + friend.getUid() + "/deviceToken/").setValue(friend.getDeviceToken());
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
                System.out.println("----------token: " + friend.getDeviceToken());


                // Notify the ArrayAdapter that there was a change
                arrayAdapterName.add(friend);
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
        arrayAdapterName = new MyListAdapter(this.getActivity(),R.layout.list_invite,contactList, this);
        contactListView.setAdapter(arrayAdapterName);
        return displayView;

    }

//    private void createList()
//    {
//
//        for(int i =0; i < 10; i++)
//        {
//            array.add("row"+i);
//        }
//    }
}

    class MyListAdapter extends ArrayAdapter<User>
    {
        int layout;
        ContactFragment contactFragment;
        protected MyListAdapter(Context context, int resources, List objects, ContactFragment contactFragment)
        {
            super(context, resources, objects);
            this.contactFragment = contactFragment;
            layout = resources;
        }

        @Override
        public View getView (final int position, View convertView, ViewGroup parent)
        {
            ViewHolder main = null;
            if (convertView == null)
            {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout,parent,false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.txt = (TextView) convertView.findViewById(R.id.txt);
                viewHolder.button = (Button) convertView.findViewById(R.id.bt);
                viewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        contactFragment.updateUI(
                                FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), getItem(position).getUid());

                    }

                });
                convertView.setTag(viewHolder);
//                viewHolder.button.setText("Invited");
            }
            main = (ViewHolder) convertView.getTag();
            main.txt.setText(getItem(position).getName());

            return convertView;



    }

    public class ViewHolder{
        TextView txt;
        Button button;

    }



}
