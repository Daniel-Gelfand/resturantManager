package hit.projects.resturantmanager.entity;

import lombok.*;
import org.springframework.data.util.ProxyUtils;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;
@Data
@MappedSuperclass
public abstract class Worker {

    @Id
    private String id;
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

