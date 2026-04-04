import java.util.ArrayList; // import untuk struktur data list (menyimpan banyak object)
import java.util.Scanner; // import untuk input dari user

public class MenuPembeli { // class untuk menu pembeli

    public static String centerText(String text, int width) { // method untuk membuat teks rata tengah
        int padding = (width - text.length()) / 2; // menghitung jumlah spasi di depan
        return " ".repeat(Math.max(0, padding)) + text; // menambahkan spasi agar teks ke tengah
    }

    public static void jalankan(Scanner sc,
            ArrayList<Customer> customers,
            ArrayList<Order> orders,
            ArrayList<LaundryService> services) { // method utama menu pembeli

        int pilih = -1; // variabel untuk pilihan menu

        do { // perulangan menu
            System.out.println("\n=== MENU PEMBELI ===");
            System.out.println("1. Pesan Laundry");
            System.out.println("2. Konfirmasi Pembayaran");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");

            if (!sc.hasNextInt()) { // validasi agar input harus angka
                System.out.println("Harus angka!");
                sc.next(); // buang input salah
                continue; // ulangi menu
            }

            pilih = sc.nextInt(); // ambil input menu
            sc.nextLine(); // buang newline

            switch (pilih) { // percabangan menu

                case 1:
                    // INPUT CUSTOMER
                    int nextNum = customers.isEmpty() ? 1 : customers.size() + 1; // auto increment ID
                    String id = "CUST" + nextNum; // membuat ID customer

                    System.out.println("ID Customer: " + id);

                    String nama;
                    do { // validasi nama
                        System.out.print("Nama (max 10 huruf): ");
                        nama = sc.nextLine();

                        if (!nama.matches("[a-zA-Z ]+")) { // hanya huruf
                            System.out.println("Nama hanya huruf!");
                        } else if (nama.length() > 10) { // max 10 karakter
                            System.out.println("Maksimal 10 karakter!");
                        }

                    } while (!nama.matches("[a-zA-Z ]+") || nama.length() > 10);

                    String hp;
                    do { // validasi nomor HP
                        System.out.print("No HP (max 13 digit): ");
                        hp = sc.nextLine();

                        if (!hp.matches("[0-9]+")) { // hanya angka
                            System.out.println("Hanya angka!");
                        } else if (hp.length() > 13) {
                            System.out.println("Maksimal 13 digit!");
                        }

                    } while (!hp.matches("[0-9]+") || hp.length() > 13);

                    String alamat;
                    do { // validasi alamat
                        System.out.print("Alamat (huruf saja): ");
                        alamat = sc.nextLine();

                        if (!alamat.matches("[a-zA-Z ]+")) {
                            System.out.println("Hanya huruf!");
                        }

                    } while (!alamat.matches("[a-zA-Z ]+"));

                    Customer cust = new Customer(id, nama, hp, alamat); // membuat object customer
                    customers.add(cust); // menambahkan ke list
                    simpanCustomer(cust); // simpan ke file txt

                    // PILIH LAYANAN
                    System.out.println("\n--- DAFTAR LAYANAN ---");
                    for (LaundryService ls : services) { // looping menampilkan layanan
                        System.out.println(ls.idService + " - " + ls.namaLayanan + " (Rp" + ls.hargaPerKg + "/kg)");
                    }

                    LaundryService layananDipilih = null;

                    do { // memilih layanan
                        System.out.print("Masukkan Kode Layanan (contoh: SRV1): ");
                        String kodeInput = sc.nextLine().trim();

                        for (LaundryService ls : services) {
                            if (ls.idService.equalsIgnoreCase(kodeInput)) { // cocokkan kode
                                layananDipilih = ls;
                                break;
                            }
                        }

                        if (layananDipilih == null) {
                            System.out.println("Kode layanan tidak ditemukan!");
                        }
                    } while (layananDipilih == null);

                    // BERAT
                    double berat = -1;
                    do {
                        System.out.print("Berat (kg): ");

                        if (!sc.hasNextDouble()) { // validasi angka
                            System.out.println("Harus angka!");
                            sc.next();
                            continue;
                        }

                        berat = sc.nextDouble();
                        sc.nextLine();

                        if (berat <= 0) {
                            System.out.println("Harus > 0!");
                        }

                    } while (berat <= 0);

                    // ANTAR JEMPUT
                    String input;
                    boolean antar;

                    do {
                        System.out.print("Antar Jemput (y/n): ");
                        input = sc.nextLine();

                        if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("ya")) {
                            antar = true;
                            break;
                        } else if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
                            antar = false;
                            break;
                        } else {
                            System.out.println("Input salah!");
                        }

                    } while (true);

                    if (antar) {
                        System.out.println("Akan dikirim kurir");
                    } else {
                        System.out.println("Ambil sendiri");
                    }

                    // BUAT ORDER
                    Order o = new Order("ORD" + (orders.size() + 1),
                            cust,
                            layananDipilih,
                            berat,
                            antar); // membuat object order

                    orders.add(o); // tambah ke list
                    simpanOrder(o); // simpan ke file

                    System.out.println("Order berhasil dibuat!");
                    break;

                case 2:
                    if (orders.isEmpty()) { // cek apakah ada order
                        System.out.println("Tidak ada data order ditemukan di file!");
                        break;
                    }

                    System.out.println("\n--- DAFTAR SEMUA ORDER ---");
                    for (Order ord : orders) { // tampilkan semua order
                        System.out.printf("ID: %-7s | Nama: %-10s | Total: Rp%-8s | [%s]\n",
                                ord.idOrder, ord.customer.nama, ord.harga,
                                (ord.sudahBayar ? "Sudah Bayar" : "Belum Bayar"));
                    }

                    Order orderKetemu = null;

                    do { // cari order berdasarkan ID
                        System.out.print("\nMasukkan ID Order yang ingin dibayar (atau ketik 'batal'): ");
                        String idInput = sc.nextLine().trim();

                        if (idInput.equalsIgnoreCase("batal"))
                            break;

                        for (Order ord : orders) {
                            if (ord.idOrder.equalsIgnoreCase(idInput)) {
                                orderKetemu = ord;
                                break;
                            }
                        }

                        if (orderKetemu == null) {
                            System.out.println("ID Order tidak ditemukan!");
                        } else if (orderKetemu.sudahBayar) {
                            System.out.println("Order ini sudah lunas.");
                            orderKetemu = null;
                        }
                    } while (orderKetemu == null);

                    if (orderKetemu != null) {
                        orderKetemu.sudahBayar = true; // ubah status bayar
                        updateFileOrder(orders); // update file
                        System.out.println("Pembayaran berhasil!");
                    }
                    break;
            }

        } while (pilih != 0); // ulang sampai keluar
    }

    // METHOD SIMPAN CUSTOMER
    static void simpanCustomer(Customer c) {
        try {
            java.io.FileWriter fw = new java.io.FileWriter("customers.txt", true); // append file
            fw.write(c.id + "|" + c.nama + "|" + c.noHp + "|" + c.alamat + "\n"); // simpan data
            fw.close();
        } catch (Exception e) {
            System.out.println("Gagal simpan customer!");
        }
    }

    // METHOD SIMPAN ORDER
    static void simpanOrder(Order o) {
        try {
            java.io.FileWriter fw = new java.io.FileWriter("orders.txt", true);

            String statusBayarTeks = o.sudahBayar ? "Sudah Bayar" : "Belum Bayar"; // konversi boolean
            String statusAntarTeks = o.antarJemput ? "Diantar" : "Diambil";

            fw.write(o.idOrder + "|" +
                    o.customer.nama + "|" +
                    o.service.namaLayanan + "|" +
                    o.berat + "|" +
                    o.harga + "|" +
                    o.status + "|" +
                    statusBayarTeks + "|" +
                    statusAntarTeks + "\n");

            fw.close();
        } catch (Exception e) {
            System.out.println("Gagal simpan order!");
        }
    }

    static void updateFileOrder(ArrayList<Order> orders) { // method untuk memperbarui isi file orders.txt
        try {
            // FileWriter tanpa 'true' agar menimpa file lama dengan data terbaru
            java.io.FileWriter fw = new java.io.FileWriter("orders.txt", false); // false = overwrite (hapus isi lama)

            for (Order o : orders) { // looping semua data order
                // konversi boolean menjadi teks
                String statusBayarTeks = o.sudahBayar ? "Sudah Bayar" : "Belum Bayar";
                String statusAntarTeks = o.antarJemput ? "Diantar" : "Diambil";

                // menulis data order ke file dengan format dipisahkan "|"
                fw.write(o.idOrder + "|" +
                        o.customer.nama + "|" +
                        o.service.namaLayanan + "|" +
                        o.berat + "|" +
                        o.harga + "|" +
                        o.status + "|" +
                        statusBayarTeks + "|" +
                        statusAntarTeks + "\n");
            }

            fw.close(); // menutup file setelah selesai menulis

        } catch (Exception e) { // menangkap error jika gagal
            System.out.println("Gagal memperbarui file order!");
        }
    }

    // method untuk membaca data order dari file orders.txt
    public static void loadDataDariFile(ArrayList<Order> orders, ArrayList<LaundryService> services,
            ArrayList<Customer> customers) {

        try {
            java.io.File file = new java.io.File("orders.txt"); // membuat objek file

            if (!file.exists()) // cek apakah file ada
                return; // jika tidak ada, keluar dari method

            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file)); // baca file
            String line;

            while ((line = br.readLine()) != null) { // membaca file per baris
                String[] data = line.split("\\|"); // memisahkan data berdasarkan "|"

                if (data.length < 8) // validasi jumlah data
                    continue; // skip jika data tidak lengkap

                // membuat object sementara (dummy) untuk customer
                Customer tempCust = new Customer("CUST_TEMP", data[1], "", "");

                // membuat object sementara untuk layanan
                LaundryService tempServ = new LaundryService("", data[2], 0, 0);

                // membuat object Order dari data file
                Order o = new Order(data[0], tempCust, tempServ, Double.parseDouble(data[3]), false);

                o.harga = Double.parseDouble(data[4]); // set harga dari file
                o.status = StatusLaundry.valueOf(data[5]); // konversi string ke enum status
                o.sudahBayar = data[6].equalsIgnoreCase("Sudah Bayar"); // cek status bayar
                o.antarJemput = data[7].equalsIgnoreCase("Diantar"); // cek status antar

                orders.add(o); // tambahkan ke list orders
            }

            br.close(); // tutup file setelah selesai membaca

        } catch (Exception e) { // menangkap error
            System.out.println("Gagal memuat data lama: " + e.getMessage());
        }
    }

    // method untuk membaca data customer dari file customers.txt
    public static void loadCustomerDariFile(ArrayList<Customer> customers) {

        try {
            java.io.File file = new java.io.File("customers.txt"); // membuat objek file

            if (!file.exists()) // cek apakah file ada
                return; // jika tidak ada, keluar

            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file)); // buka file
            String line;

            customers.clear(); // kosongkan list sebelum diisi ulang

            while ((line = br.readLine()) != null) { // baca file per baris
                String[] data = line.split("\\|"); // pisahkan data

                if (data.length >= 4) { // pastikan data lengkap
                    // membuat object Customer dari data file
                    customers.add(new Customer(data[0], data[1], data[2], data[3]));
                }
            }

            br.close(); // tutup file

        } catch (Exception e) { // menangani error
            System.out.println("Gagal memuat data customer: " + e.getMessage());
        }
    }
}