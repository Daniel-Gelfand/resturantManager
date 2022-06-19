package hit.projects.resturantmanager.entity;

import hit.projects.resturantmanager.ENUMS.MenuCategories;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

//import javax.persistence.Id;
@Data
@Document
public class MenuItem {
    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;
    @Indexed(unique = true)
    private String name;
    private MenuCategories menuCategories;
    private int price;

    public MenuItem(String name, MenuCategories menuCategories, int price) {
        this.name = name;
        this.menuCategories = menuCategories;
        this.price = price;
    }
}
