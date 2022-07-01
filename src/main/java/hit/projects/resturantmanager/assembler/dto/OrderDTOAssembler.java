package hit.projects.resturantmanager.assembler.dto;

import hit.projects.resturantmanager.controller.OrderController;
import hit.projects.resturantmanager.pojo.dto.OrderDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderDTOAssembler implements SimpleRepresentationModelAssembler<OrderDTO> {

    @Override
    public void addLinks(EntityModel<OrderDTO> resource) {
        resource.add(linkTo(methodOn(OrderController.class).getOrderInfo(Objects.requireNonNull(resource.getContent()).getOrder().getOrderNumber())).withSelfRel());
        resource.add(linkTo(methodOn(OrderController.class).getAllOrdersInfo()).withRel("all orders"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<OrderDTO>> resources) {
        resources.add(linkTo(methodOn(OrderController.class).getAllOrdersInfo()).withSelfRel());
    }
}
