package hit.projects.resturantmanager.controller;

import hit.projects.resturantmanager.entity.Waiter;
import hit.projects.resturantmanager.service.WaiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/waiter")
public class WaiterController {

    private WaiterService waiterService;

    @Autowired
    public void setWaiterController(WaiterService waiterService) {
        this.waiterService = waiterService;
    }

    @GetMapping
    public List<Waiter> getAllWaiters() {
        return waiterService.getAllWaiters();
    }

    @GetMapping("/{personalId}")
    public Waiter getWaiter(@PathVariable String personalId) {
        return waiterService.getWaiter(personalId);
    }

    @PutMapping("/{personalId}")
    public Waiter updateWaiter(@PathVariable String personalId ,@RequestBody Waiter waiter) {
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
