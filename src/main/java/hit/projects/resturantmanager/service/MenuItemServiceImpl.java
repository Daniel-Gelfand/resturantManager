package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.ENUMS.MenuCategories;
import hit.projects.resturantmanager.controller.MenuItemController;
import hit.projects.resturantmanager.exception.MenuItemNotFoundException;
import hit.projects.resturantmanager.repository.MenuItemAssembler;
import hit.projects.resturantmanager.entity.MenuItem;
import hit.projects.resturantmanager.repository.MenuItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private MenuItemRepository menuItemRepository;
    private MenuItemAssembler menuItemAssembler;

    @Override
    public ResponseEntity<CollectionModel<EntityModel<MenuItem>>> getMenu() {
        List<EntityModel<MenuItem>> employeesList = menuItemRepository.findAll()
                .stream().map(menuItemAssembler::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(employeesList,linkTo(methodOn(MenuItemController.class).getMenu()).withSelfRel()));
    }



    @Override
    public ResponseEntity<EntityModel<MenuItem>> getSingleMenuItem(String name) {
        MenuItem menuItem = menuItemRepository.getMenuItemByName(name).orElseThrow(()-> new MenuItemNotFoundException(name));
        return ResponseEntity.ok().body(menuItemAssembler.toModel(menuItem));
    }

    @Override
    public List<MenuItem> getAllCategory(String category) {
        return menuItemRepository.findAllByMenuCategories(MenuCategories.valueOf(category.toUpperCase()));
    }

    @Override
    public MenuItem updateMenuItem(String name, MenuItem menuItem) {
        return null;
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