package service;
import java.util.ArrayList;
import java.util.List;
import model.*;
public class customerManage {

    public static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text;
    }

    static void simpanCustomer(Customer c) {
        try {
            java.io.FileWriter fw = new java.io.FileWriter("customers.txt", true);
            fw.write(c.id + "|" + c.nama + "|" + c.noHp + "|" + c.alamat + "|" + c.password + "\n");
            fw.close();
        } catch (Exception e) {
            System.out.println("Gagal simpan customer!");
        }
    }

    static void simpanOrder(Order o) {
        try {
            java.io.FileWriter fw = new java.io.FileWriter("orders.txt", true);

            // Mengonversi boolean menjadi kata-kata yang diinginkan
            String statusBayarTeks = o.sudahBayar ? "Sudah Bayar" : "Belum Bayar";
            String statusAntarTeks = o.antarJemput ? "Diantar" : "Diambil";

            fw.write(o.idOrder + "|"
                    + o.customer.id + "|"
                    + o.customer.nama + "|"
                    + o.service.namaLayanan + "|"
                    + o.berat + "|"
                    + o.harga + "|"
                    + o.status + "|"
                    + statusBayarTeks + "|"
                    + statusAntarTeks + "\n");

            fw.close();
        } catch (Exception e) {
            System.out.println("Gagal simpan order!");
        }
    }

    static void updateFileOrder(ArrayList<Order> orders) {
        try {
            // FileWriter tanpa 'true' agar menimpa file lama dengan data terbaru
            java.io.FileWriter fw = new java.io.FileWriter("orders.txt", false);
            for (Order o : orders) {
                String statusBayarTeks = o.sudahBayar ? "Sudah Bayar" : "Belum Bayar";
                String statusAntarTeks = o.antarJemput ? "Diantar" : "Diambil";

                fw.write(o.idOrder + "|"
                        + o.customer.id + "|"
                        + o.customer.nama + "|"
                        + o.service.namaLayanan + "|"
                        + o.berat + "|"
                        + o.harga + "|"
                        + o.status + "|"
                        + statusBayarTeks + "|"
                        + statusAntarTeks + "\n");
            }
            fw.close();
        } catch (Exception e) {
            System.out.println("Gagal memperbarui file order!");
        }
    }

    private static Customer findCustomerById(ArrayList<Customer> customers, String id) {
        if (id == null) {
            return null;
        }
        for (Customer cust : customers) {
            if (id.equals(cust.id)) {
                return cust;
            }
        }
        return null;
    }

    public static void loadDataDariFile(ArrayList<Order> orders, ArrayList<LaundryService> services,
            ArrayList<Customer> customers) {
        try {
            orders.clear();
            java.io.File file = new java.io.File("orders.txt");
            if (!file.exists()) {
                return; // Jika file belum ada, abaikan
            }
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length < 8) {
                    continue;
                }

                boolean hasCustomerId = data.length >= 9;
                String orderId = data[0];
                String custId = hasCustomerId ? data[1] : "CUST_TEMP";
                String custName = hasCustomerId ? data[2] : data[1];
                String serviceName = hasCustomerId ? data[3] : data[2];
                int baseIndex = hasCustomerId ? 3 : 2;

                Customer tempCust = findCustomerById(customers, custId);
                if (tempCust == null) {
                    tempCust = new Customer(custId, custName, "", "", "");
                }
                LaundryService tempServ = new LaundryService("", serviceName, 0, 0);

                Order o = new Order(orderId, tempCust, tempServ, Double.parseDouble(data[baseIndex + 1]), false);
                o.harga = Double.parseDouble(data[baseIndex + 2]);
                o.status = StatusLaundry.valueOf(data[baseIndex + 3]);
                o.sudahBayar = data[baseIndex + 4].equalsIgnoreCase("Sudah Bayar");
                o.antarJemput = data[baseIndex + 5].equalsIgnoreCase("Diantar");

                orders.add(o);
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Gagal memuat data lama: " + e.getMessage());
        }
    }

    // update ke dalam file txt customers
    public static void loadCustomerDariFile(ArrayList<Customer> customers) {
        try {
            java.io.File file = new java.io.File("customers.txt");
            if (!file.exists()) {
                return;
            }

            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file));
            String line;
            customers.clear(); // Pastikan kosong sebelum diisi data file

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length >= 4) {
                    // Tambahkan customer lama ke dalam list memori
                    customers.add(new Customer(data[0], data[1], data[2], data[3], data[4]));
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Gagal memuat data customer: " + e.getMessage());
        }
    }

    // method login cutomers menu pembeli
    public static Customer verifLogin(List<Customer> customers, String nameTarget, String passTarget) {
        for (Customer cust : customers) {
            if (cust.nama.equals(nameTarget) && cust.password.equals(passTarget)) {
                return cust; // login berhasil
            }
        }
        return null; // login gagal
    }

    public static void saveAllData(AppData data) {
        // Simpan semua order ke file, gunakan updateFileOrder untuk menimpa file lama
        updateFileOrder(data.orders);
    }
}

