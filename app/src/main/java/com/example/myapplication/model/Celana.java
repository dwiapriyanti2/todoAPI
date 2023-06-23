package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Celana implements Parcelable {
    private String id;
    private String nama;
    private String jenis;
    private int jumlah;

    public Celana(String nama, String jenis, String jumlah) {
        this.nama = nama;
        this.jenis = jenis;
        this.jumlah = Integer.parseInt(jumlah);
    }

    protected Celana(Parcel in) {
        id = in.readString();
        nama = in.readString();
        jenis = in.readString();
        jumlah = in.readInt();
    }

    public static final Creator<Baju> CREATOR = new Creator<Baju>() {
        @Override
        public Baju createFromParcel(Parcel in) {
            return new Baju(in);
        }

        @Override
        public Baju[] newArray(int size) {
            return new Baju[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nama);
        dest.writeString(jenis);
        dest.writeInt(jumlah);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getJenis() {
        return jenis;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}
