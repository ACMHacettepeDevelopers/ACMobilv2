package com.acmhacettepe.developers.acmobil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;


public class addEvent extends AppCompatActivity {
    private Button chooseImg, uploadImg;
    private ImageView imgView;
    private int PICK_IMAGE_REQUEST = 111;
    private Uri filePath;
    private ProgressDialog pd;
    private int eventCount;
    private DatabaseReference mDatabase;
    private TextView eventName;

    //creating reference to firebase storage
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        chooseImg = (Button) findViewById(R.id.chooseImg);
        uploadImg = (Button) findViewById(R.id.uploadImg);
        imgView = (ImageView) findViewById(R.id.imgView);

        pd = new ProgressDialog(this);
        pd.setMessage("Yükleniyor...");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference events = database.child("Events/EventCount");
        final DatabaseReference eventL = database.child("Events").child("EventNames");

        events.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(final DataSnapshot snapshot) {
                eventCount = Integer.valueOf(snapshot.getValue().toString())+1;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }


        });


        chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Etkinlik Resmini Seçiniz"), PICK_IMAGE_REQUEST);
            }
        });

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filePath != null) {
                    pd.show();

                    StorageReference childRef = storageRef.child(eventCount+".jpg");

                    //uploading the image
                    UploadTask uploadTask = childRef.putFile(filePath);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //upload event class to database
                            Map<String, Object> map = new HashMap<String, Object>();
                            eventName = (EditText) findViewById(R.id.EventName);
                            Event object = new Event(eventName.getText().toString(), String.valueOf(eventCount));
                            map.put("event", object);
                            DatabaseReference eventRoot = eventL.child(String.valueOf(eventCount));
                            eventRoot.updateChildren(map);


                            pd.dismiss();
                            mDatabase.child("Events/EventCount").setValue(eventCount);
                            Toast.makeText(addEvent.this, "Etkinlik eklendi", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(addEvent.this, "Etkinlik ekleme başarısız Developers ile görüşün-> " + e, Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else {
                    Toast.makeText(addEvent.this, "Etkinlik resmini seçiniz", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Setting image to ImageView
                imgView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}