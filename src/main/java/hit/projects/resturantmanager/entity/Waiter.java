package hit.projects.resturantmanager.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "waiter")
public class Waiter extends Worker{
    //database configuration
    @Column(name = "tips",nullable = false)
    private double tips;


}
