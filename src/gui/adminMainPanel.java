package gui;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import main.*;
import model.*;

public class adminMainPanel extends JPanel {

    private appData data;

    public adminMainPanel(appData data) {
        this.data = data;
        Admin currentAdmin = data.currentAdmin;
        ArrayList<Customer> customers = data.customers;
        ArrayList<Order> orders = data.orders;
        ArrayList<LaundryService> services = data.services;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        JLabel titleLabel = new JLabel("Admin Panel - Laundry Bu Yuli");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);
        JLabel welcomeLabel = new JLabel("Selamat datang di admin panel! " + currentAdmin.nama);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(welcomeLabel, gbc);
        JButton manageCustomersButton = new JButton("Kelola Pelanggan");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(manageCustomersButton, gbc);
        JButton manageOrdersButton = new JButton("Kelola Pesanan");
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(manageOrdersButton, gbc);
        JButton logoutButton = new JButton("Logout");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(logoutButton, gbc);

        manageCustomersButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Fitur Kelola Pelanggan belum tersedia.");
        });
        manageOrdersButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Fitur Kelola Pesanan belum tersedia.");
        });
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin logout?", "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                guiMenuAdmin.showAdminLoginPanel();
            }
        });
    }
}
