package com.iteso.pmdproyectoplantas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.iteso.pmdproyectoplantas.adapters.AdapterGrupo;
import com.iteso.pmdproyectoplantas.adapters.AdapterPlanta;
import com.iteso.pmdproyectoplantas.beans.Grupo;

import java.util.ArrayList;


public class FragmentPlantsGrupos extends Fragment implements Filterable {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public FragmentPlantsGrupos() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plants_grupos, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.fragment_plants_grupos);
        recyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        ArrayList<Grupo> myDataSet = new ArrayList<>();
        for (int i=0; i<5; ++i)
            myDataSet.add(new Grupo());

        mAdapter = new AdapterGrupo(getActivity(),myDataSet);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    public void filter() {

    }

    @Override
    public Filter getFilter() {
        return ((AdapterGrupo)mAdapter).getFilter();
    }
}
