package hit.projects.resturantmanager.repository;

import hit.projects.resturantmanager.entity.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MenuRepository extends MongoRepository<Menu,Long> {
}
