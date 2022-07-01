package hit.projects.resturantmanager.pojo;

import hit.projects.resturantmanager.enums.TableStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;


@Document("Table")
@Data
public class Table {

    @Id
    private String id;
    private TableStatus tableStatus;
    @Indexed(unique = true)
    private int tableNumber;

    @DBRef
    private List<Order> orderList;
    @DBRef
    private List<Waiter> waitersList;

    @Builder
    public Table(TableStatus tableStatus, int tableNumber) {
        this.tableStatus = tableStatus;
        this.tableNumber = tableNumber;
        this.orderList = new ArrayList<>();
        this.waitersList = new ArrayList<>();
    }

    public Table update(Table tableToCopy) {

        this.setTableStatus(tableToCopy.getTableStatus());
        this.setTableNumber(tableToCopy.getTableNumber());
        this.setOrderList(tableToCopy.getOrderList());
        this.setWaitersList(tableToCopy.getWaitersList());

        return this;
    }

}
