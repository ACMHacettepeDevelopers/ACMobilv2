package com.acmhacettepe.developers.acmobil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class DelEventActivity extends AppCompatActivity {
    private Button deleteEvent;
    private EditText eventName;
    private ListView eventList;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    private DatabaseReference root;
    private ArrayList<String> numbers;


    //creating reference to firebase storage
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    /**
     *Delete Page Layout
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_event);

        deleteEvent = (Button) findViewById(R.id.delEvent);
        eventList = (ListView) findViewById(R.id.EventList);
        eventName = (EditText) findViewById(R.id.editText2);
        arrayList = new ArrayList<>();
        numbers = new ArrayList<String>();

        root = FirebaseDatabase.getInstance().getReference().child("Events").child("EventNames");
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DataSnapshot i = dataSnapshot.getChildren().iterator().next();
                String eventName = ((HashMap)(i).getValue()).get("eventName").toString();
                String number = ((HashMap)(i).getValue()).get("number").toString();
                String data = number + " - " + eventName;
                arrayList.add(data);
                numbers.add(number);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        eventList.setAdapter(adapter);

        deleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventNumber = eventName.getText().toString();
                boolean isfound =false;
                for (int i=0; i<numbers.size();++i){
                    //search event that is entered
                    if(eventNumber.equals(numbers.get(i))){
                        isfound = true;
                        break;
                    }
                }

                //if there is a event we seeked,it will delete.Then message will appear on screeon.
                //"Event is deleted successfully"
                // There is not.
                // "Event is not deleted.Please check number of event"
                if(isfound){
                    root.child(eventNumber).setValue(null);
                    Toast.makeText(DelEventActivity.this, "Etkinlik başarıyla silindi", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(DelEventActivity.this, "Etkinlik silinemedi. Lütfen etkinlik numarasını kontrol ediniz.", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
