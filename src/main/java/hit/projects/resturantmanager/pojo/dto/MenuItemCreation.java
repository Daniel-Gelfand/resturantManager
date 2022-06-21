package hit.projects.resturantmanager.pojo.dto;

import hit.projects.resturantmanager.enums.MenuCategories;
import lombok.Data;

@Data
public class MenuItemCreation {
    private String name;
    private MenuCategories menuCategories;
    private int price;
}
