package hit.projects.resturantmanager.controller;

import hit.projects.resturantmanager.pojo.Waiter;
import hit.projects.resturantmanager.service.WaiterService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/waiter")
public class WaiterController {
    private final WaiterService waiterService;

    public WaiterController(WaiterService waiterService) {
        this.waiterService = waiterService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Waiter>>> getAllWaiters() {
        return ResponseEntity.ok(waiterService.getAllWaiters());
    }

    @GetMapping("/{personalId}")
    public ResponseEntity<EntityModel<Waiter>> getWaiter(@PathVariable int personalId) {
        return ResponseEntity.ok(waiterService.getWaiter(personalId));
    }

    @GetMapping("/duty/{isOnDuty}")
    public ResponseEntity<CollectionModel<EntityModel<Waiter>>> getDutyStatus(@PathVariable boolean isOnDuty) {
        System.out.println(isOnDuty);
        return ResponseEntity.ok(waiterService.getDutyStatus(isOnDuty));
    }

    @PutMapping("/{personalId}")
    public ResponseEntity<EntityModel<Waiter>> updateWaiter(@PathVariable int personalId, @RequestBody Waiter waiter) {
        return ResponseEntity.ok(waiterService.updateWaiter(personalId, waiter));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Waiter>> addNewWaiter(@RequestBody Waiter waiterToAdd) {
        return new ResponseEntity<>(waiterService.addNewWaiter(waiterToAdd), HttpStatus.CREATED);
    }

    @DeleteMapping("/{personalId}")
    public ResponseEntity<?> deleteWaiter(@PathVariable int personalId) {
        waiterService.deleteWaiter(personalId);
        return ResponseEntity.status(202).build();
    }
}
