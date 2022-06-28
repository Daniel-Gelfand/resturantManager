package hit.projects.resturantmanager.pojo;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.List;

@Data
@MappedSuperclass
public abstract class Worker {

    @Id
    private String id;
    @Indexed(unique = true)
    private int personalId;
    private String firstName;
    private String lastName;
    private double salary;
    private boolean onDuty;

    public Worker() {
    }

    public Worker(int personalId, String firstName, String lastName, double salary, boolean onDuty) {
        this.personalId = personalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.onDuty = onDuty;
    }
}
/*
    @MappedSuperclass -> build collections for worker kids
 */

