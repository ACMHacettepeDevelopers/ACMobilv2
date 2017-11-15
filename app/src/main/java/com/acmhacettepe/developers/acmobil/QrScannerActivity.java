package com.acmhacettepe.developers.acmobil;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class QrScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView zXingScannerView;
    private DatabaseReference regdUser;
    private DatabaseReference qrRegEvents;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        qrRegEvents = FirebaseDatabase.getInstance().getReference().child("QrRegisteredEvents");

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (mCurrentUser != null) {
            String current_uid = mCurrentUser.getUid();
            regdUser = FirebaseDatabase.getInstance().getReference().child("RegisteredUsers").child(current_uid);
        }



        final int MY_PERMISSIONS_REQUEST_CAMERA = 99;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new android.support.v7.app.AlertDialog.Builder(this)
                        .setTitle("Kamera izni gerekiyor.")
                        .setMessage("ACMobil kameranızı kameranızı kullanmak için izin istiyor. Lütfen bu özelliği kulanabilmek için izin verin.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(QrScannerActivity.this,
                                        new String[]{Manifest.permission.CAMERA},
                                        MY_PERMISSIONS_REQUEST_CAMERA );
                            }
                        })
                        .create()
                        .show();

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA );

            }
        }
        //Setting the view and starting the camera.
        zXingScannerView = new ZXingScannerView(this);
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(QrScannerActivity.this);
        zXingScannerView.startCamera();


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // This method is invoked when permission request dialog is ended.
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // permission was granted, yay! Do the contacts-related task you need to do.

            zXingScannerView = new ZXingScannerView(this);
            setContentView(zXingScannerView);
            zXingScannerView.setResultHandler(QrScannerActivity.this);
            zXingScannerView.startCamera();

        } else {
            // permission denied, boo! Disable the functionality that depends on this permission.
            Toast.makeText(QrScannerActivity.this, "Kamera için izin vermediğiniz sürece bu özelliği kullanamazsınız!",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }



    @Override
    protected void onDestroy() {
        //Stop the camera when activity is destroyed.
        super.onDestroy();
        zXingScannerView.stopCamera();
    }


    @Override
    public void handleResult(Result result) {
        //Handling the scan result.

        final String scanResult = result.getText();

        qrRegEvents.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(scanResult)) {

                    regdUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(final DataSnapshot dataSnapshot) {

                            if (!dataSnapshot.child("participatedEvents").hasChild(scanResult)) {

                                regdUser.child("participatedEvents").child(scanResult).setValue("1")
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            Toast.makeText(QrScannerActivity.this,
                                                    "Etkinliğe katılımınız başarıyla kaydedildi!",
                                                    Toast.LENGTH_LONG).show();

                                            if (!dataSnapshot.hasChild("eventCount")) {
                                                regdUser.child("eventCount").setValue(1);
                                            }
                                            else {
                                                int eventCount = Integer.valueOf(dataSnapshot.child("eventCount")
                                                        .getValue().toString());
                                                regdUser.child("eventCount").setValue(eventCount+1);
                                            }
                                            finish();
                                        }
                                        else {
                                            Toast.makeText(QrScannerActivity.this,
                                                    "Bir hata oluştu lütfen bu hatayı yetkili birine bildirin!",
                                                    Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                    }
                                });

                            }
                            else {
                                Toast.makeText(QrScannerActivity.this, "Bu etkinliğe zaten katıldınız!",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                            Toast.makeText(QrScannerActivity.this,
                                    "Bir hata oluştu lütfen bu hatayı yetkili birine bildirin!",
                                    Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                }
                else {
                    Toast.makeText(QrScannerActivity.this, "Böyle bir etkinliğimiz yok!",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(QrScannerActivity.this,
                        "Bir hata oluştu lütfen bu hatayı yetkili birine bildirin!",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
