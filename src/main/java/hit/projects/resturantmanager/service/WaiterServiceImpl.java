package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.assembler.WaiterAssembler;
import hit.projects.resturantmanager.controller.WaiterController;
import hit.projects.resturantmanager.entity.Waiter;
import hit.projects.resturantmanager.repository.WaiterRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@AllArgsConstructor
public class WaiterServiceImpl implements WaiterService {

    private final WaiterRepository waiterRepository;
    private final WaiterAssembler waiterAssembler;

    @Override
    public ResponseEntity<CollectionModel<EntityModel<Waiter>>> getAllWaiters() {
        List<Waiter> waiters = waiterRepository.findAll();
        List<EntityModel<Waiter>> waitersEntityModelList = waiters.stream().map(waiterAssembler::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(waitersEntityModelList, linkTo(methodOn(WaiterController.class).getAllWaiters()).withSelfRel()));
    }

    @Override
    public Waiter getWaiter(int personalId) {
        return null;
    }

    @Override
    public Waiter updateWaiter(String personalId, Waiter waiter) {
        return null;
    }

    @Override
    public Waiter addNewWaiter(Waiter waiterToAdd) {
        return null;
    }

    @Override
    public Waiter deleteWaiter(String personalId) {
        return null;
    }
}
