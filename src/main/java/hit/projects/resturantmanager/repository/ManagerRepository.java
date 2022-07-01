package hit.projects.resturantmanager.repository;

import hit.projects.resturantmanager.pojo.Manager;
import hit.projects.resturantmanager.pojo.Waiter;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ManagerRepository extends MongoRepository<Manager, Integer> {

    Optional<Manager> getManagerByPersonalId(int personalId);

    List<Manager> getAllByOnDuty(boolean onDuty);

    boolean existsByPersonalId(int personalId);
}
