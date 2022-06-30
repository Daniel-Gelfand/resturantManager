package hit.projects.resturantmanager.assembler.dto;

import hit.projects.resturantmanager.controller.MenuItemController;
import hit.projects.resturantmanager.pojo.dto2.MenuItemDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MenuItemDTOAssembler implements SimpleRepresentationModelAssembler<MenuItemDTO> {
    @Override
    public void addLinks(EntityModel<MenuItemDTO> resource) {
        resource.add(linkTo(methodOn(MenuItemController.class).menuItemInfo(resource.getContent().getMenuItem().getName())).withSelfRel());
        resource.add(linkTo(methodOn(MenuItemController.class).allMenuItemInfo()).withRel("all menu items"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<MenuItemDTO>> resources) {
        resources.add(linkTo(methodOn(MenuItemController.class).allMenuItemInfo()).withSelfRel());
    }
}
