package hit.projects.resturantmanager.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

//import javax.persistence.Id;
@Data
@Document("MenuItem")
public class MenuItem {
    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String category;
    private int price;





}
