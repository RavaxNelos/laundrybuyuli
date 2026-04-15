package gui;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import main.*;
import model.*;

public class UserMainPanel extends JPanel {

    private AppData data;

    public UserMainPanel(AppData data) {
        this.data = data;
        Customer currentUser = data.currentUser;
        ArrayList<Order> orders = data.orders;
        ArrayList<LaundryService> services = data.services;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        setLayout(new BorderLayout());
        JPanel card = new JPanel(new GridBagLayout());
        card.setBorder(BorderFactory.createTitledBorder("Login Page"));

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel titleLabel = new JLabel("Dashboard Laundry Bu Yuli", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        card.add(titleLabel, gbc);
        // ===== TITLE =====
        JLabel welcomeLabel = new JLabel("Selamat datang di dashboard ! " + currentUser.nama, JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        card.add(welcomeLabel, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        JButton pesanButton = new JButton("Pesan Laundry");
        JButton lihatPesanan = new JButton("Lihat Pesanan");
        JButton confirmButton = new JButton("Bayar Pesanan");

        btnPanel.add(pesanButton);
        btnPanel.add(lihatPesanan);
        btnPanel.add(confirmButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        card.add(btnPanel, gbc);
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        JButton logoutButton = new JButton("Logout");

        logoutPanel.add(logoutButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        card.add(logoutPanel, gbc);

        add(card);

        pesanButton.addActionListener(e -> {
            MainMenuPembeli.showMenuPesan();
        });
        lihatPesanan.addActionListener(e -> {
            MainMenuPembeli.showMenuTampilkan();
        });
        confirmButton.addActionListener(e -> {
            MainMenuPembeli.showMenuBayar();
        });
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin logout?", "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                MainMenuPembeli.showLoginPanel();
            }
        });

    }
}
