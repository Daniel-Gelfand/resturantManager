package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.pojo.Order;

import java.util.List;

public interface OrderService {


    List<Order> getAllOrders();

    Order getOrder(int orderId);

    Order addOrder (Order newOrder);

    Order updateOrder(int orderId,Order updateOrder);

    void deleteOrder(int orderId);


}
