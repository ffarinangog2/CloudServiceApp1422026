package com.example.cloudserviceapp1422026;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adaptador extends ArrayAdapter<NombreEva> {


    public Adaptador(Context context, ArrayList<NombreEva> datos)
    {
        super(context, R.layout.item, datos);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.item, null);

        TextView lblTitulo = (TextView)item.findViewById(R.id.lblTitulo);
        lblTitulo.setText(getItem(position).getTitulo());



        ImageView imageView = (ImageView)item.findViewById(R.id.imgPortada);
        Glide.with(this.getContext())
                .load(getItem(position).getPortada())
               // .placeholder(R.drawable.ic_launcher_background)
               // .error(R.drawable.ic_launcher_foreground)
                .into(imageView);

        return(item);

    }


}
