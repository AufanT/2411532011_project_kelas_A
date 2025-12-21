package main.utils;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;

public class StyleTheme {

    // color palette
    public static final Color BG_DARK = Color.decode("#171a21");     
    public static final Color BG_PANEL = Color.decode("#1b2838");    
    public static final Color TEXT_MAIN = Color.decode("#c7d5e0");  
    public static final Color ACCENT_BLUE = Color.decode("#66c0f4"); 
    public static final Color ACCENT_GREEN = Color.decode("#a4d007");
    public static final Color ACCENT_RED = Color.decode("#c23a3a"); 
    public static final Color INPUT_BG = Color.decode("#32353c");   

    public static class ModernButton extends JButton {
        private Color normalColor;
        private Color hoverColor;
        private Color pressedColor;
        private boolean isHovered = false;

        public ModernButton(String text, Color baseColor, Color textColor) {
            super(text);
            this.normalColor = baseColor;
            this.hoverColor = baseColor.brighter();
            this.pressedColor = baseColor.darker();
            
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setForeground(textColor);
            setFont(new Font("Segoe UI", Font.BOLD, 12));
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) { isHovered = true; repaint(); }
                @Override
                public void mouseExited(MouseEvent e) { isHovered = false; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (getModel().isPressed()) {
                g2.setColor(pressedColor);
            } else if (isHovered) {
                g2.setColor(hoverColor);
            } else {
                g2.setColor(normalColor);
            }

            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

            super.paintComponent(g);
            g2.dispose();
        }
    }

    public static class ModernTextField extends JTextField {
        public ModernTextField() {
            setOpaque(false);
            setForeground(TEXT_MAIN);
            setCaretColor(TEXT_MAIN);
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
            setBorder(new EmptyBorder(5, 15, 5, 15)); 
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.setColor(INPUT_BG);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            super.paintComponent(g);
            g2.dispose();
        }
    }

    public static class ModernPasswordField extends JPasswordField {
        public ModernPasswordField() {
            setOpaque(false);
            setForeground(TEXT_MAIN);
            setCaretColor(TEXT_MAIN);
            setBorder(new EmptyBorder(5, 15, 5, 15));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(INPUT_BG);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            super.paintComponent(g);
            g2.dispose();
        }
    }

    public static void styleTable(JTable table) {
        table.setBackground(BG_PANEL);
        table.setForeground(TEXT_MAIN);
        table.setGridColor(new Color(60, 60, 60));
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(ACCENT_BLUE);
        table.setSelectionForeground(Color.BLACK);

        JTableHeader header = table.getTableHeader();
        header.setBackground(BG_DARK);
        header.setForeground(ACCENT_BLUE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBorder(null);

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.LEFT);
        headerRenderer.setBorder(new EmptyBorder(0, 10, 0, 0));
    }
    
    public static void styleTabbedPane(JTabbedPane tabbedPane) {
        tabbedPane.setBackground(BG_PANEL);
        tabbedPane.setForeground(TEXT_MAIN);
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabbedPane.setFocusable(false); 

        tabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {

            @Override
            protected void installDefaults() {
                super.installDefaults();
                shadow = BG_DARK;
                darkShadow = BG_DARK;
                lightHighlight = BG_DARK;
                highlight = BG_DARK;
                focus = BG_DARK; 
            }

            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
                if (isSelected) {
                    g.setColor(BG_PANEL); 
                } else {
                    g.setColor(BG_DARK);
                }
                g.fillRect(x, y, w, h);
            }

            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) { }

            @Override
            protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
                if(isSelected) {
                    g.setColor(ACCENT_BLUE);
                    g.fillRect(x, y + h - 3, w, 3);
                }
            }
        });
    }
}