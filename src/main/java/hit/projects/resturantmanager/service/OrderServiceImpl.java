package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.assembler.OrderAssembler;
import hit.projects.resturantmanager.exception.MenuItemException;
import hit.projects.resturantmanager.exception.OrderException;
import hit.projects.resturantmanager.pojo.MenuItem;
import hit.projects.resturantmanager.pojo.Order;
import hit.projects.resturantmanager.pojo.Table;
import hit.projects.resturantmanager.repository.MenuItemRepository;
import hit.projects.resturantmanager.repository.OrderRepository;
import hit.projects.resturantmanager.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService{

    private OrderRepository orderRepository;
    private MenuItemRepository menuItemRepository;
    private TableRepository tableRepository;
    private OrderAssembler orderAssembler;

    public OrderServiceImpl(OrderRepository orderRepository, MenuItemRepository menuItemRepository, TableRepository tableRepository, OrderAssembler orderAssembler) {
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
        this.tableRepository = tableRepository;
        this.orderAssembler = orderAssembler;
    }

    @Override
    public CollectionModel<EntityModel<Order>> getAllOrders() {
        return orderAssembler.toCollectionModel(orderRepository.findAll());
    }

    @Override
    public EntityModel<Order> getOrder(int orderId) {
        return orderAssembler.toModel(
                orderRepository.getOrderByOrderNumber(orderId)
                        .orElseThrow(()-> new OrderException("Order number not found")));
    }

    @Override
    public EntityModel<Order> addOrder(Order newOrder) {
        return null;
    }

    @Override
    public EntityModel<Order> updateOrder(int orderId,Order updateOrder) {
        return null;
    }

    @Override
    public void deleteOrder(int orderId) {
        orderRepository.getOrderByBillGreaterThan(30);
    }

    @Override
    public void addMenuItem(int orderNumber,String menuItemName, int count) {
        //TODO: need to add exception to order not found;
        Order order = orderRepository.getOrderByOrderNumber(orderNumber).orElseThrow(()-> new OrderException("Order number not found"));
        MenuItem menuItem = menuItemRepository.getMenuItemByName(menuItemName).orElseThrow(()-> new MenuItemException("Cannot add menu item to order because isn't exist."));
        for (int i = 0; i < count; i++) {
            order.getOrderList().add(menuItem);
            order.setBill(order.getBill() + menuItem.getPrice());
        }
        orderRepository.save(order);
    }

    @Override
    public CollectionModel<EntityModel<Order>> getOrderReportByDates(LocalDateTime startDate, LocalDateTime endDate) {
        List<Order> orderDateGreaterThan = orderRepository.getAllByOrderDateGreaterThan(startDate);
        List<Order> orderDateLowerThan = orderRepository.getAllByOrderDateLessThan(endDate);
        orderDateGreaterThan.retainAll(orderDateLowerThan);
        if (orderDateGreaterThan.size() > 0) {
            return orderAssembler.toCollectionModel(orderDateGreaterThan);
        }else {
            throw new OrderException("There are not exist orders between the selected dates.");
        }
    }
}
