package com.iteso.pmdproyectoplantas.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.iteso.pmdproyectoplantas.NavigationDrawerImp;
import com.iteso.pmdproyectoplantas.R;
import com.iteso.pmdproyectoplantas.beans.Grupo;
import com.iteso.pmdproyectoplantas.beans.Planta;

import java.util.ArrayList;
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
        holder.nombre.setText(mDataSet.get(position).getNombre());
        holder.descripcion.setText(mDataSet.get(position).getEspecie());
        holder.imagen.setScaleType(ImageView.ScaleType.CENTER_CROP);

        //TODO: Cambiar esto por la organización verdadera del sistema de archivos
        if(mDataSet.get(position).getImagenUriString() == null) {
            StorageReference mStorageReference = FirebaseStorage.getInstance().getReference().child("users")
                    .child("id_user").child("id_planta").child("tulipanes.jpg");
            mStorageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    mDataSet.get(position).setImagenUriString(uri.toString());
                    SimpleDraweeView draweeView = (SimpleDraweeView) holder.imagen;
                    draweeView.setImageURI(uri);
                }
            });
        } else {
            SimpleDraweeView draweeView = (SimpleDraweeView) holder.imagen;
            draweeView.setImageURI(Uri.parse(mDataSet.get(position).getImagenUriString()));
        }
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

    public List<Planta> getDataSet() { return mDataSet; }

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
