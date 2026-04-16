package gui;

import database.CourierDatabase;
import java.awt.*;
import javax.swing.*;
import main.MainMenuCourier;
import model.*;

public class CourierLoginPanel extends JPanel {

    private AppData data;

    public CourierLoginPanel(AppData data) {
        this.data = data;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // ===== TITLE =====
        JLabel title = new JLabel("Login Courier");
        title.setFont(new Font("Arial", Font.BOLD, 18));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);

        // ===== USERNAME =====
        JLabel userLabel = new JLabel("Username:");
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(userLabel, gbc);

        JTextField userField = new JTextField(15);
        gbc.gridx = 1;
        add(userField, gbc);

        // ===== PASSWORD =====
        JLabel passLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passLabel, gbc);

        JPasswordField passField = new JPasswordField(15);
        gbc.gridx = 1;
        add(passField, gbc);

        // ===== BUTTON =====
        JPanel btnPanel = new JPanel();

        JButton loginBtn = new JButton("Login");
        JButton exitBtn = new JButton("Exit");

        btnPanel.add(loginBtn);
        btnPanel.add(exitBtn);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(btnPanel, gbc);

        // ===== ACTION LOGIN =====
        loginBtn.addActionListener(e -> {
            String nama_courier = userField.getText().trim();
            String password = new String(passField.getPassword());

            if (nama_courier.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username dan Password harus diisi!");
                return;
            }

            // 🔥 CEK KE DATABASE
            Courier courier = CourierDatabase.loginCourier(nama_courier, password);

            if (courier != null) {
                JOptionPane.showMessageDialog(this, "Login berhasil!");

                // simpan ke AppData
                data.currentCourier = courier;

                // pindah ke main panel
                MainMenuCourier.showCourierMainPanel();

            } else {
                JOptionPane.showMessageDialog(this, "Username atau Password salah!");
            }
        });

        // ===== ACTION EXIT =====
        exitBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Yakin ingin keluar?",
                    "Konfirmasi",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
}