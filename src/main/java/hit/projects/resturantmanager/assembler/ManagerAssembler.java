package hit.projects.resturantmanager.assembler;

import hit.projects.resturantmanager.controller.ManagerController;
import hit.projects.resturantmanager.pojo.Manager;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ManagerAssembler implements RepresentationModelAssembler<Manager, EntityModel<Manager>> {

    @Override
    public EntityModel<Manager> toModel(Manager entity) {

        return EntityModel.of(entity, linkTo(methodOn(ManagerController.class)
                .getManager((entity.getPersonalId()))).withSelfRel(), linkTo(methodOn(ManagerController.class).
                getAllManagers()).withRel("All managers"));
    }

    @Override
    public CollectionModel<EntityModel<Manager>> toCollectionModel(Iterable<? extends Manager> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
