
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

        JButton registerButton = new JButton("Register");
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(registerButton, gbc);

        JButton exitButton = new JButton("Keluar");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(exitButton, gbc);
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
                tesGUI.showDashboardPanel();
            } else {
                JOptionPane.showMessageDialog(this, "Login Gagal! Periksa nama dan password.");
            }
        });
        registerButton.addActionListener(e -> {
            tesGUI.showRegisterPanel();
        });
    }

}
