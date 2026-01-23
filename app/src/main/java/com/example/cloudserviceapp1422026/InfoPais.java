package com.example.cloudserviceapp1422026;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.cloudserviceapp1422026.WebService.Asynchtask;
import com.example.cloudserviceapp1422026.WebService.WebService;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
public class InfoPais extends AppCompatActivity implements OnMapReadyCallback, Asynchtask {
    GoogleMap mapa;
    TextView txtNombre, txtCapital, txtIso2, txtIso3, txtIsoNum, txtFips, txtTel, txtCenter, txtRectangle;
    ImageView imgBandera;
    Double oeste, este, norte, sur;
    Double centerLat, centerLng;
    Polygon poligono = null;
    boolean datosListos = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pais);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtNombre = findViewById(R.id.txtNombre);
        txtCapital = findViewById(R.id.txtCapital);
        txtIso2 = findViewById(R.id.txtIso2);
        txtIso3 = findViewById(R.id.txtIso3);
        txtIsoNum = findViewById(R.id.txtIsoNum);
        txtFips = findViewById(R.id.txtFips);
        txtTel = findViewById(R.id.txtTel);
        txtCenter = findViewById(R.id.txtCenter);
        txtRectangle = findViewById(R.id.txtRectangle);
        imgBandera = findViewById(R.id.imgBandera);

        String iso2 = getIntent().getStringExtra("iso2");
        if (iso2 == null) iso2 = "";
        iso2 = iso2.toUpperCase();

        String urlFlag = "http://www.geognos.com/api/en/countries/flag/" + iso2 + ".png";
        Glide.with(this).load(urlFlag).into(imgBandera);

        Map<String, String> datos = new HashMap<>();
        WebService ws = new WebService(
                "http://www.geognos.com/api/en/countries/info/" + iso2 + ".json",
                datos,
                InfoPais.this,
                InfoPais.this
        );
        ws.execute("GET");

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapa = googleMap;
        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mapa.getUiSettings().setZoomControlsEnabled(true);
        if (datosListos) dibujarPais();
    }
    @Override
    public void processFinish(String result) throws JSONException {
        JSONObject root = new JSONObject(result);
        JSONObject results = root.getJSONObject("Results");
        txtNombre.setText(results.getString("Name"));
        JSONObject capitalObj = results.getJSONObject("Capital");
        txtCapital.setText("Capital: " + capitalObj.getString("Name"));
        JSONObject codes = results.getJSONObject("CountryCodes");
        txtIso2.setText("ISO2: " + codes.getString("iso2"));
        txtIso3.setText("ISO3: " + codes.getString("iso3"));
        txtIsoNum.setText("ISO Num: " + codes.getString("isoN"));
        txtFips.setText("FIPS: " + codes.getString("fips"));
        txtTel.setText("Tel Prefix: +" + results.getString("TelPref"));
        JSONArray center = results.getJSONArray("GeoPt");
        centerLat = center.getDouble(0);
        centerLng = center.getDouble(1);
        txtCenter.setText("Center: " + centerLat + ", " + centerLng);
        JSONObject rect = results.getJSONObject("GeoRectangle");
        oeste = rect.getDouble("West");
        este = rect.getDouble("East");
        norte = rect.getDouble("North");
        sur=rect.getDouble("South");
        txtRectangle.setText("Rectangle: " + oeste + ", " + este + ", " + norte + ", " + sur);
        datosListos = true;
        if (mapa != null) dibujarPais();
    }
    private void dibujarPais() {

        if (poligono != null) {
            poligono.remove();
            poligono = null;
        }
        PolygonOptions poly = new PolygonOptions()
                .add(new LatLng(norte, oeste))
                .add(new LatLng(norte, este))
                .add(new LatLng(sur, este))
                .add(new LatLng(sur, oeste))
                .strokeColor(Color.BLACK)
                .fillColor(Color.argb(60, 150, 50, 50));
        poligono = mapa.addPolygon(poly);
        CameraUpdate cam = CameraUpdateFactory.newLatLngZoom(
                new LatLng(centerLat, centerLng), 5);
        mapa.moveCamera(cam);
    }
}
