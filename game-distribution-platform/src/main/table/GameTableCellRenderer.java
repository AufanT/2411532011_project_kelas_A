package main.table;

import java.awt.*;
import javax.swing.*;
import main.model.Game;
import main.utils.StyleTheme;

public class GameTableCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        Game game = (Game) value;
        
        label.setText("<html><b style='font-size:14px'>" + game.getTitle() + "</b><br>" + 
                        "<span style='color:#66c0f4'>" + game.getGenre() + "</span><br>" + 
                        "Rp " + String.format("%,.0f", game.getPrice()) + "</html>");
        
        try {
            if (game.getImagePath() != null && !game.getImagePath().isEmpty()) {
                ImageIcon icon = new ImageIcon(new java.net.URL(game.getImagePath()));
                Image img = icon.getImage().getScaledInstance(120, 60, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(img));
            }
        } catch (Exception e) {}

        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        if (isSelected) {
            label.setBackground(StyleTheme.ACCENT_BLUE);
            label.setForeground(Color.BLACK);
        } else {
            label.setBackground(StyleTheme.BG_PANEL);
            label.setForeground(StyleTheme.TEXT_MAIN);
        }
        return label;
    }
}