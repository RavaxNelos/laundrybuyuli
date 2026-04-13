package utils;
import database.*;
import  model.*;
public class TestDatabaseConnection {
    public static void main(String[] args) {
        System.out.println("Testing OrderDatabase connection...");

        // Test koneksi
        if (OrderDatabase.testConnection()) {
            System.out.println("✓ Database connection successful!");

            // Test ambil semua data dari database
            appData data = new appData();
            data.customers = customerDatabase.getAllCustomers();
            data.services = serviceDatabase.getAllServices();

            System.out.println("Loading orders from database...");
            data.orders = OrderDatabase.getAllOrders();

            System.out.println("Found " + data.orders.size() + " orders in database");

            // Tampilkan orders
            for (Order order : data.orders) {
                System.out.println(order);
            }

            // Tutup koneksi
            OrderDatabase.closeConnection();
            System.out.println("✓ Database test completed!");
        } else {
            System.out.println("✗ Database connection failed!");
        }
    }
}
