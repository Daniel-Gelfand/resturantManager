package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.entity.Manager;
import hit.projects.resturantmanager.entity.Waiter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Override
    public List<Manager> getAllManagers() {
        return null;
    }

    @Override
    public Manager getManager(String personalId) {
        return null;
    }

    @Override
    public Manager updateManager(Manager waiter) {
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
