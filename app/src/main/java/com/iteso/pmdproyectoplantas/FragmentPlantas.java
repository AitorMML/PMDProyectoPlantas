package com.iteso.pmdproyectoplantas;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.iteso.pmdproyectoplantas.adapters.AdapterPlanta;
import com.iteso.pmdproyectoplantas.beans.Planta;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class FragmentPlantas extends Fragment implements Filterable {
    private AdapterPlanta mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Planta> plantas = new LinkedList<>();
    private DatabaseReference mDataReference;

    public FragmentPlantas() {}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDataReference = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getUid()).child("plants");
        mDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot snapshot = dataSnapshot;
                Iterable<DataSnapshot> contactChildre = snapshot.getChildren();
                for(DataSnapshot data : contactChildre) {
                    Planta planta = data.getValue(Planta.class);
                    planta.setPlantaId(data.getKey());
                    plantas.add(planta);
                }
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       //FirebaseDatabase.getInstance().getReference("users").child("KCeb2n1Ib6aJHY2tyi1LuoGQkIi2").child("plants").child("12345").child("nombre").setValue("acasia")
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plants_plantas, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.fragment_plants_plantas);
        recyclerView.setHasFixedSize(false);

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AdapterPlanta(getActivity(), plantas);
        recyclerView.setAdapter(mAdapter);

        /*Planta planta = new Planta();
        planta.setNombre("Tulip el Tulipan");
        planta.setEspecie("Liliaceae");
        planta.setCuidados("Plantar en área de sombra moderada.\nRegar menos en primavera y regar más en verano.\nCuidar que el agua no se estanque.");
        planta.setPlantaId(databaseReference.child("plantas").push().getKey());
        databaseReference.child("plantas").child(planta.getPlantaId()).setValue(planta);*/

        return view;
    }

    @Override
    public Filter getFilter() {
        return ((Filterable)mAdapter).getFilter();
    }
}
