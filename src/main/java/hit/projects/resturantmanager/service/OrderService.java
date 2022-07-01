package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.pojo.Order;
import hit.projects.resturantmanager.pojo.dto2.OrderDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.time.LocalDateTime;

public interface OrderService {

    CollectionModel<EntityModel<Order>> getOrderReportByDates(LocalDateTime startDate, LocalDateTime endDate);

    CollectionModel<EntityModel<Order>> getAllOrders();

    EntityModel<Order> getOrder(int orderId);

    EntityModel<Order> addOrder(Order newOrder);

    EntityModel<Order> updateOrder(int orderId, Order updateOrder);

    void deleteOrder(int orderId);

    void addMenuItem(int orderNumber, String menuItemName, int count);

    EntityModel<OrderDTO> getOrderDTO(int name);

    CollectionModel<EntityModel<OrderDTO>> getAllOrdersDTO();
}
