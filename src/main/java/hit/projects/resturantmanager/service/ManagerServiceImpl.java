package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.assembler.ManagerAssembler;
import hit.projects.resturantmanager.controller.ManagerController;
import hit.projects.resturantmanager.exception.ManagerException;
import hit.projects.resturantmanager.pojo.Manager;
import hit.projects.resturantmanager.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ManagerServiceImpl implements ManagerService {


    private final ManagerRepository managerRepository;
    private final ManagerAssembler managerAssembler;

    @Autowired
    public ManagerServiceImpl(ManagerRepository managerRepository, ManagerAssembler managerAssembler) {
        this.managerRepository = managerRepository;
        this.managerAssembler = managerAssembler;
    }

    @Override
    public CollectionModel<EntityModel<Manager>> getAllManagers() {
        List<EntityModel<Manager>> managerList = managerRepository.findAll()
                .stream().map(managerAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(managerList,linkTo(methodOn(ManagerController.class)
                .getAllManagers()).withSelfRel());
    }

    @Override
    public EntityModel<Manager> getManager(int personalId) {
        Manager manager = managerRepository.getManagerByPersonalId(personalId).orElseThrow(()-> new ManagerException(personalId));

        return managerAssembler.toModel(manager);
    }

    @Override
    public EntityModel<Manager> updateManager(int personalId, Manager manager) {
        return managerRepository.findById(personalId)
                .map(managerToUpdate -> {
                    managerToUpdate.setFirstName(manager.getFirstName());
                    managerToUpdate.setLastName(manager.getLastName());
                    managerToUpdate.setOnDuty(manager.isOnDuty());
                    managerToUpdate.setPersonalId(manager.getPersonalId());
                    managerToUpdate.setSalary(manager.getSalary());
                    managerRepository.save(managerToUpdate);
                    return managerAssembler.toModel(managerToUpdate);
                })
                .orElseGet(()-> {
                    managerRepository.save(manager);
                    return managerAssembler.toModel(manager);
                });
    }

    @Override
    public EntityModel<Manager> addNewManager (Manager managerToAdd) {
        //TODO: Change to ManagerException
        if (!managerRepository.existsById(managerToAdd.getPersonalId())) {
            Manager savedNewManager = managerRepository.save(managerToAdd);
            return managerAssembler.toModel(managerToAdd);
        }else {
            throw new ManagerException(managerToAdd.getFirstName());
        }
    }

    @Override
    public void deleteManager(int personalId) {
        //TODO: Change to ManagerException
        boolean isExists = managerRepository.existsById(personalId);
        if (!isExists)
        {
            throw new ManagerException(personalId);
        }
        managerRepository.deleteById(personalId);
    }

}



