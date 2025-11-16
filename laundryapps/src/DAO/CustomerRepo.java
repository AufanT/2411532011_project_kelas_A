package src.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import src.confg.Database;
import src.model.Customer;
import src.model.CustomerBuilder;

public class CustomerRepo implements CustomerDAO {
    private Connection connection;

    private final String insert = "INSERT INTO customer (nama, email, alamat, telepon) VALUES (?, ?, ?, ?)";
    private final String select = "SELECT * FROM customer";
    private final String update = "UPDATE customer SET nama=?, email=?, alamat=?, telepon=? WHERE id=?";
    private final String delete = "DELETE FROM customer WHERE id=?";

    public CustomerRepo() {
        connection = Database.koneksi();
    }

    @Override
    public void save(Customer customer) {
        PreparedStatement st = null;
        try {
            if (connection == null || connection.isClosed()) {
                connection = Database.koneksi();
            }

            st = connection.prepareStatement(insert);
            st.setString(1, customer.getNama());     
            st.setString(2, customer.getEmail());     
            st.setString(3, customer.getAlamat());
            st.setString(4, customer.getTelepon());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Customer customer) {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(update);
            st.setString(1, customer.getNama());
            st.setString(2, customer.getEmail());
            st.setString(3, customer.getAlamat());
            st.setString(4, customer.getTelepon());
            st.setString(5, customer.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void delete(String id) {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(delete);
            st.setString(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Customer> show() {
        List<Customer> ls = new ArrayList<Customer>();
        Statement st = null;
        ResultSet rs = null;
        
        try {
            if (connection == null || connection.isClosed()) {
                connection = Database.koneksi();
            }
            
            st = connection.createStatement();
            rs = st.executeQuery(select);
            
            while(rs.next()) {
                Customer cs = new CustomerBuilder()
                    .setId(rs.getString("id"))
                    .setNama(rs.getString("nama"))
                    .setEmail(rs.getString("email"))
                    .setAlamat(rs.getString("alamat"))
                    .setTelepon(rs.getString("telepon"))
                    .build();
                ls.add(cs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ls;
    }
}