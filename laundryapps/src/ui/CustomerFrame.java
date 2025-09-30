package src.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import src.DAO.CustomerRepo;
import src.model.Customer;
import src.table.TableCustomer;

public class CustomerFrame extends JFrame {

    private JTextField txtName;
    private JTextField txtUsername;
    private JTextField txtPassword;
    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnCancel;
    private JTable tableUsers;

    CustomerRepo cst = new CustomerRepo();
    List<Customer> ls;
    public String id;

    public void reset() {
        txtName.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
    }

    public void loadTable() {
        ls = cst.show(); 
        TableCustomer tu = new TableCustomer(ls); 
        tableUsers.setModel(tu); 
        tableUsers.getTableHeader().setVisible(true); 
    }

    public CustomerFrame() {
        setTitle("Customer Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        add(panel); 

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(30, 30, 80, 25);
        panel.add(lblName);

        txtName = new JTextField();
        txtName.setBounds(120, 30, 200, 25);
        panel.add(txtName);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(30, 70, 80, 25);
        panel.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(120, 70, 200, 25);
        panel.add(txtUsername);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(30, 110, 80, 25);
        panel.add(lblPassword);

        txtPassword = new JTextField();
        txtPassword.setBounds(120, 110, 200, 25);
        panel.add(txtPassword);

        txtUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                String username = txtUsername.getText();
                String formattedUsername = username.toLowerCase().replaceAll("\\s+", "");
                txtUsername.setText(formattedUsername);
            }
        });
        
        // Tombol
        btnSave = new JButton("Save");
        btnSave.setBounds(120, 150, 80, 30);
        panel.add(btnSave);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(210, 150, 80, 30);
        panel.add(btnUpdate);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(300, 150, 80, 30);
        panel.add(btnDelete);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(390, 150, 80, 30);
        panel.add(btnCancel);

        // Tabel Customers
        tableUsers = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableUsers);
        scrollPane.setBounds(30, 200, 520, 150);
        panel.add(scrollPane);


        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Customer customer = new Customer(); 
                customer.setNama(txtName.getText()); 
                customer.setUsername(txtUsername.getText()); 
                customer.setPassword(txtPassword.getText()); 

                // validasi password
                String password = txtPassword.getText();
                if (password.length() < 6) {
                    JOptionPane.showMessageDialog(null, "Password harus memiliki minimal 6 karakter.", "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
                    return; 
                }
                
                cst.save(customer); 
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
                Customer customer = new Customer(); 
                customer.setNama(txtName.getText());

                String username = txtUsername.getText().toLowerCase().replaceAll("\\s+", "");
                customer.setUsername(username); 
                
                customer.setUsername(txtUsername.getText()); 
                customer.setPassword(txtPassword.getText()); 
                customer.setId(id); 
                cst.update(customer); 
                reset(); 
                loadTable(); 
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (id != null) {
                    cst.delete(id);
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
            CustomerFrame frame = new CustomerFrame(); 
            frame.setVisible(true);
            frame.loadTable();
        });
    }
}