package utils;

public class testConnection {

    public static void main(String[] args) {
        try {
            java.sql.Connection conn = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3307/laundry", "root", "");
            System.out.println("Koneksi berhasil!");
            conn.close();
        } catch (Exception e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
        }
    }
}
