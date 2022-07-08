package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.assembler.TableAssembler;
import hit.projects.resturantmanager.assembler.dto.TableDTOAssembler;
import hit.projects.resturantmanager.controller.TableController;
import hit.projects.resturantmanager.enums.TableStatus;
import hit.projects.resturantmanager.exception.RestaurantConflictException;
import hit.projects.resturantmanager.exception.RestaurantNotFoundException;
import hit.projects.resturantmanager.pojo.Order;
import hit.projects.resturantmanager.pojo.Table;
import hit.projects.resturantmanager.pojo.dto.MenuItemDTO;
import hit.projects.resturantmanager.pojo.dto.TableDTO;
import hit.projects.resturantmanager.repository.OrderRepository;
import hit.projects.resturantmanager.repository.TableRepository;
import hit.projects.resturantmanager.utils.Constant;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class TableServiceImpl implements TableService {

    TableRepository tableRepository;
    OrderRepository orderRepository;
    TableAssembler tableAssembler;
    TableDTOAssembler tableDTOAssembler;

    TableServiceImpl(TableRepository tableRepository, OrderRepository orderRepository, TableAssembler tableAssembler,TableDTOAssembler tableDTOAssembler) {
        this.tableRepository = tableRepository;
        this.orderRepository = orderRepository;
        this.tableAssembler = tableAssembler;
        this.tableDTOAssembler = tableDTOAssembler;
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
    public EntityModel<Table> createTable(Table newTable) {
        if (!tableRepository.existsByTableNumber(newTable.getTableNumber())) {
            return tableAssembler.toModel(tableRepository.save(newTable));
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

    @Override
    public CollectionModel<EntityModel<Table>> getTableByStatus(TableStatus status) {
        List<Table> tables = tableRepository.getTableByTableStatus(status);
        List<EntityModel<Table>> tablesEntityModelList = tables.stream()
                .map(tableAssembler::toModel).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(tables)) {
            throw new RestaurantNotFoundException(
                    (String.format(Constant.NOT_FOUND_MESSAGE, "table status", status)));
        }

        return CollectionModel.of(tablesEntityModelList, linkTo(methodOn(TableController.class)
                .getAllTables()).withSelfRel());
    }

    @Override
    public CollectionModel<EntityModel<TableDTO>> getAllTablesInfo() {
        return CollectionModel.of(tableDTOAssembler
                .toCollectionModel(StreamSupport
                        .stream(tableRepository.findAll().spliterator(), false)
                        .map(TableDTO::new)
                        .collect(Collectors.toList())));
    }

    @Override
    public boolean isTableAvailable(String tableId) {
        Table table = tableRepository.findById(tableId).orElseThrow(() -> new RestaurantNotFoundException(String.format(Constant.NOT_FOUND_MESSAGE, "table id", tableId)));

        return table.getTableStatus() == TableStatus.AVAILABLE;
    }

    @Override
    public EntityModel<TableDTO> getTableInfo(int tableNumber) {
        return tableRepository
                .getTableByTableNumber(tableNumber)
                .map(TableDTO::new)
                .map(tableDTOAssembler::toModel)
                .orElseThrow(() -> new RestaurantNotFoundException(
                        (String.format(Constant.NOT_FOUND_MESSAGE, "tableNumber", tableNumber))));
    }

}
