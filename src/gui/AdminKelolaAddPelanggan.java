package gui;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import main.MainMenuAdmin;
import model.*;

public class AdminKelolaAddPelanggan extends JPanel {

    private AppData data;

    public AdminKelolaAddPelanggan(AppData data) {
        this.data = data;
        ArrayList<Customer> customers = data.customers;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Tambahkan Akun Customer Laundry Bu Yuli", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        JLabel nameLabel = new JLabel("Nama:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(nameLabel, gbc);
        JTextField nameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(nameField, gbc);
        JLabel noLabel = new JLabel("Nomor HP:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(noLabel, gbc);
        JTextField noField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(noField, gbc);
        JLabel alamatLabel = new JLabel("Alamat:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(alamatLabel, gbc);
        JTextField alamatField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(alamatField, gbc);
        JLabel passLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(passLabel, gbc);
        JPasswordField passField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(passField, gbc);
        JLabel confirmPassLabel = new JLabel("Konfirmasi Password:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(confirmPassLabel, gbc);
        JPasswordField confirmPassField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(confirmPassField, gbc);

        JPanel btnPanel = new JPanel();
        JButton addButton = new JButton("Tambahkan");
        JButton backButton = new JButton("Kembali");
        btnPanel.add(addButton);
        btnPanel.add(backButton);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnPanel, gbc);
        addButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Fitur tambah customer belum tersedia");
        });
        backButton.addActionListener(e -> {
            MainMenuAdmin.showAdminKelolaPelangganPanel();
        });
    }
}
