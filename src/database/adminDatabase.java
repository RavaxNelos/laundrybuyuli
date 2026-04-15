package database;

import java.sql.*;
import java.util.ArrayList;
import model.*;

public class AdminDatabase {

    private static final String URL = "jdbc:mysql://localhost:3306/laundry_data";
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
    // ADMIN CRUD METHODS
    // =================================================================
    // Method untuk menyimpan admin ke database
    public static boolean saveAdmin(Admin admin) {
        String sql = "INSERT INTO admins (admin_id, nama_admin, admin_phone, password) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.id);
            stmt.setString(2, admin.nama);
            stmt.setString(3, admin.noHp);
            stmt.setString(4, admin.password);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0; // return true jika berhasil

        } catch (SQLException e) {
            System.err.println("Gagal menyimpan admin ke database!");
            e.printStackTrace();
            return false;
        }
    }

    // Method untuk mendapatkan admin berdasarkan ID
    public static Admin getAdminById(String adminId) {
        String sql = "SELECT * FROM admins WHERE admin_id = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, adminId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String id = rs.getString("admin_id");
                String nama = rs.getString("nama_admin");
                String noHp = rs.getString("admin_phone");
                String password = rs.getString("password");

                return new Admin(id, nama, noHp, password, null, null); // kembalikan objek Admin
            } else {
                return null; // jika tidak ditemukan
            }

        } catch (SQLException e) {
            System.err.println("Gagal mengambil admin dari database!");
            e.printStackTrace();
            return null;
        }
    }

    // Method untuk mendapatkan semua admin dari database
    public static ArrayList<Admin> getAllAdmins() {
        ArrayList<Admin> admins = new ArrayList<>();
        String sql = "SELECT * FROM admins";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id = rs.getString("admin_id");
                String nama = rs.getString("nama_admin");
                String noHp = rs.getString("admin_phone");
                String password = rs.getString("password");

                Admin admin = new Admin(id, nama, noHp, password, null, null);
                admins.add(admin);
            }

        } catch (SQLException e) {
            System.err.println("Gagal mengambil admins dari database!");
            e.printStackTrace();
        }
        return admins;
    }

    // Method untuk update admin
    public static boolean updateAdmin(Admin admin) {
        String sql = "UPDATE admins SET nama_admin=?, admin_phone=?, password=? WHERE admin_id=?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.nama);
            stmt.setString(2, admin.noHp);
            stmt.setString(3, admin.password);
            stmt.setString(4, admin.id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // return true jika berhasil
        } catch (SQLException e) {
            System.err.println("Gagal mengupdate admin di database!");
            e.printStackTrace();
            return false;
        }
    }

    // Method untuk delete admin
    public static boolean deleteAdmin(String adminId) {
        String sql = "DELETE FROM admins WHERE admin_id=?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, adminId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0; // return true jika berhasil

        } catch (SQLException e) {
            System.err.println("Gagal menghapus admin dari database!");
            e.printStackTrace();
            return false;
        }
    }

    //Method untuk validasi login admin by ID
    public static Admin validateLogin(String adminId, String password) {
        String sql = "SELECT * FROM admins WHERE admin_id = ? AND password = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, adminId);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String id = rs.getString("admin_id");
                String nama = rs.getString("nama_admin");
                String noHp = rs.getString("admin_phone");
                String pass = rs.getString("password");

                return new Admin(id, nama, noHp, pass, null, null); // kembalikan objek Admin jika login berhasil
            }
        } catch (SQLException e) {
            System.err.println("Error validasi login admin: " + e.getMessage());
        }
        return null;

    }

    // Method untuk validasi login admin by nama
    public static Admin validateLoginByNama(String namaAdmin, String password) {
        String sql = "SELECT * FROM admins WHERE nama_admin = ? AND password = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, namaAdmin);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String id = rs.getString("admin_id");
                String nama = rs.getString("nama_admin");
                String noHp = rs.getString("admin_phone");
                String pass = rs.getString("password");

                return new Admin(id, nama, noHp, pass, null, null); // kembalikan objek Admin jika login berhasil   
            }
        } catch (SQLException e) {
            System.err.println("Error validasi login admin: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
