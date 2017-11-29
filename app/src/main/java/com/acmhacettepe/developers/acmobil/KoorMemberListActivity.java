package com.acmhacettepe.developers.acmobil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class KoorMemberListActivity extends AppCompatActivity {

    DatabaseReference addedUsers;
    private Spinner mCoorSpinner;
    private ListView mNamesListView;
    private ArrayList<String> names = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koor_member_list);

        // Setting up database root.
        addedUsers = FirebaseDatabase.getInstance().getReference().child("AddedUsers");

        mCoorSpinner = (Spinner) findViewById(R.id.coors_spinner);

        mNamesListView = (ListView) findViewById(R.id.listView_names);

        // Creating and setting adapter for spinner.
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(KoorMemberListActivity.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.coor_names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCoorSpinner.setAdapter(myAdapter);

        // Listens and runs the code below whenever selected item is changed in spinner.
        mCoorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                names = new ArrayList<>();
                // Getting selected from spinner item for comparison.
                final String coor = mCoorSpinner.getSelectedItem().toString();

                // Reading the child "AddedUsers".
                addedUsers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Key is student Number and values are again a Hash Map.
                        HashMap<String, HashMap<String,String>> usersMap =
                                ((HashMap<String,HashMap<String,String>>) dataSnapshot.getValue());
                        Iterator itrOuter = usersMap.values().iterator();

                        while (itrOuter.hasNext()) {
                            /* Each Hash Map is storing the values of a user. In this case
                             * the values below the student number like acmNum, username etc. */
                            HashMap<String,String> innerMap = (HashMap<String,String>) itrOuter.next();

                            /* Checking the if such a key named "Koordinatorluk" and "name" exists or not
                             * to avoid NullPointerException. Finally adding the name to list. */
                            if (innerMap.get("Koordinatorluk") != null) {
                                if (innerMap.get("Koordinatorluk").equals(coor)) {
                                    if (innerMap.get("name") != null) {
                                        names.add(innerMap.get("name"));
                                    }
                                }
                            }
                        }
                        // Creating the adapter for listView and setting it.
                        final ArrayAdapter<String> nameAdapter = new ArrayAdapter<>
                                (KoorMemberListActivity.this,
                                android.R.layout.simple_list_item_1, names);
                        mNamesListView.setAdapter(nameAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(KoorMemberListActivity.this, "Some error occured!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(KoorMemberListActivity.this, "Please select an option!",
                        Toast.LENGTH_LONG).show();

            }
        });
    }
}
