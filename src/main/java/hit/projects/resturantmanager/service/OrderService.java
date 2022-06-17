package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.entity.Order;
import hit.projects.resturantmanager.entity.Waiter;

import java.util.List;

public interface OrderService {


    List<Order> getAllOrders();

    Order getOrder(int orderId);

    Order addOrder (Order newOrder);

    Order updateOrder(Order updateOrder);

    void deleteOrder(int orderId);


}
