package main;

import database.*;
import gui.*;
import javax.swing.*;
import model.*;

public class guiMenuAdmin {

    static JFrame frame = new JFrame("Laundry Bu Yuli - Admin");
    static appData data = new appData();

    public static void main(String[] args) {
        // Load customer dan service dari database
        data.customers = customerDatabase.getAllCustomers();
        data.services = serviceDatabase.getAllServices();
        // Load orders dari database menggunakan OrderDatabase
        data.orders = OrderDatabase.getAllOrders();
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showAdminLoginPanel();
        frame.setVisible(true);
    }

    public static void showAdminLoginPanel() {
        frame.setContentPane(new adminLoginPanel(data));
        frame.revalidate();
        frame.repaint();
    }

    public static void showAdminMainPanel() {
        frame.setContentPane(new adminMainPanel(data));
        frame.revalidate();
        frame.repaint();
    }
}
