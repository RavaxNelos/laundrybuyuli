import java.util.ArrayList;

public class Courier {
    ArrayList<Order> orders;

    public Courier(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public void kirimOrder(int index) {
        Order o = orders.get(index);

        // ❗ cek apakah sudah selesai
        if (o.status != StatusLaundry.SELESAI) {
            System.out.println("Laundry belum selesai! Tidak bisa dikirim.");
            return;
        }

        // ❗ cek apakah sudah bayar
        if (!o.sudahBayar) {
            System.out.println("Laundry belum dibayar! Harap lakukan pembayaran terlebih dahulu.");
            return;
        }

        if (!o.antarJemput) {
            System.out.println("Order ini tidak menggunakan layanan antar jemput.");
            return;
        }

        // ✅ baru boleh dikirim
        o.status = StatusLaundry.DIKIRIM;
        System.out.println("Laundry berhasil dikirim!");
    }
}