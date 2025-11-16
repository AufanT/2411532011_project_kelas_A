package src.ui;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import src.DAO.CustomerRepo;
import src.model.Customer;
import src.table.TableCustomer;
import src.model.CustomerBuilder;

public class CustomerFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtCustomerName;
    private JTextField txtCustomerEmail;
    private JTextField txtCustomerAlamat;
    private JTextField txtCustomerHp;
    private JTable tableCustomers;

    public String id;
    List<Customer> ls;
    CustomerRepo customerRepo = new CustomerRepo();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CustomerFrame frame = new CustomerFrame();
                    frame.setVisible(true);
                    frame.loadTable();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public void loadTable() {
        ls = customerRepo.show();
        TableCustomer tc = new TableCustomer(ls);
        tableCustomers.setModel(tc);
        tableCustomers.getTableHeader().setVisible(true);
        tableCustomers.revalidate();
        tableCustomers.repaint();
    }

    public void reset() {
        txtCustomerName.setText("");
        txtCustomerEmail.setText("");
        txtCustomerAlamat.setText("");
        txtCustomerHp.setText("");
        id = null;
    }

    public CustomerFrame() {
        setTitle("Data Pelanggan - Laundry Apps");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 550);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(245, 245, 245));
        setContentPane(contentPane);

        JLabel titleLabel = new JLabel("PELANGGAN");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setBounds(250, 20, 150, 30);
        contentPane.add(titleLabel);

        JLabel lblName = new JLabel("Nama");
        lblName.setBounds(50, 70, 80, 25);
        contentPane.add(lblName);

        txtCustomerName = new JTextField();
        txtCustomerName.setBounds(140, 70, 200, 25);
        contentPane.add(txtCustomerName);

        JLabel lblAlamat = new JLabel("Alamat");
        lblAlamat.setBounds(50, 105, 80, 25);
        contentPane.add(lblAlamat);

        txtCustomerAlamat = new JTextField();
        txtCustomerAlamat.setBounds(140, 105, 200, 25);
        contentPane.add(txtCustomerAlamat);

        JLabel lblHp = new JLabel("No HP");
        lblHp.setBounds(50, 140, 80, 25);
        contentPane.add(lblHp);

        txtCustomerHp = new JTextField();
        txtCustomerHp.setBounds(140, 140, 200, 25);
        contentPane.add(txtCustomerHp);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setBounds(50, 175, 80, 25);
        contentPane.add(lblEmail);

        txtCustomerEmail = new JTextField();
        txtCustomerEmail.setBounds(140, 175, 200, 25);
        contentPane.add(txtCustomerEmail);

        JButton btnSave = new JButton("Simpan");
        btnSave.setBounds(140, 220, 100, 30);
        btnSave.setBackground(new Color(70, 130, 180));
        btnSave.setForeground(Color.WHITE);
        contentPane.add(btnSave);

        JButton btnCancel = new JButton("Batal");
        btnCancel.setBounds(250, 220, 100, 30);
        btnCancel.addActionListener(e -> reset());
        contentPane.add(btnCancel);

        JSeparator separator = new JSeparator();
        separator.setBounds(50, 270, 500, 2);
        separator.setForeground(Color.GRAY);
        contentPane.add(separator);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(50, 290, 500, 180);
        contentPane.add(scrollPane);

        tableCustomers = new JTable();
        scrollPane.setViewportView(tableCustomers);

        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (txtCustomerName.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Nama harus diisi!");
                        return;
                    }
                    
                    Customer customer = new CustomerBuilder()
                        .setNama(txtCustomerName.getText())
                        .setEmail(txtCustomerEmail.getText())
                        .setAlamat(txtCustomerAlamat.getText())
                        .setTelepon(txtCustomerHp.getText())
                        .build();
                    
                    customerRepo.save(customer);
                    reset();
                    loadTable();
                    JOptionPane.showMessageDialog(null, "Data pelanggan berhasil disimpan!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

        tableCustomers.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableCustomers.getSelectedRow();
                if (row >= 0) {
                    id = ls.get(row).getId();
                    txtCustomerName.setText(ls.get(row).getNama());
                    txtCustomerAlamat.setText(ls.get(row).getAlamat());
                    txtCustomerHp.setText(ls.get(row).getTelepon());
                    txtCustomerEmail.setText(ls.get(row).getEmail());
                    
                    String[] options = {"Edit", "Hapus", "Batal"};
                    int choice = JOptionPane.showOptionDialog(null, 
                        "Pilih aksi untuk data ini:", "Aksi Data",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null, options, options[0]);
                    
                    if (choice == 0) {
                    } else if (choice == 1) {
                        int confirm = JOptionPane.showConfirmDialog(null, 
                            "Apakah yakin ingin menghapus data ini?", "Konfirmasi Hapus",
                            JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            customerRepo.delete(id);
                            reset();
                            loadTable();
                            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
                        }
                    }
                }
            }
        });
        
        loadTable();
    }
}