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
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationFragment extends Fragment {
    @Nullable
    private ArrayList<String> senderNames;
    private static DatabaseReference database;


    private FirebaseUser user;
    private ListView friendRequestView;
    private View displayView;
    private ArrayAdapter<Map<String, String>> arrayAdapterName;;

    public void updateUI(String senderID, String receiverID) {
        Intent createMessage = new Intent(getActivity(), CreateMessageActivity.class);
        createMessage.putExtra("senderID", senderID);
        createMessage.putExtra("receiverID", receiverID);
        startActivityForResult(createMessage, 1);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        displayView = inflater.inflate(R.layout.fragment_notification,container,false);
        friendRequestView = displayView.findViewById(R.id.friendRequests);
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
        senderNames = new ArrayList<String>();
        database.child("FriendRequests/" + user.getUid())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        GenericTypeIndicator<Map<String, Object>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Object>>() {};
                        final Map<String, Object> o = dataSnapshot.getValue(genericTypeIndicator);
                        System.out.println("-----------o: " + o);
                        final Map<String, String> mapAdapter = new HashMap<>();
                        mapAdapter.put("name", (String) o.get("senderName"));
                        mapAdapter.put("uid", (String) o.get("senderID"));
                        mapAdapter.put("email", (String) o.get("email"));
                        mapAdapter.put("friendRequestID", (String) o.get("key"));

                        System.out.println("-----Check status: " + ((String) o.get("status")).equals("received"));

                        // Notify the ArrayAdapter that there was a change
                        if (((String) o.get("status")).equals("received")) {
                            System.out.println("----------RECEIVED");
                            arrayAdapterName.add(mapAdapter);

                        }

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
        arrayAdapterName = new NotificationListAdapter(this.getActivity(),R.layout.list_notification,senderNames, this);
        friendRequestView.setAdapter(arrayAdapterName);
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

class NotificationListAdapter extends ArrayAdapter<Map<String, String>>
{
    int layout;
    NotificationFragment notificationFragment;
    protected NotificationListAdapter(Context context, int resources, List objects, NotificationFragment notificationFragment)
    {
        super(context, resources, objects);
        this.notificationFragment = notificationFragment;
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
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.senderName = (TextView) convertView.findViewById(R.id.senderName);
            viewHolder.acceptBtn = (Button) convertView.findViewById(R.id.accept_btn);
            viewHolder.declineBtn = (Button) convertView.findViewById(R.id.decline_btn);

            final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            viewHolder.acceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DatabaseReference friendRequests = FirebaseDatabase.getInstance().getReference().child("FriendRequests/" + currentUser.getUid() + "/" + getItem(position).get("key"));
                    Map<String, Object> updateMap = new HashMap<>();
                    updateMap.put("status", "accepted");
                    friendRequests.updateChildren(updateMap);
                    DatabaseReference friendReference = FirebaseDatabase.getInstance().getReference().child("Users/" + currentUser.getUid() + "/friends");
                    User newFriend = new User(getItem(position).get("name"), getItem(position).get("email"), new ArrayList<User>(), new ArrayList<Game>());
                    newFriend.setUid(getItem(position).get("uid"));
                    friendReference.child(getItem(position).get("uid")).setValue(newFriend);

                    friendReference = FirebaseDatabase.getInstance().getReference().child("Users/" + getItem(position).get("uid") + "/friends");
                    User thisFriend = new User(currentUser.getDisplayName(), currentUser.getEmail(), new ArrayList<User>(), new ArrayList<Game>());
                    thisFriend.setUid(currentUser.getUid());
                    friendReference.child(currentUser.getUid()).setValue(thisFriend);
                    viewHolder.acceptBtn.setText("Added");

                }
            });
            viewHolder.declineBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseReference friendRequests = FirebaseDatabase.getInstance().getReference().child("FriendRequests/" + currentUser.getUid() + "/" + getItem(position).get("key"));
                    friendRequests.setValue(null);
                    viewHolder.declineBtn.setText("Declined");



                }
            });
            System.out.println("------hahaha: " + position);
            System.out.println("-----------------hahahahahaha");
            convertView.setTag(viewHolder);
//                viewHolder.button.setText("Invited");
        }
        main = (ViewHolder) convertView.getTag();
        main.senderName.setText(getItem(position).get("name"));

        return convertView;



    }

    public class ViewHolder{
        TextView senderName;
        Button acceptBtn;
        Button declineBtn;

    }



}

//class NotificationListAdapter extends ArrayAdapter<Map<String, String>> {
//    int layout;
//    NotificationFragment notificationFragment;
//
//    protected NotificationListAdapter(Context context, int resources, List objects, NotificationFragment notificationFragment) {
//        super(context, resources, objects);
//        this.notificationFragment = notificationFragment;
//        layout = resources;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        ViewHolder main = null;
//        if (convertView == null) {
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            convertView = inflater.inflate(layout, parent, false);
//            ViewHolder viewHolder = new ViewHolder();
//            viewHolder.txt = (TextView) convertView.findViewById(R.id.txt);
//            viewHolder.button = (Button) convertView.findViewById(R.id.bt);
//            viewHolder.button.setText("Add");
//            viewHolder.button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    DatabaseReference databaseReferenceRequests = FirebaseDatabase.getInstance().getReference()
//                            .child("FriendRequests/" + getItem(position).get("uid"));
//                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//                    Map<String, Object> friendRequest = new HashMap<>();
//                    friendRequest.put("senderID", currentUser.getUid());
//                    friendRequest.put("senderName", currentUser.getDisplayName());
//                    friendRequest.put("email", currentUser.getEmail());
//                    friendRequest.put("status", "received");
//                    friendRequest.put("message", "You have a friend request from " + currentUser.getDisplayName() + ". Please log in to accept or decline");
//                    databaseReferenceRequests = databaseReferenceRequests.push();
//                    String key = databaseReferenceRequests.getKey();
//                    friendRequest.put("key", key);
//                    databaseReferenceRequests.setValue(friendRequest);
//                    Log.d("Notify", "Your friend request has been sent");
//
//                }
//
//            });
//            convertView.setTag(viewHolder);
////            viewHolder.button.setText("Pending");
//        }
//        main = (ViewHolder) convertView.getTag();
//        main.txt.setText(getItem(position).get("name"));
//
//        return convertView;
//
//
//    }
//
//    public class ViewHolder{
//        TextView txt;
//        Button button;
//
//    }
//
//}