package hit.projects.resturantmanager.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.ManyToMany;
import java.util.List;


@Document("Table")

public class Table {

    private boolean tableStatus;
    private int tableNumber;

    @ManyToMany
    private List<Order> orderList;

}
