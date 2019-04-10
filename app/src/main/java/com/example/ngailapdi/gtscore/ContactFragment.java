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
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ContactFragment extends Fragment {
    @Nullable
    private ArrayList<User> contactList;
    private static DatabaseReference database;


    private FirebaseUser user;
    private ListView contactListView;
    private View displayView;
    private ArrayAdapter<User> arrayAdapterName;
    private Button addUser;

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

                User friend = dataSnapshot.getValue(User.class);

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
        arrayAdapterName = new MyListAdapter(this.getActivity(),R.layout.list_invite,contactList);
        contactListView.setAdapter(arrayAdapterName);
        return displayView;

    }
    public static void sendInvitationToUser(String uid, final String message) {
        DatabaseReference databaseInvitation = database.child("Users/" + uid + "/invitations");
        Map<String, String> invitation = new HashMap<>();
        invitation.put(uid, message);
        databaseInvitation.push().setValue(invitation);
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
        protected MyListAdapter(Context context, int resources, List objects)
        {
            super(context, resources, objects);
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
                        User friend = getItem(position);
                        String friendDeviceToken = friend.getDeviceToken();
                        FirebaseMessaging fm = FirebaseMessaging.getInstance();
                        System.out.println("----------!!!!" + friend.getName());

                        fm.send(new RemoteMessage.Builder(friendDeviceToken + "@gcm.googleapis.com")
                                .setMessageId(Integer.toString(new Random().nextInt(60000)))
                                .addData("my_message", "Hello World")
                                .addData("my_action","SAY_HELLO")
                                .build());
//                        System.out.println("----------!!!!" + friend.getName());
//                        RemoteMessage message = new RemoteMessage.Builder(friendDeviceToken)
//                                .addData("invitation", "Invitation")
//                                .build();
//
//                        FirebaseMessaging.getInstance().send(message);




                    }
                });
                convertView.setTag(viewHolder);
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
