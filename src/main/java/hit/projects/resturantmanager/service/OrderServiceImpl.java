package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.assembler.OrderAssembler;
import hit.projects.resturantmanager.assembler.dto.OrderDTOAssembler;
import hit.projects.resturantmanager.exception.RestaurantConflictException;
import hit.projects.resturantmanager.exception.RestaurantNotFoundException;
import hit.projects.resturantmanager.pojo.MenuItem;
import hit.projects.resturantmanager.pojo.Order;
import hit.projects.resturantmanager.pojo.dto.OrderDTO;
import hit.projects.resturantmanager.repository.MenuItemRepository;
import hit.projects.resturantmanager.repository.OrderRepository;
import hit.projects.resturantmanager.utils.Constant;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private MenuItemRepository menuItemRepository;
    private OrderAssembler orderAssembler;
    private OrderDTOAssembler orderDTOAssembler;

    public OrderServiceImpl(OrderRepository orderRepository, MenuItemRepository menuItemRepository, OrderAssembler orderAssembler, OrderDTOAssembler orderDTOAssembler) {
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderAssembler = orderAssembler;
        this.orderDTOAssembler = orderDTOAssembler;
    }

    @Override
    public CollectionModel<EntityModel<Order>> getAllOrders() {
        return orderAssembler.toCollectionModel(orderRepository.findAll());
    }

    @Override
    public EntityModel<Order> getOrder(int orderId) {
        return orderAssembler.toModel(
                orderRepository.getOrderByOrderNumber(orderId)
                        .orElseThrow(() -> new RestaurantNotFoundException(
                                (String.format(Constant.NOT_FOUND_MESSAGE, "order id", orderId)))));
    }

    @Override
    public EntityModel<Order> addOrder(Order newOrder) {
        if (!orderRepository.existsByOrderNumber(newOrder.getOrderNumber())) {
            return orderAssembler.toModel(orderRepository.save(newOrder));
        }

        throw new RestaurantConflictException(
                (String.format(Constant.ALREADY_EXISTS_MESSAGE, "order number", newOrder.getOrderNumber())));
    }

    @Override
    public EntityModel<Order> updateOrder(int orderId, Order updateOrder) {
        return orderRepository.findByOrderNumber(orderId)
                .map(orderToUpdate -> orderAssembler
                        .toModel(orderRepository
                                .save(orderToUpdate.update(updateOrder))))
                .orElseGet(() -> orderAssembler.toModel(orderRepository.save(updateOrder)));
    }

    @Override
    public void deleteOrder(int orderId) {
        if (!orderRepository.existsByOrderNumber(orderId)) {
            throw new RestaurantNotFoundException(
                    (String.format(Constant.NOT_FOUND_MESSAGE, "order number", orderId)));
        }
        orderRepository.deleteByOrderNumber(orderId);
    }

    @Override
    public void addMenuItem(int orderNumber, String menuItemName, int count) {
        Order order = orderRepository
                .getOrderByOrderNumber(orderNumber)
                .orElseThrow(() -> new RestaurantNotFoundException(
                        (String.format(Constant.NOT_FOUND_MESSAGE, "order number", orderNumber))));

        MenuItem menuItem = menuItemRepository
                .getMenuItemByName(menuItemName)
                .orElseThrow(() -> new RestaurantNotFoundException(
                        (String.format(Constant.NOT_FOUND_MESSAGE, "menu item name", menuItemName))));

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

        if (!CollectionUtils.isEmpty(orderDateGreaterThan)) {
            return orderAssembler.toCollectionModel(orderDateGreaterThan);
        }

        throw new RestaurantNotFoundException
                (String.format("There are not exist orders between %s - %s.", startDate, endDate));
    }

    @Override
    public EntityModel<OrderDTO> getOrderDTO(int orderNumber) {
        return orderRepository
                .getOrderByOrderNumber(orderNumber)
                .map(OrderDTO::new).map(orderDTOAssembler::toModel)
                .orElseThrow(() -> new RestaurantNotFoundException(
                        (String.format(Constant.NOT_FOUND_MESSAGE, "order number", orderNumber))));
    }

    @Override
    public CollectionModel<EntityModel<OrderDTO>> getAllOrdersDTO() {
        return CollectionModel.of(orderDTOAssembler
                .toCollectionModel(StreamSupport
                        .stream(orderRepository.findAll().spliterator(), false)
                        .map(OrderDTO::new)
                        .collect(Collectors.toList())));
    }
}
