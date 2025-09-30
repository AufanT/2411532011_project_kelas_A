package src.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import src.confg.Database;
import src.model.Customer;

public class CustomerRepo implements CustomerDAO {
    private Connection connection;

    final String insert = "INSERT INTO customer (nama, alamat, telepon) VALUES (?,?,?);";
    final String select = "SELECT * FROM customer;";
    final String update = "UPDATE customer SET nama=?, alamat=?, telepon=? WHERE id=?;";
    final String delete = "DELETE FROM customer WHERE id=?;";

    public CustomerRepo() {
        connection = Database.koneksi();
    }

    @Override
    public void save(Customer customer) {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(insert);
            st.setString(1, customer.getNama());      
            st.setString(2, customer.getAlamat());
            st.setString(3, customer.getTelepon());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Customer> show() {
        List<Customer> ls=null;
		try {
			ls = new ArrayList<Customer>();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(select);
			while(rs.next()) {
				Customer customer = new Customer();
				customer.setId(rs.getString("id"));
				customer.setNama(rs.getString("name"));
				customer.setUsername(rs.getString("username"));
				customer.setPassword(rs.getString("password"));
				ls.add(customer);
				
			}
		} catch (SQLException e) {
			Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null,e);
		}
		return ls;
    }

    @Override
    public void update(Customer customer) {
        PreparedStatement st = null;
	    try {
	        st = connection.prepareStatement(update);
	        st.setString(1, customer.getNama());
	        st.setString(2, customer.getUsername());
	        st.setString(3, customer.getPassword());
	        st.setString(4, customer.getId());
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
}