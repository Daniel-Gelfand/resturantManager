package hit.projects.resturantmanager.entity;

import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.List;

public class Order {

    private int orderNumber;
    private double bill;
    private Date orderDate;
    private boolean orderStatus;

    @ManyToMany
    private List<Table> tableList;

}
