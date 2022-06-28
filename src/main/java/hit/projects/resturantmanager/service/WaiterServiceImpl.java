package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.assembler.WaiterAssembler;
import hit.projects.resturantmanager.controller.WaiterController;
import hit.projects.resturantmanager.pojo.Waiter;
import hit.projects.resturantmanager.exception.WaiterException;
import hit.projects.resturantmanager.repository.WaiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class WaiterServiceImpl implements WaiterService {

    private WaiterRepository waiterRepository;

    private WaiterAssembler waiterAssembler;

    private final int NO_ONE_IN_DUTY = 0;

    @Autowired
    public WaiterServiceImpl(WaiterRepository waiterRepository, WaiterAssembler waiterAssembler) {
        this.waiterRepository = waiterRepository;
        this.waiterAssembler = waiterAssembler;
    }

    /**
     * This method Return all Waiters with links (to him self and to all waiters).
     * @return
     */
    @Override
    public CollectionModel<EntityModel<Waiter>> getAllWaiters() {
        List<Waiter> waiters = waiterRepository.findAll();
        List<EntityModel<Waiter>> waitersEntityModelList = waiters.stream().
                map(waiterAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(waitersEntityModelList,
                linkTo(methodOn(WaiterController.class).getAllWaiters()).withSelfRel());
    }

    /**
     * This method Return Waiter with self link.
     * @return
     */
    @Override
    public EntityModel<Waiter> getWaiter(int personalId) {
        Waiter waiter = waiterRepository.findByPersonalId(personalId).
                orElseThrow(() -> new IllegalArgumentException("NOT EXIST!"));
        // TODO: מי תופס את האקסיפשן הזה ?

        return waiterAssembler.toModel(waiter);
    }

    @Override
    public CollectionModel<EntityModel<Waiter>> getDutyStatus(boolean onDuty) {
        System.out.println("service");
        List<Waiter> waiters = waiterRepository.getAllByOnDuty(onDuty);
        List<EntityModel<Waiter>> waitersEntityModelList = waiters.stream().map(waiterAssembler::toModel).collect(Collectors.toList());

        if (waiters.size() == NO_ONE_IN_DUTY) {
           throw new WaiterException(onDuty);
        }

        return CollectionModel.of(waitersEntityModelList, linkTo(methodOn(WaiterController.class).getAllWaiters()).withSelfRel());
    }

    /**
     * This method update specific waiter.
     * If the waiter personalId doesn't exist we save new waiter in DB.
     * @param personalId
     * @param waiter
     * @return ResponseEntity<EntityModel<Waiter>>
     */
    @Override
    public EntityModel<Waiter> updateWaiter(int personalId, Waiter waiter) {
        return waiterRepository.findByPersonalId(personalId)
                .map(waiterToUpdate -> {
                    copyWaiterDetails(waiterToUpdate, waiter);
                    waiterRepository.save(waiterToUpdate);
                    return waiterAssembler.toModel(waiterToUpdate);
                })
                .orElseGet(()-> {
                    waiter.setPersonalId(personalId);
                    waiterRepository.save(waiter);
                    return waiterAssembler.toModel(waiter);
                });
    }

    /**
     * This method insert new waiter to the DB
     * @param waiterToAdd
     * @return
     */
    @Override
    public EntityModel<Waiter> addNewWaiter(Waiter waiterToAdd) {
        //TODO: we need to check validation of the body before we save in DB ???
        // אם אתה רוצה לחייב שדות מסוימים, זה המקום לעשות ולידציה ואז לזרוק אקסישן בהתאם לשדות שאתה רוצה לחייב לקבל
        waiterRepository.insert(waiterToAdd);

        return waiterAssembler.toModel(waiterToAdd);
    }

    /**
     * This method delete waiter from DB
     * @param personalId
     */
    @Override
    public void deleteWaiter(int personalId) {
        //TODO: להוסיף ולידציה ואז לזרוק אקסיפשן במקרה שהיוזר לא קיים במערכת
        if (waiterRepository.existsByPersonalId(personalId)) {
            waiterRepository.deleteByPersonalId(personalId);
        }else {
            throw new WaiterException(personalId);
        }
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
