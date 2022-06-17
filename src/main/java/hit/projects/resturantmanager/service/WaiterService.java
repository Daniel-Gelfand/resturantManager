package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.entity.Waiter;

import java.util.List;

public interface WaiterService {
    List<Waiter> getAllWaiters();

    Waiter getWaiter(String personalId);

    Waiter updateWaiter(String personalId, Waiter waiter);

    Waiter addNewWaiter(Waiter waiterToAdd);

    Waiter deleteWaiter(String personalId);
}
