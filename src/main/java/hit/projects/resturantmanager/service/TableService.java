package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.pojo.Order;
import hit.projects.resturantmanager.pojo.Table;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface TableService{
    EntityModel<Table> getTable(int tableNumber);
    Double getAverageBillIncome();
    void addOrder(int tableNumber, Order order);
    List<Table> getAllTables();
    void updateTable(Table tableToUpdate);
    void createTable(Table newTable);
    void deleteTable(int tableNumber);
}