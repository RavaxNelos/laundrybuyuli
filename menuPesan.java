
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class menuPesan extends JPanel {

    private appData data;

    public menuPesan(appData data) {
        this.data = data;
        // Load orders dari database menggunakan OrderDatabase
        data.orders = OrderDatabase.getAllOrders(data.customers, data.services);
        Customer currentUser = data.currentUser;
        ArrayList<Order> orders = data.orders;
        ArrayList<LaundryService> services = data.services;
        System.out.println("services count = " + services.size());
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Menu Pesan Laundry");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        String[] kolomOrder = {
            "ID", "Layanan", "Harga per Kg"
        };
        DefaultTableModel model = new DefaultTableModel(kolomOrder, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        if (services.isEmpty()) {
            model.addRow(new Object[]{"-", "Tidak ada layanan tersedia", "-"});
        } else {
            for (LaundryService o : services) {
                Object[] row = {
                    o.idService,
                    o.namaLayanan,
                    "Rp" + o.hargaPerKg
                };
                model.addRow(row);
            }
        }
        JTable serviceTable = new JTable(model);
        serviceTable.setRowHeight(25);
        serviceTable.setDefaultEditor(Object.class, null);
        serviceTable.getTableHeader().setReorderingAllowed(false);
        serviceTable.getTableHeader().setResizingAllowed(false);
        serviceTable.setPreferredScrollableViewportSize(new Dimension(800, 200));
        JScrollPane scrollPane = new JScrollPane(serviceTable);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(scrollPane, gbc);
        JLabel pilihLabel = new JLabel("Pilih ID Service:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(pilihLabel, gbc);
        JTextField idServiceField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(idServiceField, gbc);
        JLabel beratLabel = new JLabel("Berat (Kg):");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(beratLabel, gbc);
        JTextField beratField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(beratField, gbc);
        JLabel antarJemputLabel = new JLabel("Antar/Jemput (Y/N):");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(antarJemputLabel, gbc);
        JTextField antarJemputField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(antarJemputField, gbc);
        JButton pesanButton = new JButton("Pesan");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(pesanButton, gbc);
        pesanButton.addActionListener(e -> {
            String idService = idServiceField.getText().trim();
            String beratText = beratField.getText().trim();
            String antarJemputText = antarJemputField.getText().trim();

            if (idService.isEmpty() || beratText.isEmpty() || antarJemputText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                return;
            }

            LaundryService layananDipilih = null;
            for (LaundryService ls : services) {
                if (ls.idService.equalsIgnoreCase(idService)) {
                    layananDipilih = ls;
                    break;
                }
            }

            if (layananDipilih == null) {
                JOptionPane.showMessageDialog(this, "ID Service tidak ditemukan!");
                return;
            }

            double berat;
            try {
                berat = Double.parseDouble(beratText);
                if (berat <= 0) {
                    JOptionPane.showMessageDialog(this, "Berat harus lebih dari 0!");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Berat harus berupa angka!");
                return;
            }

            boolean antarJemput;
            if (antarJemputText.equalsIgnoreCase("Y")) {
                antarJemput = true;
            } else if (antarJemputText.equalsIgnoreCase("N")) {
                antarJemput = false;
            } else {
                JOptionPane.showMessageDialog(this, "Antar/Jemput harus Y atau N!");
                return;
            }

            // Proses pemesanan
            double hargaTotal = layananDipilih.hitungHarga(berat);

            String pesan = "Pesanan berhasil!\n"
                    + "Layanan: " + layananDipilih.namaLayanan + "\n"
                    + "Berat: " + berat + " kg\n"
                    + "Antar/Jemput: " + (antarJemput ? "Ya" : "Tidak") + "\n"
                    + "Total Harga: Rp" + hargaTotal;
            System.out.println("Pesanan dibuat oleh " + currentUser.nama + "ID :" + currentUser.id);
            int nextOrderNum = data.orders.isEmpty() ? 1 : data.orders.size() + 1;
            String orderID = "ORD" + nextOrderNum;
            Order newOrder = new Order(orderID, currentUser, layananDipilih, berat, antarJemput);
            data.orders.add(newOrder);

            // Simpan ke database menggunakan OrderDatabase
            boolean saved = OrderDatabase.saveOrder(newOrder);
            if (saved) {
                JOptionPane.showMessageDialog(this, pesan);
                tesGUI.showDashboardPanel();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan pesanan ke database!");
                data.orders.remove(newOrder); // Hapus dari memory jika gagal simpan
            }
        });
    }
}
