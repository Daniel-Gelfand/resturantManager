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
    private int bill;
    private double bill_btc;
    private LocalDateTime orderDate;
    private boolean isBillPaid;
    private List<MenuItem> orderList;
    private String tableId;
    private int receivedPayment;

    @Builder
    public Order(int orderNumber, int bill, boolean isBillPaid, List<MenuItem> orderList, String tableId) {
        this.orderNumber = orderNumber;
        this.bill = bill;
        this.orderDate = LocalDateTime.now();
        this.isBillPaid = isBillPaid;
        this.orderList = orderList;
        this.tableId = tableId;
        this.receivedPayment = 0;
    }

    public Order update(Order detailsToUpdate) {
        this.setBill(detailsToUpdate.getBill());
        this.setOrderDate(detailsToUpdate.getOrderDate());
        this.setOrderNumber(detailsToUpdate.getOrderNumber());
        this.setBillPaid(detailsToUpdate.isBillPaid());
        this.setTableId(detailsToUpdate.getTableId());

        return this;
    }
}
