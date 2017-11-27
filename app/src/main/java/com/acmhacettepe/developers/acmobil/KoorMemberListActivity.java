package com.acmhacettepe.developers.acmobil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class KoorMemberListActivity extends AppCompatActivity {

    DatabaseReference addedUsers;
    private Spinner mCoorSpinner;
    private ListView mNamesListView;
    private ArrayList<String> names;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koor_member_list);

        addedUsers = FirebaseDatabase.getInstance().getReference().child("AddedUsers");

        mCoorSpinner = (Spinner) findViewById(R.id.coors_spinner);

        mNamesListView = (ListView) findViewById(R.id.listView_names);



        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(KoorMemberListActivity.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.coor_names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCoorSpinner.setAdapter(myAdapter);

        mCoorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                names = new ArrayList<>();
                final String coor = mCoorSpinner.getSelectedItem().toString();
                System.out.println(coor);
                addedUsers.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("Koordinatorluk")) {
                            System.out.println("adsfdsgfdgdfgfdgfddf");
                            if (dataSnapshot.child("Koordinatorluk").getValue().toString().equals(coor)) {
                                System.out.println("adsfdsgfdgdfgfdgfddf");
                                names.add(dataSnapshot.child("name").getValue().toString());
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(KoorMemberListActivity.this,
                        android.R.layout.simple_list_item_1, names);
                mNamesListView.setAdapter(nameAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
