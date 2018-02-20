package com.acmhacettepe.developers.acmobil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class DiscountFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ExpandableListView expandableListView;
    ExpListAdapterForDiscounts listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public DiscountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiscountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiscountFragment newInstance(String param1, String param2) {
        DiscountFragment fragment = new DiscountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity.adminButton.setVisibility(View.GONE);
        MainActivity.mainImage.setVisibility(View.GONE);
        MainActivity.mainText.setVisibility(View.GONE);

        View view = inflater.inflate(R.layout.fragment_discount, container, false);

        expandableListView = (ExpandableListView) view.findViewById(R.id.discountExpList);

        prepareListData();

        listAdapter = new ExpListAdapterForDiscounts(getContext(), listDataHeader, listDataChild);
        expandableListView.setAdapter(listAdapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event


    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();


        listDataHeader.add("Bahçeli");
        listDataHeader.add("Beytepe Kampüsü");
        listDataHeader.add("Cepa AVM");
        listDataHeader.add("Arcadium AVM");
        listDataHeader.add("Kızılay");
        listDataHeader.add("Gordion AVM");
        listDataHeader.add("Kentpark AVM");
        listDataHeader.add("Tunalı");
        listDataHeader.add("Armada AVM");

        // Adding child data
        List<String> bahceli = new ArrayList<>();
        bahceli.add("Kocatepe Kahvecisi (3. ve 7. Cadde) %10 indirim");
        bahceli.add("HMBRGR (3.Cadde) %10 indirim");
        bahceli.add("Ab'bas Waffle %10 indirim");
        bahceli.add("Baattin %10 indirim");
        bahceli.add("Espresso Lab %15 indirim");
        bahceli.add("Benzin %15 indirim");
        bahceli.add("OT %15 indirim");
        bahceli.add("Tabu %20 indirim");

        List<String> beytepe = new ArrayList<>();
        beytepe.add("Çikoponçi %10 indirim");
        beytepe.add("Şirin Baba %10 indirim (20TL üzerine %20)");
        beytepe.add("Döner 360 %10 indirim");
        beytepe.add("Yelken %10 indirim");
        beytepe.add("Vitamin %10 indirim");
        beytepe.add("On the Corner %15 indirim");

        List<String> cepa = new ArrayList<>();
        cepa.add("Zeynel Çilli %10 indirim");
        cepa.add("Kukla Döner %15 indirim");

        List<String> arcadium = new ArrayList<>();
        arcadium.add("HMBRGR %10 indirim");
        arcadium.add("İl Pazzi %10 indirim");

        List<String> kizilay = new ArrayList<>();
        kizilay.add("Route %10 indirim");
        kizilay.add("El Paso %10 indirim");
        kizilay.add("OT %15 indirim");
        kizilay.add("Koliba %15 indirim (Akşam 7'den sonra %10)");

        List<String> gordion = new ArrayList<>();
        gordion.add("HMBRGR %10 indirim");
        gordion.add("Zeynel Çilli %10 indirim");

        List<String> kentpark = new ArrayList<>();
        kentpark.add("Şah Antep %10 indirim");
        kentpark.add("Döner Park %10 indirim");
        kentpark.add("Mado %10 indirim");

        List<String> tunali = new ArrayList<>();
        tunali.add("Ab'bas %10 indirim");
        tunali.add("Pub 65 %10 indirim (Akşam 6'ya kadar)");
        tunali.add("City Lounge %10 indirim");
        tunali.add("El Paso %15 indirim");
        tunali.add("Bench %15 indirim");
        tunali.add("Dumadum %15 indirim");
        tunali.add("IF %10 indirim (Alkollü içeceklerde)");

        List<String> armada = new ArrayList<>();
        armada.add("HMBRGR %10 indirim");
        armada.add("Zeynel Çilli %20 indirim");


        listDataChild.put(listDataHeader.get(0), bahceli);
        listDataChild.put(listDataHeader.get(1), beytepe);
        listDataChild.put(listDataHeader.get(2), cepa);
        listDataChild.put(listDataHeader.get(3), arcadium);
        listDataChild.put(listDataHeader.get(4), kizilay);
        listDataChild.put(listDataHeader.get(5), gordion);
        listDataChild.put(listDataHeader.get(6), kentpark);
        listDataChild.put(listDataHeader.get(7), tunali);
        listDataChild.put(listDataHeader.get(8), armada);
    }
}
