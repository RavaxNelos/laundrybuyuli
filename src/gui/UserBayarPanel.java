package gui;

import database.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import main.*;
import model.*;

public class UserBayarPanel extends JPanel {

    private AppData data;

    public UserBayarPanel(AppData data) {
        this.data = data;

        // Ambil data
        Customer currentUser = data.currentUser;
        ArrayList<Order> orders = OrderDatabase.getAllOrders();

        // Filter hanya order milik user
        ArrayList<Order> userOrders = new ArrayList<>();
        for (Order o : orders) {
            if (o.customer.id.equals(currentUser.id)) {
                userOrders.add(o);
            }
        }

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // ================= TITLE =================
        JLabel title = new JLabel("Menu Pembayaran Laundry");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);

        // ================= TABLE =================
        String[] kolom = { "ID Order", "Layanan", "Berat", "Total", "Status" };
        DefaultTableModel model = new DefaultTableModel(kolom, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        if (userOrders.isEmpty()) {
            model.addRow(new Object[] { "-", "Tidak ada pesanan", "-", "-", "-" });
        } else {
            for (Order o : userOrders) {
                Object[] row = {
                        o.idOrder,
                        o.service.namaLayanan,
                        o.berat + " Kg",
                        "Rp" + o.harga,
                        o.sudahBayar ? "Lunas" : "Belum Bayar"
                };
                model.addRow(row);
            }
        }

        JTable table = new JTable(model);
        table.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(table);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(scroll, gbc);

        // ================= LABEL PILIH =================
        JLabel pilihLabel = new JLabel("Pilih ID Order:");
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(pilihLabel, gbc);

        // ================= COMBOBOX =================
        JComboBox<String> orderComboBox = new JComboBox<>();

        for (Order o : userOrders) {
            if (!o.sudahBayar) { // hanya yang belum dibayar
                orderComboBox.addItem(o.idOrder + " - " + o.service.namaLayanan);
            }
        }

        gbc.gridx = 1;
        add(orderComboBox, gbc);

        // ================= BUTTON =================
        JPanel btnPanel = new JPanel(new FlowLayout());

        JButton bayarBtn = new JButton("Bayar");
        JButton backBtn = new JButton("Kembali");

        btnPanel.add(bayarBtn);
        btnPanel.add(backBtn);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        add(btnPanel, gbc);

        // ================= ACTION BAYAR =================
        bayarBtn.addActionListener(e -> {
            String selected = (String) orderComboBox.getSelectedItem();

            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Pilih order terlebih dahulu!");
                return;
            }

            String idOrder = selected.split(" - ")[0];

            Order selectedOrder = null;
            for (Order o : userOrders) {
                if (o.idOrder.equalsIgnoreCase(idOrder)) {
                    selectedOrder = o;
                    break;
                }
            }

            if (selectedOrder == null) {
                JOptionPane.showMessageDialog(this, "Order tidak ditemukan!");
                return;
            }

            if (selectedOrder.sudahBayar) {
                JOptionPane.showMessageDialog(this, "Order sudah dibayar!");
                return;
            }

            // proses bayar
            selectedOrder.sudahBayar = true;

            boolean updated = OrderDatabase.updateOrder(selectedOrder);

            if (updated) {
                JOptionPane.showMessageDialog(this,
                        "Pembayaran berhasil!\nTotal: Rp" + selectedOrder.harga);
                MainMenuPembeli.showDashboardPanel();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal update pembayaran!");
            }
        });

        // ================= ACTION BACK =================
        backBtn.addActionListener(e -> {
            MainMenuPembeli.showDashboardPanel();
        });
    }
}