
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class menuTampilkan extends JPanel {

    private appData data;

    public menuTampilkan(appData data) {
        this.data = data;
        // Refresh orders dari database
        data.orders = OrderDatabase.getAllOrders();
        Customer currentUser = data.currentUser;
        ArrayList<Order> orders = data.orders;
        ArrayList<LaundryService> services = data.services;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Menu Tampilkan Pesanan Laundry");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, gbc);

        JLabel greetingLabel = new JLabel("Halo, " + currentUser.nama + "! Berikut adalah pesanan Anda:");
        greetingLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 1;
        add(greetingLabel, gbc);

        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Customer", "Layanan", "Berat", "Harga", "Status", "Pembayaran", "Antar"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Order o : orders) {
            if (o.customer != null && currentUser != null && currentUser.id.equals(o.customer.id)) {
                System.out.println(currentUser.id + " == " + o.customer.id);
                model.addRow(new Object[]{
                    o.idOrder,
                    o.customer.nama,
                    o.service.namaLayanan,
                    o.berat + " kg",
                    "Rp" + o.harga,
                    o.status,
                    o.sudahBayar ? "Lunas" : "Belum Lunas",
                    o.antarJemput ? "Ya" : "Tidak"
                });
            }
        }

        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.setDefaultEditor(Object.class, null);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 200));
        add(scrollPane, gbc);

        if (model.getRowCount() == 0) {
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            gbc.weighty = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(new JLabel("Tidak ada pesanan untuk user ini."), gbc);
        }
        JButton backButton = new JButton("Kembali ke Menu Utama");
        backButton.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll();
            topFrame.getContentPane().add(new dashboardPanel(data));
            topFrame.revalidate();
            topFrame.repaint();
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(backButton, gbc);

    }
}
