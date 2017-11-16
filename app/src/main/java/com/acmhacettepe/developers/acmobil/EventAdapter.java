package com.acmhacettepe.developers.acmobil;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Batuhan on 29.10.2017.
 */

public class EventAdapter extends BaseAdapter{

    ArrayList<String> chatMessageList = new ArrayList<String>();
    private DatabaseReference root;

    public EventAdapter(){
        root = FirebaseDatabase.getInstance().getReference().child("Events").child("EventNames");
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DataSnapshot i = dataSnapshot.getChildren().iterator().next();
                String eventName = ((HashMap)(i).getValue()).get("eventName").toString();
                String number = ((HashMap)(i).getValue()).get("number").toString();
                String data = number + " - " + eventName;
                chatMessageList.add(data);
                notifyDataSetChanged();
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
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
