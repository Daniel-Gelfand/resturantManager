package hit.projects.resturantmanager.pojo;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document("Order")
@Data
public class Order {

    @Id
    private String id;
    @Indexed(unique = true)
    private int orderNumber;
    private double bill;
    private LocalDateTime orderDate;

    private boolean orderStatus;
    private List<MenuItem> orderList;
    private String tableId;

    @Builder
    public Order(int orderNumber, double bill, boolean orderStatus, List<MenuItem> orderList, String tableId) {
        this.orderNumber = orderNumber;
        this.bill = bill;
        this.orderDate = LocalDateTime.now();
        this.orderStatus = orderStatus;
        this.orderList = orderList;
        this.tableId = tableId;
    }

}
