package hit.projects.resturantmanager.assembler.dto;

import hit.projects.resturantmanager.controller.ManagerController;
import hit.projects.resturantmanager.pojo.dto.ManagerDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component

public class ManagerDTOAssembler implements SimpleRepresentationModelAssembler<ManagerDTO> {

    @Override
    public void addLinks(EntityModel<ManagerDTO> resource) {
        resource.add(linkTo(methodOn(ManagerController.class).getManagerInfo(resource.getContent().getManager().getPersonalId())).withSelfRel());
        resource.add(linkTo(methodOn(ManagerController.class).getAllManagerInfo()).withRel("all managers"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<ManagerDTO>> resources) {
        resources.add(linkTo(methodOn(ManagerController.class).getAllManagers()).withSelfRel());
    }
}
