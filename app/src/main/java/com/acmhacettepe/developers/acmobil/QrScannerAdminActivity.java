package com.acmhacettepe.developers.acmobil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class QrScannerAdminActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView zXingScannerView;
    private DatabaseReference qrRegisteredEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Settingg up the path for database Reference.
        qrRegisteredEvents = FirebaseDatabase.getInstance().getReference().child("QrRegisteredEvents");

        final int MY_PERMISSIONS_REQUEST_CAMERA = 99;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.CAMERA)) {

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
                                ActivityCompat.requestPermissions(QrScannerAdminActivity.this,
                                        new String[]{android.Manifest.permission.CAMERA},
                                        MY_PERMISSIONS_REQUEST_CAMERA );
                            }
                        })
                        .create()
                        .show();

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA );

            }
        }
        //Setting the view and starting the camera.
        zXingScannerView = new ZXingScannerView(this);
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(QrScannerAdminActivity.this);
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
            zXingScannerView.setResultHandler(QrScannerAdminActivity.this);
            zXingScannerView.startCamera();

        } else {
            // permission denied, boo! Disable the functionality that depends on this permission.
            Toast.makeText(QrScannerAdminActivity.this, "Kamera için izin vermediğiniz sürece bu özelliği kullanamazsınız!",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    // Continue using camera.
    @Override
    protected void onResume() {
        super.onResume();
        zXingScannerView.resumeCameraPreview(QrScannerAdminActivity.this);
    }

    // Stop the camera when activity is destroyed.
    @Override
    protected void onDestroy() {
        //Stop the camera when activity is destroyed.
        super.onDestroy();
        zXingScannerView.stopCamera();
    }


    @Override
    public void handleResult(Result result) {
        //Handling the scan result.

        final String scanResult = result.getText(); // Get text of the QR code.
        AlertDialog.Builder builder = new AlertDialog.Builder(QrScannerAdminActivity.this);
        builder.setTitle(scanResult + "yeni etkinlik olarak ekle?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                qrRegisteredEvents.child(scanResult).setValue("1").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(QrScannerAdminActivity.this,
                                    "Etkinlik başarılı bir şekilde eklendi!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else {
                            Toast.makeText(QrScannerAdminActivity.this,
                                    "Etkinliği eklerken bir hata oluştu! Gökberk'e ulaşın!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                zXingScannerView.resumeCameraPreview(QrScannerAdminActivity.this);
            }
        });
        builder.setMessage(scanResult);
        AlertDialog alert = builder.create();
        alert.show();

    }
}
