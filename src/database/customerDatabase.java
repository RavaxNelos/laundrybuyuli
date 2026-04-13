package database;
import java.sql.*;
import java.util.ArrayList;
import model.*;
public class customerDatabase {

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
    // CUSTOMER CRUD METHODS
    // =================================================================
    // Method untuk menyimpan customer ke database
    public static boolean saveCustomer(Customer customer) {
        String sql = "INSERT INTO customer (customer_id, nama_customer, phone, alamat, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.id);
            stmt.setString(2, customer.nama);
            stmt.setString(3, customer.noHp);
            stmt.setString(4, customer.alamat);
            stmt.setString(5, customer.password);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error menyimpan customer: " + e.getMessage());
            return false;
        }
    }

    // Method untuk mengambil semua customer dari database
    public static ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id = rs.getString("customer_id");
                String nama = rs.getString("nama_customer");
                String noHp = rs.getString("phone");
                String alamat = rs.getString("alamat");
                String password = rs.getString("password");

                Customer customer = new Customer(id, nama, noHp, alamat, password);
                customers.add(customer);
            }

        } catch (SQLException e) {
            System.err.println("Error mengambil customers: " + e.getMessage());
        }

        return customers;
    }

    // Method untuk mencari customer berdasarkan ID
    public static Customer getCustomerById(String customerId) {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String id = rs.getString("customer_id");
                String nama = rs.getString("nama_customer");
                String noHp = rs.getString("phone");
                String alamat = rs.getString("alamat");
                String password = rs.getString("password");

                return new Customer(id, nama, noHp, alamat, password);
            }

        } catch (SQLException e) {
            System.err.println("Error mencari customer: " + e.getMessage());
        }

        return null;
    }

    // Method untuk update customer
    public static boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customer SET nama_customer=?, phone=?, alamat=?, password=? WHERE customer_id=?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.nama);
            stmt.setString(2, customer.noHp);
            stmt.setString(3, customer.alamat);
            stmt.setString(4, customer.password);
            stmt.setString(5, customer.id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error update customer: " + e.getMessage());
            return false;
        }
    }

    // Method untuk delete customer
    public static boolean deleteCustomer(String customerId) {
        String sql = "DELETE FROM customer WHERE customer_id=?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error delete customer: " + e.getMessage());
            return false;
        }
    }

    // Method untuk validasi login berdasarkan customer ID
    public static Customer validateLogin(String customerId, String password) {
        String sql = "SELECT * FROM customer WHERE customer_id = ? AND password = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerId);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String id = rs.getString("customer_id");
                String nama = rs.getString("nama_customer");
                String noHp = rs.getString("phone");
                String alamat = rs.getString("alamat");
                String pass = rs.getString("password");

                return new Customer(id, nama, noHp, alamat, pass);
            }

        } catch (SQLException e) {
            System.err.println("Error validasi login: " + e.getMessage());
        }

        return null;
    }

    // Method untuk validasi login berdasarkan customer name
    public static Customer validateLoginByName(String customerName, String password) {
        String sql = "SELECT * FROM customer WHERE nama_customer = ? AND password = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerName);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String id = rs.getString("customer_id");
                String nama = rs.getString("nama_customer");
                String noHp = rs.getString("phone");
                String alamat = rs.getString("alamat");
                String pass = rs.getString("password");

                System.out.println("Login berhasil: " + nama);
                return new Customer(id, nama, noHp, alamat, pass);
            } else {
                System.out.println("Login gagal: nama atau password salah");
            }

        } catch (SQLException e) {
            System.err.println("Error validasi login by name: " + e.getMessage());
        }

        return null;
    }

    // Method untuk cek apakah customer ID sudah ada (untuk registrasi)
    public static boolean isCustomerIdExists(String customerId) {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerId);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // true jika ada, false jika tidak ada

        } catch (SQLException e) {
            System.err.println("Error cek customer ID: " + e.getMessage());
            return false;
        }
    }

    // Method untuk menampilkan semua customer (untuk admin)
    public static void displayAllCustomers() {
        ArrayList<Customer> customers = getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("Tidak ada data customer.");
        } else {
            System.out.println("\n=== DAFTAR CUSTOMER ===");
            for (Customer c : customers) {
                System.out.println(c);
            }
        }
    }
}
