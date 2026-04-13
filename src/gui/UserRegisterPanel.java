package gui;

import database.*;
import java.awt.*;
import javax.swing.*;
import main.*;
import model.*;

public class UserRegisterPanel extends JPanel {

    private AppData data;

    public UserRegisterPanel(AppData data) {
        this.data = data;
        Customer currentUser = data.currentUser;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        JLabel titleLabel = new JLabel("Register Akun Laundry Bu Yuli");
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
        JLabel noLabel = new JLabel("Nomor HP:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(noLabel, gbc);
        JTextField noField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(noField, gbc);
        JLabel alamatLabel = new JLabel("Alamat:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(alamatLabel, gbc);
        JTextField alamatField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(alamatField, gbc);
        JLabel passLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(passLabel, gbc);
        JPasswordField passField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(passField, gbc);
        JLabel confirmPassLabel = new JLabel("Konfirmasi Password:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(confirmPassLabel, gbc);
        JPasswordField confirmPassField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(confirmPassField, gbc);

        JButton submitButton = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submitButton, gbc);
        submitButton.addActionListener(e -> {
            do {
                String nameReg = nameField.getText();
                String noReg = noField.getText();
                String alamat = alamatField.getText();
                String passReg = new String(passField.getPassword());
                String confirmPass = new String(confirmPassField.getPassword());

                if (!passReg.equals(confirmPass)) {
                    JOptionPane.showMessageDialog(this, "Password dan konfirmasi tidak cocok!");
                    return;
                }

                int nextNum = data.customers.isEmpty() ? 1 : data.customers.size() + 1;
                String regID = "CUST" + nextNum;

                Customer cust = new Customer(regID, nameReg, noReg, alamat, passReg);

                // Simpan customer baru ke database
                boolean saved = CustomerDatabase.saveCustomer(cust);
                if (!saved) {
                    JOptionPane.showMessageDialog(this, "Gagal menyimpan customer ke database!");
                    return;
                }

                // Tambah ke ArrayList juga agar langsung bisa dipakai
                data.customers.add(cust);
            } while (false);
            JOptionPane.showMessageDialog(null, "Registrasi berhasil!");
            MainMenuPembeli.showLoginPanel(); // kembali ke login panel setelah registrasi
        });
    }

}
