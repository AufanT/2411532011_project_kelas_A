package main.view;

import java.awt.*;
import javax.swing.*; // <-- Import Style Baru
import main.error.RegisterFailedException;
import main.utils.DataManager;
import main.utils.StyleTheme;

public class RegisterDialog extends JDialog {
    
    public RegisterDialog(JFrame parent) {
        super(parent, "Create New Account", true);
        setSize(350, 480);
        setLocationRelativeTo(parent);
        setLayout(null);
        getContentPane().setBackground(StyleTheme.BG_DARK); // Pakai warna tema

        initUI();
    }

    private void initUI() {
        JLabel title = new JLabel("JOIN MINI STEAM");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(StyleTheme.ACCENT_BLUE);
        title.setBounds(85, 30, 200, 30);
        add(title);

        // Input Fields (Sekarang pakai Modern Component)
        add(createLabel("USERNAME (No Space)", 80));
        JTextField userTxt = new StyleTheme.ModernTextField();
        userTxt.setBounds(50, 105, 240, 40);
        add(userTxt);

        add(createLabel("PASSWORD (Min 5 Char)", 160));
        JPasswordField passTxt = new StyleTheme.ModernPasswordField();
        passTxt.setBounds(50, 185, 240, 40);
        add(passTxt);

        add(createLabel("CONFIRM PASSWORD", 240));
        JPasswordField confirmTxt = new StyleTheme.ModernPasswordField();
        confirmTxt.setBounds(50, 265, 240, 40);
        add(confirmTxt);

        // Tombol Modern
        JButton regBtn = new StyleTheme.ModernButton("CREATE ACCOUNT", StyleTheme.ACCENT_BLUE, Color.BLACK);
        regBtn.setBounds(50, 340, 240, 45);

        regBtn.addActionListener(e -> {
            String u = userTxt.getText();
            String p = new String(passTxt.getPassword());
            String c = new String(confirmTxt.getPassword());

            try {
                DataManager.getInstance().registerUser(u, p, c);
                JOptionPane.showMessageDialog(this, "Registrasi Berhasil! Silakan Login.");
                dispose();
            } catch (RegisterFailedException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Registrasi Gagal", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "System Error: " + ex.getMessage());
            }
        });

        add(regBtn);
    }

    // Helper Label Saja (Field & Button sudah pakai class StyleTheme)
    private JLabel createLabel(String text, int y) {
        JLabel l = new JLabel(text);
        l.setForeground(StyleTheme.ACCENT_BLUE);
        l.setBounds(50, y, 200, 20);
        l.setFont(new Font("Segoe UI", Font.BOLD, 11));
        return l;
    }
}