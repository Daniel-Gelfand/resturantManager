package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.entity.Waiter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WaiterService {
    ResponseEntity<CollectionModel<EntityModel<Waiter>>> getAllWaiters();

    Waiter getWaiter(int personalId);

    Waiter updateWaiter(String personalId, Waiter waiter);

    Waiter addNewWaiter(Waiter waiterToAdd);

    Waiter deleteWaiter(String personalId);
}
