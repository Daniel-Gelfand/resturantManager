package hit.projects.resturantmanager.repository;

import hit.projects.resturantmanager.pojo.Order;
import hit.projects.resturantmanager.pojo.Table;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TableRepository extends MongoRepository<Table, String> {
    Table getTableByTableNumber(int tableNumber);
}
