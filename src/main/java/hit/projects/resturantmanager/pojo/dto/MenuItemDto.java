package hit.projects.resturantmanager.pojo.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class MenuItemDto {

    private String id;
    private String name;
    private int price;

    @Builder
    public MenuItemDto(String id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
