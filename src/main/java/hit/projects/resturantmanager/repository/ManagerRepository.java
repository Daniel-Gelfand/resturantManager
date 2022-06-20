package hit.projects.resturantmanager.repository;

import hit.projects.resturantmanager.pojo.Manager;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ManagerRepository extends MongoRepository<Manager, Long> {

}
