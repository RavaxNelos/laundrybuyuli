package model;

public class User { // class induk (parent) untuk Admin, Courier, Customer

    public String id; // menyimpan ID user (bisa admin, kurir, atau customer)
    public String nama; // menyimpan nama user
    public String noHp; // menyimpan nomor HP user

    // constructor untuk mengisi data user saat object dibuat
    public User(String id, String nama, String noHp) {
        this.id = id; // set ID
        this.nama = nama; // set nama
        this.noHp = noHp; // set nomor HP
    }

    // method untuk menampilkan informasi user
    public void tampilInfo() {
        System.out.println(id + " | " + nama + " | " + noHp);
    }
}
