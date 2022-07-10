package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.assembler.TableAssembler;
import hit.projects.resturantmanager.assembler.dto.TableDTOAssembler;
import hit.projects.resturantmanager.controller.TableController;
import hit.projects.resturantmanager.enums.TableStatus;
import hit.projects.resturantmanager.exception.RestaurantConflictException;
import hit.projects.resturantmanager.exception.RestaurantNotFoundException;
import hit.projects.resturantmanager.pojo.Order;
import hit.projects.resturantmanager.pojo.Table;
import hit.projects.resturantmanager.pojo.Waiter;
import hit.projects.resturantmanager.pojo.dto.MenuItemDTO;
import hit.projects.resturantmanager.pojo.dto.TableDTO;
import hit.projects.resturantmanager.repository.OrderRepository;
import hit.projects.resturantmanager.repository.TableRepository;
import hit.projects.resturantmanager.utils.Constant;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    TableRepository tableRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    TableAssembler tableAssembler;
    @Autowired
    TableDTOAssembler tableDTOAssembler;

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

        order.setTableNumber(table.getTableNumber());
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
            //waiterService.addTableToWaiters(newTable);
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
    public void changeTableStatus(int tableNumber, TableStatus tableStatus) {
        Table table = tableRepository.getTableByTableNumber(tableNumber).orElseThrow(() -> new RestaurantNotFoundException(String.format(Constant.NOT_FOUND_MESSAGE, "tableNumber", tableNumber)));

        table.setTableStatus(tableStatus);
        tableRepository.save(table);
    }

    @Override
    public CollectionModel<EntityModel<TableDTO>> getAllTablesInfo() {
        return CollectionModel.of(tableDTOAssembler
                .toCollectionModel(StreamSupport
                        .stream(tableRepository.findAll().spliterator(), false)
                        .map(TableDTO::new)
                        .collect(Collectors.toList())));
    }

    /**
     * In this method we chekc if the table is available.
     * @param tableNumber -> Expect to get table number.
     * @return true or false.
     */
    @Override
    public boolean isTableAvailable(int tableNumber) {
        Table table = tableRepository.getTableByTableNumber(tableNumber).orElseThrow(() -> new RestaurantNotFoundException(String.format(Constant.NOT_FOUND_MESSAGE, "table number", tableNumber)));
        return table.getTableStatus() == TableStatus.AVAILABLE;
    }

    /**
     * In thi method we add waiter that service tables.
     * @param waiter -> Expect to get json with waiter details.
     */
    @Override
    public void addWaiterToTables(Waiter waiter) {
        List<Table> tables = tableRepository.findAll();

        for (Table table:tables) {
            table.getWaitersList().add(waiter);
            tableRepository.save(table);
        }
    }

    /**
     * In this method we return DTO of table by table number.
     * @param tableNumber Except to get valid table number.
     * @return model of table dto info.
     */
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
