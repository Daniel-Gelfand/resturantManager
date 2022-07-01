package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.assembler.TableAssembler;
import hit.projects.resturantmanager.enums.TableStatus;
import hit.projects.resturantmanager.pojo.Order;
import hit.projects.resturantmanager.pojo.Table;
import hit.projects.resturantmanager.repository.OrderRepository;
import hit.projects.resturantmanager.repository.TableRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableServiceImpl implements TableService{

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
        return tableAssembler.toModel(tableRepository.getTableByTableNumber(tableNumber).orElseThrow(()-> new TableException("Table number " + tableNumber + " not found!")));
    }

    @Override
    public Double getAverageBillIncome() {
        Double averageBill = 0.0;
        return averageBill;
    }

    @Override
    public void addOrder(int tableNumber, Order order) {
        //TODO add validation test before save
        Table table = tableRepository.getTableByTableNumber(tableNumber).orElseThrow(() -> new TableException("Table number " + tableNumber + " not found!"));
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
        if (!tableRepository.existsByTableNumber(newTable.getTableNumber())) {
            tableRepository.save(newTable);
        }else {
            throw new TableException("Table number " + newTable.getTableNumber() + " already exist!");
        }

    }

    @Override
    public void deleteTable(int tableNumber) {
        if (tableRepository.existsByTableNumber(tableNumber)) {
            tableRepository.deleteByTableNumber(tableNumber);
        }else {
            throw new TableException("Table number " + tableNumber + " is not exist!");
        }
    }
}
