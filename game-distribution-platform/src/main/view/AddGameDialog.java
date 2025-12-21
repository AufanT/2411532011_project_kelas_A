package main.view;

import java.awt.*;
import javax.swing.*;
import main.error.GameSudahAdaException; // Import Exception Validasi
import main.error.InputTidakValidException;    // Import Exception Duplikat
import main.model.Game;
import main.utils.DataManager;

public class AddGameDialog extends JDialog {
    private MainFrame parent;
    private JTextField titleTxt, genreTxt, priceTxt, imageTxt;
    private Game gameToEdit; 
    
    // Warna Tema Steam
    private final Color BG_DARK = Color.decode("#171a21");
    private final Color TEXT_WHITE = Color.decode("#c7d5e0");
    private final Color ACCENT_BLUE = Color.decode("#66c0f4");

    // Constructor 1: Mode ADD
    public AddGameDialog(MainFrame parent) {
        this(parent, null);
    }

    // Constructor 2: Mode EDIT
    public AddGameDialog(MainFrame parent, Game gameToEdit) {
        super(parent, gameToEdit == null ? "Add New Game" : "Edit Game", true);
        this.parent = parent;
        this.gameToEdit = gameToEdit;
        
        setSize(400, 480);
        setLocationRelativeTo(parent);
        setLayout(null);
        getContentPane().setBackground(BG_DARK);

        initUI();
        
        // JIKA MODE EDIT: Isi form dengan data lama
        if (gameToEdit != null) {
            titleTxt.setText(gameToEdit.getTitle());
            genreTxt.setText(gameToEdit.getGenre());
            priceTxt.setText(String.valueOf((long)gameToEdit.getPrice()));
            imageTxt.setText(gameToEdit.getImagePath());
        }
    }

    private void initUI() {
        JLabel head = new JLabel(gameToEdit == null ? "ADD NEW GAME" : "EDIT GAME DATA");
        head.setForeground(ACCENT_BLUE);
        head.setFont(new Font("SansSerif", Font.BOLD, 18));
        head.setBounds(120, 20, 200, 30);
        add(head);

        // --- INPUT FIELDS ---
        add(createLabel("TITLE", 70));
        titleTxt = createField(90);
        add(titleTxt);

        add(createLabel("GENRE", 140));
        genreTxt = createField(160);
        add(genreTxt);

        add(createLabel("PRICE (Rp)", 210));
        priceTxt = createField(230);
        add(priceTxt);

        add(createLabel("IMAGE URL (Link Gambar)", 280));
        imageTxt = createField(300);
        add(imageTxt);

        // --- BUTTONS ---
        JButton saveBtn = new JButton(gameToEdit == null ? "SAVE GAME" : "UPDATE DATA");
        saveBtn.setBackground(ACCENT_BLUE);
        saveBtn.setForeground(Color.BLACK);
        saveBtn.setBounds(50, 370, 140, 35);
        saveBtn.setFocusPainted(false);

        JButton cancelBtn = new JButton("CANCEL");
        cancelBtn.setBackground(Color.RED);
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setBounds(210, 370, 120, 35);
        cancelBtn.setFocusPainted(false);

        // LOGIC SAVE / UPDATE
        saveBtn.addActionListener(e -> {
            try {
                // 1. Validasi Input Kosong
                if (titleTxt.getText().trim().isEmpty()) 
                    throw new InputTidakValidException("Judul wajib diisi!");
                
                // 2. Validasi Harga (Harus Angka & Tidak Negatif)
                double price;
                try {
                    price = Double.parseDouble(priceTxt.getText());
                } catch (NumberFormatException ex) {
                    throw new InputTidakValidException("Harga harus berupa angka valid!");
                }
                
                if (price < 0) 
                    throw new InputTidakValidException("Harga tidak boleh negatif!");

                String inputTitle = titleTxt.getText().trim();
                DataManager dataManager = DataManager.getInstance();

                // 3. CEK DUPLIKAT JUDUL (EXCEPTION BARU DISINI)
                // Logic: Cek duplikat jika ini game BARU, ATAU jika EDIT tapi judulnya diganti
                boolean isNewGame = (gameToEdit == null);
                boolean titleChanged = (gameToEdit != null && !gameToEdit.getTitle().equalsIgnoreCase(inputTitle));

                if ((isNewGame || titleChanged) && dataManager.isGameTitleExists(inputTitle)) {
                    throw new GameSudahAdaException(inputTitle);
                }

                // --- PROSES SIMPAN ---
                String id;
                if (gameToEdit == null) {
                    // MODE ADD
                    int rand = (int)(Math.random() * 9000) + 1000;
                    id = "G" + rand;
                } else {
                    // MODE EDIT
                    id = gameToEdit.getId();
                }

                String imgPath = imageTxt.getText();
                if (imgPath.isEmpty()) {
                    imgPath = "https://cdn.akamai.steamstatic.com/steam/apps/2358720/header.jpg"; 
                }

                Game resultGame = new Game.Builder()
                    .setId(id)
                    .setTitle(inputTitle) // Pakai judul yg sudah di-trim
                    .setGenre(genreTxt.getText())
                    .setPrice(price)
                    .setImagePath(imgPath)
                    .build();

                if (gameToEdit == null) {
                    dataManager.addGame(resultGame);
                } else {
                    dataManager.updateGame(resultGame);
                }
                
                JOptionPane.showMessageDialog(this, "Berhasil Disimpan!");
                parent.refreshTable();
                dispose();

            } catch (InputTidakValidException ex) {
                // Tangkap Error Validasi Input
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Salah", JOptionPane.WARNING_MESSAGE);
            
            } catch (GameSudahAdaException ex) {
                // Tangkap Error Duplikat Judul
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Duplikat", JOptionPane.ERROR_MESSAGE);
            
            } catch (Exception ex) {
                // Tangkap Error Lainnya (System)
                JOptionPane.showMessageDialog(this, "System Error: " + ex.getMessage());
            }
        });

        cancelBtn.addActionListener(e -> dispose());

        add(saveBtn);
        add(cancelBtn);
    }

    private JLabel createLabel(String text, int y) {
        JLabel l = new JLabel(text);
        l.setForeground(Color.GRAY);
        l.setBounds(50, y, 200, 20);
        l.setFont(new Font("SansSerif", Font.PLAIN, 12));
        return l;
    }

    private JTextField createField(int y) {
        JTextField t = new JTextField();
        t.setBounds(50, y, 280, 30);
        t.setBackground(Color.decode("#32353C"));
        t.setForeground(TEXT_WHITE);
        t.setCaretColor(TEXT_WHITE);
        t.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        return t;
    }
}