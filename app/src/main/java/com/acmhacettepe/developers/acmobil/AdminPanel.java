package com.acmhacettepe.developers.acmobil;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AdminPanel extends AppCompatActivity {


    Button addUser;
    Button goToHomePage;
    Button addEvent;
    Button select_coor;
    Button delEvent;
    Button qr_scanner;
    Button koor_uyeleri_list;
    Button add_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        // Just creating buttons and using them to start the desired activity.
        addUser = (Button) findViewById(R.id.uye_ekle_button);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPanel.this, AddUser.class));
            }
        });

        goToHomePage = (Button) findViewById(R.id.home_screen);

        goToHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addEvent = (Button) findViewById(R.id.addEventButton);

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPanel.this, addEvent.class));
            }
        });

        select_coor = (Button) findViewById(R.id.koor_sec);

        select_coor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AdminPanel.this, SelectCoordinator.class));
            }

        });

        delEvent = (Button) findViewById(R.id.deleteEventButton);
        delEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPanel.this,DelEventActivity.class));
            }
        });

        qr_scanner = (Button) findViewById(R.id.qr_scan);

        qr_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPanel.this, QrScannerAdminActivity.class));
            }
        });

        koor_uyeleri_list = (Button) findViewById(R.id.koor_uyeleri_list);

        koor_uyeleri_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPanel.this, KoorMemberListActivity.class));
            }
        });



    }
}
