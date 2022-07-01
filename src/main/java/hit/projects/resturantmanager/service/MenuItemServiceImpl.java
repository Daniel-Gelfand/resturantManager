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

    @Override
    public CollectionModel<EntityModel<MenuItem>> getMenu() {
        List<EntityModel<MenuItem>> employeesList = menuItemRepository.findAll()
                .stream().map(menuItemAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(employeesList, linkTo(methodOn(MenuItemController.class)
                .getMenu()).withSelfRel());
    }

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

    @Override
    public CollectionModel<EntityModel<MenuItem>> getByCategoryAndPrice(int price, MenuCategories eCategory) {
        List<EntityModel<MenuItem>> categories = menuItemRepository
                .findAllByPriceLessThanAndMenuCategoriesEquals(price, MenuCategories.valueOf(eCategory.toString()))
                .stream()
                .map(menuItemAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(categories);
    }

    @Override
    public EntityModel<MenuItem> getSingleMenuItem(String name) {
        MenuItem menuItem = menuItemRepository
                .getMenuItemByName(name)
                .orElseThrow(() -> new RestaurantNotFoundException(
                        (String.format(Constant.NOT_FOUND_MESSAGE, "name", name))));

        return menuItemAssembler.toModel(menuItem);
    }

    @Override
    public EntityModel<MenuItem> updateMenuItem(String name, MenuItem mItem) {
        //TODO: mItem.getMenuCategories().toString().toUpperCase())  ? ? ?
        return menuItemRepository.findByName(name)
                .map(menuItemToUpdate ->
                        menuItemAssembler.toModel(menuItemRepository.save(menuItemToUpdate.update(mItem))))
                .orElseGet(() -> menuItemAssembler.toModel(menuItemRepository.save(mItem)));
    }

    @Override
    public EntityModel<MenuItem> newMenuItem(MenuItem menuItem) {
        if (!menuItemRepository.existsByName(menuItem.getName())) {
            return menuItemAssembler.toModel(menuItemRepository.save(menuItem));
        }

        throw new RestaurantNotFoundException(
                (String.format(Constant.NOT_FOUND_MESSAGE, "name", menuItem.getName())));
    }

    @Override
    public void deleteMenuItem(String name) {
        if (!menuItemRepository.existsByName(name)) {
            throw new RestaurantNotFoundException(
                    (String.format(Constant.NOT_FOUND_MESSAGE, "name", name)));
        }

        menuItemRepository.deleteByName(name);
    }

    @Override
    public EntityModel<MenuItemDTO> getMenuItemInfo(String name) {
        return menuItemRepository
                .getMenuItemByName(name)
                .map(MenuItemDTO::new)
                .map(menuItemDTOAssembler::toModel)
                .orElseThrow(() -> new RestaurantNotFoundException(
                        (String.format(Constant.NOT_FOUND_MESSAGE, "name", name))));
    }

    @Override
    public CollectionModel<EntityModel<MenuItemDTO>> getAllMenuItemInfo() {

        return CollectionModel.of(menuItemDTOAssembler
                .toCollectionModel(StreamSupport
                        .stream(menuItemRepository.findAll().spliterator(), false)
                        .map(MenuItemDTO::new)
                        .collect(Collectors.toList())));
    }
}