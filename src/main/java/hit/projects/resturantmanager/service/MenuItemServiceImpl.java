package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.ENUMS.MenuCategories;
import hit.projects.resturantmanager.controller.MenuItemController;
import hit.projects.resturantmanager.exception.MenuItemException;
import hit.projects.resturantmanager.assembler.MenuItemAssembler;
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
import java.util.Optional;
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
        MenuItem menuItem = menuItemRepository.getMenuItemByName(name).orElseThrow(()-> new MenuItemException(name));
        return ResponseEntity.ok().body(menuItemAssembler.toModel(menuItem));
    }

    @Override
    public ResponseEntity<CollectionModel<EntityModel<MenuItem>>> getAllCategory(String category) {
        List<EntityModel<MenuItem>> categories = menuItemRepository.findAllByMenuCategories(MenuCategories.valueOf(category.toUpperCase()))
                .stream().map(menuItemAssembler::toModel).collect(Collectors.toList());

        //TODO: new method of this shit
        if (categories.size() == 0 ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(CollectionModel.of(categories,linkTo(methodOn(MenuItemController.class).getAllCategory(category)).withSelfRel()));
    }

    @Override
    public ResponseEntity<EntityModel<MenuItem>> updateMenuItem(String name, MenuItem mItem) {
        //TODO: mItem.getMenuCategories().toString().toUpperCase())  ? ? ?
        return menuItemRepository.findByName(name)
                .map(menuItemToUpdate -> {
                    menuItemToUpdate.setName(mItem.getName());
                    menuItemToUpdate.setMenuCategories(MenuCategories.valueOf(mItem.getMenuCategories().toString().toUpperCase()));
                    System.out.println(menuItemToUpdate.getMenuCategories());
                    menuItemToUpdate.setPrice(mItem.getPrice());
                    menuItemRepository.save(menuItemToUpdate);
                    return ResponseEntity.ok().body(menuItemAssembler.toModel(menuItemToUpdate));
                })
                .orElseGet(()-> {
                    System.out.println("orElseGet");
//                    mItem.setMenuCategories(MenuCategories.valueOf(mItem.getMenuCategories().toString().toLowerCase(Locale.ROOT)));
                    System.out.println(mItem.getMenuCategories());
                    menuItemRepository.save(mItem);
                    return ResponseEntity.ok().body(menuItemAssembler.toModel(mItem));
                });

    }


    @Override
    public ResponseEntity<CollectionModel<EntityModel<MenuItem>>> getSingleMenuItemPrice(int price) {
        List<EntityModel<MenuItem>> itemPrices = menuItemRepository.findAllByPrice(price)
                .stream().map(menuItemAssembler::toModel).collect(Collectors.toList());
        if (itemPrices.size() == 0 ) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(CollectionModel.of(itemPrices,linkTo(methodOn(MenuItemController.class).getSingleMenuItemByPrice(price)).withSelfRel()));
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

    @Override
    public ResponseEntity<?> deleteMenuItem(String name) {
        menuItemRepository.deleteByName(name);
        return ResponseEntity.noContent().build();


    }
}