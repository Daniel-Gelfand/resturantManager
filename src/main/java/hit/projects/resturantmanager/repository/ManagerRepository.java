package hit.projects.resturantmanager.repository;

import hit.projects.resturantmanager.entity.Manager;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ManagerRepository extends MongoRepository<Manager, Long> {

}
