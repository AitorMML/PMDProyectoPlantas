package com.iteso.pmdproyectoplantas.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
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

public class AdapterGrupo extends RecyclerView.Adapter<AdapterGrupo.ViewHolder> implements Filterable {
    private List<Grupo> mDataSet;
    private Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterGrupo(Context context, ArrayList<Grupo> myDataSet) {
        mDataSet = myDataSet;
        this.context = context;
    }

    @Override
    public AdapterGrupo.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grupo_plantas, parent,false);
        AdapterGrupo.ViewHolder vh = new AdapterGrupo.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGrupo.ViewHolder holder, int position) {
        //TODO: llenar a Holder (la clase de abajo) con los datos de verdad
        holder.grupoIcono.setColorFilter(getColorForIcon((position)));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    private int getColorForIcon(int i) {
        int val = Color.GRAY;
        TypedArray colors = context.getResources().obtainTypedArray(context.getResources()
                .getIdentifier("mdcolor_500", "array", context.getPackageName()));
        val = colors.getColor((int) (Math.random() * colors.length()), val);
        colors.recycle();
        return val;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                //TODO: Implementar safe check para evitar que se vacie la lista
                mDataSet = (List<Grupo>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Grupo> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = mDataSet;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }

    protected List<Grupo> getFilteredResults(String constraint) {
        List<Grupo> results = new ArrayList<>(mDataSet);

        //TODO: Implementar la búsqueda adecuada deacuerdo al bean Grupo
        /*for (Grupo item : mDataSet) {
            if (item.getName().toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }*/

        for(int i=0; i<constraint.length(); ++i) {
            results.remove(0);
        }

        return results;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView grupoIcono;
        public TextView grupoInicial;
        public TextView grupoNombre;
        public TextView grupoIntegrantes;

        public ViewHolder(View itemView) {
            super(itemView);
            grupoIcono = itemView.findViewById(R.id.grupo_icono);
            grupoInicial = itemView.findViewById(R.id.grupo_inicial);
            grupoNombre = itemView.findViewById(R.id.grupo_nombre);
            grupoIntegrantes = itemView.findViewById(R.id.grupo_integrantes);
        }
    }
}
