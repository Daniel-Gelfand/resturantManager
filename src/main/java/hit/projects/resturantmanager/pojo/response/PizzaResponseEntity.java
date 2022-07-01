package hit.projects.resturantmanager.pojo.response;

import lombok.Data;

@Data
public class PizzaResponseEntity {
    private int id;
    private String name;
    private boolean veg;
    private int price;
    private String description;
    private String img;
    private int quantity;
    private Object sizeandcrust;
}
