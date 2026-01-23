package com.example.cloudserviceapp1422026;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cloudserviceapp1422026.WebService.Asynchtask;
import com.example.cloudserviceapp1422026.WebService.WebService;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class EvaluacionApp extends AppCompatActivity implements Asynchtask {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_evaluacion_app);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws = new WebService(
                "https://api.countrylayer.com/v2/all?access_key=cc12ad70a3ab5732b2ff8c33e2ebee66",
                datos, EvaluacionApp.this, EvaluacionApp.this
        );
        ws.execute("GET");
        //esto me estava fallandola llamada de la api
        /*WebService ws= new WebService("https://api.countrylayer.com/v2/all?access_key=",
               datos, EvaluacionApp.this, EvaluacionApp.this);
        ws.execute("GET","Bearer ","cc12ad70a3ab5732b2ff8c33e2ebee66");*/
    }
    @Override
    public void processFinish(String result) throws JSONException

    {
        JSONArray JSONLista = new JSONArray(result);
        ArrayList<NombreEva> listaFiltrada = new ArrayList<>();

        for (int i = 0; i < JSONLista.length(); i++) {
            JSONObject obj = JSONLista.getJSONObject(i);
            String iso2 = obj.getString("alpha2Code").toUpperCase();

            if (!esPaisNoSoportado(iso2)) {
                listaFiltrada.add(new NombreEva(obj));
            }
        }
        Adaptador adaptador = new Adaptador(this, listaFiltrada);
        GridView lstLista = findViewById(R.id.lstListas);
        lstLista.setAdapter(adaptador);

        lstLista.setOnItemClickListener((parent, view, position, id) -> {
            NombreEva pais = listaFiltrada.get(position);
            Intent i = new Intent(EvaluacionApp.this, InfoPais.class);
            i.putExtra("iso2", pais.getIso2());
            startActivity(i);
        });
    }
    //Estos son los paises o banderas que no existen en geognos por eso no lo muestro para que no quede en blanco
    private static final String[] ISO2_NO_GEOGNOS = {
            "AX", "BQ","UM","CW","GF","GP","MQ","XK","RE","SX","GS","SS"
            // Åland Islands // Bonaire// US Minor Outlying Islands// Curaçao// French Guiana// Guadeloupe// Martinique// Kosovo// Réunion// Sint Maarten// South Georgia// South Sudan
    };
    private boolean esPaisNoSoportado(String iso2) {
        for (String s : ISO2_NO_GEOGNOS) {
            if (s.equals(iso2)) return true;
        }
        return false;
    }

}