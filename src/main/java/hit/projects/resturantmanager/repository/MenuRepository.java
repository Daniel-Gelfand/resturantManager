package hit.projects.resturantmanager.repository;

import hit.projects.resturantmanager.entity.MenuItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MenuRepository extends MongoRepository<MenuItem,Long> {
}
