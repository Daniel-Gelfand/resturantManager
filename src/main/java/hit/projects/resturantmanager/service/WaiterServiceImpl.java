package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.assembler.WaiterAssembler;
import hit.projects.resturantmanager.controller.WaiterController;
import hit.projects.resturantmanager.entity.Waiter;
import hit.projects.resturantmanager.exception.WaiterException;
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
    private final int NO_ONE_IN_DUTY = 0;

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

    @Override
    public ResponseEntity<CollectionModel<EntityModel<Waiter>>> getDutyStatus(boolean onDuty) {
        System.out.println("service");
        List<Waiter> waiters = waiterRepository.getAllByOnDuty(onDuty);
        List<EntityModel<Waiter>> waitersEntityModelList = waiters.stream().map(waiterAssembler::toModel).collect(Collectors.toList());

        if (waiters.size() == NO_ONE_IN_DUTY) {
           throw new WaiterException(onDuty);
        }

        return ResponseEntity.ok(CollectionModel.of(waitersEntityModelList, linkTo(methodOn(WaiterController.class).getAllWaiters()).withSelfRel()));
    }

    /**
     * This method update specific waiter.
     * If the waiter personalId doesn't exist we save new waiter in DB.
     * @param personalId
     * @param waiter
     * @return ResponseEntity<EntityModel<Waiter>>
     */
    @Override
    public ResponseEntity<EntityModel<Waiter>> updateWaiter(int personalId, Waiter waiter) {
        return waiterRepository.findByPersonalId(personalId)
                .map(waiterToUpdate -> {
                    copyWaiterDetails(waiterToUpdate, waiter);
                    waiterRepository.save(waiterToUpdate);
                    return ResponseEntity.ok().body(waiterAssembler.toModel(waiterToUpdate));
                })
                .orElseGet(()-> {
                    waiter.setPersonalId(personalId);
                    waiterRepository.save(waiter);
                    return ResponseEntity.ok().body(waiterAssembler.toModel(waiter));
                });
    }

    /**
     * This method insert new waiter to the DB
     * @param waiterToAdd
     * @return
     */
    @Override
    public ResponseEntity<EntityModel<Waiter>> addNewWaiter(Waiter waiterToAdd) {
        //TODO: we need to check validation of the body before we save in DB ???
        waiterRepository.insert(waiterToAdd);

        return ResponseEntity.ok().body(waiterAssembler.toModel(waiterToAdd));
    }

    /**
     * This method delete waiter from DB
     * @param personalId
     */
    @Override
    public void deleteWaiter(int personalId) {
        waiterRepository.deleteByPersonalId(personalId);
    }

    /**
     * This method make a clone from waiter.
     * @param newWaiter
     * @param waiterToCopy
     */
    private void copyWaiterDetails(Waiter newWaiter, Waiter waiterToCopy) {
        newWaiter.setFirstName(waiterToCopy.getFirstName());
        newWaiter.setLastName(waiterToCopy.getLastName());
        newWaiter.setSalary(waiterToCopy.getSalary());
        newWaiter.setTips(waiterToCopy.getTips());
        newWaiter.setOnDuty(waiterToCopy.isOnDuty());
//        newWaiter.setOnDuty(waiterToCopy);
    }
}
