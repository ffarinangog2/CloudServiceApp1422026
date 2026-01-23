package com.example.cloudserviceapp1422026;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NombreEva {
    private String titulo;
    private String portada;

    private String iso2;

    public NombreEva(JSONObject a) throws JSONException {
        titulo = a.getString(  "name").toString();
        iso2 = a.getString("alpha2Code");
        //aqui habia una falla del httt
       //portada = "https://flagcdn.com/w160/"
                //+ a.getString("alpha2Code").toLowerCase()
             //  + ".png";
        portada = "http://www.geognos.com/api/en/countries/flag/" + iso2 + ".png";
    }



    public static ArrayList<NombreEva> JsonObjectsBuild(JSONArray datos) throws JSONException {
        ArrayList<NombreEva> nombre = new ArrayList<>();
        for (int i = 0; i < datos.length() /*&& i<20*/; i++) {
            nombre.add(new NombreEva(datos.getJSONObject(i)));
        }
        return nombre;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public String getIso2() {
        return iso2;
    }



}
