package com.example.ngailapdi.gtscore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddGameActivity extends AppCompatActivity {
    private EditText inputName;
    private EditText description;
    private Button save;
    private Button cancel;

    private DatabaseReference database;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_invite_message);
        inputName = (EditText) findViewById(R.id.message_title);
        description = (EditText) findViewById(R.id.message);
        save = (Button) findViewById(R.id.saveButton);
        cancel = (Button) findViewById(R.id.cancelButton);
        database = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (attemptSave()) {

                    database = database.child("Users/" + user.getUid() + "/games");
                    DatabaseReference newGame = database.push();
                    String gameID = newGame.getKey();
                    System.out.println("---------------------gameID create: " + gameID);
                    Game game = new Game(inputName.getText().toString(),
                            description.getText().toString());
                    game.setGameID(gameID);
                    newGame.setValue(game);

                    Log.d("Add game", "Add game success");
                    updateUI(user);

                }
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

    private boolean attemptSave() {
        return true;
    }

}
