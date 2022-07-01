package hit.projects.resturantmanager.pojo;

import lombok.Data;

@Data
public class DessertsResponseEntity {
    private int id;
    private String name;
    private int price;
    private String description;
    private String img;
    private int quantity;
}
