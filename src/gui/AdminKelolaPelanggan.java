package gui;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import model.*;

public class AdminKelolaPelanggan extends JPanel {

    private AppData data;

    public AdminKelolaPelanggan(AppData data) {
        this.data = data;
        ArrayList<Customer> customers = data.customers;
        ArrayList<Order> orders = data.orders;
        ArrayList<LaundryService> services = data.services;
        Admin currentAdmin = data.currentAdmin;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        JLabel titleLabel = new JLabel("Kelola Pelanggan");
        titleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);
    }

}
