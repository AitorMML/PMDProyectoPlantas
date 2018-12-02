package com.iteso.pmdproyectoplantas;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

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
import com.iteso.pmdproyectoplantas.tools.Constants;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class FragmentPlantas extends Fragment {
    private AdapterPlanta mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Planta> plantas = new LinkedList<>();
    private List<Planta> backup;
    private DatabaseReference mDataReference;
    private ValueEventListener valueEventListener;

    public FragmentPlantas() {
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                plantas.clear();
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
        };
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDataReference = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getUid()).child("plants");
        mDataReference.addValueEventListener(valueEventListener);
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

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            addNewPlant(data.getParcelableExtra(Constants.extraBeanPlanta));
        } else if(resultCode == RESULT_CANCELED) {
            Toast.makeText(getContext(), getString(R.string.activity_add_plant_canelar)
                    , Toast.LENGTH_SHORT).show();
        }
    }

    public void startSearch() {
        backup = new LinkedList<>(plantas);
    }

    public void doSearch(String text) {
        mAdapter.getFilter().filter(text);
    }

    public void endSearch() {
        plantas.clear();
        plantas.addAll(backup);
        mAdapter.notifyDataSetChanged();
        mAdapter.notifyItemRangeChanged(0, plantas.size());

    }

    protected void addNewPlant(Planta planta) {
        String key = mDataReference.push().getKey();

        if(mDataReference != null && planta != null) {
            mDataReference.child(key).setValue(planta).addOnCompleteListener( var->{
                if(var.isSuccessful()) {
                    Toast.makeText(getContext(), getString(R.string.fragment_plantas_agregado_exitoso), Toast.LENGTH_SHORT).show();
                    planta.setPlantaId(key);
                    plantas.add(planta);
                    //mAdapter.notifyDataSetChanged();
                }
            });
        }

    }

    public void fetchFromRemote(List<Planta> lista, DatabaseReference dbref) {
    }
}
