package hit.projects.resturantmanager.controller;

import hit.projects.resturantmanager.pojo.Waiter;
import hit.projects.resturantmanager.service.WaiterService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/waiter")
@AllArgsConstructor
public class WaiterController {

    private final WaiterService waiterService;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Waiter>>> getAllWaiters() {
        return waiterService.getAllWaiters();
    }

    @GetMapping("/{personalId}")
    public ResponseEntity<EntityModel<Waiter>> getWaiter(@PathVariable int personalId) {
        return waiterService.getWaiter(personalId);
    }

    @GetMapping("/duty/{isOnDuty}")
    public ResponseEntity<CollectionModel<EntityModel<Waiter>>> getDutyStatus(@PathVariable boolean isOnDuty) {
        System.out.println(isOnDuty);
        return waiterService.getDutyStatus(isOnDuty);
    }

    @PutMapping("/{personalId}")
    public ResponseEntity<EntityModel<Waiter>> updateWaiter(@PathVariable int personalId ,@RequestBody Waiter waiter) {
        return waiterService.updateWaiter(personalId, waiter);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Waiter>> addNewWaiter(@RequestBody Waiter waiterToAdd) {
        return waiterService.addNewWaiter(waiterToAdd);
    }

    @DeleteMapping("/{personalId}")
    public void deleteWaiter(@PathVariable int personalId) {
       waiterService.deleteWaiter(personalId);
    }
}
