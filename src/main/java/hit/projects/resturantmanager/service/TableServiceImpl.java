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
import hit.projects.resturantmanager.pojo.dto.TableDTO;
import hit.projects.resturantmanager.repository.OrderRepository;
import hit.projects.resturantmanager.repository.TableRepository;
import hit.projects.resturantmanager.utils.Constant;
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
    private TableRepository tableRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TableAssembler tableAssembler;
    @Autowired
    private TableDTOAssembler tableDTOAssembler;

    /**
     * In this method we return specific table by table number.
     * @param tableNumber Except to get valid table number.
     * @return EntityModel of Table.
     */
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


    /**
     * In this method we add new order to specific table
     * @param tableNumber Except to get valid table number.
     * @param order Except to get valid Order Object.
     */
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

    /**
     * In this method we return all tables
     * @return CollectionModel of EntityModel of Table.
     */
    @Override
    public CollectionModel<EntityModel<Table>> getAllTables() {
        List<EntityModel<Table>> tables = tableRepository.findAll()
                .stream().map(tableAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(tables, linkTo(methodOn(TableController.class)
                .getAllTables()).withSelfRel());

    }

    /**
     * In this method we update specific table by table number .
     * @param tableNumber = Except to get valid table number.
     * @param tableSent = Except to get valid Table Object.
     * @return EntityModel of Table that made change.
     */
    @Override
    public EntityModel<Table> updateTable(String tableNumber, Table tableSent) {
        return tableRepository.findById(tableNumber)
                .map(tableToUpdate -> tableAssembler
                        .toModel(tableRepository
                                .save(tableToUpdate.update(tableSent))))
                .orElseGet(() -> tableAssembler.toModel(tableRepository.save(tableSent)));
    }

    /**
     * In this method we create new table and adding him to DB.
     * @param newTable = Except to get valid Table Object .
     * @return EntityModel of the new Table
     */
    @Override
    public EntityModel<Table> createTable(Table newTable) {
        if (!tableRepository.existsByTableNumber(newTable.getTableNumber())) {
            //waiterService.addTableToWaiters(newTable);
            return tableAssembler.toModel(tableRepository.save(newTable));
        }

        throw new RestaurantConflictException(
                (String.format(Constant.ALREADY_EXISTS_MESSAGE, "table number", newTable.getTableNumber())));
    }

    /**
     * In this method we delete specific table by table number.
     * @param tableNumber Except to get valid table number
     */
    @Override
    public void deleteTable(int tableNumber) {
        if (!tableRepository.existsByTableNumber(tableNumber)) {
            throw new RestaurantNotFoundException(
                    (String.format(Constant.NOT_FOUND_MESSAGE, "table number", tableNumber)));
        }

        tableRepository.deleteByTableNumber(tableNumber);

    }

    /**
     * In this method we get all tables by specific TableStatus.
     * @param status = Except to get valid TableStatus Enum.
     * @return CollectionModel of EntityModel of Table
     */
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

    /**
     * In this method we change table status.
     * @param tableNumber - Except to get valid table number;
     * @param tableStatus -  Except to get valid table status;
     */
    @Override
    public void changeTableStatus(int tableNumber, TableStatus tableStatus) {
        Table table = tableRepository.getTableByTableNumber(tableNumber).orElseThrow(() -> new RestaurantNotFoundException(String.format(Constant.NOT_FOUND_MESSAGE, "tableNumber", tableNumber)));

        table.setTableStatus(tableStatus);
        tableRepository.save(table);
    }

    /**
     * In this method we return all tables in DTO version
     * @return CollectionModel of EntityModel of TableDTO
     */
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
