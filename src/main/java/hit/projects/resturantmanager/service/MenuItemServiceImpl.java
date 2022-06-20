package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.ENUMS.MenuCategories;
import hit.projects.resturantmanager.repository.MenuItemAssembler;
import hit.projects.resturantmanager.entity.MenuItem;
import hit.projects.resturantmanager.repository.MenuItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private MenuItemRepository menuItemRepository;
    private MenuItemAssembler menuItemAssembler;

    @Override
    public List<MenuItem> getMenu() {
        return menuItemRepository.findAll();
    }

    @Override
    public MenuItem getMenuItem(Long id) {
        return null;
    }

    @Override
    public MenuItem getSingleMenuItem(String name) {
        return menuItemRepository.getMenuItemByName(name);
    }

    @Override
    public List<MenuItem> getAllCategory(String category) {
        return menuItemRepository.findAllByMenuCategories(MenuCategories.valueOf(category.toUpperCase()));
    }

    @Override
    public MenuItem updateMenuItem(String name, MenuItem menuItem) {

    }


    @Override
    public List<MenuItem> getSingleMenuItemPrice(int price) {
        return menuItemRepository.getMenuItemByPrice(price);
    }

    @Override
    public ResponseEntity<EntityModel<MenuItem>> newMenuItem(MenuItem menuItem) {
        if (!menuItemRepository.existsByName(menuItem.getName())) {
            MenuItem savedMenuItem = menuItemRepository.save(menuItem);
            return ResponseEntity.created(linkTo(methodOn(MenuItemServiceImpl.class)
                            .getSingleMenuItem(savedMenuItem.getName()))
                            .toUri())
                    .body(menuItemAssembler.toModel(menuItem));
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}