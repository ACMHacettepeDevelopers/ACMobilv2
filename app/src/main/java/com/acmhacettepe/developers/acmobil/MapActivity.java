package com.acmhacettepe.developers.acmobil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    //Maps
    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(null);
        mMapView.onResume();
        mMapView.getMapAsync(this);

        Spinner spinner = (Spinner) findViewById(R.id.mekanlar_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getBaseContext(),
                R.array.mekanlar_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==0){ //Kız Öğrenci Yurdu
            MarkerOptions kYurdu = new MarkerOptions().position(new LatLng(39.869935, 32.731901)).title("Kız Öğrenci Yurdu").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(kYurdu);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.869935, 32.731901)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));

        }
        else if (position==1){ //Erkek Öğrenci Yurdu
            MarkerOptions eYurdu = new MarkerOptions().position(new LatLng(39.869536, 32.731768)).title("Erkek Öğrenci Yurdu").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(eYurdu);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.869536, 32.731768)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==2){ //Parlar
            MarkerOptions Parlar = new MarkerOptions().position(new LatLng(39.871564, 32.730348)).title("Parlar Öğrenci Evi").snippet("Açık Büfe");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Parlar);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.871564, 32.730348)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==3){ //Atatepe Öğrenci Yurdu
            MarkerOptions Atatepe = new MarkerOptions().position(new LatLng(39.867873, 32.731907)).title("Atatepe Öğrenci Yurdu").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Atatepe);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.867873, 32.731907)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==4){ //Öğrenci Evleri
            MarkerOptions City = new MarkerOptions().position(new LatLng(39.868436, 32.730412)).title("Öğrenci Evleri").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(City);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.868436, 32.730412)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==5){ //Spor Bilimleri
            MarkerOptions Fizik = new MarkerOptions().position(new LatLng(39.871357, 32.730986)).title("Spor Bilimleri").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Fizik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.871357, 32.730986)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==6){ //Ego Durakları
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.871254, 32.733179)).title("Ego Durağı").snippet("");
            MarkerOptions Elektronik1 = new MarkerOptions().position(new LatLng(39.867364, 32.733328)).title("Ego Durağı").snippet("");
            MarkerOptions Elektronik2 = new MarkerOptions().position(new LatLng(39.864348, 32.733476)).title("Ego Durağı").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            mGoogleMap.addMarker(Elektronik1);
            mGoogleMap.addMarker(Elektronik2);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.871254, 32.733179)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==7){ //Karma Yurt
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.867835, 32.731030)).title("Karma Yurt").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.867835, 32.731030)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==8){ //City
            MarkerOptions City = new MarkerOptions().position(new LatLng(39.869922, 32.733852)).title("City").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(City);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.869922, 32.733852)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==9){ //Şok
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.868736, 32.731910)).title("Şok").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.868736, 32.731910)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==10){ //Bam
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.868547, 32.731915)).title("BAM").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.868547, 32.731915)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }else if (position==11){ //Ydyo
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.864516, 32.735643)).title("Yabancı Diller Yüksek Okulu").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.864516, 32.735643)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }else if (position==12){ //Yıldız Amfi
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.869994, 32.735249)).title("Yıldız Amfi").snippet("");
            MarkerOptions Elektronik1 = new MarkerOptions().position(new LatLng(39.870027, 32.735979)).title("Yıldız Amfi").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            mGoogleMap.addMarker(Elektronik1);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.869994, 32.735249)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==13){ //Mae
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.868211, 32.736988)).title("Mehmet Akif Ersoy Salonu").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.868211, 32.736988)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }else if (position==14){ //Rektörlük
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.867931, 32.734701)).title("Beytepe Kampüsü Rektörlüğü").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.867931, 32.734701)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==15){ //Gün Hastanesi
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.867927, 32.733328)).title("Beytepe Gün Hastanesi").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.867927, 32.733328)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==16){ //Açık Hava Tiyatrosu
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.874077, 32.729096)).title("Beytepe Açık Hava Tiyatrosu").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.874077, 32.729096)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==17){ //Edebiyat Fakültesi
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.872192, 32.736864)).title("Edebiyat Fakültesi").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.872192, 32.736864)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==18){ //Hukuk Fakültesi
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.863310, 32.732154)).title("Hukuk Fakültesi").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.863310, 32.732154)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==19){ //Güzel Sanatlar Fakültesi
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.865944, 32.735714)).title("Güzel Sanatlar Fakültesi").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.865944, 32.735714)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==20){ //İktisadi ve idari bilimler fakültesi
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.865269, 32.736390)).title("İktisadi ve İdari Bilimler Fakültesi").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.865269, 32.736390)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==21){ //Eğitim bilimleri fakültesi
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.864421, 32.736090)).title("Eğitim Bilimleri Fakültesi").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.864421, 32.736090)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==22){ //Fizik Nükleer Enerji Mühendisliği
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.869645, 32.734085)).title("Fizik Nükleer Enerji Mühendisliği").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.869645, 32.734085)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }else if (position==23){ //Elektrik Elektronik Mühendisliği
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.870028, 32.734500)).title("Elektrik Elektronik Mühendisliği").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.870028, 32.734500)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==24){ //Bilgisayar Mühendisliği
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.871498, 32.736454)).title("Bilgisayar Mühendisliği").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.871498, 32.736454)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==25){ //Çevre Mühendisliği
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.871482, 32.737849)).title("Çevre Mühendisliği").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.871482, 32.737849)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==26){ //Jeoloji mühendisliği
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.871148, 32.736338)).title("Jeoloji mühendisliği").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.871148, 32.736338)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==27){ //Kimya Mühendisliği
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.869234, 32.736799)).title("Kimya Mühendisliği").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.869234, 32.736799)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }else if (position==28){ //Makine Mühendisliği
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.863589, 32.734584)).title("Makine Mühendisliği").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.863589, 32.734584)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==29){ //Otomotiv Mühendisliği
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.863589, 32.734584)).title("Otomotiv Mühendisliği").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.863589, 32.734584)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==30){ //Gıda Mühendisliği
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.866586, 32.737715)).title("Gıda Mühendisliği").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.866586, 32.737715)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }else if (position==31){ //Bilgi İşlem
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.871084, 32.736076)).title("Bilgi İşlem").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.871084, 32.736076)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==32){ //Kütüphane
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.870711, 32.734857)).title("Kütüphane").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.870711, 32.734857)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==33){ //Atmler
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.870602, 32.733345)).title("Atmler").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.870602, 32.733345)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==34){ //Ptt
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.871096, 32.735627)).title("Ptt").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.871096, 32.735627)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==35){ //Matematik Bölümü
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.870012, 32.736622)).title("Matematik Bölümü").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.870012, 32.736622)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }else if (position==36){ //Biyoloji Bölümü
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.871210, 32.735525)).title("Biyoloji Bölümü").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.871210, 32.735525)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==37){ //Geomatik Mühendislik
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.865639, 32.733676)).title("Geomatik Mühendisliği").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.865639, 32.733676)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==38){ //Yeşil Vadi
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.866579, 32.740211)).title("Yeşil Vadi").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.866579, 32.740211)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==39){ //Halı Saha
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.872215, 32.734696)).title("Halı Saha").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.872215, 32.734696)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==40){ //Güneş Saatleri Parkı
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.866721, 32.733735)).title("Güneş Saatleri Parkı").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.866721, 32.733735)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==41){ //Beytepe Stadı
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.871074, 32.732490)).title("Beytepe Stadı").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.871074, 32.732490)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==42){ //Beytepe Anaokulu
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.863993, 32.738002)).title("Beytepe Anaokulu").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.863993, 32.738002)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==43){ //Hacettepe Teknokent
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.862824, 32.735953)).title("Hacettepe Teknokent").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.862824, 32.735953)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==44){ //Kongre Merkezi
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.881816, 32.733237)).title("Kongre Merkezi").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.881816, 32.733237)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==45){ //Olimpik Yüzme Havuzu
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.882528, 32.733564)).title("Olimpik Yüzme Havuzu").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.882528, 32.733564)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==46){ //Yemekhaneler
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.870904, 32.734100)).title("Ana Yemekhanele").snippet("");
            MarkerOptions Elektronik1 = new MarkerOptions().position(new LatLng(39.864705, 32.733787)).title("Ydyo Yemekhane").snippet("");
            MarkerOptions Elektronik2 = new MarkerOptions().position(new LatLng(39.867711, 32.737183)).title("Mae Yemekhane").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            mGoogleMap.addMarker(Elektronik1);
            mGoogleMap.addMarker(Elektronik2);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.869977, 32.734572)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==47){ //Nizamiye
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.895872, 32.733793)).title("Nizamiye").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.895872, 32.733793)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==48){ //Yapı Kredi Bankası
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.869093, 32.735922)).title("Yapı Kredi Bankası").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.869093, 32.735922)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }else if (position==49){ //Vakıfbank
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.870714, 32.734084)).title("Vakıfbank").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.870714, 32.734084)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
        else if (position==50){ //Nacho
            MarkerOptions Elektronik = new MarkerOptions().position(new LatLng(39.871808, 32.737000)).title("Nacho").snippet("");
            mGoogleMap.clear();
            mGoogleMap.addMarker(Elektronik);
            CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.871808, 32.737000)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getBaseContext());

        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);




        CameraPosition Camera = CameraPosition.builder().target(new LatLng(39.870631, 32.733120)).zoom(16).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Camera));
    }
}
