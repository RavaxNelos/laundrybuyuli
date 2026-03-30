import java.util.ArrayList;
import java.util.Scanner;

public class MainLaundry {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Order> orders = new ArrayList<>();
        ArrayList<LaundryService> services = new ArrayList<>();

        services.add(new LaundryService("SRV1", "Cuci Basah", 5000, 2));
        services.add(new LaundryService("SRV2", "Cuci Kering", 6000, 2));
        services.add(new LaundryService("SRV3", "Setrika", 4000, 1));
        services.add(new LaundryService("SRV4", "Cuci + Setrika", 7000, 2));
        services.add(new LaundryService("SRV5", "Karpet", 8000, 3));
        services.add(new LaundryService("SRV6", "Bed Cover", 10000, 3));

        Admin admin = new Admin(customers, orders);
        Courier kurir = new Courier(orders);

        int menu = -1; // 🔥 penting!

        do {
            System.out.println("\n=== LAUNDRY BU YULI ===");
            System.out.println("1. Menu Pembeli");
            System.out.println("2. Lihat Order");
            System.out.println("3. Update Status");
            System.out.println("4. Kirim Laundry");
            System.out.println("5. Konfirmasi Pembayaran");
            System.out.println("0. Exit");

            System.out.print("Pilih: ");

            // 🔒 validasi input
            if (!sc.hasNextInt()) {
                System.out.println("Input harus berupa angka!");
                sc.next(); // buang input salah
                continue;
            }

            menu = sc.nextInt();
            sc.nextLine();

            switch (menu) {

                case 1:
                    MenuPembeli.jalankan(sc, customers, orders, services);
                    break;

                case 2:
                    admin.lihatOrder();
                    break;

                case 3:
                    if (orders.isEmpty()) {
                        System.out.println("Belum ada order!");
                        break;
                    }

                    admin.lihatOrder();
                    System.out.print("Index: ");
                    if (!sc.hasNextInt()) {
                        System.out.println("Input harus angka!");
                        sc.next(); // buang input salah
                        break;
                    }
                    int i = sc.nextInt();

                    // validasi index
                    if (i < 0 || i >= orders.size()) {
                        System.out.println("Index tidak valid!");
                        break;
                    }

                    admin.updateStatus(i);
                    break;

                case 4:
                    if (orders.isEmpty()) {
                        System.out.println("Belum ada order!");
                        break;
                    }

                    admin.lihatOrder();
                    System.out.print("Index: ");
                    if (!sc.hasNextInt()) {
                        System.out.println("Input harus angka!");
                        sc.next(); // buang input salah
                        break;
                    }
                    int k = sc.nextInt();

                    if (k < 0 || k >= orders.size()) {
                        System.out.println("Index tidak valid!");
                        break;
                    }

                    kurir.kirimOrder(k);
                    break;

                case 5:
                    if (orders.isEmpty()) {
                        System.out.println("Belum ada order!");
                        break;
                    }

                    admin.lihatOrder();
                    System.out.print("Index: ");
                    if (!sc.hasNextInt()) {
                        System.out.println("Input harus angka!");
                        sc.next(); // buang input salah
                        break;
                    }
                    int b = sc.nextInt();

                    if (b < 0 || b >= orders.size()) {
                        System.out.println("Index tidak valid!");
                        break;
                    }

                    orders.get(b).sudahBayar = true;
                    System.out.println("Pembayaran dikonfirmasi!");
                    break;
            }

        } while (menu != 0);
    }
}