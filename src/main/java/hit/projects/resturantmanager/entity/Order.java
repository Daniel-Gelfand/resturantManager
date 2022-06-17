package hit.projects.resturantmanager.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.List;

@Document("Order")

public class Order {

    private int orderNumber;
    private double bill;
    private Date orderDate;
    private boolean orderStatus;

    @ManyToMany
    private List<Table> tableList;

}
