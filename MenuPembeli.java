import java.util.ArrayList;
import java.util.Scanner;

public class MenuPembeli {

    public static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text;
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Order> orders = new ArrayList<>();
        ArrayList<LaundryService> services = new ArrayList<>();

        // isi layanan
        services.add(new LaundryService("SRV1", "Cuci Basah", 5000, 2));
        services.add(new LaundryService("SRV2", "Cuci Kering", 6000, 2));
        services.add(new LaundryService("SRV3", "Setrika", 4000, 1));
        services.add(new LaundryService("SRV4", "Cuci + Setrika", 7000, 2));
        services.add(new LaundryService("SRV5", "Karpet", 8000, 3));
        services.add(new LaundryService("SRV6", "Bed Cover", 10000, 3));

        // jalankan menu pembeli
        runSistem.loadCustomerDariFile(customers);
        runSistem.loadDataDariFile(orders, services, customers);
        runSistem.jalankan(sc, customers, orders, services);
    }
}