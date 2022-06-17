package hit.projects.resturantmanager.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Document("Waiter")
public class Waiter extends Worker{
    //database configuration
    @Column(name = "tips",nullable = false)
    private double tips;


}
