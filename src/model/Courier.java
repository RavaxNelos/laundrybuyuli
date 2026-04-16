package model;

import java.util.ArrayList;

public class Courier extends User {// Courier mewarisi User

    ArrayList<Order> orders;
    public String password;

    // constructor Courier
    public Courier(String id, String nama, String noHp, String password,
            ArrayList<Order> orders) {
        super(id, nama, noHp);
        this.password = password;
        this.orders = orders;
    }

    // method untuk mengirim laundry
    public boolean kirimOrder(int index) {
        Order o = orders.get(index); // ambil order berdasarkan index

        if (!o.sudahBayar) { // cek apakah sudah bayar
            System.out.println("Belum bayar!");
            return false; // gagal kirim
        }

        if (!o.antarJemput) { // cek apakah pakai antar jemput
            System.out.println("Tidak pakai antar jemput!");
            return false;
        }

        if (o.status != StatusLaundry.SELESAI) { // cek apakah sudah selesai
            System.out.println("Belum selesai!");
            return false;
        }

        o.status = StatusLaundry.DIANTAR; // ubah status jadi DIANTAR
        System.out.println("Sedang diantar...");
        return true; // berhasil kirim
    }
}
