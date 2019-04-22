package com.example.ngailapdi.gtscore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Fragment create = new CreateMatchFragment();




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            android.support.v4.app.Fragment selectedFrag = null;
            switch (item.getItemId()) {
                case R.id.create_match:
                    selectedFrag = new CreateMatchFragment();
                    break;
                case R.id.contact:
                    selectedFrag = new ContactFragment();
                    break;
                case R.id.history:
                    selectedFrag = new HistoryFragment();

                    break;
                case R.id.ongoing:
                    selectedFrag = new OngoingMatchFragment();

                    break;
                case R.id.notification:
                    selectedFrag = new NotificationFragment();
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
        Intent intent = getIntent();
        String thinga = intent.getStringExtra("itemID");
        if (thinga.equals("true"))
        {
            navigation.setSelectedItemId(R.id.history);
            getSupportFragmentManager().beginTransaction().replace(R.id.profile_frag,new HistoryFragment()).commit();

        }
        else {

            getSupportFragmentManager().beginTransaction().replace(R.id.profile_frag, create).commit();
        }
    }

}
