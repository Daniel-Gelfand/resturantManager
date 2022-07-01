package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.enums.TableStatus;
import hit.projects.resturantmanager.pojo.Order;
import hit.projects.resturantmanager.pojo.Table;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;


public interface TableService{

    EntityModel<Table> getTable(int tableNumber);

    CollectionModel<EntityModel<Table>> getAllTables();

    Double getAverageBillIncome();

    void addOrder(int tableNumber, Order order);

    EntityModel<Table> updateTable(String tableNumber ,Table tableSent);

    EntityModel<Table> createTable(Table newTable);

    void deleteTable(int tableNumber);

    CollectionModel<EntityModel<Table>> getTableByStatus(TableStatus status);
}

