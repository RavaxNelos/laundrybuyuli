package main;

import database.*;
import gui.*;
import javax.swing.*;
import model.*;

public class MainMenuPembeli {

    static JFrame frame = new JFrame("Laundry Bu Yuli");
    static AppData data = new AppData();

    public static void main(String[] args) {
        // Load customer dan service dari database
        data.customers = CustomerDatabase.getAllCustomers();
        data.services = ServiceDatabase.getAllServices();
        // Load orders dari database menggunakan OrderDatabase
        data.orders = OrderDatabase.getAllOrders();
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showLoginPanel();
        frame.setVisible(true);
    }

    public static void showLoginPanel() {
        frame.setContentPane(new UserLoginPanel(data));
        frame.revalidate();
        frame.repaint();
    }

    public static void showRegisterPanel() {
        frame.setContentPane(new UserRegisterPanel(data));
        frame.revalidate();
        frame.repaint();
    }

    public static void showDashboardPanel() {
        UserMainPanel panel = new UserMainPanel(data);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

    public static void showMenuPesan() {
        UserPesanPanel panel = new UserPesanPanel(data);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

    public static void showMenuTampilkan() {
        UserTampilkanPanel panel = new UserTampilkanPanel(data);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }
}
