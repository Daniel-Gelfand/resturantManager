package hit.projects.resturantmanager.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/*
    @MappedSuperclass -> build collections for worker kids
 */

@Data

@MappedSuperclass
public abstract class Worker {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long personalId;
    private String firstName;
    private String lastName;
    private Double salary;

}
