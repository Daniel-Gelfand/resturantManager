package hit.projects.resturantmanager.repository;

import hit.projects.resturantmanager.pojo.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {
    Optional<Order> getOrderByOrderNumber(int number);
    List<Order> getAllByOrderDateGreaterThan(LocalDateTime startDate);
    List<Order> getAllByOrderDateLessThan(LocalDateTime startDate);
    List<Order> getOrderByBillGreaterThan(double bill);
}


