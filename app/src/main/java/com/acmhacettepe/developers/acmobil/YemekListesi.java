package com.acmhacettepe.developers.acmobil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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


public class YemekListesi extends Fragment implements OnFoodListCompleted {

    private ArrayAdapter<String> adapter;

    ListView yemekListesi;

    public YemekListesi() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_yemek_listesi, container, false);

        yemekListesi = (ListView) view.findViewById(R.id.yemekList);

        /*adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.select_dialog_item, android.R.id.text1, new ArrayList<String>());

        yemekListesi.setAdapter(adapter);*/

        if (checkConnection(getContext()) == true)
            new GetData(this).execute(); // Executing asynctask if there is network connection.
        else
            Toast.makeText(getActivity(), "Please check your internet connection and try again.", Toast.LENGTH_LONG).show();

        return view;
    }

    class GetData extends AsyncTask<Void, Void, ArrayList<String> > {
        URL url;
        private final OnFoodListCompleted onTaskCompleted;
        ArrayList<String> yemekListesi = new ArrayList<String>();

        public GetData (OnFoodListCompleted onTaskCompleted) {
            this.onTaskCompleted = onTaskCompleted;
        }

        public InputStream getInputStream(URL url) {
            try {
                return url.openConnection().getInputStream();
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected ArrayList<String> doInBackground(Void... params) { // Parses the xml file and publises the progress for each item.
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
                while (eventType != XmlPullParser.END_DOCUMENT) {

                    if (eventType == XmlPullParser.START_TAG) {

                        if (xpp.getName().equals("tarih")) {
                            String date = xpp.nextText();
                            if (Integer.parseInt(date.split("\\.")[0]) >= Integer.parseInt(getCurrentDate().split("\\.")[0])
                                    && Integer.parseInt(date.split("\\.")[1]) >= Integer.parseInt(getCurrentDate().split("\\.")[1])) {
                                isCorrectDate = true;
                                yemekListesi.add(date);
                            }
                            else {
                                isCorrectDate = false;
                            }
                        }
                        else if (xpp.getName().equals("yemek" ) && isCorrectDate == true) {
                            yemekListesi.add(xpp.nextText());
                        }
                        else if (xpp.getName().equals("kalori") && isCorrectDate == true) {
                            yemekListesi.add(xpp.nextText());
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
            return yemekListesi;
        }

        /*@Override
        protected void onPreExecute() { // First method that is called after execute(). Gets and passes the ArrayAdapter.
            adapter = (ArrayAdapter<String>) yemekListesi.getAdapter();
        }*/

        /*@Override
        protected void onProgressUpdate(String... values) { // This method is called in the UI thread when publishProgress() is called.
            yemekListesi.add(values[0]);
        }*/

        @Override
        protected void onPostExecute(ArrayList<String> yemekler) {
            if (onTaskCompleted != null) {
                onTaskCompleted.onTaskCompleted(yemekler);
            }
        }
    }

    public boolean checkConnection (Context ctx){ // Method for if there is network connection.
        ConnectivityManager conMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        return i != null && i.isConnected() && i.isAvailable();
    }

    public String getCurrentDate () {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        return df.format(calendar.getTime());
    }
    @Override
    public ArrayList<String> onTaskCompleted(ArrayList<String> yemekler) {
        return yemekler;
    }


}

