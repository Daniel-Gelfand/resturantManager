package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.assembler.TableAssembler;
import hit.projects.resturantmanager.controller.TableController;
import hit.projects.resturantmanager.enums.TableStatus;
import hit.projects.resturantmanager.exception.RestaurantConflictException;
import hit.projects.resturantmanager.exception.RestaurantNotFoundException;
import hit.projects.resturantmanager.pojo.Order;
import hit.projects.resturantmanager.pojo.Table;
import hit.projects.resturantmanager.repository.OrderRepository;
import hit.projects.resturantmanager.repository.TableRepository;
import hit.projects.resturantmanager.utils.Constant;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class TableServiceImpl implements TableService {

    TableRepository tableRepository;
    OrderRepository orderRepository;
    TableAssembler tableAssembler;

    TableServiceImpl(TableRepository tableRepository, OrderRepository orderRepository, TableAssembler tableAssembler) {
        this.tableRepository = tableRepository;
        this.orderRepository = orderRepository;
        this.tableAssembler = tableAssembler;
    }

    @Override
    public EntityModel<Table> getTable(int tableNumber) {
        return tableAssembler.toModel(tableRepository.getTableByTableNumber(tableNumber)
                .orElseThrow(() -> new RestaurantNotFoundException(
                        (String.format(Constant.NOT_FOUND_MESSAGE, "table number", tableNumber)))));
    }

    // TODO : No USE ?!
    @Override
    public Double getAverageBillIncome() {
        Double averageBill = 0.0;
        return averageBill;
    }

    @Override
    public void addOrder(int tableNumber, Order order) {

        Table table = tableRepository.getTableByTableNumber(tableNumber)
                .orElseThrow(() -> new RestaurantNotFoundException(
                        (String.format(Constant.NOT_FOUND_MESSAGE, "table number", tableNumber))));

        order.setTableId(table.getId());
        Order newOrder = orderRepository.save(order);
        table.getOrderList().add(newOrder);
        table.setTableStatus(TableStatus.BUSY);
        tableRepository.save(table);
    }

    @Override
    public CollectionModel<EntityModel<Table>> getAllTables() {
        List<EntityModel<Table>> tables = tableRepository.findAll()
                .stream().map(tableAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(tables, linkTo(methodOn(TableController.class)
                .getAllTables()).withSelfRel());

    }

    @Override
    public EntityModel<Table> updateTable(String tableNumber, Table tableSent) {
        return tableRepository.findById(tableNumber)
                .map(tableToUpdate -> tableAssembler
                        .toModel(tableRepository
                                .save(tableToUpdate.update(tableSent))))
                .orElseGet(() -> tableAssembler.toModel(tableRepository.save(tableSent)));
    }

    @Override
    public void createTable(Table newTable) {
        if (!tableRepository.existsByTableNumber(newTable.getTableNumber())) {
            tableRepository.save(newTable);
        }

        throw new RestaurantConflictException(
                (String.format(Constant.ALREADY_EXISTS_MESSAGE, "table number", newTable.getTableNumber())));
    }

    @Override
    public void deleteTable(int tableNumber) {
        if (tableRepository.existsByTableNumber(tableNumber)) {
            tableRepository.deleteByTableNumber(tableNumber);
        }

        throw new RestaurantNotFoundException(
                (String.format(Constant.NOT_FOUND_MESSAGE, "table number", tableNumber)));
    }
}
