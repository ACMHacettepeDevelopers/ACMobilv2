package com.acmhacettepe.developers.acmobil;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SelectCoordinator extends AppCompatActivity {

    private Spinner select_coor;
    private Button assign;
    private DatabaseReference regUsers;
    private EditText acmNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_coordinator);

        select_coor = (Spinner) findViewById(R.id.select_coor);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(SelectCoordinator.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.coor_names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_coor.setAdapter(myAdapter);

        regUsers = FirebaseDatabase.getInstance().getReference().child("AddedUsers");

        acmNum = (EditText) findViewById(R.id.acmNum);

        assign = (Button) findViewById(R.id.assign_btn);
        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                regUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        HashMap<String, HashMap<String,String>> usersMap =
                                ((HashMap<String,HashMap<String,String>>) dataSnapshot.getValue());
                        Iterator itrOuter = usersMap.values().iterator();
                        boolean found = false;
                        while(itrOuter.hasNext()) {

                            HashMap<String,String> innerMap = (HashMap<String,String>) itrOuter.next();

                            if(innerMap.values().contains(acmNum.getText().toString())) {
                                innerMap.put("Koordinatorluk", select_coor.getSelectedItem().toString());
                                Toast.makeText(SelectCoordinator.this,
                                        "Üye seçilen koordinatörlüğe başarıyla yerleştirildi.",Toast.LENGTH_LONG).show();
                                found = true;
                                break;
                            }
                        }
                        if(!found) Toast.makeText(SelectCoordinator.this,
                                "Verilen Acm numarasında üye bulunamadı",Toast.LENGTH_LONG).show();
                        regUsers.updateChildren((Map)usersMap);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(SelectCoordinator.this,
                                "There was some error...", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }
}
