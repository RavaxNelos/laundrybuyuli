import java.util.ArrayList;

public class Admin {
    ArrayList<Customer> customers;
    ArrayList<Order> orders;

    public Admin(ArrayList<Customer> customers, ArrayList<Order> orders) {
        this.customers = customers;
        this.orders = orders;
    }

    public void lihatOrder() {

        // cek kalau kosong
        if (orders.isEmpty()) {
            System.out.println("Belum ada order!");
            return;
        }

        // 🔥 header
        System.out.println("=== DAFTAR ORDER ===");

        // tampilkan data
        for (int i = 0; i < orders.size(); i++) {
            System.out.println(i + ". " + orders.get(i));
        }
    }

    public void updateStatus(int index) {
        Order o = orders.get(index);

        // ❗ batasi sampai SELESAI (bukan DIKIRIM)
        if (o.status == StatusLaundry.SELESAI) {
            System.out.println("Laundry sudah selesai! Tidak bisa diupdate lagi oleh admin.");
            return;
        }

        if (o.status.ordinal() < StatusLaundry.SELESAI.ordinal()) {
            o.status = StatusLaundry.values()[o.status.ordinal() + 1];
            System.out.println("Status berhasil diupdate menjadi: " + o.status);
        }
    }
}