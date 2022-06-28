package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.exception.MenuItemException;
import hit.projects.resturantmanager.pojo.MenuItem;
import hit.projects.resturantmanager.pojo.Order;
import hit.projects.resturantmanager.pojo.Table;
import hit.projects.resturantmanager.repository.MenuItemRepository;
import hit.projects.resturantmanager.repository.OrderRepository;
import hit.projects.resturantmanager.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService{

    private OrderRepository orderRepository;
    private MenuItemRepository menuItemRepository;
    private TableRepository tableRepository;

    public OrderServiceImpl(OrderRepository orderRepository, MenuItemRepository menuItemRepository, TableRepository tableRepository) {
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
        this.tableRepository = tableRepository;
    }

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

    public void addMenuItem(int orderNumber,String menuItemName, int count) {
        Order order = orderRepository.getOrderByOrderNumber(orderNumber);
        MenuItem menuItem = menuItemRepository.getMenuItemByName(menuItemName).orElseThrow(()-> new MenuItemException("Cannot add menu item to order because isn't exist."));
        for (int i = 0; i < count; i++) {
            order.getOrderList().add(menuItem);
            order.setBill(order.getBill() + menuItem.getPrice());
        }
        orderRepository.save(order);
    }
}
