import java.util.ArrayList; // import class ArrayList untuk menyimpan banyak data
import java.util.Scanner; // import Scanner untuk input dari user

public class MainLaundry { // class utama program
    public static void main(String[] args) { // method utama (entry point program)

        Scanner sc = new Scanner(System.in); // membuat object Scanner untuk input
        
        // Load data dari database MySQL
        ArrayList<Customer> customers = customerDatabase.getAllCustomers(); // load customer dari DB
        ArrayList<LaundryService> services = serviceDatabase.getAllServices(); // load service dari DB
        ArrayList<Order> orders = OrderDatabase.getAllOrders(); // load orders dari DB

        // membuat object Admin (inherit dari User)
        Admin admin = new Admin("ADM1", "Admin Utama", "08123456789", customers, orders);

        // membuat object Courier (inherit dari User)
        Courier kurir = new Courier("CR1", "Kurir 1", "08129876543", orders);

        int menu = -1; // variabel untuk menyimpan pilihan menu

        do { // perulangan menu
            System.out.println("\n=== ADMIN LAUNDRY BU YULI ===");
            System.out.println("1. Lihat Order");
            System.out.println("2. Lihat Customer");
            System.out.println("3. Update Status");
            System.out.println("4. Kirim Laundry");
            System.out.println("0. Keluar");

            System.out.print("Pilih: ");

            // validasi input agar hanya angka
            if (!sc.hasNextInt()) {
                System.out.println("Input harus berupa angka!");
                sc.next(); // buang input yang salah
                continue; // ulangi menu
            }

            menu = sc.nextInt(); // ambil input menu
            sc.nextLine(); // buang newline

            switch (menu) { // percabangan menu

                case 1:
                    // Reload orders dari database untuk memastikan data terbaru
                    orders = OrderDatabase.getAllOrders();
                    
                    // cek list order
                    if (orders.isEmpty()) { // cek apakah order kosong
                        System.out.println("Belum ada order di sistem!");
                        break;
                    }
                    
                    System.out.println("\n=== DAFTAR ORDER ===");
                    for (Order o : orders) {
                        System.out.println(o);
                    }
                    break;

                case 2:
                    // Reload customer dari database untuk memastikan data terbaru
                    customers = customerDatabase.getAllCustomers();

                    // cek list customer
                    if (customers.isEmpty()) {
                        System.out.println("Belum ada customer di sistem!");
                        break;
                    }

                    // tampilkan dari ArrayList
                    System.out.println("\n=== DAFTAR CUSTOMER ===");
                    for (Customer c : customers) {
                        System.out.println(c);
                    }
                    break;

                case 3:
                    // Reload orders dari database
                    orders = OrderDatabase.getAllOrders();
                    
                    // update status laundry
                    if (orders.isEmpty()) { // cek apakah order kosong
                        System.out.println("Belum ada order di sistem!");
                        break;
                    }

                    // tampilkan data order
                    System.out.println("\n=== DAFTAR ORDER ===");
                    for (Order o : orders) {
                        System.out.println(o);
                    }
                    
                    System.out.print("Masukkan ID Order: ");
                    String idUpdate = sc.nextLine().trim(); // input ID

                    Order orderUpdate = null; // variabel untuk menyimpan order

                    // mencari order berdasarkan ID
                    for (Order o : orders) {
                        if (o.idOrder.equalsIgnoreCase(idUpdate)) {
                            orderUpdate = o;
                            break;
                        }
                    }

                    if (orderUpdate != null) { // jika ditemukan
                        int idx = orders.indexOf(orderUpdate); // ambil index
                        admin.updateStatus(idx); // update status lewat admin

                        // Simpan ke database
                        OrderDatabase.updateOrder(orders.get(idx));
                        System.out.println("Status Berhasil Diperbarui!");
                    } else {
                        System.out.println("ID Order tidak ditemukan!");
                    }
                    break;

                case 4:
                    // Reload orders dari database
                    orders = OrderDatabase.getAllOrders();
                    
                    // kirim laundry
                    if (orders.isEmpty()) {
                        System.out.println("Belum ada order di sistem!");
                        break;
                    }

                    // tampilkan data
                    System.out.println("\n=== DAFTAR ORDER ===");
                    for (Order o : orders) {
                        System.out.println(o);
                    }
                    
                    System.out.print("Masukkan ID Order: ");
                    String idKirim = sc.nextLine().trim(); // input ID

                    Order orderKirim = null;

                    // cari order berdasarkan ID
                    for (Order o : orders) {
                        if (o.idOrder.equalsIgnoreCase(idKirim)) {
                            orderKirim = o;
                            break;
                        }
                    }

                    if (orderKirim != null) {
                        int idx = orders.indexOf(orderKirim); // ambil index

                        boolean berhasil = kurir.kirimOrder(idx); // proses kirim

                        if (berhasil) {
                            // Simpan ke database
                            OrderDatabase.updateOrder(orders.get(idx));
                            System.out.println("Konfirmasi Pengiriman Berhasil!");
                        }

                    } else {
                        System.out.println("ID Order tidak ditemukan!");
                    }
                    break;
            }

        } while (menu != 0); // ulang sampai pilih 0 (keluar)
        sc.close();
    }

    
}