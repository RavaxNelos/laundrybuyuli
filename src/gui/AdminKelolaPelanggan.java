package gui;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import main.MainMenuAdmin;
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
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Nama", "Nomor HP", "Alamat"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Customer c : customers) {
            model.addRow(new Object[]{
                c.id,
                c.nama,
                c.noHp,
                c.alamat
            });
        }

        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.setDefaultEditor(Object.class, null);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 200));
        add(scrollPane, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton addButton = new JButton("Tambah");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Hapus");
        JButton backButton = new JButton("Back");

        btnPanel.add(addButton);
        btnPanel.add(editButton);
        btnPanel.add(deleteButton);
        btnPanel.add(backButton);

        gbc.gridy = 4;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;

        add(btnPanel, gbc);

        addButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Fitur Tambah Pelanggan belum tersedia.");
        });
        editButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Fitur Edit Pelanggan belum tersedia.");
        });
        deleteButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Fitur Hapus Pelanggan belum tersedia.");
        });
        backButton.addActionListener(e -> {
            MainMenuAdmin.showAdminMainPanel();
        });
    }

}
/*
            MainMenuAdmin.showAdminKelolaAddPelangganPanel(); 
            MainMenuAdmin.showAdminKelolaEditPelangganPanel();
            MainMenuAdmin.showAdminKelolaDelPelangganPanel();
            
            */