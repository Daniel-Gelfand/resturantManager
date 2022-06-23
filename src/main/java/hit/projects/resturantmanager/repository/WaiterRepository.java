package hit.projects.resturantmanager.repository;

import hit.projects.resturantmanager.pojo.Waiter;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface WaiterRepository extends MongoRepository<Waiter, Long> {

    Optional<Waiter> findByPersonalId(int personalId);
    void deleteByPersonalId(int personalId);
    List<Waiter> findAllByOnDuty(boolean onDuty);
    List<Waiter> getAllByOnDuty(boolean onDuty);
    boolean existsByPersonalId(int personalId);
}
