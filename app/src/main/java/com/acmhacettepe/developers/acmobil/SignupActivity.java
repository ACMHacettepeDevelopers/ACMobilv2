package com.acmhacettepe.developers.acmobil;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputOgrNum, inputUsername, inputUyeNum;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private VideoView mVideoView2;
    private DatabaseReference mDatabase;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Start of background video
        mVideoView2 = (VideoView) findViewById(R.id.videoSignUp);

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.bgvideo);
        mVideoView2.setVideoURI(uri);
        mVideoView2.start();

        mVideoView2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        //End of background video

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        //Get Firebase database instance
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputOgrNum = (EditText) findViewById(R.id.ogrNum);
        inputUsername = (EditText) findViewById(R.id.username);
        inputUyeNum = (EditText) findViewById(R.id.uyeNum);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        btnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();
                final String ogrNum = inputOgrNum.getText().toString().trim();
                final String username = inputUsername.getText().toString().trim();
                final String uyeNum = inputUyeNum.getText().toString().trim();
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                DatabaseReference users = database.child("AddedUsers");
                mDatabase = FirebaseDatabase.getInstance().getReference();


                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        System.out.println(snapshot.child(ogrNum).child("acmNum").getValue().equals(uyeNum));
                        if (snapshot.child(ogrNum).exists() &&
                                snapshot.child(ogrNum).child("isRegistered").getValue().equals(false) &&
                                snapshot.child(ogrNum).child("acmNum").getValue().equals(uyeNum)) {



                            if (TextUtils.isEmpty(email)) {
                                Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (TextUtils.isEmpty(password)) {
                                Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (password.length() < 6) {
                                Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                                return;
                            }



                            progressBar.setVisibility(View.VISIBLE);

                            //create user
                            auth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            progressBar.setVisibility(View.GONE);

                                            mDatabase.child("AddedUsers/").child(ogrNum).child("isRegistered").setValue(true);
                                            mDatabase.child("AddedUsers/").child(ogrNum).child("userName").setValue(username);
                                            mDatabase.child("RegisteredUsers").child(auth.getCurrentUser().getUid()).setValue(username);
                                            // If sign in fails, display a message to the user. If sign in succeeds
                                            // the auth state listener will be notified and logic to handle the
                                            // signed in user can be handled in the listener.
                                            if (!task.isSuccessful()) {
                                                Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                                        Toast.LENGTH_SHORT).show();
                                            } else {
                                                if(mDatabase.child("AddedUsers/").child(ogrNum).child("isAdmin").equals(true)){
                                                    startActivity(new Intent(SignupActivity.this, AdminPanel.class));
                                                } else{
                                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                                }
                                                finish();
                                            }
                                        }
                                    });

                        } else {

                                Toast.makeText(getApplicationContext(), "Ogr no hatali", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);

        // This line needed for video playback bug.
        mVideoView2.start();
    }
}