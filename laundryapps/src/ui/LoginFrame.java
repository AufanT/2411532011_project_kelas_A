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
        frame.setLocationRelativeTo(null); 

        // Panel utama
        JPanel panel = new JPanel();
        panel.setLayout(null); // Menggunakan Absolute Layout
        panel.setBackground(new Color(245, 245, 245)); 
        frame.add(panel);

        // Judul aplikasi
        JLabel titleLabel = new JLabel("Laundry Apps");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setBounds(130, 20, 200, 30); 
        panel.add(titleLabel);

        // Subtitle
        JLabel subTitle = new JLabel("Males aja nyuci, biar kami cuciin");
        subTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subTitle.setBounds(90, 50, 220, 25);
        panel.add(subTitle);

        // Username label
        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(50, 90, 80, 25);
        panel.add(userLabel);

        // Username field
        JTextField txtUsername = new JTextField(15);
        txtUsername.setBounds(140, 90, 180, 25);
        panel.add(txtUsername);

        // Password label
        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(50, 125, 80, 25);
        panel.add(passLabel);

        // Password field
        JPasswordField txtPassword = new JPasswordField(15);
        txtPassword.setBounds(140, 125, 180, 25);
        panel.add(txtPassword);

        // Tombol login
        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(140, 170, 100, 30);
        panel.add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (User.login(txtUsername.getText(), new String(txtPassword.getPassword()))) {
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