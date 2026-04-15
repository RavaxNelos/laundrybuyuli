package gui;

import database.CustomerDatabase;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import main.MainMenuAdmin;
import model.*;

public class AdminKelolaDelPelanggan extends JPanel {

    private AppData data;

    public AdminKelolaDelPelanggan(AppData data) {
        this.data = data;

        ArrayList<Customer> customers = CustomerDatabase.getAllCustomers();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // ===== TITLE =====
        JLabel title = new JLabel("Hapus Data Customer", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);

        // ===== COMBOBOX =====
        JLabel pilihLabel = new JLabel("Pilih Customer:");
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

        // ===== BUTTON =====
        JPanel btnPanel = new JPanel();

        JButton deleteBtn = new JButton("Hapus");
        JButton backBtn = new JButton("Kembali");

        btnPanel.add(deleteBtn);
        btnPanel.add(backBtn);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(btnPanel, gbc);

        // ===== ACTION DELETE =====
        deleteBtn.addActionListener(e -> {
            String selected = (String) customerCombo.getSelectedItem();

            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Pilih customer dulu!");
                return;
            }

            String id = selected.split(" - ")[0];

            // KONFIRMASI
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Yakin ingin menghapus customer ini?",
                    "Konfirmasi",
                    JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION)
                return;

            // HAPUS DARI DATABASE
            boolean deleted = CustomerDatabase.deleteCustomer(id);

            if (deleted) {
                // HAPUS DARI MEMORY
                data.customers.removeIf(c -> c.id.equals(id));

                JOptionPane.showMessageDialog(this, "Customer berhasil dihapus!");

                // pindah halaman (akan refresh)
                MainMenuAdmin.showAdminKelolaPelangganPanel();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus customer!");
            }
        });

        // ===== BACK =====
        backBtn.addActionListener(e -> {
            MainMenuAdmin.showAdminKelolaPelangganPanel();
        });
    }
}