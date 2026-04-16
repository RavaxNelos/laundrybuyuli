package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Admin;
import model.Courier;

public class CourierDatabase {
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
    // validasi login kurir
    public static Courier loginCourier(String username, String password) {
        String sql = "SELECT * FROM courier WHERE BINARY nama_courier = ? AND BINARY password = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String id = rs.getString("courier_id");
                String nama = rs.getString("nama_courier");
                String noHp = rs.getString("courier_phone");
                String pass = rs.getString("password");
                return new Courier(id, nama, noHp, pass, null);
            }

        } catch (Exception e) {
            System.err.println("Error validasi login kurir: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
