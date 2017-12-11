package com.acmhacettepe.developers.acmobil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
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

public class DisplayEventParticipantsActivity extends AppCompatActivity {

    private Spinner mEventsSpinner;
    private ListView mParticipantsLv;
    private DatabaseReference qrRegisteredEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_event_participants);

        // Database Reference to "QrRegisteredEvents"
        qrRegisteredEvents = FirebaseDatabase.getInstance().getReference().child("QrRegisteredEvents");

        mEventsSpinner = (Spinner) findViewById(R.id.sp_events);
        mParticipantsLv = (ListView) findViewById(R.id.lv_participants);

        // Arraylist for storing event names.
        final ArrayList<String> eventNames = new ArrayList<>();


        // Reading database and extracting event names then adding it to eventNames arraylist.
        // Finally setting the spinner's adapter with this arraylist.
        qrRegisteredEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, HashMap<String, String>> eventsMap =
                        ((HashMap<String, HashMap<String, String>>) dataSnapshot.getValue());
                for (String eventName : eventsMap.keySet()) {
                    eventNames.add(eventName);
                }
                // Creating and setting adapter for spinner.
                ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(DisplayEventParticipantsActivity.this,
                        android.R.layout.simple_list_item_1,
                        eventNames);
                myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mEventsSpinner.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DisplayEventParticipantsActivity.this, "Some Error Occured!",
                        Toast.LENGTH_LONG).show();
            }
        });

        // OnItemSelectedListener is to listen event name changes and updating list view accordingly.
        mEventsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Getting Selected item.
                final String eventName = mEventsSpinner.getSelectedItem().toString();
                // Reading database to get participant names.
                qrRegisteredEvents.child(eventName).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Initializing arraylist here to avoid having duplicate names on list view.
                        ArrayList<String> participants = new ArrayList<>();
                        // If event has participants go ahead and add them to participant arraylist.
                        if (dataSnapshot.hasChildren()) {
                            // We need get the data as Hashmap from Firebase.
                            HashMap<String, String> participantNames =
                                    (HashMap<String, String>) dataSnapshot.getValue();
                            // Adding the Hashmap's keys which is participant names to arraylist.
                            for (String name : participantNames.keySet()) {
                                participants.add(name);
                            }
                            // Finallys setting up the adapter for the listview.
                            final ArrayAdapter<String> nameAdapter = new ArrayAdapter<>
                                    (DisplayEventParticipantsActivity.this,
                                            android.R.layout.simple_list_item_1, participants);
                            mParticipantsLv.setAdapter(nameAdapter);
                        }
                        // Else, set the adapter to null to clear the list view.
                        else {
                            mParticipantsLv.setAdapter(null);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        Toast.makeText(DisplayEventParticipantsActivity.this, "Some Error Occured!",
                                Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(DisplayEventParticipantsActivity.this,
                        "Select some event name to see participants", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
