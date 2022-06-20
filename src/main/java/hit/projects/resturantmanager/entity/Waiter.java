package hit.projects.resturantmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Document("Waiter")
public class Waiter extends Worker {
    //database configuration
    @Column(name = "tips",nullable = false)
    private double tips;

    public Waiter(int personalId, String firstName, String lastName, double salary, double tips) {
        super(personalId, firstName, lastName, salary);
        this.tips = tips;
    }
}
