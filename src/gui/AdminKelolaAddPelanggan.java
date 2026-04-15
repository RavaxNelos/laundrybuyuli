package gui;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import main.MainMenuAdmin;
import model.*;
import database.CustomerDatabase;

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
            String nama = nameField.getText().trim();
            String noHp = noField.getText().trim();
            String alamat = alamatField.getText().trim();
            String password = new String(passField.getPassword());
            String confirmPassword = new String(confirmPassField.getPassword());

            // VALIDASI
            if (nama.isEmpty() || noHp.isEmpty() || alamat.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Password tidak cocok!");
                return;
            }

            // BUAT ID CUSTOMER OTOMATIS
            String idCustomer = CustomerDatabase.generateCustomerId();

            // BUAT OBJECT CUSTOMER
            Customer newCustomer = new Customer(idCustomer, nama, noHp, alamat, password);

            // SIMPAN KE DATABASE
            boolean saved = CustomerDatabase.saveCustomer(newCustomer);

            if (saved) {
                // simpan juga ke memory
                data.customers.add(newCustomer);

                JOptionPane.showMessageDialog(this, "Customer berhasil ditambahkan!");

                // reset field
                nameField.setText("");
                noField.setText("");
                alamatField.setText("");
                passField.setText("");
                confirmPassField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan ke database!");
            }
        });
        backButton.addActionListener(e -> {
            MainMenuAdmin.showAdminKelolaPelangganPanel();
        });
    }
}
