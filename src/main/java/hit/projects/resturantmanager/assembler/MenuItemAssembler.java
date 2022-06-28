package hit.projects.resturantmanager.assembler;

import hit.projects.resturantmanager.controller.MenuItemController;
import hit.projects.resturantmanager.pojo.MenuItem;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MenuItemAssembler implements RepresentationModelAssembler<MenuItem, EntityModel<MenuItem>> {

    @Override
    public EntityModel<MenuItem> toModel(MenuItem entity) {
        return EntityModel.of(entity, linkTo(methodOn(MenuItemController.class)
                .getSingleMenuItem(entity.getName())).withSelfRel(), linkTo(methodOn(MenuItemController.class)
                .getMenu()).withRel("All Menu"));
    }

    @Override
    public CollectionModel<EntityModel<MenuItem>> toCollectionModel(Iterable<? extends MenuItem> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
