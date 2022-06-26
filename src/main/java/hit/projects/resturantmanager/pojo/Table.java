package hit.projects.resturantmanager.pojo;

import hit.projects.resturantmanager.enums.TableStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@Document("Table")
@Data
public class Table {

    @Id
    private String id;
    private TableStatus tableStatus;
    private int tableNumber;

    @DBRef
    private List<Order> orderList;


    @Builder
    public Table(TableStatus tableStatus, int tableNumber) {
        this.tableStatus = tableStatus;
        this.tableNumber = tableNumber;
        this.orderList = new ArrayList<>();
    }


}
