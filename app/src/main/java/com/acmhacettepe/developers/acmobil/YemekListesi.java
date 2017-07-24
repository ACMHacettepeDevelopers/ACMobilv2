package com.acmhacettepe.developers.acmobil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import java.util.ArrayList;


public class YemekListesi extends Fragment {
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


        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, new ArrayList<String>());

        yemekListesi.setAdapter(adapter);

        if (checkConnection(getContext()) == true)
            new GetData().execute();
        else
            Toast.makeText(getActivity(), "Please check your internet connection and try again.", Toast.LENGTH_LONG).show();

        return view;
    }

    class GetData extends AsyncTask<Void, String, Void>{
        URL url;

        ArrayAdapter<String> adapter;

        public InputStream getInputStream(URL url) {
            try {
                return url.openConnection().getInputStream();
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                url = new URL("http://www.sksdb.hacettepe.edu.tr/YemekListesi.xml");

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();

                // We will get the XML from an input stream
                xpp.setInput(getInputStream(url), "UTF_8");


                boolean insideItem = false;

                // Returns the type of current event: START_TAG, END_TAG, etc..
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {

                    if (eventType == XmlPullParser.START_TAG) {

                        if (xpp.getName().equalsIgnoreCase("gun")) {
                            insideItem = true;
                        } else if (xpp.getName().equalsIgnoreCase("tarih")) {
                            if (insideItem)
                                publishProgress(xpp.nextText());
                        } else if (xpp.getName().equalsIgnoreCase("yemek")) {
                            if (insideItem)
                                publishProgress(xpp.nextText());
                        } else if (xpp.getName().equalsIgnoreCase("kalori")) {
                            if (insideItem)
                                publishProgress(xpp.nextText());
                        }
                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("gun")) {
                        insideItem = false;
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
            return null;
        }

        @Override
        protected void onPreExecute() {
            adapter = (ArrayAdapter<String>) yemekListesi.getAdapter();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            adapter.add(values[0]);
        }
    }

    public boolean checkConnection (Context ctx){
        ConnectivityManager conMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        return i != null && i.isConnected() && i.isAvailable();
    }

}
