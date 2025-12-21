package main.ui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import main.error.*;
import main.model.*;
import main.service.AppService;
import main.utils.StyleTheme; 

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private AppService appService;

    public MainFrame() {
        appService = AppService.getInstance();
        initUI();
    }

    private void initUI() {
        setTitle("Mini Steam - Project PBO");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setIconImage(new ImageIcon("C:\\Users\\VICTUS\\Downloads\\mini-steam-icon.png").getImage());

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(StyleTheme.BG_DARK);

        mainPanel.add(createLoginPanel(), "LOGIN");
        add(mainPanel);
        
        cardLayout.show(mainPanel, "LOGIN");
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(StyleTheme.BG_DARK);

        JPanel loginBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(StyleTheme.BG_PANEL);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); 
                
                g2.setColor(StyleTheme.ACCENT_BLUE);
                g2.setStroke(new BasicStroke(1.5f)); 

                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 30, 30);
                
                g2.dispose();
            }
        };
        loginBox.setOpaque(false);
        loginBox.setLayout(null);
        loginBox.setBounds(300, 150, 400, 400);

        JLabel title = new JLabel("SIGN IN");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(StyleTheme.ACCENT_BLUE);
        title.setBounds(145, 20, 200, 40);

        JLabel userLbl = new JLabel("USERNAME");
        userLbl.setForeground(StyleTheme.ACCENT_BLUE);
        userLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        userLbl.setBounds(40, 80, 100, 20);

        JTextField userTxt = new StyleTheme.ModernTextField();
        userTxt.setBounds(40, 105, 320, 40);

        JLabel passLbl = new JLabel("PASSWORD");
        passLbl.setForeground(StyleTheme.ACCENT_BLUE);
        passLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        passLbl.setBounds(40, 160, 100, 20);

        JPasswordField passTxt = new StyleTheme.ModernPasswordField();
        passTxt.setBounds(40, 185, 320, 40);

        JButton loginBtn = new StyleTheme.ModernButton("LOGIN", StyleTheme.ACCENT_BLUE, Color.BLACK);
        loginBtn.setBounds(40, 260, 320, 45);

        JLabel noAccountLbl = new JLabel("Belum punya akun?");
        noAccountLbl.setForeground(Color.GRAY);
        noAccountLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        noAccountLbl.setBounds(90, 330, 120, 30);
        
        JLabel regLink = new JLabel("<html><u>Daftar Disini</u></html>");
        regLink.setForeground(StyleTheme.TEXT_MAIN);
        regLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        regLink.setBounds(210, 330, 100, 30);

        regLink.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { new RegisterDialog(MainFrame.this).setVisible(true); }
            public void mouseEntered(MouseEvent e) { regLink.setForeground(StyleTheme.ACCENT_BLUE); }
            public void mouseExited(MouseEvent e) { regLink.setForeground(StyleTheme.TEXT_MAIN); }
        });

        loginBtn.addActionListener(e -> {
            try {
                String u = userTxt.getText();
                String p = new String(passTxt.getPassword());

                if (u.isEmpty() || p.isEmpty()) throw new InputTidakValidException("Username dan Password harus diisi!");

                if (appService.login(u, p)) {
                    if (appService.getCurrentUser().getRole().equals("ADMIN")) {
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
        loginBox.add(noAccountLbl);
        loginBox.add(regLink);

        panel.add(loginBox);
        return panel;
    }

    private DefaultTableModel tableModel; 

    private JPanel createAdminPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(StyleTheme.BG_DARK);

        JLabel title = new JLabel("ADMIN DASHBOARD");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(StyleTheme.TEXT_MAIN);
        title.setBounds(30, 20, 300, 30);

        String[] col = {"ID", "Title", "Genre", "Price"};
        tableModel = new DefaultTableModel(col, 0);
        JTable table = new JTable(tableModel);
        StyleTheme.styleTable(table); 
        refreshTable();

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(30, 70, 925, 450);
        scroll.getViewport().setBackground(StyleTheme.BG_PANEL);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        JButton addBtn = new StyleTheme.ModernButton("+ ADD NEW GAME", StyleTheme.ACCENT_BLUE, Color.BLACK);
        addBtn.setBounds(750, 20, 200, 35);

        JButton editBtn = new StyleTheme.ModernButton("EDIT SELECTED", Color.ORANGE, Color.BLACK);
        editBtn.setBounds(680, 540, 150, 40);

        JButton deleteBtn = new StyleTheme.ModernButton("DELETE", StyleTheme.ACCENT_RED, Color.WHITE);
        deleteBtn.setBounds(840, 540, 110, 40);

        JButton logoutBtn = new StyleTheme.ModernButton("LOGOUT", Color.GRAY, Color.WHITE);
        logoutBtn.setBounds(30, 540, 120, 40);

        addBtn.addActionListener(e -> new AddGameDialog(this).setVisible(true));

        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row != -1) {
                Game selectedGame = appService.getGames().get(row);
                new AddGameDialog(this, selectedGame).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Pilih game di tabel dulu!");
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row != -1) {
                if(JOptionPane.showConfirmDialog(this, "Hapus game ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    String id = tableModel.getValueAt(row, 0).toString();
                    appService.deleteGame(new Game.Builder().setId(id).build());
                    refreshTable();
                }
            } else JOptionPane.showMessageDialog(this, "Pilih game yang mau dihapus!");
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) { 
                    int row = table.getSelectedRow();
                    if(row != -1) {
                        Game selectedGame = appService.getGames().get(row);
                        new AddGameDialog(MainFrame.this, selectedGame).setVisible(true);
                    }
                }
            }
        });

        logoutBtn.addActionListener(e -> {
            appService.logout();
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
            for(Game g : appService.getGames()){
                tableModel.addRow(new Object[]{g.getId(), g.getTitle(), g.getGenre(), g.getPrice()});
            }
        }
    }

    private JPanel createUserPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(StyleTheme.BG_DARK);

        RegularUser user = (RegularUser) appService.getCurrentUser();

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(StyleTheme.BG_DARK);
        header.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("MINI STEAM");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(StyleTheme.ACCENT_BLUE);

        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightHeader.setBackground(StyleTheme.BG_DARK);

        JLabel balLabel = new JLabel("WALLET: Rp " + String.format("%,.0f", user.getBalance()));
        balLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        balLabel.setForeground(StyleTheme.ACCENT_GREEN);

        JButton topUpBtn = new StyleTheme.ModernButton("+ TOP UP", StyleTheme.ACCENT_BLUE, Color.BLACK);
        topUpBtn.setPreferredSize(new Dimension(90, 30));
        topUpBtn.setFont(new Font("Segoe UI", Font.BOLD, 11));

        rightHeader.add(balLabel);
        rightHeader.add(topUpBtn);
        header.add(title, BorderLayout.WEST);
        header.add(rightHeader, BorderLayout.EAST);

        topUpBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Masukkan Jumlah Top Up (Rp):");
            if (input != null && !input.isEmpty()) {
                try {
                    double amount = Double.parseDouble(input);
                    if (amount <= 0) throw new InputTidakValidException("Jumlah Top Up harus lebih dari 0!");
                    user.setBalance(user.getBalance() + amount);
                    appService.updateUserBalance(user);
                    balLabel.setText("WALLET: Rp " + String.format("%,.0f", user.getBalance()));
                    JOptionPane.showMessageDialog(this, "Top Up Berhasil!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Gagal: " + ex.getMessage());
                }
            }
        });

        JTabbedPane tabbedPane = new JTabbedPane();

        StyleTheme.styleTabbedPane(tabbedPane);
        
        JPanel storePanel = createStoreTab(user, balLabel);
        tabbedPane.addTab(" STORE ", storePanel); 
        
        JPanel libraryPanel = createLibraryTab(user);
        tabbedPane.addTab(" MY LIBRARY ", libraryPanel);

        JPanel wishlistPanel = createWishlistTab(user);
        tabbedPane.addTab(" WISHLIST ", wishlistPanel);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10)); 
        footer.setBackground(StyleTheme.BG_DARK);
        JButton logoutBtn = new StyleTheme.ModernButton("LOGOUT", StyleTheme.ACCENT_RED, Color.WHITE);
        logoutBtn.setPreferredSize(new Dimension(100, 35));
        logoutBtn.addActionListener(e -> {
            appService.logout();
            cardLayout.show(mainPanel, "LOGIN");
        });
        footer.add(logoutBtn);

        tabbedPane.addChangeListener(e -> {
            int idx = tabbedPane.getSelectedIndex();
            if (idx == 1) refreshLibraryTab(libraryPanel, user);
            if (idx == 2) refreshWishlistTab(wishlistPanel, user);
        });

        panel.add(header, BorderLayout.NORTH);
        panel.add(tabbedPane, BorderLayout.CENTER);
        panel.add(footer, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createStoreTab(RegularUser user, JLabel balLabel) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(StyleTheme.BG_DARK);
        
        DefaultListModel<Game> listModel = new DefaultListModel<>();
        for(Game g : appService.getGames()) listModel.addElement(g);
        
        JList<Game> list = new JList<>(listModel);
        list.setBackground(StyleTheme.BG_PANEL);
        list.setForeground(StyleTheme.TEXT_MAIN);
        list.setCellRenderer(createGameRenderer());
        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        btnPanel.setBackground(StyleTheme.BG_PANEL); 
        
        JButton buyBtn = new StyleTheme.ModernButton("BUY GAME", StyleTheme.ACCENT_GREEN, Color.WHITE);
        buyBtn.setPreferredSize(new Dimension(140, 40)); 
        
        JButton wishBtn = new StyleTheme.ModernButton("ADD TO WISHLIST", Color.ORANGE, Color.BLACK);
        wishBtn.setPreferredSize(new Dimension(160, 40));

        buyBtn.addActionListener(e -> {
            Game g = list.getSelectedValue();
            if(g != null) {
                try {
                    if (appService.isGameOwned(user.getId(), g.getId())) throw new GameSudahDimilikiException(g.getTitle());
                    g.purchase(user.getBalance()); 
                    user.setBalance(user.getBalance() - g.getPrice());
                    appService.updateUserBalance(user);
                    appService.addToLibrary(user.getId(), g.getId());
                    balLabel.setText("WALLET: Rp " + String.format("%,.0f", user.getBalance()));
                    JOptionPane.showMessageDialog(this, "Success! Added to Library.");
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
            } else JOptionPane.showMessageDialog(this, "Pilih game dulu!");
        });

        wishBtn.addActionListener(e -> {
            Game g = list.getSelectedValue();
            if (g != null) {
                try {
                    if (appService.isGameOwned(user.getId(), g.getId())) throw new GameSudahDimilikiException(g.getTitle());
                    if (appService.isGameInWishlist(user.getId(), g.getId())) throw new GameSudahDiWishlistException(g.getTitle());
                    appService.addToWishlist(user.getId(), g.getId());
                    JOptionPane.showMessageDialog(this, "Game ditambahkan ke Wishlist!");
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
            } else JOptionPane.showMessageDialog(this, "Pilih game dulu!");
        });

        btnPanel.add(buyBtn);
        btnPanel.add(wishBtn);

        panel.add(scroll, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createWishlistTab(RegularUser user) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(StyleTheme.BG_DARK);
        return panel; 
    }

    private void refreshWishlistTab(JPanel panel, RegularUser user) {
        panel.removeAll();
        panel.setLayout(new BorderLayout());
        panel.setBackground(StyleTheme.BG_DARK);

        DefaultListModel<Game> wishModel = new DefaultListModel<>();
        for(Game g : appService.getUserWishlist(user.getId())) wishModel.addElement(g);
        
        if (wishModel.isEmpty()) {
            JLabel emptyLbl = new JLabel("Wishlist kamu masih kosong.", SwingConstants.CENTER);
            emptyLbl.setForeground(Color.GRAY);
            emptyLbl.setFont(new Font("Segoe UI", Font.ITALIC, 18));
            panel.add(emptyLbl, BorderLayout.CENTER);
        } else {
            JList<Game> list = new JList<>(wishModel);
            list.setBackground(StyleTheme.BG_PANEL);
            list.setForeground(StyleTheme.TEXT_MAIN);
            list.setCellRenderer(createGameRenderer());
            JScrollPane scroll = new JScrollPane(list);
            scroll.setBorder(BorderFactory.createEmptyBorder());
            
            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
            btnPanel.setBackground(StyleTheme.BG_PANEL);

            JButton buyBtn = new StyleTheme.ModernButton("BUY NOW", StyleTheme.ACCENT_GREEN, Color.WHITE);
            buyBtn.setPreferredSize(new Dimension(130, 40));

            JButton removeBtn = new StyleTheme.ModernButton("REMOVE", StyleTheme.ACCENT_RED, Color.WHITE);
            removeBtn.setPreferredSize(new Dimension(130, 40));
            
            buyBtn.addActionListener(e -> {
                Game g = list.getSelectedValue();
                if(g != null) {
                    try {
                        if (appService.isGameOwned(user.getId(), g.getId())) {
                            appService.removeFromWishlist(user.getId(), g.getId());
                            refreshWishlistTab(panel, user);
                            throw new GameSudahDimilikiException(g.getTitle());
                        }
                        g.purchase(user.getBalance()); 
                        user.setBalance(user.getBalance() - g.getPrice());
                        appService.updateUserBalance(user);       
                        appService.addToLibrary(user.getId(), g.getId()); 
                        appService.removeFromWishlist(user.getId(), g.getId());
                        JOptionPane.showMessageDialog(this, "Berhasil membeli " + g.getTitle() + "!");
                        refreshWishlistTab(panel, user); 
                    } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
                } else JOptionPane.showMessageDialog(this, "Pilih game dulu!");
            });
            removeBtn.addActionListener(e -> {
                Game g = list.getSelectedValue();
                if(g != null && JOptionPane.showConfirmDialog(this, "Hapus " + g.getTitle() + "?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    appService.removeFromWishlist(user.getId(), g.getId());
                    refreshWishlistTab(panel, user);
                } else if (g == null) JOptionPane.showMessageDialog(this, "Pilih game dulu!");
            });

            btnPanel.add(buyBtn);
            btnPanel.add(removeBtn);
            panel.add(scroll, BorderLayout.CENTER);
            panel.add(btnPanel, BorderLayout.SOUTH);
        }
        panel.revalidate(); 
        panel.repaint();
    }

    private JPanel createLibraryTab(RegularUser user) { return new JPanel(); }

    private void refreshLibraryTab(JPanel panel, RegularUser user) {
        panel.removeAll(); 
        panel.setLayout(new BorderLayout());
        panel.setBackground(StyleTheme.BG_DARK);
        
        DefaultListModel<Game> libModel = new DefaultListModel<>();
        for(Game g : appService.getUserLibrary(user.getId())) libModel.addElement(g);
        
        if (libModel.isEmpty()) {
            JLabel emptyLbl = new JLabel("Belum ada game di Library. Yuk beli di Store!", SwingConstants.CENTER);
            emptyLbl.setForeground(Color.GRAY);
            emptyLbl.setFont(new Font("Segoe UI", Font.ITALIC, 18));
            panel.add(emptyLbl, BorderLayout.CENTER);
        } else {
            JList<Game> list = new JList<>(libModel);
            list.setBackground(StyleTheme.BG_PANEL);
            list.setForeground(StyleTheme.TEXT_MAIN);
            list.setCellRenderer(createGameRenderer());
            JScrollPane scroll = new JScrollPane(list);
            scroll.setBorder(BorderFactory.createEmptyBorder());

            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
            btnPanel.setBackground(StyleTheme.BG_PANEL);

            JButton playBtn = new StyleTheme.ModernButton("PLAY GAME", StyleTheme.ACCENT_BLUE, Color.BLACK);
            playBtn.setPreferredSize(new Dimension(180, 45)); 
            playBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            
            playBtn.addActionListener(e -> {
                Game g = list.getSelectedValue();
                if(g != null) JOptionPane.showMessageDialog(this, "Launching " + g.getTitle() + "...\nHave Fun!");
                else JOptionPane.showMessageDialog(this, "Pilih game yang mau dimainkan!");
            });

            btnPanel.add(playBtn);
            panel.add(scroll, BorderLayout.CENTER);
            panel.add(btnPanel, BorderLayout.SOUTH);
        }
        panel.revalidate(); 
        panel.repaint();
    }

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
                if (isSelected) {
                    label.setBackground(StyleTheme.ACCENT_BLUE);
                    label.setForeground(Color.BLACK);
                } else {
                    label.setBackground(StyleTheme.BG_PANEL);
                    label.setForeground(StyleTheme.TEXT_MAIN);
                }
                return label;
            }
        };
    }
}