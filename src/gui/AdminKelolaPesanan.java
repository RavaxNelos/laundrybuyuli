package gui;

import database.OrderDatabase;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import main.MainMenuAdmin;
import model.*;

public class AdminKelolaPesanan extends JPanel {

    private AppData data;

    public AdminKelolaPesanan(AppData data) {
        this.data = data;

        ArrayList<Order> orders = OrderDatabase.getAllOrders();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("Kelola Status Pesanan", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);
        // ===== TABLE ORDER =====
        String[] kolom = { "ID", "Customer", "Layanan", "Berat", "Harga", "Status" };

        DefaultTableModel tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // isi data
        for (Order o : orders) {
            Object[] row = {
                    o.idOrder,
                    o.customer.nama,
                    o.service.namaLayanan,
                    o.berat + " kg",
                    "Rp" + o.harga,
                    o.status
            };
            tableModel.addRow(row);
        }

        JTable table = new JTable(tableModel);
        table.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(700, 200));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        // ===== COMBOBOX ORDER =====
        JLabel pilihLabel = new JLabel("Pilih Order:");
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(pilihLabel, gbc);

        JComboBox<String> orderCombo = new JComboBox<>();

        for (Order o : orders) {
            orderCombo.addItem(o.idOrder + " - " + o.service.namaLayanan + " (" + o.status + ")");
        }

        gbc.gridx = 1;
        add(orderCombo, gbc);

        // ===== STATUS LABEL =====
        JLabel statusLabel = new JLabel("Status Sekarang:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(statusLabel, gbc);

        JLabel statusValue = new JLabel("-");
        gbc.gridx = 1;
        add(statusValue, gbc);

        // UPDATE LABEL SAAT DIPILIH
        orderCombo.addActionListener(e -> {
            String selected = (String) orderCombo.getSelectedItem();
            if (selected == null)
                return;

            String id = selected.split(" - ")[0];

            for (Order o : orders) {
                if (o.idOrder.equals(id)) {
                    statusValue.setText(o.status.toString());
                    break;
                }
            }
        });

        if (!orders.isEmpty()) {
            orderCombo.setSelectedIndex(0);
        }

        // ===== BUTTON =====
        JPanel btnPanel = new JPanel();

        JButton updateBtn = new JButton("Update Status");
        JButton backBtn = new JButton("Kembali");

        btnPanel.add(updateBtn);
        btnPanel.add(backBtn);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(btnPanel, gbc);

        // ===== ACTION UPDATE =====
        updateBtn.addActionListener(e -> {
            String selected = (String) orderCombo.getSelectedItem();

            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Pilih order dulu!");
                return;
            }

            String id = selected.split(" - ")[0];

            Order selectedOrder = null;

            for (Order o : orders) {
                if (o.idOrder.equals(id)) {
                    selectedOrder = o;
                    break;
                }
            }

            if (selectedOrder == null) {
                JOptionPane.showMessageDialog(this, "Order tidak ditemukan!");
                return;
            }

            // UPDATE STATUS
            selectedOrder.updateStatus();

            // SIMPAN KE DATABASE
            boolean updated = OrderDatabase.updateOrder(selectedOrder);

            if (updated) {
                JOptionPane.showMessageDialog(this, "Status berhasil diupdate!");
                tableModel.setRowCount(0);
                ArrayList<Order> newOrders = OrderDatabase.getAllOrders();

                for (Order o : newOrders) {
                    tableModel.addRow(new Object[] {
                            o.idOrder,
                            o.customer.nama,
                            o.service.namaLayanan,
                            o.berat + " kg",
                            "Rp" + o.harga,
                            o.status
                    });
                }
                // refresh isi jcombobox
                orderCombo.removeAllItems();

                for (Order o : newOrders) {
                    orderCombo.addItem(o.idOrder + " - " + o.service.namaLayanan + " (" + o.status + ")");
                }

                if (!newOrders.isEmpty()) {
                    orderCombo.setSelectedIndex(0);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Gagal update status!");
            }
        });

        // ===== BACK =====
        backBtn.addActionListener(e -> {
            MainMenuAdmin.showAdminMainPanel();
        });
    }
}