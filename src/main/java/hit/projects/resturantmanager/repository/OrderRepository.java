package hit.projects.resturantmanager.repository;

import hit.projects.resturantmanager.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, Long> {
    Order getOrderByOrderNumber();

    List<Order> getOrderByBillGreaterThan(double bill);
}


