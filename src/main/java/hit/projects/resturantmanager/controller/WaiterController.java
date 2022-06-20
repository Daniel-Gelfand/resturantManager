package hit.projects.resturantmanager.controller;

import hit.projects.resturantmanager.entity.Waiter;
import hit.projects.resturantmanager.service.WaiterService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @PutMapping("/{personalId}")
    public ResponseEntity<EntityModel<Waiter>> updateWaiter(@PathVariable int personalId ,@RequestBody Waiter waiter) {
        return waiterService.updateWaiter(personalId, waiter);
    }

    @PostMapping
    public Waiter addNewWaiter(@RequestBody Waiter waiterToAdd) {
        return waiterService.addNewWaiter(waiterToAdd);
    }

    @DeleteMapping("/{personalId}")
    public Waiter deleteWaiter(@PathVariable String personalId) {
        return waiterService.deleteWaiter(personalId);
    }

}
