package hit.projects.resturantmanager.pojo;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document("Order")
@Data
public class Order {

    @Id
    private String id;
    private int orderNumber;
    private double bill;
    private Date orderDate;
    private boolean orderStatus;
    private List<MenuItem> orderList;
//    @DBRef
    private String tableId;

    @Builder
    public Order(int orderNumber, double bill, boolean orderStatus, List<MenuItem> orderList, String tableId) {
        this.orderNumber = orderNumber;
        this.bill = bill;
        this.orderDate = new Date();
        this.orderStatus = orderStatus;
        this.orderList = orderList;
        this.tableId = tableId;
    }

}
