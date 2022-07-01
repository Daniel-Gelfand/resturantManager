package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.assembler.ManagerAssembler;
import hit.projects.resturantmanager.controller.ManagerController;
import hit.projects.resturantmanager.exception.RestaurantConflictException;
import hit.projects.resturantmanager.exception.RestaurantNotFoundException;
import hit.projects.resturantmanager.pojo.Manager;
import hit.projects.resturantmanager.repository.ManagerRepository;
import hit.projects.resturantmanager.utils.Constant;
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

        return CollectionModel.of(managerList, linkTo(methodOn(ManagerController.class)
                .getAllManagers()).withSelfRel());
    }

    @Override
    public EntityModel<Manager> getManager(int personalId) {
        Manager manager = managerRepository
                .getManagerByPersonalId(personalId)
                .orElseThrow(()->
                        new RestaurantNotFoundException(
                                (String.format(Constant.NOT_FOUND_MESSAGE , "personal id", personalId))));

        return managerAssembler.toModel(manager);
    }

    @Override
    public EntityModel<Manager> updateManager(int personalId, Manager manager) {
        return managerRepository.findById(personalId)
                .map(managerToUpdate -> managerAssembler
                        .toModel(managerRepository
                                .save(managerToUpdate.update(manager))))
                .orElseGet(()-> managerAssembler.toModel(managerRepository.save(manager)));
    }

    @Override
    public EntityModel<Manager> addNewManager (Manager managerToAdd) {

        if (!managerRepository.existsById(managerToAdd.getPersonalId())) {
            return managerAssembler.toModel(managerRepository.save(managerToAdd));
        }
        throw new RestaurantConflictException(
                    (String.format(Constant.ALREADY_EXISTS_MESSAGE , "manager", managerToAdd.getFirstName())));
    }

    @Override
    public void deleteManager(int personalId) {
        if (!managerRepository.existsById(personalId))
        {
            throw new RestaurantNotFoundException(
                    (String.format(Constant.NOT_FOUND_MESSAGE , "personal id", personalId)));
        }
        managerRepository.deleteById(personalId);
    }

}



