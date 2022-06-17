package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.entity.Manager;

import java.util.List;

public interface ManagerService {

    List<Manager> getAllManagers();

    Manager getManager(String personalId);

    Manager updateManager(Manager managerToUpdate);

    Manager addNewManager(Manager managerToAdd);

    Manager deleteManager(String personalId);
}
