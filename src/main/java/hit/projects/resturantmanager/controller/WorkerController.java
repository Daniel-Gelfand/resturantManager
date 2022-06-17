package hit.projects.resturantmanager.controller;

import hit.projects.resturantmanager.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/worker")
public class WorkerController {

    private WorkerService workerService;

    @Autowired
    public void setWorkerService(WorkerService workerService) {
        this.workerService = workerService;
    }
}
