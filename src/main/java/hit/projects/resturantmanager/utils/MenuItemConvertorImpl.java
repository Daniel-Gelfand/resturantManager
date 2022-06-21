package hit.projects.resturantmanager.utils;

import hit.projects.resturantmanager.pojo.MenuItem;
import hit.projects.resturantmanager.pojo.dto.MenuItemCreation;
import hit.projects.resturantmanager.pojo.dto.MenuItemDto;
import org.springframework.stereotype.Component;

@Component
public class MenuItemConvertorImpl implements MenuItemConvertor {
    // TODO: create DTO to our objects
    // TODO: build convertor method from our object to DTO

    @Override
    public MenuItem convertToEntityFromCreation(MenuItemCreation menuItemCreation) {
        return MenuItem.builder().name(menuItemCreation.getName())
                .menuCategories(menuItemCreation.getMenuCategories())
                .price(menuItemCreation.getPrice())
                .build();
    }

    @Override
    public MenuItemDto convertToDtoFromEntity(MenuItem menuItem) {
        return MenuItemDto.builder()
                .id(menuItem.getId())
                .name(menuItem.getName())
                .price(menuItem.getPrice())
                .build();
    }
}
