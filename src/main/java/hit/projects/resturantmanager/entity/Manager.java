package hit.projects.resturantmanager.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;



@Document("Manager")
@Getter @Setter
public class Manager extends Worker {


}
