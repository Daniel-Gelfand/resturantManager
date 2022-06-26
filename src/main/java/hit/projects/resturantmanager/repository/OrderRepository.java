package hit.projects.resturantmanager.repository;

import hit.projects.resturantmanager.pojo.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    Order getOrderByOrderNumber(int number);

    List<Order> getOrderByBillGreaterThan(double bill);
}


