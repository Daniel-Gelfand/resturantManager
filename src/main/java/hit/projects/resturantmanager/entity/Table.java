package hit.projects.resturantmanager.entity;

import javax.persistence.ManyToMany;
import java.util.List;

public class Table {

    private boolean tableStatus;
    private int tableNumber;

    @ManyToMany
    private List<Order> orderList;

}
