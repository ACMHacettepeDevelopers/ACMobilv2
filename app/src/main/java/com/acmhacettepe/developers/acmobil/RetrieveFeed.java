package com.acmhacettepe.developers.acmobil;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Gokberk on 6/30/2017.
 */

public class RetrieveFeed extends AsyncTask {

    URL url;
    ArrayList<String> headlines = new ArrayList();
    ArrayList<String> links = new ArrayList();
    @Override
    protected Object doInBackground(Object[] objects) {
        // Initializing instance variables


        try {
            url = new URL("http://www.sksdb.hacettepe.edu.tr/YemekListesi.xml");

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();

            // We will get the XML from an input stream
            xpp.setInput(getInputStream(url), "UTF_8");

        /* We will parse the XML content looking for the "<title>" tag which appears inside the "<item>" tag.
         * However, we should take in consideration that the rss feed name also is enclosed in a "<title>" tag.
         * As we know, every feed begins with these lines: "<channel><title>Feed_Name</title>...."
         * so we should skip the "<title>" tag which is a child of "<channel>" tag,
         * and take in consideration only "<title>" tag which is a child of "<item>"
         *
         * In order to achieve this, we will make use of a boolean variable.
         */
            boolean insideItem = false;

            // Returns the type of current event: START_TAG, END_TAG, etc..
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG) {

                    if (xpp.getName().equalsIgnoreCase("gun")) {
                        insideItem = true;
                    } else if (xpp.getName().equalsIgnoreCase("tarih")) {
                        if (insideItem)
                            links.add(xpp.nextText()); //extract the headline
                    } else if (xpp.getName().equalsIgnoreCase("yemek")) {
                        if (insideItem)
                            links.add(xpp.nextText()); //extract the link of article
                    }
                    else if (xpp.getName().equalsIgnoreCase("kalori")) {
                        if (insideItem)
                            links.add(xpp.nextText()); //extract the link of article
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

        return headlines;
    }


    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    public ArrayList<String> heads()
    {
        return links;
    }
}
