package com.example.ngailapdi.gtscore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class CreateMessageActivity extends AppCompatActivity {
        private EditText messageTitle;
        private EditText message;
        private EditText address;
        private EditText date;
        private EditText time;
        private Button save;
        private Button cancel;

        private DatabaseReference database;
        private FirebaseUser user;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Intent intent = getIntent();
            final String senderID = intent.getStringExtra("senderID");
            final String receiverID = intent.getStringExtra("receiverID");
            setContentView(R.layout.activity_create_invite_message);
            messageTitle = (EditText) findViewById(R.id.message_title);
            message = (EditText) findViewById(R.id.message);
            address = (EditText) findViewById(R.id.address);
            date = (EditText) findViewById(R.id.date);
            time = (EditText) findViewById(R.id.time);
            save = (Button) findViewById(R.id.saveButton);
            cancel = (Button) findViewById(R.id.cancelButton);
            database = FirebaseDatabase.getInstance().getReference();
            user = FirebaseAuth.getInstance().getCurrentUser();

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (attemptSave()) {
                        sendInvitationToUser(senderID, receiverID,
                                "From: " + senderID + " " + message.getText().toString() + ". Date:  " + date.getText().toString()
                                + ". Time: " + time.getText().toString() + ". Location: " + address.getText().toString(),
                                messageTitle.getText().toString(), date.getText().toString(),
                                time.getText().toString(), address.getText().toString());

                    }
                    updateUI(user);
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

    public void sendInvitationToUser(String senderID, String uid, final String message,
                                     final String title, final String date, final String time, final String address) {
        DatabaseReference databaseInvitation = FirebaseDatabase.getInstance().getReference()
                .child("Notifications/" + uid);
        Map<String, Object> invitation = new HashMap<>();
        invitation.put("message", message);
        invitation.put("title", title);
        invitation.put("sender", senderID);
        invitation.put("date", date);
        invitation.put("time", time);
        invitation.put("address", address);
        databaseInvitation.push().setValue(invitation);
    }

        private boolean attemptSave() {
            return true;
        }

}

