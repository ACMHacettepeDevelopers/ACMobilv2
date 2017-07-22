package com.acmhacettepe.developers.acmobil;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class YemekListesi extends Fragment {
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

        ListView yemekListesi = (ListView) view.findViewById(R.id.yemekList);

        ArrayList<String> headlines = new ArrayList<String>();

        RetrieveFeed getXML = new RetrieveFeed();
        getXML.execute();
        int counter = 0;

        while (headlines.isEmpty() || counter <= 10) {
            counter++;


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, headlines);

            headlines = getXML.heads();
            adapter.notifyDataSetChanged();
            yemekListesi.setAdapter(adapter);

        }
        return view;
    }

}
