package main.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import main.error.GameSudahDiWishlistException;
import main.error.GameSudahDimilikiException;
import main.error.InputTidakValidException;
import main.error.SaldoKurangException;
import main.model.*;
import main.utils.DataManager;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private DataManager dataManager;

    private final Color BG_DARK = Color.decode("#1b2838");
    private final Color BG_DARKER = Color.decode("#171a21");
    private final Color TEXT_WHITE = Color.decode("#c7d5e0");
    private final Color ACCENT_BLUE = Color.decode("#66c0f4");
    private final Color ACCENT_GREEN = Color.decode("#a4d007");

    public MainFrame() {
        dataManager = DataManager.getInstance();
        initUI();
    }

    private void initUI() {
        setTitle("Mini Steam - Project PBO");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(BG_DARK);

        mainPanel.add(createLoginPanel(), "LOGIN");
        add(mainPanel);
        
        cardLayout.show(mainPanel, "LOGIN");
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(BG_DARK);

        JPanel loginBox = new JPanel();
        loginBox.setLayout(null);
        loginBox.setBackground(BG_DARKER);
        loginBox.setBounds(300, 150, 400, 400); // <-- PERBESAR DIKIT TINGGINYA (350 -> 400)
        loginBox.setBorder(new LineBorder(ACCENT_BLUE, 1));

        JLabel title = new JLabel("SIGN IN");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(ACCENT_BLUE);
        title.setBounds(145, 20, 200, 40);

        JLabel userLbl = new JLabel("USERNAME");
        userLbl.setForeground(TEXT_WHITE);
        userLbl.setBounds(40, 80, 100, 20);

        JTextField userTxt = new JTextField();
        styleField(userTxt);
        userTxt.setBounds(40, 105, 320, 35);

        JLabel passLbl = new JLabel("PASSWORD");
        passLbl.setForeground(TEXT_WHITE);
        passLbl.setBounds(40, 160, 100, 20);

        JPasswordField passTxt = new JPasswordField();
        styleField(passTxt);
        passTxt.setBounds(40, 185, 320, 35);

        JButton loginBtn = new JButton("LOGIN");
        styleButton(loginBtn, ACCENT_BLUE, Color.BLACK);
        loginBtn.setBounds(40, 260, 320, 45);

        // --- TOMBOL REGISTER BARU ---
        JLabel noAccountLbl = new JLabel("Belum punya akun?");
        noAccountLbl.setForeground(Color.GRAY);
        noAccountLbl.setBounds(90, 330, 120, 30);
        
        JButton regBtn = new JButton("Daftar Disini");
        regBtn.setForeground(ACCENT_BLUE);
        regBtn.setBackground(BG_DARKER);
        regBtn.setBorder(null);
        regBtn.setFocusPainted(false);
        regBtn.setBounds(210, 330, 100, 30);
        regBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // LOGIC BUKA REGISTER
        regBtn.addActionListener(e -> {
            new RegisterDialog(this).setVisible(true);
        });
        // ---------------------------

        loginBtn.addActionListener(e -> {
            try {
                String u = userTxt.getText();
                String p = new String(passTxt.getPassword());

                if (u.isEmpty() || p.isEmpty()) {
                    throw new InputTidakValidException("Username dan Password harus diisi!");
                }

                if (dataManager.login(u, p)) {
                    if (dataManager.getCurrentUser().getRole().equals("ADMIN")) {
                        mainPanel.add(createAdminPanel(), "ADMIN");
                        cardLayout.show(mainPanel, "ADMIN");
                    } else {
                        mainPanel.add(createUserPanel(), "USER");
                        cardLayout.show(mainPanel, "USER");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Username/Password Salah!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }

            } catch (InputTidakValidException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });

        loginBox.add(title);
        loginBox.add(userLbl); loginBox.add(userTxt);
        loginBox.add(passLbl); loginBox.add(passTxt);
        loginBox.add(loginBtn);
        
        // Add component Register
        loginBox.add(noAccountLbl);
        loginBox.add(regBtn);

        panel.add(loginBox);
        return panel;
    }

    // Variabel Global untuk Panel Admin (supaya bisa direfresh dari luar)
    private DefaultTableModel tableModel; 

    private JPanel createAdminPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(BG_DARK);

        JLabel title = new JLabel("ADMIN DASHBOARD");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(TEXT_WHITE);
        title.setBounds(30, 20, 300, 30);

        // TABEL
        String[] col = {"ID", "Title", "Genre", "Price"};
        tableModel = new DefaultTableModel(col, 0);
        JTable table = new JTable(tableModel);
        styleTable(table);
        refreshTable(); // Load data

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(30, 70, 925, 450); // Tabel lebih panjang ke bawah
        scroll.getViewport().setBackground(BG_DARKER);

        // TOMBOL UTAMA
        JButton addBtn = new JButton("+ ADD NEW GAME");
        styleButton(addBtn, ACCENT_BLUE, Color.BLACK);
        addBtn.setBounds(750, 20, 200, 35);

        // Tombol Aksi di Bawah
        JButton editBtn = new JButton("EDIT SELECTED");
        styleButton(editBtn, Color.ORANGE, Color.BLACK);
        editBtn.setBounds(680, 540, 150, 40);

        JButton deleteBtn = new JButton("DELETE");
        styleButton(deleteBtn, Color.RED, Color.WHITE);
        deleteBtn.setBounds(840, 540, 110, 40);

        JButton logoutBtn = new JButton("LOGOUT");
        styleButton(logoutBtn, Color.GRAY, Color.WHITE);
        logoutBtn.setBounds(30, 540, 120, 40);

        // LOGIC 1: ADD (Buka Dialog Kosong)
        addBtn.addActionListener(e -> {
            new AddGameDialog(this).setVisible(true);
        });

        // LOGIC 2: EDIT (Buka Dialog Terisi Data)
        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row != -1) {
                // Ambil data game dari list asli berdasarkan index baris
                Game selectedGame = dataManager.getGames().get(row);
                
                // Buka Dialog dengan membawa data game tersebut
                new AddGameDialog(this, selectedGame).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Pilih game di tabel dulu!");
            }
        });

        // LOGIC 3: DELETE
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row != -1) {
                if(JOptionPane.showConfirmDialog(this, "Hapus game ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    String id = tableModel.getValueAt(row, 0).toString();
                    dataManager.deleteGame(new Game.Builder().setId(id).build());
                    refreshTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pilih game yang mau dihapus!");
            }
        });

        // LOGIC 4: Double Click Tabel untuk Edit (Fitur Bonus UX)
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { // Kalau didouble click
                    int row = table.getSelectedRow();
                    if(row != -1) {
                        Game selectedGame = dataManager.getGames().get(row);
                        new AddGameDialog(MainFrame.this, selectedGame).setVisible(true);
                    }
                }
            }
        });

        logoutBtn.addActionListener(e -> {
            dataManager.logout();
            cardLayout.show(mainPanel, "LOGIN");
        });

        panel.add(title); panel.add(scroll); 
        panel.add(addBtn); panel.add(editBtn); panel.add(deleteBtn);
        panel.add(logoutBtn);

        return panel;
    }

    public void refreshTable() {
        if(tableModel != null) {
            tableModel.setRowCount(0);
            for(Game g : dataManager.getGames()){
                tableModel.addRow(new Object[]{g.getId(), g.getTitle(), g.getGenre(), g.getPrice()});
            }
        }
    }

    // --- USER PANEL UTAMA (DENGAN 3 TAB) ---
    private JPanel createUserPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_DARK);

        RegularUser user = (RegularUser) dataManager.getCurrentUser();

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_DARK);
        header.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("MINI STEAM");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(ACCENT_BLUE);

        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightHeader.setBackground(BG_DARK);

        JLabel balLabel = new JLabel("WALLET: Rp " + String.format("%,.0f", user.getBalance()));
        balLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        balLabel.setForeground(ACCENT_GREEN);

        JButton topUpBtn = new JButton("+ TOP UP");
        styleButton(topUpBtn, ACCENT_BLUE, Color.BLACK);
        topUpBtn.setFont(new Font("SansSerif", Font.BOLD, 11));
        topUpBtn.setPreferredSize(new Dimension(90, 30));

        rightHeader.add(balLabel);
        rightHeader.add(topUpBtn);
        header.add(title, BorderLayout.WEST);
        header.add(rightHeader, BorderLayout.EAST);

        // LOGIC TOP UP
        topUpBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Masukkan Jumlah Top Up (Rp):");
            if (input != null && !input.isEmpty()) {
                try {
                    double amount = Double.parseDouble(input);
                    if (amount <= 0) throw new InputTidakValidException("Jumlah Top Up harus lebih dari 0!");
                    
                    user.setBalance(user.getBalance() + amount);
                    dataManager.updateUserBalance(user);
                    
                    balLabel.setText("WALLET: Rp " + String.format("%,.0f", user.getBalance()));
                    JOptionPane.showMessageDialog(this, "Top Up Berhasil!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Input harus angka!", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (InputTidakValidException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Gagal", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // --- TABS (STORE, LIBRARY, WISHLIST) ---
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(BG_DARKER);
        tabbedPane.setForeground(TEXT_WHITE);
        
        // Tab 1: Store
        JPanel storePanel = createStoreTab(user, balLabel);
        tabbedPane.addTab("STORE", storePanel);
        
        // Tab 2: Library
        JPanel libraryPanel = createLibraryTab(user);
        tabbedPane.addTab("MY LIBRARY", libraryPanel);

        // Tab 3: Wishlist (BARU)
        JPanel wishlistPanel = createWishlistTab(user);
        tabbedPane.addTab("WISHLIST", wishlistPanel);

        // Footer Logout
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(BG_DARK);
        JButton logoutBtn = new JButton("LOGOUT");
        styleButton(logoutBtn, Color.RED, Color.WHITE);
        logoutBtn.addActionListener(e -> {
            dataManager.logout();
            cardLayout.show(mainPanel, "LOGIN");
        });
        footer.add(logoutBtn);

        // Event Listener Refresh Tab
        tabbedPane.addChangeListener(e -> {
            int idx = tabbedPane.getSelectedIndex();
            if (idx == 1) refreshLibraryTab(libraryPanel, user);
            if (idx == 2) refreshWishlistTab(wishlistPanel, user); // Refresh Wishlist
        });

        panel.add(header, BorderLayout.NORTH);
        panel.add(tabbedPane, BorderLayout.CENTER);
        panel.add(footer, BorderLayout.SOUTH);

        return panel;
    }

    // --- TAB 1: STORE (Update: Tambah Tombol Wishlist) ---
    private JPanel createStoreTab(RegularUser user, JLabel balLabel) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_DARK);
        
        DefaultListModel<Game> listModel = new DefaultListModel<>();
        for(Game g : dataManager.getGames()) listModel.addElement(g);
        
        JList<Game> list = new JList<>(listModel);
        list.setBackground(BG_DARKER);
        list.setForeground(TEXT_WHITE);
        list.setCellRenderer(createGameRenderer());
        
        // Panel Tombol di Bawah
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnPanel.setBackground(BG_DARK);
        btnPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        JButton buyBtn = new JButton("BUY GAME");
        styleButton(buyBtn, ACCENT_GREEN, Color.WHITE);

        JButton wishBtn = new JButton("ADD TO WISHLIST");
        styleButton(wishBtn, Color.ORANGE, Color.BLACK);
        
        // LOGIC BELI
        buyBtn.addActionListener(e -> {
            Game g = list.getSelectedValue();
            if(g != null) {
                try {
                    if (dataManager.isGameOwned(user.getId(), g.getId())) {
                        throw new GameSudahDimilikiException(g.getTitle());
                    }
                    g.purchase(user.getBalance()); 
                    user.setBalance(user.getBalance() - g.getPrice());
                    dataManager.updateUserBalance(user);
                    dataManager.addToLibrary(user.getId(), g.getId());
                    balLabel.setText("WALLET: Rp " + String.format("%,.0f", user.getBalance()));
                    JOptionPane.showMessageDialog(this, "Success! Added to Library.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Info", JOptionPane.WARNING_MESSAGE);
                }
            } else JOptionPane.showMessageDialog(this, "Pilih game dulu!");
        });

        // LOGIC WISHLIST
        wishBtn.addActionListener(e -> {
            Game g = list.getSelectedValue();
            if (g != null) {
                try {
                    // Cek 1: Apakah sudah punya di Library?
                    if (dataManager.isGameOwned(user.getId(), g.getId())) {
                        throw new GameSudahDimilikiException(g.getTitle()); // Eror: Sudah punya
                    }
                    // Cek 2: Apakah sudah ada di Wishlist?
                    if (dataManager.isGameInWishlist(user.getId(), g.getId())) {
                        throw new GameSudahDiWishlistException(g.getTitle()); // Eror: Sudah di wishlist
                    }
                    
                    // Add to DB
                    dataManager.addToWishlist(user.getId(), g.getId());
                    JOptionPane.showMessageDialog(this, "Game ditambahkan ke Wishlist!");
                
                } catch (GameSudahDimilikiException ex) {
                    JOptionPane.showMessageDialog(this, "Gagal: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (GameSudahDiWishlistException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            } else JOptionPane.showMessageDialog(this, "Pilih game dulu!");
        });

        btnPanel.add(buyBtn);
        btnPanel.add(wishBtn);

        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    // --- TAB 3: WISHLIST (BARU) ---
    private JPanel createWishlistTab(RegularUser user) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_DARK);
        return panel; 
    }

    private void refreshWishlistTab(JPanel panel, RegularUser user) {
        panel.removeAll();
        
        DefaultListModel<Game> wishModel = new DefaultListModel<>();
        for(Game g : dataManager.getUserWishlist(user.getId())) {
            wishModel.addElement(g);
        }
        
        JList<Game> list = new JList<>(wishModel);
        list.setBackground(BG_DARKER);
        list.setForeground(TEXT_WHITE);
        list.setCellRenderer(createGameRenderer());
        
        // Panel Tombol di Bawah (Grid 1 Baris, 2 Kolom)
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnPanel.setBackground(BG_DARK);
        btnPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        JButton buyBtn = new JButton("BUY NOW");
        styleButton(buyBtn, ACCENT_GREEN, Color.WHITE);

        JButton removeBtn = new JButton("REMOVE");
        styleButton(removeBtn, Color.RED, Color.WHITE);
        
        // --- LOGIC BELI DARI WISHLIST ---
        buyBtn.addActionListener(e -> {
            Game g = list.getSelectedValue();
            if(g != null) {
                try {
                    // 1. Cek apakah anehnya user sudah punya (Validasi ganda)
                    if (dataManager.isGameOwned(user.getId(), g.getId())) {
                        // Kalau sudah punya, hapus saja dari wishlist
                        dataManager.removeFromWishlist(user.getId(), g.getId());
                        refreshWishlistTab(panel, user);
                        throw new GameSudahDimilikiException(g.getTitle());
                    }

                    // 2. Cek Saldo & Purchase (Exception SaldoKurang)
                    g.purchase(user.getBalance()); 
                    
                    // 3. Proses Transaksi
                    user.setBalance(user.getBalance() - g.getPrice());
                    dataManager.updateUserBalance(user);       // Update Saldo DB
                    dataManager.addToLibrary(user.getId(), g.getId()); // Masuk Library DB
                    
                    // 4. HAPUS DARI WISHLIST (Penting!)
                    dataManager.removeFromWishlist(user.getId(), g.getId());
                    
                    // 5. Update UI
                    JOptionPane.showMessageDialog(this, "Berhasil membeli " + g.getTitle() + "!");
                    refreshWishlistTab(panel, user); // Refresh agar game hilang dari list

                } catch (GameSudahDimilikiException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Info", JOptionPane.INFORMATION_MESSAGE);
                } catch (SaldoKurangException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Gagal Beli", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pilih game yang mau dibeli!");
            }
        });

        // --- LOGIC HAPUS DARI WISHLIST ---
        removeBtn.addActionListener(e -> {
            Game g = list.getSelectedValue();
            if(g != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "Hapus " + g.getTitle() + " dari Wishlist?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION) {
                    dataManager.removeFromWishlist(user.getId(), g.getId());
                    refreshWishlistTab(panel, user); // Refresh otomatis
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pilih game dulu!");
            }
        });

        btnPanel.add(buyBtn);
        btnPanel.add(removeBtn);

        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        
        panel.revalidate();
        panel.repaint();
    }

    // --- TAB 2: LIBRARY (LOGIC BARU) ---
    private JPanel createLibraryTab(RegularUser user) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_DARK);
        return panel; // Kosong dulu, nanti diisi refreshLibraryTab
    }

    // Method untuk refresh isi Library
    private void refreshLibraryTab(JPanel panel, RegularUser user) {
        panel.removeAll(); // Hapus tampilan lama
        
        DefaultListModel<Game> libModel = new DefaultListModel<>();
        // Ambil game yang dimiliki user dari database
        for(Game g : dataManager.getUserLibrary(user.getId())) {
            libModel.addElement(g);
        }
        
        JList<Game> list = new JList<>(libModel);
        list.setBackground(BG_DARKER);
        list.setForeground(TEXT_WHITE);
        list.setCellRenderer(createGameRenderer()); // Pakai renderer yang sama
        
        JButton playBtn = new JButton("PLAY GAME");
        styleButton(playBtn, ACCENT_BLUE, Color.BLACK);
        
        playBtn.addActionListener(e -> {
            Game g = list.getSelectedValue();
            if(g != null) {
                g.launch(); // Panggil method launch() -> Interface OOP
                JOptionPane.showMessageDialog(this, "Launching " + g.getTitle() + "...\nHave Fun!");
            }
        });

        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        panel.add(playBtn, BorderLayout.SOUTH);
        
        panel.revalidate();
        panel.repaint();
    }

    // RENDERER GAMBAR (Dipakai ulang)
    private ListCellRenderer<Object> createGameRenderer() {
        return new DefaultListCellRenderer() {
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
                return label;
            }
        };
    }

    private void styleField(JTextField f) {
        f.setBackground(Color.decode("#32353C"));
        f.setForeground(TEXT_WHITE);
        f.setCaretColor(TEXT_WHITE);
        f.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    private void styleButton(JButton b, Color bg, Color fg) {
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
    }

    private void styleTable(JTable t) {
        t.setBackground(BG_DARKER);
        t.setForeground(TEXT_WHITE);
        t.setGridColor(Color.GRAY);
        t.setRowHeight(30);
        t.getTableHeader().setBackground(BG_DARK);
        t.getTableHeader().setForeground(TEXT_WHITE);
    }
}