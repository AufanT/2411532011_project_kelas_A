package src.DAO;

import java.util.List;
import src.model.Service;

public interface ServiceDAO {
    void save(Service service);
    public List<Service> show();
    public void update(Service service);
    public void delete(String id);
}
