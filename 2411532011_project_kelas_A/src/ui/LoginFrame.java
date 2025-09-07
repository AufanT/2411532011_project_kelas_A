package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import model.User;

public class LoginFrame {
        public static void main(String[] args) {
        // Frame utama
        JFrame frame = new JFrame("Laundry Apps");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // biar ke tengah layar

        // Panel utama
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // fleksibel
        panel.setBackground(new Color(245, 245, 245)); // warna abu muda
        frame.add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // margin antar komponen
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Judul aplikasi
        JLabel titleLabel = new JLabel("Laundry Apps");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Subtitle
        JLabel subTitle = new JLabel("Males aja nyuci, biar kami cuciin");
        subTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 1;
        panel.add(subTitle, gbc);

        // Username label
        JLabel userLabel = new JLabel("Username");
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(userLabel, gbc);

        // Username field
        JTextField txtUsername = new JTextField(15);
        gbc.gridx = 1;
        panel.add(txtUsername, gbc);

        // Password label
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel passLabel = new JLabel("Password");
        panel.add(passLabel, gbc);

        // Password field
        gbc.gridx = 1;
        JPasswordField txtPassword = new JPasswordField(15);
        panel.add(txtPassword, gbc);

        // Tombol login
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JButton btnLogin = new JButton("Login");
        panel.add(btnLogin, gbc);

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(User.login(txtUsername.getText(), new String(txtPassword.getPassword()))) {
                    new MainFrame().setVisible(true);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Login Gagal");
                }
            }
        });

        frame.setVisible(true);
    }


}
