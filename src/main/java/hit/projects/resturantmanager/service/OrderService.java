package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.pojo.Order;
import hit.projects.resturantmanager.pojo.dto.OrderDTO;
import hit.projects.resturantmanager.pojo.response.BitcoinResponseEntity;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface OrderService {

    CollectionModel<EntityModel<Order>> getOrderReportByDates(LocalDateTime startDate, LocalDateTime endDate);

    CollectionModel<EntityModel<Order>> getAllOrders();

    EntityModel<Order> getOrder(int orderId);

    EntityModel<Order> addOrder(Order newOrder);

    EntityModel<Order> updateOrder(int orderId, Order updateOrder);

    void deleteOrder(int orderId);

    EntityModel<Order> addMenuItem(int orderNumber, String menuItemName, int count);

    EntityModel<OrderDTO> getOrderDTO(int name);

    CollectionModel<EntityModel<OrderDTO>> getAllOrdersDTO();
}
