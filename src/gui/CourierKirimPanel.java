package gui;

import database.OrderDatabase;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import main.MainMenuCourier;
import model.*;

public class CourierKirimPanel extends JPanel {

    private AppData data;

    public CourierKirimPanel(AppData data) {
        this.data = data;

        ArrayList<Order> orders = OrderDatabase.getAllOrders();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // ===== TITLE =====
        JLabel title = new JLabel("Kirim Pesanan Laundry", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);

        // ===== TABLE =====
        String[] kolom = { "ID", "Customer", "Layanan", "Berat", "Harga", "Status", "Antar/Jemput", "Pembayaran" };

        DefaultTableModel tableModel = new DefaultTableModel(kolom, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(750, 200));

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        add(scroll, gbc);

        // ===== COMBOBOX =====
        JLabel pilihLabel = new JLabel("Pilih Order:");
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(pilihLabel, gbc);

        JComboBox<String> orderCombo = new JComboBox<>();
        orderCombo.setMaximumRowCount(8);

        gbc.gridx = 1;
        add(orderCombo, gbc);

        // ===== BUTTON =====
        JPanel btnPanel = new JPanel();

        JButton kirimBtn = new JButton("Kirim");
        JButton backBtn = new JButton("Kembali");

        btnPanel.add(kirimBtn);
        btnPanel.add(backBtn);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(btnPanel, gbc);

        // ===== METHOD REFRESH =====
        Runnable refreshData = () -> {
            ArrayList<Order> newOrders = OrderDatabase.getAllOrders();

            // refresh table
            tableModel.setRowCount(0);

            for (Order o : newOrders) {
                tableModel.addRow(new Object[] {
                        o.idOrder,
                        o.customer.nama,
                        o.service.namaLayanan,
                        o.berat + " kg",
                        "Rp" + o.harga,
                        o.status,
                        o.antarJemput ? "Diantar" : "Diambil",
                        o.sudahBayar ? "Sudah Bayar" : "Belum Bayar"
                });
            }

            // refresh combo
            orderCombo.removeAllItems();

            for (Order o : newOrders) {
                orderCombo.addItem(o.idOrder + " - " + o.customer.nama);
            }
        };

        // pertama kali load
        refreshData.run();

        // ===== ACTION KIRIM =====
        kirimBtn.addActionListener(e -> {
            String selected = (String) orderCombo.getSelectedItem();

            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Pilih order dulu!");
                return;
            }

            String id = selected.split(" - ")[0];

            Order selectedOrder = null;

            for (Order o : OrderDatabase.getAllOrders()) {
                if (o.idOrder.equals(id)) {
                    selectedOrder = o;
                    break;
                }
            }

            if (selectedOrder == null) {
                JOptionPane.showMessageDialog(this, "Order tidak ditemukan!");
                return;
            }

            // JIKA DIAMBIL SENDIRI
            if (!selectedOrder.antarJemput) {
                JOptionPane.showMessageDialog(this,
                        "Pesanan ini akan diambil sendiri oleh customer!");
                return;
            }

            // BELUM SELESAI
            if (selectedOrder.status != StatusLaundry.SELESAI) {
                JOptionPane.showMessageDialog(this,
                        "Pesanan belum selesai!");
                return;
            }

            // BELUM BAYAR
            if (!selectedOrder.sudahBayar) {
                JOptionPane.showMessageDialog(this,
                        "Pesanan belum dibayar!");
                return;
            }

            // UPDATE STATUS
            selectedOrder.status = StatusLaundry.DIANTAR;

            boolean updated = OrderDatabase.updateOrder(selectedOrder);

            if (updated) {
                JOptionPane.showMessageDialog(this, "Pesanan sedang diantar!");

                // refresh langsung
                refreshData.run();

            } else {
                JOptionPane.showMessageDialog(this, "Gagal update!");
            }
        });

        // ===== BACK =====
        backBtn.addActionListener(e -> {
            MainMenuCourier.showCourierMainPanel();
        });
    }
}