package src.DAO;

import java.util.List;
import src.model.Customer;

public interface CustomerDAO {
    void save(Customer customer);
    public List<Customer> show();
    public void update(Customer customer);
    public void delete(String id);
}