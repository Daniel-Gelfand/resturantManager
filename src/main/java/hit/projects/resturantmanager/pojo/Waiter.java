package hit.projects.resturantmanager.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;

@Data
@Document("Waiter")
public class Waiter extends Worker {
    //database configuration
    @Column(name = "tips",nullable = false)
    private double tips;

    public Waiter(int personalId, String firstName, String lastName, double salary, double tips, boolean onDuty) {
        super(personalId, firstName, lastName, salary, onDuty);
        this.tips = tips;
    }
}
