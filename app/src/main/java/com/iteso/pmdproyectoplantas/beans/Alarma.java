package com.iteso.pmdproyectoplantas.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Alarma implements Parcelable {
    private boolean isOnce;
    private List<Boolean> DaysOfWeek = new ArrayList<>(7);
    private boolean isRegar;
    private boolean isCambiarTierra;
    private boolean isDesparacitar;
    private boolean isPodar;
    private boolean isOtro;
    private String otroCampo;
    private String time;
    private String date;

    public Alarma() {
    }

    protected Alarma(Parcel in) {
        isOnce = in.readByte() != 0;
        DaysOfWeek = in.readArrayList(null);
        isRegar = in.readByte() != 0;
        isCambiarTierra = in.readByte() != 0;
        isDesparacitar = in.readByte() != 0;
        isPodar = in.readByte() != 0;
        isOtro = in.readByte() != 0;
        otroCampo = in.readString();
        time = in.readString();
        date = in.readString();
    }

    public static final Creator<Alarma> CREATOR = new Creator<Alarma>() {
        @Override
        public Alarma createFromParcel(Parcel in) {
            return new Alarma(in);
        }

        @Override
        public Alarma[] newArray(int size) {
            return new Alarma[size];
        }
    };

    public boolean isOnce() {
        return isOnce;
    }

    public void setOnce(boolean once) {
        isOnce = once;
    }

    public List<Boolean> getDaysOfWeek() {
        return DaysOfWeek;
    }

    public void setDaysOfWeek(List<Boolean> daysOfWeek) {
        DaysOfWeek = daysOfWeek;
    }

    public boolean isRegar() {
        return isRegar;
    }

    public void setRegar(boolean regar) {
        isRegar = regar;
    }

    public boolean isCambiarTierra() {
        return isCambiarTierra;
    }

    public void setCambiarTierra(boolean cambiarTierra) {
        isCambiarTierra = cambiarTierra;
    }

    public boolean isDesparacitar() {
        return isDesparacitar;
    }

    public void setDesparacitar(boolean desparacitar) {
        isDesparacitar = desparacitar;
    }

    public boolean isPodar() {
        return isPodar;
    }

    public void setPodar(boolean podar) {
        isPodar = podar;
    }

    public boolean getOtro() {
        return isOtro;
    }

    public void setOtro(boolean otroCampo) {
        isOtro = otroCampo;
    }

    public String getOtroCampo() {
        return otroCampo;
    }

    public void setOtroCampo(String otro) {
        this.otroCampo = otro;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (isOnce ? 1 : 0));
        parcel.writeList(DaysOfWeek);
        parcel.writeByte((byte) (isRegar ? 1 : 0));
        parcel.writeByte((byte) (isCambiarTierra ? 1 : 0));
        parcel.writeByte((byte) (isDesparacitar ? 1 : 0));
        parcel.writeByte((byte) (isPodar ? 1 : 0));
        parcel.writeByte((byte) (isOtro ? 1 : 0));
        parcel.writeString(otroCampo);
        parcel.writeString(time);
        parcel.writeString(date);
    }

    @Override
    public String toString() {
        return "Alarma{" +
                "isOnce=" + isOnce +
                ", DaysOfWeek=" + DaysOfWeek.toString() +
                ", isRegar=" + isRegar +
                ", isCambiarTierra=" + isCambiarTierra +
                ", isDesparacitar=" + isDesparacitar +
                ", isPodar=" + isPodar +
                ", isOtro=" + isOtro +
                ", otroCampo='" + otroCampo + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
