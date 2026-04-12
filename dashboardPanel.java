
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
public class dashboardPanel extends JPanel {

    private appData data;
    public dashboardPanel(appData data) {
        this.data = data;
        Customer currentUser = data.currentUser;
        ArrayList<Order> orders = data.orders;
        ArrayList<LaundryService> services = data.services;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        JLabel titleLabel = new JLabel("Dashboard Laundry Bu Yuli");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);
        JLabel welcomeLabel = new JLabel("Selamat datang di dashboard! " + currentUser.nama);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(welcomeLabel, gbc);
        JButton pesanButton = new JButton("Pesan Laundry");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(pesanButton, gbc);
        JButton lihatPesanan = new JButton("Lihat Pesanan");
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(lihatPesanan, gbc);
        JButton confirmButton = new JButton("Konfirmasi Pembayaran");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(confirmButton, gbc);
        JButton logoutButton = new JButton("Logout");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(logoutButton, gbc);

        pesanButton.addActionListener(e -> {
            tesGUI.showMenuPesan();
        });
        lihatPesanan.addActionListener(e -> {
            tesGUI.showMenuTampilkan();
        });
        confirmButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Fitur Konfirmasi Pembayaran belum tersedia.");
        });
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin logout?", "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                tesGUI.showLoginPanel();
            }
        });

    }
}
