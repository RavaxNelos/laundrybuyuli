import java.util.ArrayList;
import java.util.Scanner;

public class MenuPembeli {

    public static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text;
    }

    // method utama menu
    public static void jalankan(Scanner sc,
            ArrayList<Customer> customers,
            ArrayList<Order> orders,
            ArrayList<LaundryService> services) {

        int pilih = -1; // 🔥 kasih nilai awal

        do {
            System.out.println("\n=== MENU PEMBELI ===");
            System.out.println("1. Tambah Customer");
            System.out.println("2. Pesan Laundry");
            System.out.println("0. Kembali");

            System.out.print("Pilih: ");

            if (!sc.hasNextInt()) {
                System.out.println("Input harus berupa angka!");
                sc.next();
                continue; // aman karena pilih sudah ada nilai
            }

            pilih = sc.nextInt();
            sc.nextLine();

            switch (pilih) {

                case 1:
                    // ID otomatis
                    String id = "CUST" + (customers.size() + 1);
                    System.out.println("ID Customer: " + id);

                    // NAMA
                    String nama;
                    do {
                        System.out.print("Nama (max 10 huruf): ");
                        nama = sc.nextLine();

                        if (!nama.matches("[a-zA-Z ]+")) {
                            System.out.println("Nama hanya boleh huruf!");
                        } else if (nama.length() > 10) {
                            System.out.println("Nama maksimal 10 karakter!");
                        }

                    } while (!nama.matches("[a-zA-Z ]+") || nama.length() > 10);

                    // NO HP
                    String hp;
                    do {
                        System.out.print("No HP (max 13 digit): ");
                        hp = sc.nextLine();

                        if (!hp.matches("[0-9]+")) {
                            System.out.println("No HP hanya boleh angka!");
                        } else if (hp.length() > 13) {
                            System.out.println("No HP maksimal 13 digit!");
                        }

                    } while (!hp.matches("[0-9]+") || hp.length() > 13);

                    // ALAMAT
                    String alamat;
                    do {
                        System.out.print("Alamat (huruf saja): ");
                        alamat = sc.nextLine();

                        if (!alamat.matches("[a-zA-Z ]+")) {
                            System.out.println("Alamat hanya boleh huruf!");
                        }

                    } while (!alamat.matches("[a-zA-Z ]+"));

                    customers.add(new Customer(id, nama, hp, alamat));

                    System.out.println("Customer berhasil ditambahkan!");
                    break;

                case 2:
                    if (customers.isEmpty()) {
                        System.out.println("Belum ada customer!");
                        break;
                    }

                    // HEADER
                    System.out.printf("%-10s | %-10s | %-13s | %-15s\n",
                            centerText("ID", 10),
                            centerText("Nama", 10),
                            centerText("NoHP", 13),
                            centerText("Alamat", 15));

                    System.out.println("--------------------------------------------------------------");

                    // DATA
                    for (Customer c : customers) {
                        System.out.printf("%-10s | %-10s | %-13s | %-15s\n",
                                centerText(c.id, 10),
                                centerText(c.nama, 10),
                                centerText(c.noHp, 13),
                                centerText(c.alamat, 15));
                    }

                    System.out.print("Masukkan ID Customer: ");
                    String inputId = sc.nextLine();

                    Customer selected = null;

                    for (Customer c : customers) {
                        if (c.id.equalsIgnoreCase(inputId)) {
                            selected = c;
                            break;
                        }
                    }

                    // cek apakah ditemukan
                    if (selected == null) {
                        System.out.println("ID Customer tidak ditemukan!");
                        return; // atau break sesuai posisi
                    }

                    // tampil layanan
                    for (int i = 0; i < services.size(); i++) {
                        System.out.println(i + ". " + services.get(i));
                    }

                    System.out.print("Pilih layanan: ");
                    int s = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Berat: ");
                    double berat = sc.nextDouble();
                    sc.nextLine();

                    // input antar jemput (loop validasi)
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
                            System.out.println("Input tidak valid! (y/ya/n/no)");
                        }

                    } while (true);

                    // 🔥 TARUH DI SINI
                    if (antar) {
                        System.out.println("Akan dikirim oleh kurir");
                    } else {
                        System.out.println("Ambil sendiri di tempat");
                    }

                    // lalu baru buat order
                    Order o = new Order("ORD" + (orders.size() + 1),
                            selected,
                            services.get(s),
                            berat,
                            antar);

                    orders.add(o);

                    System.out.println("Order berhasil dibuat!");
                    break;
            }

        } while (pilih != 0);
    }
}