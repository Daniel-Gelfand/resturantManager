package hit.projects.resturantmanager.repository;

import hit.projects.resturantmanager.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, Long> {


}


