package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.pojo.Waiter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface WaiterService {
    CollectionModel<EntityModel<Waiter>> getAllWaiters();

    EntityModel<Waiter> getWaiter(int personalId);

    EntityModel<Waiter> updateWaiter(int personalId, Waiter waiter);

    EntityModel<Waiter> addNewWaiter(Waiter waiterToAdd);

    void deleteWaiter(int personalId);

    CollectionModel<EntityModel<Waiter>> getDutyStatus(boolean isOnDuty);
}
