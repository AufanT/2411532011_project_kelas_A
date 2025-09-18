package src.ui;

// Impor yang diperlukan untuk event, list, model, dao, dan tabel
import src.DAO.UserRepo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import src.model.User;
import src.table.TableUser;

public class UserFrame extends JFrame {

    private JTextField txtName;
    private JTextField txtUsername;
    private JTextField txtPassword;
    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnCancel;
    private JTable tableUsers;

    UserRepo usr = new UserRepo();
    List<User> ls;
    public String id;

    public void reset() {
        txtName.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
    }

    public void loadTable() {
        ls = usr.show(); 
        TableUser tu = new TableUser(ls); 
        tableUsers.setModel(tu); 
        tableUsers.getTableHeader().setVisible(true); 
    }

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


        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                User user = new User(); 
                user.setNama(txtName.getText()); 
                user.setUsername(txtUsername.getText()); 
                user.setPassword(txtPassword.getText()); 
                usr.save(user); 
                reset(); 
                loadTable(); 
            }
        });

        tableUsers.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                id = tableUsers.getValueAt(tableUsers.getSelectedRow(), 0).toString(); 
                txtName.setText(tableUsers.getValueAt(tableUsers.getSelectedRow(), 1).toString()); 
                txtUsername.setText(tableUsers.getValueAt(tableUsers.getSelectedRow(), 2).toString());
                txtPassword.setText(tableUsers.getValueAt(tableUsers.getSelectedRow(), 3).toString());
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                User user = new User(); 
                user.setNama(txtName.getText());
                user.setUsername(txtUsername.getText()); 
                user.setPassword(txtPassword.getText()); 
                user.setId(id); 
                usr.update(user); 
                reset(); 
                loadTable(); 
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (id != null) {
                    usr.delete(id);
                    reset();
                    loadTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Silahkan pilih data yang akan di hapus");
                }
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserFrame frame = new UserFrame(); 
            frame.setVisible(true);
            frame.loadTable();
        });
    }
}