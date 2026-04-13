package database;
import java.sql.*;
import java.util.ArrayList;
import model.*;
public class ServiceDatabase {

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
    // SERVICE CRUD METHODS
    // =================================================================
    // Method untuk menyimpan service ke database
    public static boolean saveService(LaundryService service) {
        String sql = "INSERT INTO service (service_id, nama_service, harga_per_kg, estimasi) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, service.idService);
            stmt.setString(2, service.namaLayanan);
            stmt.setDouble(3, service.hargaPerKg);
            stmt.setInt(4, service.estimasiHari);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error menyimpan service: " + e.getMessage());
            return false;
        }
    }

    // Method untuk mengambil semua service dari database
    public static ArrayList<LaundryService> getAllServices() {
        ArrayList<LaundryService> services = new ArrayList<>();
        String sql = "SELECT * FROM service";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String idService = rs.getString("service_id");
                String namaService = rs.getString("nama_service");
                double hargaPerKg = rs.getDouble("harga_per_kg");
                int estimasi = rs.getInt("estimasi");

                LaundryService service = new LaundryService(idService, namaService, hargaPerKg, estimasi);
                services.add(service);
            }

        } catch (SQLException e) {
            System.err.println("Error mengambil services: " + e.getMessage());
        }

        return services;
    }

    // Method untuk mencari service berdasarkan ID
    public static LaundryService getServiceById(String serviceId) {
        String sql = "SELECT * FROM service WHERE service_id = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, serviceId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String idService = rs.getString("service_id");
                String namaService = rs.getString("nama_service");
                double hargaPerKg = rs.getDouble("harga_per_kg");
                int estimasi = rs.getInt("estimasi");

                return new LaundryService(idService, namaService, hargaPerKg, estimasi);
            }

        } catch (SQLException e) {
            System.err.println("Error mencari service: " + e.getMessage());
        }

        return null;
    }

    // Method untuk update service
    public static boolean updateService(LaundryService service) {
        String sql = "UPDATE service SET nama_service=?, harga_per_kg=?, estimasi=? WHERE service_id=?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, service.namaLayanan);
            stmt.setDouble(2, service.hargaPerKg);
            stmt.setInt(3, service.estimasiHari);
            stmt.setString(4, service.idService);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error update service: " + e.getMessage());
            return false;
        }
    }

    // Method untuk delete service
    public static boolean deleteService(String serviceId) {
        String sql = "DELETE FROM service WHERE service_id=?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, serviceId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error delete service: " + e.getMessage());
            return false;
        }
    }

    // Method untuk cek apakah service ID sudah ada
    public static boolean isServiceIdExists(String serviceId) {
        String sql = "SELECT * FROM service WHERE service_id = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, serviceId);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // true jika ada, false jika tidak ada

        } catch (SQLException e) {
            System.err.println("Error cek service ID: " + e.getMessage());
            return false;
        }
    }

    // Method untuk menampilkan semua service
    public static void displayAllServices() {
        ArrayList<LaundryService> services = getAllServices();
        if (services.isEmpty()) {
            System.out.println("Tidak ada data service.");
        } else {
            System.out.println("\n=== DAFTAR SERVICE ===");
            for (LaundryService s : services) {
                System.out.println(s);
            }
        }
    }
}
