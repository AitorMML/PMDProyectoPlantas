package com.iteso.pmdproyectoplantas.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.iteso.pmdproyectoplantas.ActivityLogin;
import com.iteso.pmdproyectoplantas.ActivityMain;
import com.iteso.pmdproyectoplantas.ActivityPlantDetail;
import com.iteso.pmdproyectoplantas.NavigationDrawerImp;
import com.iteso.pmdproyectoplantas.R;
import com.iteso.pmdproyectoplantas.beans.Grupo;
import com.iteso.pmdproyectoplantas.beans.Planta;
import com.iteso.pmdproyectoplantas.tools.Constants;
import com.iteso.pmdproyectoplantas.tools.ImageHelper;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AdapterPlanta extends RecyclerView.Adapter<AdapterPlanta.ViewHolder> implements Filterable {
    private List<Planta> mDataSet;
    private Context context;

    private DatabaseReference databaseReference;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterPlanta(Context context, List<Planta> myDataSet) {
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Planta current = mDataSet.get(position);
        holder.nombre.setText(current.getNombre());
        holder.descripcion.setText(current.getEspecie());
        holder.imagen.setScaleType(ImageView.ScaleType.CENTER_CROP);

        ImageHelper.loadImage(holder.imagen, current);

        holder.contenedor.setOnClickListener((View v)->{
            Intent intent = new Intent(context, ActivityPlantDetail.class);
            intent.putExtra(Constants.extraBeanPlanta, current);
            context.startActivity(intent);
        });
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
                if(results.count == 0) {
                    mDataSet.clear();
                    notifyDataSetChanged();
                } else {
                    mDataSet.clear();
                    mDataSet.addAll((List<Planta>) results.values);
                    notifyDataSetChanged();
                }
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<Planta> filteredResults = null;
                if (constraint == null || constraint.length() == 0) {
                    results.values = mDataSet;
                    results.count = mDataSet.size();
                } else {
                    List<Planta> nPlantas = new LinkedList<>();
                    for(Planta p : mDataSet) {
                        if(p.getNombre().toLowerCase().contains(constraint.toString().toLowerCase()) || p.getEspecie().contains(constraint)) {
                            nPlantas.add(p);
                        }
                    }
                    results.values = nPlantas;
                    results.count = nPlantas.size();
                }

                return results;
            }
        };
    }

    public List<Planta> getDataSet() { return mDataSet; }

    protected List<Planta> getSearchResults(String query) {
        List<Planta> results = new ArrayList<>(mDataSet);

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
        public CardView contenedor;

        public ViewHolder(View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.planta_imagen);
            nombre = itemView.findViewById(R.id.planta_nombre);
            descripcion = itemView.findViewById(R.id.planta_descripcion);
            contenedor = itemView.findViewById(R.id.planta_individual_holder);
        }
    }
}
