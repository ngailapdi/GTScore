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


public class CreateMessage extends AppCompatActivity {
        private EditText inputName;
        private EditText description;
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
            setContentView(R.layout.activity_add_game);
            inputName = (EditText) findViewById(R.id.nameGameText);
            description = (EditText) findViewById(R.id.descriptionGameText);
            save = (Button) findViewById(R.id.saveButton);
            cancel = (Button) findViewById(R.id.cancelButton);
            database = FirebaseDatabase.getInstance().getReference();
            user = FirebaseAuth.getInstance().getCurrentUser();

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (attemptSave()) {
                        sendInvitationToUser(senderID, receiverID,
                                "From: " + senderID + " " + description.getText().toString(), inputName.getText().toString());

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

    public void sendInvitationToUser(String senderID, String uid, final String message, final String title) {
        DatabaseReference databaseInvitation = FirebaseDatabase.getInstance().getReference()
                .child("Notifications/" + uid);
        Map<String, Object> invitation = new HashMap<>();
        invitation.put("message", message);
        invitation.put("title", title);
        invitation.put("sender", senderID);
        databaseInvitation.push().setValue(invitation);
    }

        private boolean attemptSave() {
            return true;
        }

}

