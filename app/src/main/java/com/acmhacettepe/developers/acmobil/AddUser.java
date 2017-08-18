package com.acmhacettepe.developers.acmobil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddUser extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    Button button;
    private EditText ogrNum;
    private TextView addUserNum, addUserAcmNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        //Get Firebase database instance

        mDatabase = FirebaseDatabase.getInstance().getReference();
        ogrNum = (EditText) findViewById(R.id.ogrNumSignUp);
        addUserNum = (TextView) findViewById(R.id.addUserNumber);
        addUserAcmNum = (TextView) findViewById(R.id.addUserAcmNumber);

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference lastNumber = database.child("LastNumber");


        button = (Button) findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastNumber.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        String lastNumber = snapshot.getValue().toString();
                        final String ogrNumText = ogrNum.getText().toString().trim();
                        addUserNum.setText(ogrNumText);
                        User user = new User("", lastNumber, false, false);
                        int acmNum = Integer.valueOf(lastNumber)+1;
                        mDatabase.child("LastNumber").setValue(acmNum);
                        addUserAcmNum.setText(String.valueOf(acmNum));
                        mDatabase.child("AddedUsers").child(ogrNumText).setValue(user);
                        Toast.makeText(AddUser.this, "Üye Başarıyla Eklendi", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}



class User {

    public String acmNum;
    public boolean isAdmin;
    public boolean isRegistered;
    public String username;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String acmNum, boolean isRegistered, boolean isAdmin) {
        this.username = username;
        this.acmNum = acmNum;
        this.isAdmin = isAdmin;
        this.isRegistered = isRegistered;
    }

}