package hit.projects.resturantmanager.assembler.dto;

import hit.projects.resturantmanager.controller.OrderController;
import hit.projects.resturantmanager.controller.TableController;
import hit.projects.resturantmanager.pojo.dto.OrderDTO;
import hit.projects.resturantmanager.pojo.dto.TableDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TableDTOAssembler implements SimpleRepresentationModelAssembler<TableDTO> {

    @Override
    public void addLinks(EntityModel<TableDTO> resource) {
        resource.add(linkTo(methodOn(TableController.class).getTableInfo(Objects.requireNonNull(resource.getContent()).getTable().getTableNumber())).withSelfRel());
        resource.add(linkTo(methodOn(TableController.class).getAllTablesInfo()).withRel("all tables"));
    }


    @Override
    public void addLinks(CollectionModel<EntityModel<TableDTO>> resources) {
        resources.add(linkTo(methodOn(TableController.class).getAllTablesInfo()).withSelfRel());
    }
}
