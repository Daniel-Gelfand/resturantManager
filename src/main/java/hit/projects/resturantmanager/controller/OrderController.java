package hit.projects.resturantmanager.controller;

import hit.projects.resturantmanager.configuration.MongoConfiguration;
import hit.projects.resturantmanager.pojo.Order;
import hit.projects.resturantmanager.pojo.dto.OrderDTO;
import hit.projects.resturantmanager.service.OrderService;
import hit.projects.resturantmanager.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;


@RequestMapping("/order")
@RestController
public class OrderController {

    private final OrderService orderService;


    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * This method return specific order by order number.
     * @param orderNumber -> Expect to order number of exist specific order.
     * @return status ok(200) if the order is really exist or trow new Restaurant Exception.
     */
    @GetMapping("/{orderNumber}")
    public ResponseEntity<EntityModel<Order>> getOrder(@PathVariable int orderNumber) {
        return ResponseEntity.ok().body(orderService.getOrder(orderNumber));
    }

    /**
     * In this method we except to get all the order that have been in the restaurant.
     * @return status ok(200) with all the orders from DB.
     */
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Order>>> getAllOrders() {
        return ResponseEntity.ok().body(orderService.getAllOrders());
    }

    /**
     * In this method we return order list by user selection
     * @param startYear -> Except to get valid year something like 1995
     * @param startMonth -> Except to get valid month something like 5
     * @param startDay -> Except to get valid day something like 30
     * @param endYear -> Except to get valid year something like 1995
     * @param endMonth -> Except to get valid month something  like 5
     * @param endDay -> Except to get valid day something like 30
     * @return if the details are ok the method return status 200 else trow Restaurant Exception
     */
    @GetMapping("/report")
    public ResponseEntity<CollectionModel<EntityModel<Order>>> getReportByDates(@RequestParam int startYear, @RequestParam int startMonth, @RequestParam int startDay, @RequestParam int endYear, @RequestParam int endMonth, @RequestParam int endDay) {
        return ResponseEntity.ok().body(orderService.getOrderReportByDates(LocalDateTime.of(startYear, startMonth, startDay, 0, 0), LocalDateTime.of(endYear, endMonth, endDay, 23, 59)));
    }

    /**
     * In this method we return order DTO by order number
     * @param orderNumber Except to get exist order number else trow Restaurant Exception.
     * @return status ok(200) and the order DTO Entity Model.
     */
    @GetMapping("/{orderNumber}/info")
    public ResponseEntity<EntityModel<OrderDTO>> getOrderInfo(@PathVariable int orderNumber) {
        return ResponseEntity.ok().body(orderService.getOrderDTO(orderNumber));
    }

    /**
     * In this method we except to get all the order that have been in the restaurant in DTO shape.
     * @return status ok(200) with all the orders DTO collection.
     */
    @GetMapping("/info")
    public ResponseEntity<CollectionModel<EntityModel<OrderDTO>>> getAllOrdersInfo() {
        return ResponseEntity.ok().body(orderService.getAllOrdersDTO());
    }

    /**
     *
     * @param newOrder Except to get json with valid Order object properties.
     * @return status created(201) if everything going well.
     */
    @PostMapping
    public ResponseEntity<EntityModel<Order>> addOrder(@RequestBody Order newOrder) {
        return new ResponseEntity<>(orderService.addOrder(newOrder), HttpStatus.CREATED);
    }

    /**
     *
     * @param orderId
     * @param updateOrder
     * @return
     */
    @PutMapping("/{orderId}")
    public ResponseEntity<EntityModel<Order>> updateOrder(@PathVariable int orderId, @RequestBody Order updateOrder) {
        return ResponseEntity.ok().body(orderService.updateOrder(orderId, updateOrder));
    }

    /**
     * This method able to user add menu item to order list in specific order.
     *
     * @param orderId ->Except to get exist order number.
     * @param name ->Except to get exist MenuItem name.
     * @param count ->Except to the count of items you want to add into orderList.
     * @return status ok(200) if everything going well.
      */
    @PutMapping("/{orderId}/add/menuItem")
    public ResponseEntity<?> addMenuItemToOrderList(@PathVariable int orderId, @RequestParam String name, @RequestParam int count) {
        return ResponseEntity.ok().body(orderService.addMenuItem(orderId, name, count));
    }

    /**
     * This method able to delete specific user by order  number
     * @param orderId -> Except to get exist order number.
     * @return status deleted(202) if everything going well.
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable int orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.status(202).build();
    }
}
