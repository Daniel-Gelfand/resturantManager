package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.assembler.MenuItemAssembler;
import hit.projects.resturantmanager.assembler.dto.MenuItemDTOAssembler;
import hit.projects.resturantmanager.controller.MenuItemController;
import hit.projects.resturantmanager.enums.MenuCategories;
import hit.projects.resturantmanager.exception.RestaurantNotFoundException;
import hit.projects.resturantmanager.pojo.MenuItem;
import hit.projects.resturantmanager.pojo.dto.MenuItemDTO;
import hit.projects.resturantmanager.repository.MenuItemRepository;
import hit.projects.resturantmanager.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Slf4j
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final MenuItemAssembler menuItemAssembler;
    private final MenuItemDTOAssembler menuItemDTOAssembler;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository, MenuItemAssembler menuItemAssembler, MenuItemDTOAssembler menuItemDTOAssembler) {
        this.menuItemRepository = menuItemRepository;
        this.menuItemAssembler = menuItemAssembler;
        this.menuItemDTOAssembler = menuItemDTOAssembler;
    }

    /**
     *  In this method we return all items in Menu restaurant.
     * @return Menu
     */
    @Override
    public CollectionModel<EntityModel<MenuItem>> getMenu() {
        List<EntityModel<MenuItem>> menus = menuItemRepository.findAll()
                .stream().map(menuItemAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(menus, linkTo(methodOn(MenuItemController.class)
                .getMenu()).withSelfRel());
    }

    /**
     * In this method we return single item from menu by his price.
     * @param price -> price of item in menu. EXAMPLE: Coca-Cola = 14 ILS
     * @return Single menu item
     */
    @Override
    public CollectionModel<EntityModel<MenuItem>> getSingleMenuItemPrice(int price) {
        List<EntityModel<MenuItem>> itemPrices = menuItemRepository.findAllByPrice(price)
                .stream().map(menuItemAssembler::toModel).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(itemPrices)) {
            throw new RestaurantNotFoundException(
                    (String.format(Constant.NOT_FOUND_MESSAGE, "price lower then", price)));
        }
        return CollectionModel.of(itemPrices, linkTo(methodOn(MenuItemController.class).getSingleMenuItemByPrice(price)).withSelfRel());
    }

    /**
     * In this method we return all items in menu from specific category
     * @param category -> category of item in menu, EXAMPLE: Coca-Cola = DRINKS
     * @return All items in menu from specific category.
     */
    @Override
    public CollectionModel<EntityModel<MenuItem>> getAllCategory(String category) {
        List<EntityModel<MenuItem>> categories = menuItemRepository.findAllByMenuCategories
                        (MenuCategories.valueOf(category.toUpperCase()))
                .stream().map(menuItemAssembler::toModel).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(categories)) {
            throw new RestaurantNotFoundException(
                    (String.format(Constant.NOT_FOUND_MESSAGE, "category", category)));
        }

        return CollectionModel.of(categories, linkTo(methodOn(MenuItemController.class)
                .getAllCategory(category)).withSelfRel());
    }

    /**
     * In this method we return all items in menu from specific category and price
     * @param price -> price of item in menu. EXAMPLE: Coca-Cola = 14 ILS
     * @param eCategory -> category of item in menu, EXAMPLE: Coca-Cola = DRINKS
     * @return All items in menu from specific category and his price.
     */
    @Override
    public CollectionModel<EntityModel<MenuItem>> getByCategoryAndPrice(int price, MenuCategories eCategory) {
        List<EntityModel<MenuItem>> categories = menuItemRepository
                .findAllByPriceLessThanAndMenuCategoriesEquals(price, MenuCategories.valueOf(eCategory.toString()))
                .stream()
                .map(menuItemAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(categories);
    }

    /**
     * In this method we return single item from restaurant menu by his name.
     * @param name -> name of item in the menu. EXAMPLE : Steak Pargit
     * @return single item from menu.
     */
    @Override
    public EntityModel<MenuItem> getSingleMenuItem(String name) {
        MenuItem menuItem = menuItemRepository
                .getMenuItemByName(name)
                .orElseThrow(() -> new RestaurantNotFoundException(
                        (String.format(Constant.NOT_FOUND_MESSAGE, "name", name))));

        return menuItemAssembler.toModel(menuItem);
    }

    /**
     * In this method we update specific item in menu, by his name.
     * @param name -> name of item in the menu. EXAMPLE : Steak Pargit
     * @param mItem -> json with details about the item.
     * @return update the item in db.
     */
    @Override
    public EntityModel<MenuItem> updateMenuItem(String name, MenuItem mItem) {
        //TODO: mItem.getMenuCategories().toString().toUpperCase())  ? ? ?
        return menuItemRepository.findByName(name)
                .map(menuItemToUpdate ->
                        menuItemAssembler.toModel(menuItemRepository.save(menuItemToUpdate.update(mItem))))
                .orElseGet(() -> menuItemAssembler.toModel(menuItemRepository.save(mItem)));
    }

    /**
     * In this method we create new item in menu.
     * @param menuItem -> json with details about the item.
     * @return create new item in db.
     */
    @Override
    public EntityModel<MenuItem> newMenuItem(MenuItem menuItem) {
        if (!menuItemRepository.existsByName(menuItem.getName())) {
            return menuItemAssembler.toModel(menuItemRepository.save(menuItem));
        }

        throw new RestaurantNotFoundException(
                (String.format(Constant.NOT_FOUND_MESSAGE, "name", menuItem.getName())));
    }

    /**
     * In this method delete item from menu.
     * @param name -> name of item in the menu. EXAMPLE : Steak Pargit
     */
    @Override
    public void deleteMenuItem(String name) {
        if (!menuItemRepository.existsByName(name)) {
            throw new RestaurantNotFoundException(
                    (String.format(Constant.NOT_FOUND_MESSAGE, "name", name)));
        }

        menuItemRepository.deleteByName(name);
    }

    /**
     * In this method we return info (name & price) about specific item (DTO).
     * @param name -> name of item in the menu. EXAMPLE : Steak Pargit
     * @return specific item from menu. (DTO -> name & price).
     */
    @Override
    public EntityModel<MenuItemDTO> getMenuItemInfo(String name) {
        return menuItemRepository
                .getMenuItemByName(name)
                .map(MenuItemDTO::new)
                .map(menuItemDTOAssembler::toModel)
                .orElseThrow(() -> new RestaurantNotFoundException(
                        (String.format(Constant.NOT_FOUND_MESSAGE, "name", name))));
    }

    /**
     * In this method we return info (name & price) about all items (DTO).
     * @return name and price of items.
     */
    @Override
    public CollectionModel<EntityModel<MenuItemDTO>> getAllMenuItemInfo() {

        return CollectionModel.of(menuItemDTOAssembler
                .toCollectionModel(StreamSupport
                        .stream(menuItemRepository.findAll().spliterator(), false)
                        .map(MenuItemDTO::new)
                        .collect(Collectors.toList())));
    }
}