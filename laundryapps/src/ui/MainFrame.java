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
        panel.setLayout(null); 
        add(panel);

        // Judul
        JLabel titleLabel = new JLabel("Laundry Apps");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.MAGENTA);
        titleLabel.setBounds(200, 20, 200, 35); 
        panel.add(titleLabel);


        // Baris pertama: Pesanan, Layanan, Pelanggan
        JButton btnPesanan = new JButton("PESANAN");
        btnPesanan.setBounds(50, 80, 150, 40);
        panel.add(btnPesanan);

        JButton btnLayanan = new JButton("LAYANAN");
        btnLayanan.setBounds(220, 80, 150, 40);
        panel.add(btnLayanan);

        JButton btnPelanggan = new JButton("PELANGGAN");
        btnPelanggan.setBounds(390, 80, 150, 40);
        panel.add(btnPelanggan);


        // Baris kedua: Pengguna, Laporan, Profile
        JButton btnPengguna = new JButton("PENGGUNA");
        btnPengguna.setBounds(50, 140, 150, 40);
        panel.add(btnPengguna);

        JButton btnLaporan = new JButton("LAPORAN");
        btnLaporan.setBounds(220, 140, 150, 40);
        panel.add(btnLaporan);

        JButton btnProfile = new JButton("PROFILE");
        btnProfile.setBounds(390, 140, 150, 40);
        panel.add(btnProfile);

        // Baris ketiga: tombol keluar
        JButton btnKeluar = new JButton("KELUAR");
        btnKeluar.setBounds(50, 220, 490, 40); // Span lebar
        panel.add(btnKeluar);

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