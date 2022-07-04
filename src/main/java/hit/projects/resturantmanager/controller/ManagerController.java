package hit.projects.resturantmanager.controller;


import hit.projects.resturantmanager.pojo.Manager;
import hit.projects.resturantmanager.pojo.dto.ManagerDTO;
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

    /**
     * This method return all managers from DB
     * @return
     */
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Manager>>> getAllManagers() {
        return ResponseEntity.ok().body(managerService.getAllManagers());
    }

    /**
     * In his method we get manager details by his id.
     * @param personalId -> Expect to get personal id of manager.
     * @return status 200, or else 404 not found and manager details.
     */
    @GetMapping("/{personalId}")
    public ResponseEntity<EntityModel<Manager>> getManager(@PathVariable int personalId) {
        return ResponseEntity.ok().body(managerService.getManager(personalId));
    }

    /**
     * In this method we get managers status if they  on duty or not.
     * @param isOnDuty -> Expect to get true or false.
     * @return status 200 and manager is on duty or not.
     */
    @GetMapping("/duty/{isOnDuty}")
    public ResponseEntity<CollectionModel<EntityModel<Manager>>> getDutyStatus(@PathVariable boolean isOnDuty) {
        return ResponseEntity.ok(managerService.getDutyStatus(isOnDuty));
    }

    /**
     * In this method we update specific manager.
     * @param personalId -> Expect to get personal id of manager.
     * @param managerToUpdate -> Expect to GET json with details to update the manager.
     * @return status 200, item was updated and manager details.
     */
    @PutMapping("/{personalId}")
    public ResponseEntity<EntityModel<Manager>> updateManager(@PathVariable int personalId, @RequestBody Manager managerToUpdate) {
        return ResponseEntity.ok().body(managerService.updateManager(personalId, managerToUpdate));
    }

    /**
     * In this method we add new manager to our database.
     * @param managerToAdd -> Expect to GET json with details to update the manager.
     * @return status 201, item was created.
     */
    @PostMapping
    public ResponseEntity<EntityModel<Manager>> addNewManager(@RequestBody Manager managerToAdd) {
        return new ResponseEntity<>(managerService.addNewManager(managerToAdd), HttpStatus.CREATED);
    }

    /**
     * In this method we delete manager by id in our database.
     * @param personalId -> Expect to get personal id of manager.
     * @return status 202, item was deleted.
     */
    @DeleteMapping("/{personalId}")
    public ResponseEntity<EntityModel<Manager>> deleteManager(@PathVariable int personalId) {
        managerService.deleteManager(personalId);
        return ResponseEntity.status(202).build();
    }

    /**
     * In this method we get all manager's info with specific details (fullname,isOnDuty).
     * @return status 200, all managers DTO (fullname,is on duty?)
     */
    @GetMapping("/info")
    public ResponseEntity<CollectionModel<EntityModel<ManagerDTO>>> getAllManagerInfo(){
        return ResponseEntity.ok().body(managerService.getAllManagerInfo());
    }


    /**
     * In this method we get manager info with specific details (fullname,isOnDuty).
     * @param personalId -> Expect to get personal id of manager.
     * @return status 200, specific manager DTO (fullname,is on duty?) by his name.
     */
    @GetMapping("/{personalId}/info")
    public ResponseEntity<EntityModel<ManagerDTO>> getManagerInfo(@PathVariable int personalId){
        return ResponseEntity.ok().body(managerService.getManagerInfo(personalId));
    }
}
