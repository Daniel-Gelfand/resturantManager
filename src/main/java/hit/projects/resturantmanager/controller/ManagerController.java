package hit.projects.resturantmanager.controller;


import hit.projects.resturantmanager.pojo.Manager;
import hit.projects.resturantmanager.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/manager")
@RestController
public class ManagerController {

    ManagerService managerService;

    @Autowired
    public void setManagerService(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping
    public List<Manager> getAllManagers() {
        return managerService.getAllManagers();
    }

    @GetMapping("/{personalId}")
    public Manager getManager(@PathVariable String personalId) {
        return managerService.getManager(personalId);
    }

    @PutMapping("/{personalId}")
    public Manager updateManager(@PathVariable String personalId, @RequestBody Manager managerToUpdate) {
        return managerService.updateManager(personalId, managerToUpdate);
    }

    @PostMapping
    public Manager addNewManager(@RequestBody Manager managerToAdd) {
        return managerService.addNewManager(managerToAdd);
    }

    @DeleteMapping("/{personalId}")
    public Manager deleteManager(@PathVariable String personalId) {
        return managerService.deleteManager(personalId);
    }
}
