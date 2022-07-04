package hit.projects.resturantmanager.assembler.dto;

import hit.projects.resturantmanager.controller.MenuItemController;
import hit.projects.resturantmanager.controller.WaiterController;
import hit.projects.resturantmanager.pojo.dto.WaiterDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class WaiterDTOAssembler implements SimpleRepresentationModelAssembler<WaiterDTO> {
    @Override
    public void addLinks(EntityModel<WaiterDTO> resource) {
        resource.add(linkTo(methodOn(WaiterController.class).waiterInfo(resource.getContent().getWaiter().getPersonalId())).withSelfRel());
        resource.add(linkTo(methodOn(WaiterController.class).allWaitersInfo()).withRel("all menu items"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<WaiterDTO>> resources) {

    }
}
