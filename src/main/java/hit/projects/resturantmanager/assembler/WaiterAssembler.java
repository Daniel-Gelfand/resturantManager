package hit.projects.resturantmanager.assembler;

import hit.projects.resturantmanager.controller.MenuItemController;
import hit.projects.resturantmanager.controller.WaiterController;
import hit.projects.resturantmanager.entity.MenuItem;
import hit.projects.resturantmanager.entity.Waiter;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.awt.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class WaiterAssembler implements RepresentationModelAssembler<Waiter, EntityModel<Waiter>> {

    @Override
    public EntityModel<Waiter> toModel(Waiter entity) {
        return EntityModel.of(entity, linkTo(methodOn(WaiterController.class).getWaiter(entity.getPersonalId())).withSelfRel(), linkTo(methodOn(WaiterController.class).getAllWaiters()).withRel("All Waiters"));
    }
}
