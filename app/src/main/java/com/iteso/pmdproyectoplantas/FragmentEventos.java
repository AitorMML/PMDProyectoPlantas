package com.iteso.pmdproyectoplantas;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iteso.pmdproyectoplantas.adapters.AdapterEvento;
import com.iteso.pmdproyectoplantas.adapters.AdapterPlanta;
import com.iteso.pmdproyectoplantas.beans.Evento;

import java.util.ArrayList;

public class FragmentEventos extends Fragment {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public FragmentEventos() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventos, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.fragment_eventos);
        recyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        //TODO: Cambiar por implementación real de las plantas en la bd
        ArrayList<Evento> myDataSet = new ArrayList<>();
        for (int i=0; i<10; ++i)
            myDataSet.add(new Evento());

        mAdapter = new AdapterEvento(getActivity(),myDataSet);
        recyclerView.setAdapter(mAdapter);

        return view;
    }
}
