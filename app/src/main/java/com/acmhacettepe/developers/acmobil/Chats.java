package com.acmhacettepe.developers.acmobil;

/**
 * Created by Oguz on 8/6/2017.
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Chats extends Fragment implements OnClickListener {

    private EditText msg_edittext;
    public static String nickname = "unnamed12";
    private Random random;
    public static ArrayList<ChatMessage> chatlist;
    public static ChatAdapter chatAdapter;
    ListView msgListView;
    public static Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_giybet, container, false);
        random = new Random();

        //Remove admin panel button
        MainActivity.adminButton.setVisibility(View.GONE);
        MainActivity.mainImage.setVisibility(View.GONE);
        MainActivity.mainText.setVisibility(View.GONE);

        //Take username from user
        nameDialog();

        msg_edittext = (EditText) view.findViewById(R.id.messageEditText);
        msgListView = (ListView) view.findViewById(R.id.msgListView);
        ImageButton sendButton = (ImageButton) view
                .findViewById(R.id.sendMessageButton);
        sendButton.setOnClickListener(this);

        // ----Set autoscroll of listview when a new message arrives----//
        msgListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        msgListView.setStackFromBottom(true);

        chatlist = new ArrayList<ChatMessage>();
        chatAdapter = new ChatAdapter(getActivity(), chatlist);
        msgListView.setAdapter(chatAdapter);
        context = getContext();
        return view;
    }

    private void nameDialog(){
        final EditText input = new EditText(getContext());
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Enter Nickname")
                .setView(input)
                .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(input.getText().toString().length() > 17 || input.getText().toString().length() < 3){
                            Toast.makeText(getActivity(), "Rumuzunuz 3-16 harf uzunluğunda olmalıdır.", Toast.LENGTH_LONG).show();

                            nameDialog();//reshow dialog until length is correct
                        }
                        else {
                            nickname = input.getText().toString();

                        }
                    }
                }).setOnKeyListener(new Dialog.OnKeyListener(){
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Toast.makeText(getActivity(), "Lütfen rumuzunuzu giriniz.", Toast.LENGTH_LONG).show();
                }
                return true;
            }

        });
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    public void sendTextMessage(View v) {
        String message = msg_edittext.getEditableText().toString();
            if (!message.equalsIgnoreCase("")) {
                final ChatMessage chatMessage = new ChatMessage(nickname,
                        message, "" + (ChatAdapter.lastId + 1));
                chatMessage.setMsgID();
                chatMessage.body = message;
                chatMessage.Date = CommonMethods.getCurrentDate();
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String currentDateandTime = sdf.format(currentTime);
                chatMessage.Time = currentDateandTime;
                msg_edittext.setText("");
                chatAdapter.add(chatMessage);
                chatAdapter.notifyDataSetChanged();




            }

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendMessageButton:
                sendTextMessage(v);


        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}