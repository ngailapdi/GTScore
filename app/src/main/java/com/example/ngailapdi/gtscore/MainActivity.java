package com.example.ngailapdi.gtscore;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            android.support.v4.app.Fragment selectedFrag = null;
            switch (item.getItemId()) {
                case R.id.profile:
                    selectedFrag = new ProfileFragment();
                    break;
                case R.id.contact:
                    selectedFrag = new ContactFragment();
                    break;
                case R.id.game:
                    selectedFrag = new GamesFragment();
                    break;
                case R.id.tournament:
                    selectedFrag = new TournamentFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.profile_frag,selectedFrag).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navi_bottom);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.profile_frag,new ProfileFragment()).commit();
    }

}