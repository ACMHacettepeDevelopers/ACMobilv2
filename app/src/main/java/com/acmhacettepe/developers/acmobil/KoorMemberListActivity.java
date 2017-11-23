package com.acmhacettepe.developers.acmobil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class KoorMemberListActivity extends AppCompatActivity {

    DatabaseReference addedUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koor_member_list);

        addedUsers = FirebaseDatabase.getInstance().getReference().child("addedUsers");



    }
}
