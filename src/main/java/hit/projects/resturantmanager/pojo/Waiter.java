package hit.projects.resturantmanager.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import java.util.LinkedList;
import java.util.List;

@Data
@Document("Waiter")
public class Waiter extends Worker {
    //database configuration
    @Column(name = "tips", nullable = false)
    private double tips;
    @DBRef
    private List<Table> tableList;


    public Waiter(int personalId, String firstName, String lastName, double salary, double tips, boolean onDuty) {
        super(personalId, firstName, lastName, salary, onDuty);
        this.tips = tips;
        this.tableList = new LinkedList<>();
    }


    public Waiter update(Waiter waiterToCopy) {

        this.setFirstName(waiterToCopy.getFirstName());
        this.setLastName(waiterToCopy.getLastName());
        this.setSalary(waiterToCopy.getSalary());
        this.setTips(waiterToCopy.getTips());
        this.setOnDuty(waiterToCopy.isOnDuty());

        return this;
    }
}
