package com.iteso.pmdproyectoplantas.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedList;
import java.util.List;

public class Grupo implements Parcelable {
    private String groupId;
    private String nombre;
    private List<String> integrantes = new LinkedList<>();

    public Grupo() {
    }

    protected Grupo(Parcel in) {
        groupId = in.readString();
        nombre = in.readString();
        integrantes = in.createStringArrayList();
    }

    public static final Creator<Grupo> CREATOR = new Creator<Grupo>() {
        @Override
        public Grupo createFromParcel(Parcel in) {
            return new Grupo(in);
        }

        @Override
        public Grupo[] newArray(int size) {
            return new Grupo[size];
        }
    };

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<String> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(List<String> integrantes) {
        this.integrantes = integrantes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(groupId);
        parcel.writeString(nombre);
        parcel.writeStringList(integrantes);
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "groupId='" + groupId + '\'' +
                ", nombre='" + nombre + '\'' +
                ", integrantes=" + integrantes +
                '}';
    }
}
