package src.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import src.DAO.ServiceRepo;
import src.model.Service;   
import src.table.TableService; 

public class ServiceFrame extends JFrame {

    private JTextField txtJenis;
    private JTextField txtHarga;
    private JTextField txtStatus;
    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnCancel;
    private JTable tableServices;

    ServiceRepo serviceRepo = new ServiceRepo();
    List<Service> serviceList;
    public String id;

    public void reset() {
        txtJenis.setText("");
        txtHarga.setText("");
        txtStatus.setText("");
    }

    public void loadTable() {
        serviceList = serviceRepo.show();
        TableService ts = new TableService(serviceList);
        tableServices.setModel(ts);
        tableServices.getTableHeader().setVisible(true);
    }

    public ServiceFrame() {
        setTitle("Manajemen Layanan");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(null);

        // Label dan TextField untuk Jenis Layanan
        JLabel lblJenis = new JLabel("Jenis:");
        lblJenis.setBounds(30, 30, 80, 25);
        add(lblJenis);
        txtJenis = new JTextField();
        txtJenis.setBounds(120, 30, 200, 25);
        add(txtJenis);

        // Label dan TextField untuk Harga
        JLabel lblHarga = new JLabel("Harga:");
        lblHarga.setBounds(30, 70, 80, 25);
        add(lblHarga);
        txtHarga = new JTextField();
        txtHarga.setBounds(120, 70, 200, 25);
        add(txtHarga);

        // Label dan TextField untuk Status
        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setBounds(30, 110, 80, 25);
        add(lblStatus);
        txtStatus = new JTextField();
        txtStatus.setBounds(120, 110, 200, 25);
        add(txtStatus);

        // Tombol-tombol
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

        // Tabel untuk menampilkan data
        tableServices = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableServices);
        scrollPane.setBounds(30, 200, 520, 150);
        add(scrollPane);

        // Action Listener untuk tombol Save
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Service service = new Service();
                service.setJenis(txtJenis.getText());
                service.setHarga(txtHarga.getText());
                service.setStatus(txtStatus.getText());
                serviceRepo.save(service);
                reset();
                loadTable();
            }
        });

        // Mouse Listener untuk Tabel
        tableServices.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tableServices.getSelectedRow();
                id = tableServices.getValueAt(selectedRow, 0).toString();
                txtJenis.setText(tableServices.getValueAt(selectedRow, 1).toString());
                txtHarga.setText(tableServices.getValueAt(selectedRow, 2).toString());
                txtStatus.setText(tableServices.getValueAt(selectedRow, 3).toString());
            }
        });

        // Action Listener untuk tombol Update
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Service service = new Service();
                service.setJenis(txtJenis.getText());
                service.setHarga(txtHarga.getText());
                service.setStatus(txtStatus.getText());
                service.setId(id); // Jangan lupa sertakan ID untuk update
                serviceRepo.update(service);
                reset();
                loadTable();
            }
        });

        // Action Listener untuk tombol Delete
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (id != null) {
                    serviceRepo.delete(id);
                    reset();
                    loadTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Silakan pilih data yang akan dihapus");
                }
            }
        });

        // Action Listener untuk tombol Cancel
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
    }

    // Main method untuk testing (opsional)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ServiceFrame frame = new ServiceFrame();
            frame.setVisible(true);
            frame.loadTable();
        });
    }
}