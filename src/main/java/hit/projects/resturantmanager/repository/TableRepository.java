package hit.projects.resturantmanager.repository;

import hit.projects.resturantmanager.pojo.Table;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TableRepository extends MongoRepository<Table, String> {
    Optional<Table> getTableByTableNumber(int tableNumber);

    boolean existsByTableNumber(int tableNumber);

    void deleteByTableNumber(int tableNumber);
}
