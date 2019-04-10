package com.example.ngailapdi.gtscore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mNameView;
    private Button cancelButton;
    private Button signUpButton;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseUser;
//    private String deviceToken;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        mNameView = (EditText) findViewById(R.id.name_reg);
        mEmailView = (EditText) findViewById(R.id.email_reg);
        mPasswordView = (EditText) findViewById(R.id.pwd_reg);
        signUpButton = (Button) findViewById(R.id.reg_btn);
        cancelButton = (Button) findViewById(R.id.cancel_btn);
        signUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (attemptLogin()) {
                    mAuth.createUserWithEmailAndPassword(mEmailView.getText().toString(),
                            mPasswordView.getText().toString()).addOnCompleteListener(SignupActivity.this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(mNameView.getText().toString())
                                                .build();
                                        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("Sign up", "Sign up success");
                                                    putUserToDatabase();


                                                } else {
                                                    Log.d("Sign up", "Sign up failed");
                                                }

                                            }
                                        });
                                        updateUI(user);
                                    } else {
                                        Log.d("Sign up", "Sign up fail");
                                        Toast.makeText(SignupActivity.this, "Cannot create user account",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
        cancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUI(null);
            }
        });
    }


    private boolean attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        return !cancel;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void updateUI(FirebaseUser user) {
        Intent returnIntent = new Intent();
        if (user != null) {
            returnIntent.putExtra("result", user.getEmail());
            setResult(Activity.RESULT_OK, returnIntent);
        } else {
            setResult(Activity.RESULT_CANCELED, returnIntent);
        }
        finish();

    }

    private void putUserToDatabase() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(
                SignupActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                String deviceToken = instanceIdResult.getToken();
                DatabaseReference databaseUser = FirebaseDatabase.getInstance().getReference();
                databaseUser = databaseUser.child("Users/" + user.getUid());
                User newUser = new User(user.getDisplayName(), user.getEmail(),
                        new ArrayList<User>(), new ArrayList<Game>());
                newUser.setUid(user.getUid());
                newUser.setDeviceToken(deviceToken);
                databaseUser.setValue(newUser);
            }
        });

    }

}
