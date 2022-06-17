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

    @GetMapping("/{personalId")
    public Waiter getWaiter(@PathVariable String personalId) {
        return waiterService.getWaiter(personalId);
    }

    @PutMapping("/update")
    public Waiter updateWaiter(@RequestBody Waiter waiter) {
        return waiterService.updateWaiter(waiter);
    }

    @PostMapping("/add")
    public Waiter addNewWaiter(@RequestBody Waiter waiterToAdd) {
        return waiterService.addNewWaiter(waiterToAdd);
    }


}
