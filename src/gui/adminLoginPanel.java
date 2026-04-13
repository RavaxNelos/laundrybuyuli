package gui;

import database.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import main.*;
import model.*;

public class AdminLoginPanel extends JPanel {

    private AppData data;

    public AdminLoginPanel(AppData data) {
        this.data = data;
        ArrayList<Admin> admins = data.admins;
        Admin currentAdmin = data.currentAdmin;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Admin Login - Laundry Bu Yuli");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        JLabel nameLabel = new JLabel("Nama:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(nameLabel, gbc);

        JTextField nameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(nameField, gbc);

        JLabel passLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passLabel, gbc);

        JPasswordField passField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(passField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(loginButton, gbc);

        JButton exitButton = new JButton("Exit");
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(exitButton, gbc);

        loginButton.addActionListener(e -> {
            String nameLogin = nameField.getText();
            String passLogin = new String(passField.getPassword());
            Admin logAdmin = AdminDatabase.validateLoginByNama(nameLogin, passLogin);
            if (logAdmin != null) {
                data.currentAdmin = logAdmin;
                JOptionPane.showMessageDialog(this, "Berhasil Login sebagai Admin!");
                // Pindah ke panel admin utama
                MainMenuAdmin.showAdminMainPanel();
            } else {
                JOptionPane.showMessageDialog(this, "Nama atau password salah!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin keluar?", "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

    }
}
