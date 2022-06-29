package hit.projects.resturantmanager.repository;

import hit.projects.resturantmanager.pojo.Manager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.hateoas.EntityModel;

import java.util.Optional;

public interface ManagerRepository extends MongoRepository<Manager, Integer> {

    Optional<Manager> getManagerByPersonalId(int personalId);

}
