package main.view;

import java.awt.*;
import javax.swing.*; // Import Exception Baru
import main.error.RegisterFailedException;
import main.utils.DataManager;

public class RegisterDialog extends JDialog {
    
    // Warna Tema
    private final Color BG_DARK = Color.decode("#171a21");
    private final Color TEXT_WHITE = Color.decode("#c7d5e0");
    private final Color ACCENT_BLUE = Color.decode("#66c0f4");

    public RegisterDialog(JFrame parent) {
        super(parent, "Create New Account", true);
        setSize(350, 450); // Agak tinggian dikit biar lega
        setLocationRelativeTo(parent);
        setLayout(null);
        getContentPane().setBackground(BG_DARK);

        initUI();
    }

    private void initUI() {
        JLabel title = new JLabel("JOIN MINI STEAM");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(ACCENT_BLUE);
        title.setBounds(85, 30, 200, 30);
        add(title);

        // Input Fields
        add(createLabel("USERNAME (No Space)", 80));
        JTextField userTxt = createField(100);
        add(userTxt);

        add(createLabel("PASSWORD (Min 5 Char)", 150));
        JPasswordField passTxt = createPasswordField(170);
        add(passTxt);

        add(createLabel("CONFIRM PASSWORD", 220));
        JPasswordField confirmTxt = createPasswordField(240);
        add(confirmTxt);

        // Buttons
        JButton regBtn = new JButton("CREATE ACCOUNT");
        styleButton(regBtn, ACCENT_BLUE, Color.BLACK);
        regBtn.setBounds(50, 310, 240, 40);

        regBtn.addActionListener(e -> {
            String u = userTxt.getText();
            String p = new String(passTxt.getPassword());
            String c = new String(confirmTxt.getPassword());

            try {
                // Panggil Backend (Semua pengecekan ada di sana sekarang)
                DataManager.getInstance().registerUser(u, p, c);

                JOptionPane.showMessageDialog(this, "Registrasi Berhasil! Silakan Login dengan akun baru.");
                dispose(); // Tutup window

            } catch (RegisterFailedException ex) {
                // Tangkap Error Validasi (Username kembar, password beda, spasi, dll)
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Registrasi Gagal", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                // Tangkap Error Lainnya
                JOptionPane.showMessageDialog(this, "System Error: " + ex.getMessage());
            }
        });

        add(regBtn);
    }

    // Helper Styles
    private JLabel createLabel(String text, int y) {
        JLabel l = new JLabel(text);
        l.setForeground(Color.GRAY);
        l.setBounds(50, y, 200, 20);
        l.setFont(new Font("SansSerif", Font.BOLD, 11));
        return l;
    }

    private JTextField createField(int y) {
        JTextField t = new JTextField();
        t.setBounds(50, y, 240, 35);
        t.setBackground(Color.decode("#32353C"));
        t.setForeground(TEXT_WHITE);
        t.setCaretColor(TEXT_WHITE);
        t.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return t;
    }
    
    private JPasswordField createPasswordField(int y) {
        JPasswordField t = new JPasswordField();
        t.setBounds(50, y, 240, 35);
        t.setBackground(Color.decode("#32353C"));
        t.setForeground(TEXT_WHITE);
        t.setCaretColor(TEXT_WHITE);
        t.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return t;
    }

    private void styleButton(JButton b, Color bg, Color fg) {
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
    }
}