package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.pojo.Order;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface OrderService {

    CollectionModel<EntityModel<Order>> getOrderReportByDates(LocalDateTime startDate, LocalDateTime endDate);

    CollectionModel<EntityModel<Order>> getAllOrders();

    EntityModel<Order> getOrder(int orderId);

    EntityModel<Order> addOrder (Order newOrder);

    EntityModel<Order> updateOrder(int orderId,Order updateOrder);

    void deleteOrder(int orderId);

    void addMenuItem(int orderNumber, String menuItemName, int count);


}
