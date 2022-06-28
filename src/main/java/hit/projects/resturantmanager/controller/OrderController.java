package hit.projects.resturantmanager.controller;

import hit.projects.resturantmanager.pojo.Order;
import hit.projects.resturantmanager.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/order")
@RestController
public class OrderController {

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderid}")
    public ResponseEntity<Order> getOrder(@PathVariable int orderId) {
        return ResponseEntity.ok().body(orderService.getOrder(orderId));
    }

    @GetMapping
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    @PostMapping
    public Order addOrder(@RequestBody Order newOrder){
        return orderService.addOrder(newOrder);
    }

    @PutMapping("/{orderId}")
    public Order updateOrder(@PathVariable int orderId,@RequestBody Order updateOrder){
        return orderService.updateOrder(orderId,updateOrder);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable int orderId) {
        orderService.deleteOrder(orderId);
    }
}
