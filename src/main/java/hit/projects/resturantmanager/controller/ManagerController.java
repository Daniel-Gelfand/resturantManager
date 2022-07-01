package hit.projects.resturantmanager.controller;


import hit.projects.resturantmanager.pojo.Manager;
import hit.projects.resturantmanager.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/manager")
@RestController
public class ManagerController {
    ManagerService managerService;

    @Autowired
    public void setManagerService(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Manager>>> getAllManagers() {
        return ResponseEntity.ok().body(managerService.getAllManagers());
    }

    @GetMapping("/{personalId}")
    public ResponseEntity<EntityModel<Manager>> getManager(@PathVariable int personalId) {
        return ResponseEntity.ok().body(managerService.getManager(personalId));
    }

    @GetMapping("/duty/{isOnDuty}")
    public ResponseEntity<CollectionModel<EntityModel<Manager>>> getDutyStatus(@PathVariable boolean isOnDuty) {
        return ResponseEntity.ok(managerService.getDutyStatus(isOnDuty));
    }

    @PutMapping("/{personalId}")
    public ResponseEntity<EntityModel<Manager>> updateManager(@PathVariable int personalId, @RequestBody Manager managerToUpdate) {
        return ResponseEntity.ok().body(managerService.updateManager(personalId, managerToUpdate));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Manager>> addNewManager(@RequestBody Manager managerToAdd) {
        return new ResponseEntity<>(managerService.addNewManager(managerToAdd), HttpStatus.CREATED);
    }

    @DeleteMapping("/{personalId}")
    public ResponseEntity<EntityModel<Manager>> deleteManager(@PathVariable int personalId) {
        managerService.deleteManager(personalId);
        return ResponseEntity.status(202).build();
    }
}
