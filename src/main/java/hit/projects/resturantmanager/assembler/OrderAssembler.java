package hit.projects.resturantmanager.assembler;

import hit.projects.resturantmanager.controller.OrderController;
import hit.projects.resturantmanager.controller.TableController;
import hit.projects.resturantmanager.pojo.Order;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class OrderAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

    @Override
    public EntityModel<Order> toModel(Order entity) {
        return EntityModel.of(entity,linkTo(methodOn(OrderController.class)
                .getOrder((entity.getOrderNumber()))).withSelfRel(), linkTo(methodOn(OrderController.class).
                getAllOrders()).withRel("All orders"));
    }

    @Override
    public CollectionModel<EntityModel<Order>> toCollectionModel(Iterable<? extends Order> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);

    }
}
