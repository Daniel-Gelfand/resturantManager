package hit.projects.resturantmanager.pojo;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;



@Document("Manager")
@Data
public class Manager extends Worker {

    public Manager(int personalId, String firstName, String lastName, double salary, boolean onDuty) {
        super(personalId, firstName, lastName, salary, onDuty);
    }

    public Manager update(Manager detailsToUpdate){
        this.setFirstName(detailsToUpdate.getFirstName());
        this.setLastName(detailsToUpdate.getLastName());
        this.setOnDuty(detailsToUpdate.isOnDuty());
        this.setPersonalId(detailsToUpdate.getPersonalId());
        this.setSalary(detailsToUpdate.getSalary());

        return this;
    }
}
