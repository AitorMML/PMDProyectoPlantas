package com.iteso.pmdproyectoplantas.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.storage.StorageReference;

public class Planta implements Parcelable {
    private String plantaId;
    private String nombre;
    private String especie;
    private String cuidados;
    private String imagenUriString;

    public Planta() {
        imagenUriString = null;
    }

    protected Planta(Parcel in) {
        plantaId = in.readString();
        nombre = in.readString();
        especie = in.readString();
        cuidados = in.readString();
        imagenUriString = in.readString();
    }

    public static final Creator<Planta> CREATOR = new Creator<Planta>() {
        @Override
        public Planta createFromParcel(Parcel in) {
            return new Planta(in);
        }

        @Override
        public Planta[] newArray(int size) {
            return new Planta[size];
        }
    };

    public String getPlantaId() {
        return plantaId;
    }

    public void setPlantaId(String plantaId) {
        this.plantaId = plantaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getCuidados() {
        return cuidados;
    }

    public void setCuidados(String cuidados) {
        this.cuidados = cuidados;
    }

    public String getImagenUriString() {
        return imagenUriString;
    }

    public void setImagenUriString(String imagenUriString) {
        this.imagenUriString = imagenUriString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(plantaId);
        parcel.writeString(nombre);
        parcel.writeString(especie);
        parcel.writeString(cuidados);
    }

    @Override
    public String toString() {
        return "Planta{" +
                "plantaId='" + plantaId + '\'' +
                ", nombre='" + nombre + '\'' +
                ", especie='" + especie + '\'' +
                ", cuidados='" + cuidados + '\'' +
                ", imagen='" + imagenUriString +
                '}';
    }
}
