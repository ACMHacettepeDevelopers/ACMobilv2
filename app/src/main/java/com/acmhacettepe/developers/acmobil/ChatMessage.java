package com.acmhacettepe.developers.acmobil;

/**
 * Created by Oguz on 8/6/2017.
 */

import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

public class ChatMessage {

    public String body, senderName;
    public String Date, Time;
    public String msgid;
    public String userId;
    private FirebaseAuth auth;


    public ChatMessage(String Sender, String messageString, String ID) {
        auth = FirebaseAuth.getInstance();
        body = messageString;
        senderName = Sender;
        msgid = ID;
        userId = auth.getCurrentUser().getUid();
    }

    public ChatMessage(String Sender, String messageString, String ID, String uid) {
        body = messageString;
        senderName = Sender;
        msgid = ID;
        userId = uid;
    }

    public void setMsgID() {
        msgid += "-" + String.format("%02d", new Random().nextInt(100000));
        System.out.println(msgid);
    }

    public boolean IsMe(){
        return senderName.equals(Chats.nickname);
    }
}