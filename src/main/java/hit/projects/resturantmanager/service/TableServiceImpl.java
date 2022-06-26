package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.enums.TableStatus;
import hit.projects.resturantmanager.pojo.Order;
import hit.projects.resturantmanager.pojo.Table;
import hit.projects.resturantmanager.repository.OrderRepository;
import hit.projects.resturantmanager.repository.TableRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TableServiceImpl implements TableService{

    TableRepository tableRepository;
    OrderRepository orderRepository;

    TableServiceImpl(TableRepository tableRepository, OrderRepository orderRepository) {
        this.tableRepository = tableRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Table getTable(int tableNumber) {
        return tableRepository.getTableByTableNumber(tableNumber);
    }

    @Override
    public Double getAverageBillIncome() {
        Double averageBill = 0.0;
        return averageBill;
    }

    @Override
    public void addOrder(int tableNumber, Order order) {
        //TODO add validation test before save
        Table table = tableRepository.getTableByTableNumber(tableNumber);
        order.setTableId(table.getId());
        Order newOrder = orderRepository.save(order);
        table.getOrderList().add(newOrder);
        table.setTableStatus(TableStatus.BUSY);
        tableRepository.save(table);
    }

    @Override
    public List<Table> getAllTables() {
        return tableRepository.findAll();
    }

    @Override
    public void updateTable(Table tableToUpdate) {

    }

    @Override
    public void createTable(Table newTable) {

    }
}
