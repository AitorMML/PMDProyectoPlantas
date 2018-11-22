package com.iteso.pmdproyectoplantas.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.storage.StorageReference;

import java.sql.Date;

public class Evento implements Parcelable {
    private String eventoId;
    private String titulo;
    private String grupoId;
    private String notas;
    private StorageReference imagen;
    private long epochFecha;

    public Evento() {
    }

    protected Evento(Parcel in) {
        eventoId = in.readString();
        titulo = in.readString();
        grupoId = in.readString();
        notas = in.readString();
        epochFecha = in.readLong();
    }

    public static final Creator<Evento> CREATOR = new Creator<Evento>() {
        @Override
        public Evento createFromParcel(Parcel in) {
            return new Evento(in);
        }

        @Override
        public Evento[] newArray(int size) {
            return new Evento[size];
        }
    };

    public String getEventoId() {
        return eventoId;
    }

    public void setEventoId(String eventoId) {
        this.eventoId = eventoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(String grupoId) {
        this.grupoId = grupoId;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public long getEpochFecha() {
        return epochFecha;
    }

    public void setEpochFecha(long epochFecha) {
        this.epochFecha = epochFecha;
    }

    public StorageReference getImagen() {
        return imagen;
    }

    public void setImagen(StorageReference imagen) {
        this.imagen = imagen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(eventoId);
        parcel.writeString(titulo);
        parcel.writeString(grupoId);
        parcel.writeString(notas);
        parcel.writeLong(epochFecha);
    }

    @Override
    public String toString() {
        return "Evento{" +
                "eventoId='" + eventoId + '\'' +
                ", titulo='" + titulo + '\'' +
                ", imagen=" + imagen.getPath() +
                ", grupoId='" + grupoId + '\'' +
                ", notas='" + notas + '\'' +
                ", fecha=" + (new Date(epochFecha)).toString() +
                '}';
    }
}
