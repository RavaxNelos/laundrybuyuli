package gui;

import java.awt.*;
import javax.swing.*;
import main.*;
import model.*;

public class CourierMainPanel extends JPanel {

    private AppData data;

    public CourierMainPanel(AppData data) {
        this.data = data;

        Courier currentCourier = data.currentCourier;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // ===== TITLE =====
        JLabel titleLabel = new JLabel("Courier Panel - Laundry Bu Yuli");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // ===== WELCOME =====
        JLabel welcomeLabel = new JLabel("Selamat datang courier, " + currentCourier.nama);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridy = 1;
        add(welcomeLabel, gbc);

        // ===== BUTTON KIRIM =====
        JButton kirimButton = new JButton("Kirim Pesanan");

        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(kirimButton, gbc);

        // ===== LOGOUT =====
        JButton logoutButton = new JButton("Logout");

        gbc.gridy = 3;
        add(logoutButton, gbc);

        // ===== ACTION =====
        kirimButton.addActionListener(e -> {
            MainMenuCourier.showCourierKirimPanel();
        });

        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Apakah Anda yakin ingin logout?",
                    "Konfirmasi Logout",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                MainMenuCourier.showCourierLoginPanel();
            }
        });
    }
}