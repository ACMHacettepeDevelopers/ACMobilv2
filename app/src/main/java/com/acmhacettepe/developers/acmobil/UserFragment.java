package com.acmhacettepe.developers.acmobil;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;




/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FirebaseAuth auth;
    private DatabaseReference mDatabase;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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

        final View _view = inflater.inflate(R.layout.fragment_user, container, false);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference registeredUsers = db.child("RegisteredUsers");


        //Remove admin panel button
        MainActivity.adminButton.setVisibility(View.GONE);
        MainActivity.mainImage.setVisibility(View.GONE);
        MainActivity.mainText.setVisibility(View.GONE);

        registeredUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final ImageView pp = (ImageView) _view.findViewById(R.id.profileP);
                final TextView userName = (TextView) _view.findViewById(R.id.fragmentUser);
                final TextView userNameGiybet = (TextView) _view.findViewById(R.id.username);

                auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                if(user!=null){
                    final String User = user.getUid();

                    userName.setText(snapshot.child(User).child("name").getValue().toString());
                    userNameGiybet.setText(snapshot.child(User).child("username").getValue().toString());

                    Picasso.with(getContext()).load("https://robohash.org/" + snapshot.child(User).child("username").getValue()+ "?set=set2&bgset=bg2&size=160x160").transform(new CircleTransform()).into(pp);

                } else{

                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        String[] items = {"Kullanıcı Adını Değiştir", "Şifreyi Değiştir", "İsim Değiştir"};



        ListAdapter userAdapter = new ArrayAdapter<String>(_view.getContext(), android.R.layout.simple_list_item_1, items);
        ListView userList = (ListView) _view.findViewById(R.id.userList);
        userList.setAdapter(userAdapter);




        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){ //Change username
                    final EditText input = new EditText(getContext());
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Kullanıcı Adı Giriniz")
                            .setView(input)
                            .setPositiveButton("Tamam", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    auth = FirebaseAuth.getInstance();
                                    String text = input.getText().toString();
                                    if(text.length() < 3 || text.length() >32){
                                        Toast.makeText(getContext(), "Yeni kullanıcı adı 3-32 karakter arasında olmalıdır", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        final String User = auth.getCurrentUser().getUid();
                                        final TextView userName = (TextView) _view.findViewById(R.id.username);
                                        final ImageView pp = (ImageView) _view.findViewById(R.id.profileP);

                                        mDatabase = FirebaseDatabase.getInstance().getReference();
                                        mDatabase.child("RegisteredUsers").child(User).child("username").setValue(text);
                                        userName.setText(text);
                                        Picasso.with(getContext()).load("https://robohash.org/" + text + "?set=set2&bgset=bg2&size=160x160").transform(new CircleTransform()).into(pp);
                                    }


                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.setCanceledOnTouchOutside(true);
                    alert.show();

                } else if (position==1){ //Change password
                    Intent intent = new Intent(getActivity(),ResetPasswordActivity.class);
                    startActivity(intent);
                } else if (position==2){ //Change coord.
                    final EditText input = new EditText(getContext());
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("İsim Giriniz")
                            .setView(input)
                            .setPositiveButton("Tamam", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    auth = FirebaseAuth.getInstance();
                                    String text = input.getText().toString();

                                        final String User = auth.getCurrentUser().getUid();
                                        final TextView userName = (TextView) _view.findViewById(R.id.fragmentUser);

                                        mDatabase = FirebaseDatabase.getInstance().getReference();
                                        mDatabase.child("RegisteredUsers").child(User).child("name").setValue(text);
                                        userName.setText(text);




                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.setCanceledOnTouchOutside(true);
                    alert.show();

                }



            }
        });




        // Inflate the layout for this fragment
        return _view;
    }

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
