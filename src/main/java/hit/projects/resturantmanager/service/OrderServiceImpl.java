package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.assembler.OrderAssembler;
import hit.projects.resturantmanager.assembler.dto.OrderDTOAssembler;
import hit.projects.resturantmanager.enums.TableStatus;
import hit.projects.resturantmanager.exception.RestaurantConflictException;
import hit.projects.resturantmanager.exception.RestaurantNotFoundException;
import hit.projects.resturantmanager.pojo.MenuItem;
import hit.projects.resturantmanager.pojo.Order;
import hit.projects.resturantmanager.pojo.dto.OrderDTO;
import hit.projects.resturantmanager.pojo.response.BitcoinResponseEntity;
import hit.projects.resturantmanager.repository.MenuItemRepository;
import hit.projects.resturantmanager.repository.OrderRepository;
import hit.projects.resturantmanager.utils.Constant;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private TableService tableService;
    @Autowired
    private OrderAssembler orderAssembler;
    @Autowired
    private OrderDTOAssembler orderDTOAssembler;
    @Autowired
    private RestTemplate restTemplate;

//    public OrderServiceImpl(OrderRepository orderRepository, MenuItemRepository menuItemRepository,
//                            OrderAssembler orderAssembler, OrderDTOAssembler orderDTOAssembler, TableRepository tableRepository) {
//        this.orderRepository = orderRepository;
//        this.menuItemRepository = menuItemRepository;
//        this.orderAssembler = orderAssembler;
//        this.orderDTOAssembler = orderDTOAssembler;
//        this.tableRepository = tableRepository;
//    }

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

        //TODO : change table id to table number in the order.

        if (!tableService.isTableAvailable(newOrder.getTableNumber())) {
            throw new RestaurantConflictException("Table isn't available");
        }

        if (orderRepository.existsByOrderNumber(newOrder.getOrderNumber())) {
            throw new RestaurantConflictException(
                    (String.format(Constant.ALREADY_EXISTS_MESSAGE, "order number", newOrder.getOrderNumber())));
        }

        return orderAssembler.toModel(orderRepository.save(newOrder));
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
    public EntityModel<Order> addMenuItem(int orderNumber, String menuItemName, int count) {
        Double bitcoinRate = 0.0;
        try {
            bitcoinRate = bitcoinDetails(restTemplate).get();
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }

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
            order.setBill_btc((order.getBill() / bitcoinRate));
        }

        orderRepository.save(order);

        return orderAssembler.toModel(order);
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

    @Override
    public EntityModel<Order> payOrderBill(int orderNumber, int payment) {
        //TODO : add option to pay in parts

        Order order = orderRepository
                .getOrderByOrderNumber(orderNumber)
                .orElseThrow(() -> new RestaurantNotFoundException(String.format(Constant.NOT_FOUND_MESSAGE, "order number", orderNumber)));

        order.setReceivedPayment(order.getReceivedPayment() + payment);

        if (order.getBill() <= order.getReceivedPayment()) {
            order.setBillPaid(true);
            tableService.changeTableStatus(order.getTableNumber(), TableStatus.AVAILABLE);
        }

        orderRepository.save(order);
        return orderAssembler.toModel(order);
    }

    @Async
    CompletableFuture<Double> bitcoinDetails(RestTemplate restTemplate){
        String urlTemplate = "https://api.coindesk.com/v1/bpi/currentprice.json";

        ResponseEntity<BitcoinResponseEntity> result =
                restTemplate.getForEntity(urlTemplate,BitcoinResponseEntity.class);

        return CompletableFuture.completedFuture(result.getBody().getBpi().get("USD").getRate_float());
    }
}
