package hit.projects.resturantmanager.controller;

import hit.projects.resturantmanager.pojo.Order;
import hit.projects.resturantmanager.pojo.dto2.OrderDTO;
import hit.projects.resturantmanager.service.OrderService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RequestMapping("/order")
@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<EntityModel<Order>> getOrder(@PathVariable int orderNumber) {
        return ResponseEntity.ok().body(orderService.getOrder(orderNumber));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Order>>> getAllOrders(){
        return ResponseEntity.ok().body(orderService.getAllOrders());
    }

    @GetMapping("/report")
    public ResponseEntity<CollectionModel<EntityModel<Order>>> getReportByDates(@RequestParam int startYear,@RequestParam int startMonth,@RequestParam int startDay,@RequestParam int endYear,@RequestParam int endMonth,@RequestParam int endDay) {
        return ResponseEntity.ok().body(orderService.getOrderReportByDates(LocalDateTime.of(startYear,startMonth,startDay,0,0), LocalDateTime.of(endYear,endMonth,endDay,23,59)));
    }


    @PostMapping
    public ResponseEntity<EntityModel<Order>> addOrder(@RequestBody Order newOrder){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.addOrder(newOrder));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<EntityModel<Order>> updateOrder(@PathVariable int orderId, @RequestBody Order updateOrder){
        return ResponseEntity.ok().body(orderService.updateOrder(orderId,updateOrder));
    }

    @PutMapping("/{orderId}/add/menuItem")
    public ResponseEntity<?> addMenuItemToOrderList(@PathVariable int orderId, @RequestParam String name, @RequestParam int count) {
        orderService.addMenuItem(orderId, name, count);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{orderId}")
    //TODO: CHANHGE TO RESPONSEENTITY
    public ResponseEntity<?> deleteOrder(@PathVariable int orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.status(202).build();

    }

    @GetMapping("/{orderNumber}/info")
    public ResponseEntity<EntityModel<OrderDTO>> getOrderInfo(@PathVariable int orderNumber) {
        return ResponseEntity.ok().body(orderService.getOrderDTO(orderNumber));
    }

    @GetMapping("/info")
    public ResponseEntity<CollectionModel<EntityModel<OrderDTO>>> getAllOrdersInfo() {
        return ResponseEntity.ok().body(orderService.getAllOrdersDTO());
    }


}
