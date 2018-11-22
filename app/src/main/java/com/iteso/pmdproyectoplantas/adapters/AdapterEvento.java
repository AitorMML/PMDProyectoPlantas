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
import com.iteso.pmdproyectoplantas.beans.Evento;

import java.util.ArrayList;
import java.util.List;

public class AdapterEvento extends RecyclerView.Adapter<AdapterEvento.ViewHolder> {
    private List<Evento> mDataSet;
    private Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterEvento(Context context, ArrayList<Evento> myDataSet) {
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
        holder.mEventImage.setImageResource(R.drawable.tulipanes);
        holder.mEventTitle.setText("Tulipanes florecieron");
        holder.mPlantGroups.setText("Flores de sombra");
        holder.mEventNotes.setText("Ver tus anotaciones");
        holder.mEventDate.setText("10 de julio del 1996");
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button mDetail;
        public Button mShare;
        public TextView mEventTitle;
        public TextView mPlantGroups;
        public TextView mEventDate;
        public TextView mEventNotes;
        public ImageView mEventImage;
        public RelativeLayout mEventLayout;
        public LinearLayout mHomeInfo;

        public ViewHolder(View v) {
            super(v);
            mEventLayout = (RelativeLayout)v.findViewById(R.id.item_event_layout);
            mEventImage = (ImageView)v.findViewById(R.id.item_event_image);
            mEventTitle = (TextView)v.findViewById(R.id.item_event_title);
            mHomeInfo = v.findViewById(R.id.card_home_info);
            mPlantGroups = (TextView)v.findViewById(R.id.item_plant_groups);
            mEventDate = (TextView)v.findViewById(R.id.item_event_date);
            mEventNotes = (TextView)v.findViewById(R.id.item_event_notes);
            mShare = (Button)v.findViewById(R.id.item_event_share);
            mDetail = (Button)v.findViewById(R.id.item_event_details);
        }
    }
}
