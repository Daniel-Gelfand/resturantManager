package hit.projects.resturantmanager.assembler;

import hit.projects.resturantmanager.controller.TableController;
import hit.projects.resturantmanager.pojo.Table;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TableAssembler implements RepresentationModelAssembler<Table, EntityModel<Table>> {
    @Override
    public EntityModel<Table> toModel(Table entity) {
        return EntityModel.of(entity, linkTo(methodOn(TableController.class)
                .getTable(entity.getTableNumber())).withSelfRel(), linkTo(methodOn(TableController.class)
                .getAllTables()).withRel("All tables"));
    }

    @Override
    public CollectionModel<EntityModel<Table>> toCollectionModel(Iterable<? extends Table> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
