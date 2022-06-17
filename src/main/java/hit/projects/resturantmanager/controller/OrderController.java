package hit.projects.resturantmanager.controller;

import hit.projects.resturantmanager.entity.Order;
import hit.projects.resturantmanager.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderid}")
    public Order getOrder(@PathVariable int orderId) {
        return orderService.getOrder(orderId);
    }

    @GetMapping
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    @PostMapping
    public Order addOrder(@RequestBody Order newOrder){
        return orderService.addOrder(newOrder);
    }

    @PutMapping
    public Order updateOrder(@RequestBody Order updateOrder){
        return orderService.updateOrder(updateOrder);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable int orderId) {
        orderService.deleteOrder(orderId);
    }
}
