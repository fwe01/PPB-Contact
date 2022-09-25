package com.example.ppbcontact.model;

public class Contact {
    private String nama;
    private String nomor_telp;
    private int id;

    public Contact(int id, String nama, String nomor_telp) {
        this.nama = nama;
        this.nomor_telp = nomor_telp;
        this.id = id;
    }

    public static Contact create(String nama, String nomor_telp) {
        return new Contact(-1, nama, nomor_telp);
    }

    public String getNama() {
        return nama;
    }

    public int getId() {
        return id;
    }

    public String getNomorTelp() {
        return nomor_telp;
    }
}
