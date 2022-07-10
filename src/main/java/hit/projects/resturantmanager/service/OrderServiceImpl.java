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

    /**
     * In this method we return all orders from DB
     * @return Collection fo EntityModel of Order
     */
    @Override
    public CollectionModel<EntityModel<Order>> getAllOrders() {
        return orderAssembler.toCollectionModel(orderRepository.findAll());
    }

    /**
     * In this method we return specific order by order number
     * @param orderNumber = valid order number
     * @return
     */
    @Override
    public EntityModel<Order> getOrder(int orderNumber) {
        return orderAssembler.toModel(
                orderRepository.getOrderByOrderNumber(orderNumber)
                        .orElseThrow(() -> new RestaurantNotFoundException(
                                (String.format(Constant.NOT_FOUND_MESSAGE, "order number", orderNumber)))));
    }

    /**
     * In this method we return order after add new order.
     * before returning we check that new order isn't available
     * @param newOrder
     * @return EntityModel of Order.
     */
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

    /**
     * In this method we update order by order number.
     *
     * @param orderNumber except to get valid order number.
     * @param updateOrder - except to get valid order body.
     * @return EntityModel of Order.
     */
    @Override
    public EntityModel<Order> updateOrder(int orderNumber, Order updateOrder) {
        return orderRepository.findByOrderNumber(orderNumber)
                .map(orderToUpdate -> orderAssembler
                        .toModel(orderRepository
                                .save(orderToUpdate.update(updateOrder))))
                .orElseGet(() -> orderAssembler.toModel(orderRepository.save(updateOrder)));
    }

    /**
     * In this method we delete order by order number.
     * @param orderNumber except to get valid order number.
     */
    @Override
    public void deleteOrder(int orderNumber) {
        if (!orderRepository.existsByOrderNumber(orderNumber)) {
            throw new RestaurantNotFoundException(
                    (String.format(Constant.NOT_FOUND_MESSAGE, "order number", orderNumber)));
        }
        orderRepository.deleteByOrderNumber(orderNumber);
    }

    /**
     * In this method we add menuItem by count to order list in Order Object.
     * @param orderNumber - Except to get valid order number.
     * @param menuItemName -  Except to get valid menuItem name.
     * @param count -  Except to get valid count of menu items.
     * @return EntityModel or updated order.
     */
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


    /**
     * In this method we return orders by dates
     * @param startDate - Except to get valid start date.
     * @param endDate - Except to get valid end date.
     * @return CollectionModel of EntityModel of Order.
     */
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

    /**
     * In this method we return data transfer object of Order.
     * @param orderNumber Except to get valid order number.
     * @return EntityModel of OrderDTO;
     */
    @Override
    public EntityModel<OrderDTO> getOrderDTO(int orderNumber) {
        return orderRepository
                .getOrderByOrderNumber(orderNumber)
                .map(OrderDTO::new).map(orderDTOAssembler::toModel)
                .orElseThrow(() -> new RestaurantNotFoundException(
                        (String.format(Constant.NOT_FOUND_MESSAGE, "order number", orderNumber))));
    }


    /**
     * In this method we return all order in DTO shape.
     * @return
     */
    @Override
    public CollectionModel<EntityModel<OrderDTO>> getAllOrdersDTO() {
        return CollectionModel.of(orderDTOAssembler
                .toCollectionModel(StreamSupport
                        .stream(orderRepository.findAll().spliterator(), false)
                        .map(OrderDTO::new)
                        .collect(Collectors.toList())));
    }

    /**
     * In this method we allow to users to pay a part from the bill
     * If the bill is get full payment we change the order isBillPayed to true and change the table status to available.
     * @param orderNumber - Except to get valid order number.
     * @param payment - amount of money that received.
     * @return The EntityModel of Order that made changes.
     */
    @Override
    public EntityModel<Order> payOrderBill(int orderNumber, int payment) {

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

    /**
     * In this async method we return the current value of BTC in american dollar.
     * @param restTemplate - Except to get valid rest template that help us to fetch BTC data.
     * @return CompletableFuture of Double that represent the BTC current value
     */
    @Async
    CompletableFuture<Double> bitcoinDetails(RestTemplate restTemplate){
        String urlTemplate = "https://api.coindesk.com/v1/bpi/currentprice.json";

        ResponseEntity<BitcoinResponseEntity> result =
                restTemplate.getForEntity(urlTemplate,BitcoinResponseEntity.class);

        return CompletableFuture.completedFuture(result.getBody().getBpi().get("USD").getRate_float());
    }
}
