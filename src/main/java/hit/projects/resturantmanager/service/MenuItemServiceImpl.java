package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.enums.MenuCategories;
import hit.projects.resturantmanager.controller.MenuItemController;
import hit.projects.resturantmanager.exception.MenuItemException;
import hit.projects.resturantmanager.assembler.MenuItemAssembler;
import hit.projects.resturantmanager.pojo.MenuItem;
import hit.projects.resturantmanager.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final MenuItemAssembler menuItemAssembler;


    @Autowired
    public MenuItemServiceImpl(MenuItemRepository menuItemRepository, MenuItemAssembler menuItemAssembler) {
        this.menuItemRepository = menuItemRepository;
        this.menuItemAssembler = menuItemAssembler;
    }

    @Override
    public CollectionModel<EntityModel<MenuItem>> getMenu() {
        List<EntityModel<MenuItem>> employeesList = menuItemRepository.findAll()
                .stream().map(menuItemAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(employeesList,linkTo(methodOn(MenuItemController.class)
                .getMenu()).withSelfRel());
    }

    @Override
    public CollectionModel<EntityModel<MenuItem>> getSingleMenuItemPrice(int price) {
        List<EntityModel<MenuItem>> itemPrices = menuItemRepository.findAllByPrice(price)
                .stream().map(menuItemAssembler::toModel).collect(Collectors.toList());
        if (itemPrices.size() == 0 ) {
            throw new MenuItemException(price);
        }
        return CollectionModel.of(itemPrices,linkTo(methodOn(MenuItemController.class).getSingleMenuItemByPrice(price)).withSelfRel());
    }

    @Override
    public CollectionModel<EntityModel<MenuItem>> getAllCategory(String category) {
        List<EntityModel<MenuItem>> categories = menuItemRepository.findAllByMenuCategories
                        (MenuCategories.valueOf(category.toUpperCase()))
                .stream().map(menuItemAssembler::toModel).collect(Collectors.toList());

        //TODO: maybe add some description into the exception
        // For example "Category X not found".

        if (categories.isEmpty()) {
            throw new MenuItemException(category);
        }

        return CollectionModel.of(categories,linkTo(methodOn(MenuItemController.class)
                .getAllCategory(category)).withSelfRel());
    }


    @Override
    public CollectionModel<EntityModel<MenuItem>> getByCategoryAndPrice(int price, MenuCategories eCategory) {
        List<EntityModel<MenuItem>> categories = menuItemRepository
                .findAllByPriceLessThanAndMenuCategoriesEquals(price,MenuCategories.valueOf(eCategory.toString()))
                .stream()
                .map(menuItemAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(categories);
    }

    @Override
    public EntityModel<MenuItem> getSingleMenuItem(String name) {
        MenuItem menuItem = menuItemRepository.getMenuItemByName(name).orElseThrow(()-> new MenuItemException(name));
        return menuItemAssembler.toModel(menuItem);
    }

    @Override
    public EntityModel<MenuItem> updateMenuItem(String name, MenuItem mItem) {
        //TODO: mItem.getMenuCategories().toString().toUpperCase())  ? ? ?
        return menuItemRepository.findByName(name)
                .map(menuItemToUpdate -> {
                    menuItemToUpdate.setName(mItem.getName());
                    menuItemToUpdate.setMenuCategories(MenuCategories.valueOf(mItem.getMenuCategories().toString().toUpperCase()));
                    System.out.println(menuItemToUpdate.getMenuCategories());
                    menuItemToUpdate.setPrice(mItem.getPrice());
                    menuItemRepository.save(menuItemToUpdate);
                    return menuItemAssembler.toModel(menuItemToUpdate);
                })
                .orElseGet(()-> {
                    System.out.println("orElseGet");
//                    mItem.setMenuCategories(MenuCategories.valueOf(mItem.getMenuCategories().toString().toLowerCase(Locale.ROOT)));
                    System.out.println(mItem.getMenuCategories());
                    menuItemRepository.save(mItem);
                    return menuItemAssembler.toModel(mItem);
                });

    }

    @Override
    public EntityModel<MenuItem> newMenuItem(MenuItem menuItem) {
        if (!menuItemRepository.existsByName(menuItem.getName())) {
            menuItemRepository.save(menuItem);
            return menuItemAssembler.toModel(menuItem);
        }
        else {
            throw new MenuItemException(menuItem);
        }
    }

    @Override
    public void deleteMenuItem(String name) {
        boolean isExists = menuItemRepository.existsByName(name);
        if (!isExists)
        {
            throw new MenuItemException(name);
        }
        menuItemRepository.deleteByName(name);
    }


}