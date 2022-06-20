package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.pojo.Manager;
import hit.projects.resturantmanager.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public List<Manager> getAllManagers() {
        return null;
    }

    @Override
    public Manager getManager(String personalId) {
        return null;
    }

    @Override
    public Manager updateManager(String personalId, Manager waiter) {
        return null;
    }

    @Override
    public Manager addNewManager (Manager waiterToAdd) {
        return null;
    }

    @Override
    public Manager deleteManager(String personalId) {
        return null;
    }


}
