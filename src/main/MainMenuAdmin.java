package main;

import database.*;
import gui.*;
import javax.swing.*;
import model.*;

public class MainMenuAdmin {

    static JFrame frame = new JFrame("Laundry Bu Yuli - Admin");
    static AppData data = new AppData();

    public static void main(String[] args) {
        // Load customer dan service dari database
        data.customers = CustomerDatabase.getAllCustomers();
        data.services = ServiceDatabase.getAllServices();
        // Load orders dari database menggunakan OrderDatabase
        data.orders = OrderDatabase.getAllOrders();
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showAdminLoginPanel();
        frame.setVisible(true);
    }

    public static void showAdminLoginPanel() {
        frame.setContentPane(new AdminLoginPanel(data));
        frame.revalidate();
        frame.repaint();
    }

    public static void showAdminMainPanel() {
        frame.setContentPane(new AdminMainPanel(data));
        frame.revalidate();
        frame.repaint();
    }

    public static void showAdminKelolaPelangganPanel() {
        frame.setContentPane(new AdminKelolaPelanggan(data));
        frame.revalidate();
        frame.repaint();
    }
    public static void showAdminKelolaAddPelangganPanel() {
        frame.setContentPane(new AdminKelolaAddPelanggan(data));
        frame.revalidate();
        frame.repaint();
    }
    public static void showAdminKelolaEditPelangganPanel() {
        frame.setContentPane(new AdminKelolaEditPelanggan(data));
        frame.revalidate();
        frame.repaint();
    }
    public static void showAdminKelolaDelPelangganPanel() {
        frame.setContentPane(new AdminKelolaDelPelanggan(data));
        frame.revalidate();
        frame.repaint();
    }
}
