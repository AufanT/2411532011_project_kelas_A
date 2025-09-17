package ui;

import javax.swing.*;

public class UserFrame extends JFrame {

    // Deklarasi komponen
    private JTextField txtName;
    private JTextField txtUsername;
    private JTextField txtPassword;
    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnCancel;
    private JTable tableUsers;

    public UserFrame() {
        setTitle("User Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        setLayout(null);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(30, 30, 80, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(120, 30, 200, 25);
        add(txtName);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(30, 70, 80, 25);
        add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(120, 70, 200, 25);
        add(txtUsername);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(30, 110, 80, 25);
        add(lblPassword);

        txtPassword = new JTextField();
        txtPassword.setBounds(120, 110, 200, 25);
        add(txtPassword);

        // Tombol
        btnSave = new JButton("Save");
        btnSave.setBounds(120, 150, 80, 30);
        add(btnSave);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(210, 150, 80, 30);
        add(btnUpdate);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(300, 150, 80, 30);
        add(btnDelete);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(390, 150, 80, 30);
        add(btnCancel);

        // Tabel Users
        tableUsers = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableUsers);
        scrollPane.setBounds(30, 200, 520, 150);
        add(scrollPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new UserFrame().setVisible(true);
        });
    }
}
