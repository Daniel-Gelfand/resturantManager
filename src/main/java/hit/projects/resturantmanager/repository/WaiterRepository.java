package hit.projects.resturantmanager.repository;

import hit.projects.resturantmanager.entity.Waiter;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WaiterRepository extends MongoRepository<Waiter, Long> {
    Optional<Waiter> findByPersonalId(int personalId);
}
