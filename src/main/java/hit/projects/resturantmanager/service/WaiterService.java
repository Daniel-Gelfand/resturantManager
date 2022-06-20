package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.pojo.Waiter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface WaiterService {
    ResponseEntity<CollectionModel<EntityModel<Waiter>>> getAllWaiters();

    ResponseEntity<EntityModel<Waiter>> getWaiter(int personalId);

    ResponseEntity<EntityModel<Waiter>> updateWaiter(int personalId, Waiter waiter);

    ResponseEntity<EntityModel<Waiter>> addNewWaiter(Waiter waiterToAdd);

    void deleteWaiter(int personalId);

    ResponseEntity<CollectionModel<EntityModel<Waiter>>> getDutyStatus(boolean isOnDuty);
}
