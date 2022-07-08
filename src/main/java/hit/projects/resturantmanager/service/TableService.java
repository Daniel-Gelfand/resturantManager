package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.enums.TableStatus;
import hit.projects.resturantmanager.pojo.Order;
import hit.projects.resturantmanager.pojo.Table;
import hit.projects.resturantmanager.pojo.Waiter;
import hit.projects.resturantmanager.pojo.dto.TableDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;


public interface TableService {

    EntityModel<Table> getTable(int tableNumber);

    CollectionModel<EntityModel<Table>> getAllTables();

    Double getAverageBillIncome();

    void addOrder(int tableNumber, Order order);

    EntityModel<Table> updateTable(String tableNumber, Table tableSent);

    EntityModel<Table> createTable(Table newTable);

    EntityModel<TableDTO> getTableInfo(int tableNumber);

    void deleteTable(int tableNumber);

    CollectionModel<EntityModel<Table>> getTableByStatus(TableStatus status);

    void changeTableStatus(int tableNumber, TableStatus tableStatus);

    CollectionModel<EntityModel<TableDTO>> getAllTablesInfo();

    boolean isTableAvailable(int tableNumber);

    void addWaiterToTables(Waiter waiter);

}

