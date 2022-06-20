package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.pojo.Order;
import hit.projects.resturantmanager.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired private OrderRepository orderRepository;


    @Override
    public List<Order> getAllOrders() {
        return null;
    }

    @Override
    public Order getOrder(int orderId) {
        return null;
    }

    @Override
    public Order addOrder(Order newOrder) {
        return null;
    }

    @Override
    public Order updateOrder(int orderId,Order updateOrder) {
        return null;
    }

    @Override
    public void deleteOrder(int orderId) {
        orderRepository.getOrderByBillGreaterThan(30);
    }
}
