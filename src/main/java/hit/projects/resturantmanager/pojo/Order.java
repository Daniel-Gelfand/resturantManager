package hit.projects.resturantmanager.pojo;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
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

    public Order update(Order detailsToUpdate) {
        this.setBill(detailsToUpdate.getBill());
        this.setOrderDate(detailsToUpdate.getOrderDate());
        this.setOrderNumber(detailsToUpdate.getOrderNumber());
        this.setOrderStatus(detailsToUpdate.isOrderStatus());
        this.setTableId(detailsToUpdate.getTableId());

        return this;
    }
}
