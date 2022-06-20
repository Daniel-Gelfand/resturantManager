package hit.projects.resturantmanager.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.List;

@Document("Order")
@Data
public class Order {

    private int orderNumber;
    private double bill;
    private Date orderDate;
    private boolean orderStatus;

    @ManyToMany
    private List<Table> tableList;

}
