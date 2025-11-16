package src.DAO;

import java.util.List;
import src.model.Customer;

public interface CustomerDAO {
    public void save(Customer customer);
    public void update(Customer customer);
    public void delete(String id);
    public List<Customer> show();
}