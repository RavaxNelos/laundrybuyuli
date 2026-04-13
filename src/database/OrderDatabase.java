package database;
import java.sql.*;
import java.util.ArrayList;
import model.*;
import service.*;
import utils.*;
public class OrderDatabase {

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3307/laundry_data";
    private static final String USER = "root"; // ganti dengan username MySQL Anda
    private static final String PASSWORD = ""; // ganti dengan password MySQL Anda

    private static Connection connection = null;

    // Method untuk mendapatkan koneksi database
    private static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Load MySQL JDBC Driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Buat koneksi
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Koneksi ke database berhasil!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver tidak ditemukan!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Gagal koneksi ke database!");
            e.printStackTrace();
        }
        return connection;
    }

    // Method untuk menutup koneksi
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Koneksi database ditutup.");
            }
        } catch (SQLException e) {
            System.err.println("Error menutup koneksi database!");
            e.printStackTrace();
        }
    }

    // =================================================================
    // ORDER CRUD METHODS
    // =================================================================
    // Method untuk menyimpan order ke database
    public static boolean saveOrder(Order order) {
        String sql = "INSERT INTO orders (id_order, customer_id, service_id, berat, harga, status, sudah_bayar, antar_jemput) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, order.idOrder);
            stmt.setString(2, order.customer.id);
            stmt.setString(3, order.service.idService);
            stmt.setDouble(4, order.berat);
            stmt.setDouble(5, order.harga);
            stmt.setString(6, order.status.toString());
            stmt.setBoolean(7, order.sudahBayar);
            stmt.setBoolean(8, order.antarJemput);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error menyimpan order: " + e.getMessage());
            return false;
        }
    }

    // Method untuk mengambil semua order dari database
    public static ArrayList<Order> getAllOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY created_at DESC";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String idOrder = rs.getString("id_order");
                String customerId = rs.getString("customer_id");
                String serviceId = rs.getString("service_id");
                double berat = rs.getDouble("berat");
                double harga = rs.getDouble("harga");
                String statusStr = rs.getString("status");
                boolean sudahBayar = rs.getBoolean("sudah_bayar");
                boolean antarJemput = rs.getBoolean("antar_jemput");

                // Load customer dari database
                Customer customer = CustomerDatabase.getCustomerById(customerId);
                if (customer == null) {
                    // Jika customer tidak ditemukan, buat dummy customer
                    customer = new Customer(customerId, "Unknown", "", "", "");
                }

                // Load service dari database
                LaundryService service = ServiceDatabase.getServiceById(serviceId);
                if (service == null) {
                    // Jika service tidak ditemukan, buat dummy service
                    service = new LaundryService(serviceId, "Unknown Service", 0, 0);
                }

                // Buat objek Order
                Order order = new Order(idOrder, customer, service, berat, antarJemput);
                order.harga = harga; // Override harga dari database
                order.status = StatusLaundry.valueOf(statusStr);
                order.sudahBayar = sudahBayar;

                orders.add(order);
            }

        } catch (SQLException e) {
            System.err.println("Error mengambil orders: " + e.getMessage());
        }

        return orders;
    }

    // Method untuk update order
    public static boolean updateOrder(Order order) {
        String sql = "UPDATE orders SET customer_id=?, service_id=?, berat=?, harga=?, status=?, sudah_bayar=?, antar_jemput=? WHERE id_order=?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, order.customer.id);
            stmt.setString(2, order.service.idService);
            stmt.setDouble(3, order.berat);
            stmt.setDouble(4, order.harga);
            stmt.setString(5, order.status.toString());
            stmt.setBoolean(6, order.sudahBayar);
            stmt.setBoolean(7, order.antarJemput);
            stmt.setString(8, order.idOrder);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error update order: " + e.getMessage());
            return false;
        }
    }

    // Method untuk delete order
    public static boolean deleteOrder(String orderId) {
        String sql = "DELETE FROM orders WHERE id_order=?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, orderId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error delete order: " + e.getMessage());
            return false;
        }
    }

    // Method untuk mencari order berdasarkan customer ID
    public static ArrayList<Order> getOrdersByCustomerId(String customerId) {
        ArrayList<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE customer_id=? ORDER BY created_at DESC";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String idOrder = rs.getString("id_order");
                String serviceId = rs.getString("service_id");
                double berat = rs.getDouble("berat");
                double harga = rs.getDouble("harga");
                String statusStr = rs.getString("status");
                boolean sudahBayar = rs.getBoolean("sudah_bayar");
                boolean antarJemput = rs.getBoolean("antar_jemput");

                // Load customer dari database
                Customer customer = CustomerDatabase.getCustomerById(customerId);
                // Load service dari database
                LaundryService service = ServiceDatabase.getServiceById(serviceId);

                if (customer != null && service != null) {
                    Order order = new Order(idOrder, customer, service, berat, antarJemput);
                    order.harga = harga;
                    order.status = StatusLaundry.valueOf(statusStr);
                    order.sudahBayar = sudahBayar;
                    orders.add(order);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error mengambil orders by customer: " + e.getMessage());
        }

        return orders;
    }

    // Method untuk test koneksi database
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Test koneksi gagal: " + e.getMessage());
            return false;
        }
    }
}
