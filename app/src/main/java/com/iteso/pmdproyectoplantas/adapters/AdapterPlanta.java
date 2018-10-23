package com.iteso.pmdproyectoplantas.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iteso.pmdproyectoplantas.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterPlanta extends RecyclerView.Adapter<AdapterPlanta.ViewHolder> {
    //TODO: Cambiar object por el bean de planta
    private List<Object> mDataSet;
    private Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterPlanta(Context context, ArrayList<Object> myDataSet) {
        mDataSet = myDataSet;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdapterPlanta.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_planta_individual, parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //TODO: llenar a Holder (la clase de abajo) con los datos de verdad

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagen;
        public TextView nombre;
        public TextView descripcion;

        public ViewHolder(View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.planta_imagen);
            nombre = itemView.findViewById(R.id.planta_nombre);
            descripcion = itemView.findViewById(R.id.planta_descripcion);
        }
    }
}
