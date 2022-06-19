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

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String personalId;
    private String firstName;
    private String lastName;
    private double salary;

    public Worker() {
    }

    public Worker(String personalId, String firstName, String lastName, double salary) {
        this.personalId = personalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }
}
/*
    @MappedSuperclass -> build collections for worker kids
 */

