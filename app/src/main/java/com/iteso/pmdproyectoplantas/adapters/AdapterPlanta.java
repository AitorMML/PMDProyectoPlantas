package com.iteso.pmdproyectoplantas.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.iteso.pmdproyectoplantas.R;
import com.iteso.pmdproyectoplantas.beans.Grupo;
import com.iteso.pmdproyectoplantas.beans.Planta;

import java.util.ArrayList;
import java.util.List;

public class AdapterPlanta extends RecyclerView.Adapter<AdapterPlanta.ViewHolder> implements Filterable {
    private List<Planta> mDataSet;
    private Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterPlanta(Context context, ArrayList<Planta> myDataSet) {
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
        holder.nombre.setText("Tulipanes");
        holder.descripcion.setText("Flores de sombra moderada");
        holder.imagen.setImageResource(R.drawable.tulipanes);
        holder.imagen.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                //TODO: Implementar safe check para evitar que se vacie la lista
                mDataSet = (List<Planta>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Planta> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = mDataSet;
                } else {
                    filteredResults = getSearchResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }

    protected List<Planta> getSearchResults(String query) {
        List<Planta> results = new ArrayList<>(mDataSet);

        /*for (Grupo item : mDataSet) {
            if (item.getName().toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }*/
        //TODO: Implementar la búsqueda adecuada deacuerdo al bean Planta
        for(int i=0; i<query.length(); ++i) {
            results.remove(0);
        }

        return results;
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
