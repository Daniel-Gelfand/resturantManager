package hit.projects.resturantmanager.repository;

import hit.projects.resturantmanager.entity.Waiter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WaiterRepository extends MongoRepository<Waiter, Long> {

}
