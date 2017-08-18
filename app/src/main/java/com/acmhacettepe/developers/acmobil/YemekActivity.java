package com.acmhacettepe.developers.acmobil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.acmhacettepe.developers.acmobil.CommonMethods.getCurrentDate;

public class YemekActivity extends AppCompatActivity {
    ExpandableListView expandableListView;
    ExpListAdapterForFoodList listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yemek);

        if (checkConnection(getBaseContext())) {
            new YemekActivity.GetData().execute(); // Executing asynctask if there is network connection.
        }
        else Toast.makeText(this, "Please check your internet connection and try again.", Toast.LENGTH_LONG).show();

        expandableListView = (ExpandableListView) findViewById(R.id.yemekExpList);
    }

    class GetData extends AsyncTask<Void, Void, HashMap<String, List<String>> > {

        URL url;
        List<String> dateList = new ArrayList<>() ;
        HashMap<String, List<String>> yemekler = new HashMap<>();

        public InputStream getInputStream(URL url) {
            try {
                return url.openConnection().getInputStream();
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected HashMap<String, List<String>> doInBackground(Void... params) { // Parses the xml file and publises the progress for each item.
            try {
                url = new URL("http://www.sksdb.hacettepe.edu.tr/YemekListesi.xml");

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();

                // We will get the XML from an input stream
                xpp.setInput(getInputStream(url), "UTF_8");


                boolean isCorrectDate = true;

                // Returns the type of current event: START_TAG, END_TAG, etc..
                int eventType = xpp.getEventType();
                String date = null;
                while (eventType != XmlPullParser.END_DOCUMENT) {

                    if (eventType == XmlPullParser.START_TAG) {

                        if (xpp.getName().equals("tarih")) {

                            date = xpp.nextText();
                            if (Integer.parseInt(date.split("\\.")[0]) >= Integer.parseInt(getCurrentDate().split("\\.")[0])
                                    && Integer.parseInt(date.split("\\.")[1]) >= Integer.parseInt(getCurrentDate().split("\\.")[1])) {

                                isCorrectDate = true;
                                addToList(yemekler, date, null);
                                dateList.add(date);
                            }
                            else {
                                isCorrectDate = false;
                            }
                        }
                        else if (xpp.getName().equals("yemek" ) && isCorrectDate == true) {
                            addToList(yemekler,date,xpp.nextText());
                        }
                        else if (xpp.getName().equals("kalori") && isCorrectDate == true) {
                            addToList(yemekler,date,xpp.nextText());
                        }
                    }

                    eventType = xpp.next(); //move to next element
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return yemekler;
        }

        @Override
        protected void onPostExecute(HashMap<String, List<String>> foodList) { //This method will be invoked after AsyncTask finished in UI thread.
            // Declaring adapter and setting the adapter.
            listAdapter = new ExpListAdapterForFoodList(getBaseContext(), dateList, foodList ) ;
            expandableListView.setAdapter(listAdapter);
        }
    }

    public boolean checkConnection (Context ctx){ // Method for if there is network connection.
        ConnectivityManager conMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        return i != null && i.isConnected() && i.isAvailable();
    }

    public String getCurrentDate () { // Returns current calendar date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        return df.format(calendar.getTime());
    }

    public synchronized void addToList(HashMap<String, List<String> > map, String key ,String item) { // Method for adding values to array in hashmap
        if (map.get(key) == null) { //gets the value for an id)
            map.put(key, new ArrayList<String>());
        }
        if (item != null) {
            if (!item.trim().isEmpty()) {
                map.get(key).add(item); //adds value to list
            }
        }
    }
}
