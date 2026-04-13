
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class loginPanel extends JPanel {

    private appData data;

    public loginPanel(appData data) {
        this.data = data;
        Customer currentUser = data.currentUser;
        ArrayList<Order> orders = data.orders;
        ArrayList<LaundryService> services = data.services;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Login Dashboard Laundry Bu Yuli");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        setLayout(new BorderLayout());

// PANEL UTAMA (CARD)
JPanel card = new JPanel(new GridBagLayout());
card.setBorder(BorderFactory.createTitledBorder("Login Page"));

gbc.insets = new Insets(10, 10, 10, 10);
gbc.fill = GridBagConstraints.HORIZONTAL;

// ===== TITLE =====
JLabel title = new JLabel("Login Dashboard Laundry Bu Yuli", JLabel.CENTER);
title.setFont(new Font("Arial", Font.BOLD, 18));

gbc.gridx = 0;
gbc.gridy = 0;
gbc.gridwidth = 2;
card.add(title, gbc);

// ===== NAMA =====
gbc.gridwidth = 1;
gbc.gridy = 1;
gbc.gridx = 0;
gbc.anchor = GridBagConstraints.EAST;
card.add(new JLabel("Nama:"), gbc);

gbc.gridx = 1;
gbc.anchor = GridBagConstraints.WEST;
JTextField nameField = new JTextField(15);
card.add(nameField, gbc);

// ===== PASSWORD =====
gbc.gridy = 2;
gbc.gridx = 0;
gbc.anchor = GridBagConstraints.EAST;
card.add(new JLabel("Password:"), gbc);

gbc.gridx = 1;
gbc.anchor = GridBagConstraints.WEST;
JPasswordField passField = new JPasswordField(15);
card.add(passField, gbc);

// ===== BUTTON =====
JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
JButton loginButton = new JButton("Login");
JButton registerButton = new JButton("Register");
JButton exitButton = new JButton("Exit");

btnPanel.add(loginButton);
btnPanel.add(registerButton);
btnPanel.add(exitButton);

gbc.gridy = 3;
gbc.gridx = 0;
gbc.gridwidth = 2;
gbc.anchor = GridBagConstraints.CENTER;
card.add(btnPanel, gbc);


// ===== MASUKIN KE FRAME (CENTER) =====
add(card);
        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin keluar?", "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        loginButton.addActionListener(e -> {
            String namaLogin = nameField.getText().trim();
            String passLogin = new String(passField.getPassword());

            // Validasi login menggunakan database (berdasarkan nama customer)
            Customer loginUser = customerDatabase.validateLoginByName(namaLogin, passLogin);
            if (loginUser != null) {
                JOptionPane.showMessageDialog(this, "Berhasil Login sebagai " + loginUser.nama);
                data.currentUser = loginUser;
                guiMenuPembeli.showDashboardPanel();
            } else {
                JOptionPane.showMessageDialog(this, "Login Gagal! Periksa nama dan password.");
            }
        });
        registerButton.addActionListener(e -> {
            guiMenuPembeli.showRegisterPanel();
        });

    }

}
