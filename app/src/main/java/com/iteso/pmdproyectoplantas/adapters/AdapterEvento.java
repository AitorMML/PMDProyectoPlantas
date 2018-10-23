package com.iteso.pmdproyectoplantas.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iteso.pmdproyectoplantas.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterEvento extends RecyclerView.Adapter<AdapterEvento.ViewHolder> {
    //TODO: Cambiar object por el bean de Evento
    private List<Object> mDataSet;
    private Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterEvento(Context context, ArrayList<Object> myDataSet) {
        mDataSet = myDataSet;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdapterEvento.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evento_planta, parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //TODO: llenar a Holder (la clase de abajo) con los datos de verdad
        holder.mProductImage.setImageResource(R.drawable.tulipanes);
        holder.mProductTitle.setText("Tulipanes florecieron");
        holder.mProductStore.setText("Flores de sombra");
        holder.mProductPhone.setText("Ver tus anotaciones");
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button mDetail;
        public Button mShare;
        public TextView mProductTitle;
        public TextView mProductStore;
        public TextView mProductLocation;
        public TextView mProductPhone;
        public ImageView mProductImage;
        public RelativeLayout mEventLayout;
        public LinearLayout mHomeInfo;

        public ViewHolder(View v) {
            super(v);
            mEventLayout = (RelativeLayout)v.findViewById(R.id.item_product_layout);
            mProductImage = (ImageView)v.findViewById(R.id.item_product_image);
            mProductTitle = (TextView)v.findViewById(R.id.item_product_title);
            mHomeInfo = v.findViewById(R.id.card_home_info);
            mProductStore = (TextView)v.findViewById(R.id.item_product_store);
            mProductLocation = (TextView)v.findViewById(R.id.item_product_location);
            mProductPhone = (TextView)v.findViewById(R.id.item_product_phone);
            mShare = (Button)v.findViewById(R.id.item_product_share);
            mDetail = (Button)v.findViewById(R.id.item_product_detail);
        }
    }
}
