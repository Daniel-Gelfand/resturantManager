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

    /**
     * This method Return all Waiters with links (to him self and to all waiters).
     * @return
     */
    @Override
    public ResponseEntity<CollectionModel<EntityModel<Waiter>>> getAllWaiters() {
        List<Waiter> waiters = waiterRepository.findAll();
        List<EntityModel<Waiter>> waitersEntityModelList = waiters.stream().map(waiterAssembler::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(waitersEntityModelList, linkTo(methodOn(WaiterController.class).getAllWaiters()).withSelfRel()));
    }

    /**
     * This method Return Waiter with self link.
     * @return
     */
    @Override
    public ResponseEntity<EntityModel<Waiter>> getWaiter(int personalId) {
        Waiter waiter = waiterRepository.findByPersonalId(personalId).orElseThrow(() -> new IllegalArgumentException("NOT EXIST!"));

        return ResponseEntity.ok().body(waiterAssembler.toModel(waiter));
    }

    /**
     * This method update specific waiter
     * If the waiter personalId doesn't
     *
     *
     * exist we save the new waiter in DB.
     * @param personalId
     * @param waiter
     * @return
     */
    @Override
    public ResponseEntity<EntityModel<Waiter>> updateWaiter(int personalId, Waiter waiter) {
        return waiterRepository.findByPersonalId(personalId)
                .map(waiterToUpdate -> {
                    waiterToUpdate.setFirstName(waiter.getFirstName());
                    waiterToUpdate.setLastName(waiter.getLastName());
                    waiterToUpdate.setSalary(waiter.getSalary());
                    waiterToUpdate.setTips(waiter.getTips());
                    waiterRepository.save(waiterToUpdate);
                    return ResponseEntity.ok().body(waiterAssembler.toModel(waiterToUpdate));
                })
                .orElseGet(()-> {
                    waiter.setPersonalId(personalId);
                    waiterRepository.save(waiter);
                    return ResponseEntity.ok().body(waiterAssembler.toModel(waiter));
                });
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
