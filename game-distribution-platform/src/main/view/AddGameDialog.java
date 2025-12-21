package main.view;

import java.awt.*;
import javax.swing.*;
import main.error.GameSudahAdaException;
import main.error.InputTidakValidException;
import main.model.Game;
import main.utils.DataManager;
import main.utils.StyleTheme; // <-- Import Style Baru

public class AddGameDialog extends JDialog {
    private MainFrame parent;
    private JTextField titleTxt, genreTxt, priceTxt, imageTxt;
    private Game gameToEdit; 
    
    public AddGameDialog(MainFrame parent) {
        this(parent, null);
    }

    public AddGameDialog(MainFrame parent, Game gameToEdit) {
        super(parent, gameToEdit == null ? "Add New Game" : "Edit Game", true);
        this.parent = parent;
        this.gameToEdit = gameToEdit;
        
        setSize(400, 520); // Sedikit lebih tinggi biar lega
        setLocationRelativeTo(parent);
        setLayout(null);
        getContentPane().setBackground(StyleTheme.BG_DARK);

        initUI();
        
        // Isi data jika mode EDIT
        if (gameToEdit != null) {
            titleTxt.setText(gameToEdit.getTitle());
            genreTxt.setText(gameToEdit.getGenre());
            priceTxt.setText(String.valueOf((long)gameToEdit.getPrice()));
            imageTxt.setText(gameToEdit.getImagePath());
        }
    }

    private void initUI() {
        JLabel head = new JLabel(gameToEdit == null ? "ADD NEW GAME" : "EDIT GAME DATA");
        head.setForeground(StyleTheme.ACCENT_BLUE);
        head.setFont(new Font("Segoe UI", Font.BOLD, 20));
        head.setBounds(110, 20, 200, 30);
        add(head);

        // --- INPUT FIELDS (Modern) ---
        add(createLabel("TITLE", 70));
        titleTxt = new StyleTheme.ModernTextField();
        titleTxt.setBounds(50, 95, 280, 40);
        add(titleTxt);

        add(createLabel("GENRE", 150));
        genreTxt = new StyleTheme.ModernTextField();
        genreTxt.setBounds(50, 175, 280, 40);
        add(genreTxt);

        add(createLabel("PRICE (Rp)", 230));
        priceTxt = new StyleTheme.ModernTextField();
        priceTxt.setBounds(50, 255, 280, 40);
        add(priceTxt);

        add(createLabel("IMAGE URL (Link Gambar)", 310));
        imageTxt = new StyleTheme.ModernTextField();
        imageTxt.setBounds(50, 335, 280, 40);
        add(imageTxt);

        // --- BUTTONS (Modern) ---
        String btnText = gameToEdit == null ? "SAVE GAME" : "UPDATE DATA";
        JButton saveBtn = new StyleTheme.ModernButton(btnText, StyleTheme.ACCENT_BLUE, Color.BLACK);
        saveBtn.setBounds(50, 410, 140, 40);

        JButton cancelBtn = new StyleTheme.ModernButton("CANCEL", StyleTheme.ACCENT_RED, Color.WHITE);
        cancelBtn.setBounds(210, 410, 120, 40);

        saveBtn.addActionListener(e -> {
            try {
                if (titleTxt.getText().trim().isEmpty()) throw new InputTidakValidException("Judul wajib diisi!");
                
                double price;
                try {
                    price = Double.parseDouble(priceTxt.getText());
                } catch (NumberFormatException ex) {
                    throw new InputTidakValidException("Harga harus berupa angka valid!");
                }
                if (price < 0) throw new InputTidakValidException("Harga tidak boleh negatif!");

                String inputTitle = titleTxt.getText().trim();
                DataManager dataManager = DataManager.getInstance();

                boolean isNewGame = (gameToEdit == null);
                boolean titleChanged = (gameToEdit != null && !gameToEdit.getTitle().equalsIgnoreCase(inputTitle));

                if ((isNewGame || titleChanged) && dataManager.isGameTitleExists(inputTitle)) {
                    throw new GameSudahAdaException(inputTitle);
                }

                String id = (gameToEdit == null) ? "G" + ((int)(Math.random() * 9000) + 1000) : gameToEdit.getId();
                String imgPath = imageTxt.getText().isEmpty() ? "https://cdn.akamai.steamstatic.com/steam/apps/2358720/header.jpg" : imageTxt.getText();

                Game resultGame = new Game.Builder()
                    .setId(id)
                    .setTitle(inputTitle)
                    .setGenre(genreTxt.getText())
                    .setPrice(price)
                    .setImagePath(imgPath)
                    .build();

                if (gameToEdit == null) dataManager.addGame(resultGame);
                else dataManager.updateGame(resultGame);
                
                JOptionPane.showMessageDialog(this, "Berhasil Disimpan!");
                parent.refreshTable();
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        cancelBtn.addActionListener(e -> dispose());

        add(saveBtn);
        add(cancelBtn);
    }

    private JLabel createLabel(String text, int y) {
        JLabel l = new JLabel(text);
        l.setForeground(StyleTheme.ACCENT_BLUE); // Ganti jadi biru biar senada
        l.setBounds(50, y, 200, 20);
        l.setFont(new Font("Segoe UI", Font.BOLD, 11));
        return l;
    }
}