package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Laundry Apps - Main Menu");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel utama
        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 245, 245));
        panel.setLayout(new GridBagLayout());
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Judul
        JLabel titleLabel = new JLabel("Laundry Apps");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.MAGENTA);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panel.add(titleLabel, gbc);

        // Reset gridwidth untuk tombol
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;

        // Baris pertama: Pesanan, Layanan, Pelanggan
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(new JButton("PESANAN"), gbc);

        gbc.gridx = 1;
        panel.add(new JButton("LAYANAN"), gbc);

        gbc.gridx = 2;
        panel.add(new JButton("PELANGGAN"), gbc);

        // Baris kedua: Pengguna, Laporan, Profile
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(new JButton("PENGGUNA"), gbc);

        gbc.gridx = 1;
        panel.add(new JButton("LAPORAN"), gbc);

        gbc.gridx = 2;
        panel.add(new JButton("PROFILE"), gbc);

        // Baris ketiga: tombol keluar
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        JButton btnKeluar = new JButton("KELUAR");
        panel.add(btnKeluar, gbc);

        // Action tombol keluar
        btnKeluar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int konfirmasi = JOptionPane.showConfirmDialog(
                        null,
                        "Apakah yakin ingin keluar?",
                        "Konfirmasi",
                        JOptionPane.YES_NO_OPTION
                );
                if (konfirmasi == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }
}
