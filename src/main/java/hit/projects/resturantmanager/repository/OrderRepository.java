package hit.projects.resturantmanager.repository;

import hit.projects.resturantmanager.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {

}
