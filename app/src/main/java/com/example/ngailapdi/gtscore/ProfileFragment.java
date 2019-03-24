package com.example.ngailapdi.gtscore;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class ProfileFragment extends Fragment {
    private DatabaseReference databaseUser;
    private TextView userDisplayNameView;
    View inflatedView = null;
    FirebaseUser user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_profile,container,false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userDisplayNameView = (TextView) inflatedView.findViewById(R.id.textView2);
        userDisplayNameView.setText(user.getDisplayName());
        return inflatedView;


    }
}
