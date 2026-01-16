package com.example.cloudserviceapp1422026;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
   GoogleMap mapa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapa = googleMap;

        //personalizado
        //mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
       // mapa.getUiSettings().setZoomControlsEnabled(true);

        CameraUpdate camUpd1 =
        CameraUpdateFactory
        .newLatLngZoom(new LatLng(-1.012913631339308, -79.46966143509194), 10);

        mapa.moveCamera(camUpd1);


        LatLng madrid = new LatLng(-1.012913631339308, -79.46966143509194);

        CameraPosition camPos = new CameraPosition.Builder()
                .target(madrid)
                .zoom(15)
                .bearing(45)      //noreste arriba
                .tilt(70)         //punto de vista de la cámara 70 grados
                .build();

        CameraUpdate camUpd3 =
                CameraUpdateFactory.newCameraPosition(camPos);

        mapa.animateCamera(camUpd3);

        PolylineOptions lineas = new PolylineOptions()
        .add(new LatLng(-1.013557260834752, -79.46715088759329))
        .add(new LatLng(-1.0132139917864333, -79.47200032122319))
        .add(new LatLng(-1.0119481868564997, -79.47191449053946))
        .add(new LatLng(-1.012656179505335, -79.46712942992235))
        .add(new LatLng(-1.013557260834752, -79.46715088759329));

        lineas.width(8);
        lineas.color(Color.RED);

        mapa.addPolyline(lineas);



        LatLng uteq = new LatLng(
                -1.0130230552665598, -79.46939056333176
        );

        // Marcador
        mapa.addMarker(new MarkerOptions()
                .position(uteq)
                .title("Universidad Técnica Estatal de Quevedo")
        );

    }
}