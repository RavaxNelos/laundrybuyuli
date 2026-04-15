package gui;

import database.CustomerDatabase;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import main.MainMenuAdmin;
import model.*;

public class AdminKelolaEditPelanggan extends JPanel {

    private AppData data;

    public AdminKelolaEditPelanggan(AppData data) {
        this.data = data;

        ArrayList<Customer> customers = CustomerDatabase.getAllCustomers();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // ===== TITLE =====
        JLabel title = new JLabel("Edit Data Customer", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);

        // ===== COMBOBOX CUSTOMER =====
        JLabel pilihLabel = new JLabel("Pilih ID Customer:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(pilihLabel, gbc);

        JComboBox<String> customerCombo = new JComboBox<>();
        for (Customer c : customers) {
            customerCombo.addItem(c.id + " - " + c.nama);
        }

        gbc.gridx = 1;
        add(customerCombo, gbc);

        // ===== FIELD EDIT =====
        JLabel namaLabel = new JLabel("Nama:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(namaLabel, gbc);

        JTextField namaField = new JTextField(15);
        gbc.gridx = 1;
        add(namaField, gbc);

        JLabel noLabel = new JLabel("Nomor HP:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(noLabel, gbc);

        JTextField noField = new JTextField(15);
        gbc.gridx = 1;
        add(noField, gbc);

        JLabel alamatLabel = new JLabel("Alamat:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(alamatLabel, gbc);

        JTextField alamatField = new JTextField(15);
        gbc.gridx = 1;
        add(alamatField, gbc);

        // ===== AUTO FILL SAAT DIPILIH =====
        customerCombo.addActionListener(e -> {
            String selected = (String) customerCombo.getSelectedItem();
            if (selected == null)
                return;

            String id = selected.split(" - ")[0];

            for (Customer c : customers) {
                if (c.id.equals(id)) {
                    namaField.setText(c.nama);
                    noField.setText(c.noHp);
                    alamatField.setText(c.alamat);
                    break;
                }
            }
        });

        // Trigger pertama kali (biar langsung muncul data)
        if (!customers.isEmpty()) {
            customerCombo.setSelectedIndex(0);
        }

        // ===== BUTTON =====
        JPanel btnPanel = new JPanel();

        JButton updateBtn = new JButton("Update");
        JButton backBtn = new JButton("Kembali");

        btnPanel.add(updateBtn);
        btnPanel.add(backBtn);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(btnPanel, gbc);

        // ===== ACTION UPDATE =====
        updateBtn.addActionListener(e -> {
            String selected = (String) customerCombo.getSelectedItem();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Pilih customer dulu!");
                return;
            }

            String id = selected.split(" - ")[0];

            String nama = namaField.getText().trim();
            String noHp = noField.getText().trim();
            String alamat = alamatField.getText().trim();

            if (nama.isEmpty() || noHp.isEmpty() || alamat.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                return;
            }

            Customer customerToUpdate = null;

            for (Customer c : customers) {
                if (c.id.equals(id)) {
                    customerToUpdate = c;
                    break;
                }
            }

            if (customerToUpdate == null) {
                JOptionPane.showMessageDialog(this, "Customer tidak ditemukan!");
                return;
            }

            // update data
            customerToUpdate.nama = nama;
            customerToUpdate.noHp = noHp;
            customerToUpdate.alamat = alamat;

            // update ke database
            boolean updated = CustomerDatabase.updateCustomer(customerToUpdate);

            if (updated) {
                // update memory juga
                for (Customer c : data.customers) {
                    if (c.id.equals(customerToUpdate.id)) {
                        c.nama = nama;
                        c.noHp = noHp;
                        c.alamat = alamat;
                        break;
                    }
                }

                JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
                MainMenuAdmin.showAdminKelolaPelangganPanel();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal update ke database!");
            }
        });

        // ===== BACK =====
        backBtn.addActionListener(e -> {
            MainMenuAdmin.showAdminKelolaPelangganPanel();
        });
    }
}