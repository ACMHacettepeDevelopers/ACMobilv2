package com.acmhacettepe.developers.acmobil;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.acmhacettepe.developers.acmobil.MainActivity.*;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, AdapterView.OnItemSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Maps
    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
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

        //Remove admin panel button
        MainActivity.adminButton.setVisibility(View.GONE);



        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_map, container, false);
        return mView;


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) mView.findViewById(R.id.map);
        mMapView.onCreate(null);
        mMapView.onResume();
        mMapView.getMapAsync(this);

        Spinner spinner = (Spinner) getView().findViewById(R.id.mekanlar_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.mekanlar_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


    }

    // TODO: Create get function for selected items and add them to map with markers.

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());

        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);




        CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.870631, 32.733120)).zoom(16).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(position==0){ //Kız Öğrenci Yurdu
            MarkerOptions kYurdu = new MarkerOptions().position(new LatLng(39.869935, 32.731901)).title("Kız Öğrenci Yurdu").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(kYurdu);

        }
        else if (position==1){ //Erkek Öğrenci Yurdu
            MarkerOptions eYurdu = new MarkerOptions().position(new LatLng(39.869536, 32.731768)).title("Erkek Öğrenci Yurdu").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(eYurdu);
        }
        else if (position==2){ //Parlar
            MarkerOptions Parlar = new MarkerOptions().position(new LatLng(39.871564, 32.730348)).title("Parlar Öğrenci Evi").snippet("Açık Büfe");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Parlar);
        }
        else if (position==3){ //Atatepe Öğrenci Yurdu
            MarkerOptions Atatepe = new MarkerOptions().position(new LatLng(39.867873, 32.731907)).title("Atatepe Öğrenci Yurdu").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Atatepe);
        }
        else if (position==4){ //City
            MarkerOptions City = new MarkerOptions().position(new LatLng(39.869907, 32.733832)).title("City").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(City);
        }
        else if (position==5){ //Fizik Müh
            MarkerOptions Fizik = new MarkerOptions().position(new LatLng(39.869644, 32.734053)).title("Fizik Mühendisliği").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Fizik);
        }
        else if (position==6){ //Elektrik
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.869977, 32.734572)).title("Elektrik Elektronik Mühendisliği").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
