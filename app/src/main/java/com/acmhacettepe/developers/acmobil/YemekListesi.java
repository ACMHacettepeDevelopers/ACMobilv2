package com.acmhacettepe.developers.acmobil;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class YemekListesi extends Fragment  {

    ExpandableListView expandableListView;
    ExpListAdapterForFoodList listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity.adminButton.setVisibility(View.GONE);
        MainActivity.mainImage.setVisibility(View.GONE);
        MainActivity.mainText.setVisibility(View.GONE);

        View view = inflater.inflate(R.layout.fragment_yemek_listesi, container, false);

        expandableListView = (ExpandableListView) view.findViewById(R.id.yemekExpList);

        if (checkConnection(getContext())) {
            new GetData().execute(); // Executing asynctask if there is network connection.
        }
        else
            Toast.makeText(getActivity(), "Please check your internet connection and try again.", Toast.LENGTH_LONG).show();

        return view;
    }

    /* Setting up a asynchronous task to download the foodList xml. Then parsing it to use
     * the necessary data. We're using a function called compareDatetoToday to eliminate the
     * food lists of older days. OnPostExecute we set up the adapter with the lists which we
     * populated with the foods and date.
     */
    private class GetData extends AsyncTask<Void, Void, HashMap<String, List<String>> > {

        URL url;
        List<String> dateList = new ArrayList<>() ;
        HashMap<String, List<String>> yemekler = new HashMap<>();

        private InputStream getInputStream(URL url) {
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

                // Returns the type of current event: START_TAG, END_TAG, etc..
                int eventType = xpp.getEventType();
                String date = null;
                while (eventType != XmlPullParser.END_DOCUMENT) {

                    if (eventType == XmlPullParser.START_TAG) {

                        if (xpp.getName().equals("tarih")) {

                            date = xpp.nextText();
                            if (compareDatetoToday(date.split("\\s+")[0])) { // check if date is after today or its today
                                addToList(yemekler, date, null);
                                dateList.add(date);
                            }
                        }
                        else if (xpp.getName().equals("yemek" )) {
                            addToList(yemekler,date,xpp.nextText());
                        }
                        else if (xpp.getName().equals("kalori")) {
                            addToList(yemekler,date,"Kalori: " + xpp.nextText());
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
            listAdapter = new ExpListAdapterForFoodList(getContext(), dateList, foodList ) ;
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

    // This method takes a date and returns true if date is after today or its today, returns false if not
    public boolean compareDatetoToday (String date) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date comparedDate = formatter.parse(date);
            Date today = formatter.parse(getCurrentDate());

            if (today.compareTo(comparedDate) < 0 || getCurrentDate().equals(date)) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;

    }
}



