package hit.projects.resturantmanager.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;



@Document("Manager")
@Data
public class Manager extends Worker {

    public Manager(int personalId, String firstName, String lastName, double salary) {
        super(personalId, firstName, lastName, salary);
    }
}
