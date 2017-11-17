package com.acmhacettepe.developers.acmobil;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Batuhan on 28.10.2017.
 */

public class Event {
    public String eventName;
    public String number;
    private static FirebaseAuth auth = FirebaseAuth.getInstance();


    public Event(String eventName,String number){
        auth = FirebaseAuth.getInstance();
        this.eventName = eventName;
        this.number = number;
    }


}
