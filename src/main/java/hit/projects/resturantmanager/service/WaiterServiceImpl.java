package hit.projects.resturantmanager.service;

import hit.projects.resturantmanager.assembler.WaiterAssembler;
import hit.projects.resturantmanager.assembler.dto.WaiterDTOAssembler;
import hit.projects.resturantmanager.controller.WaiterController;
import hit.projects.resturantmanager.exception.RestaurantConflictException;
import hit.projects.resturantmanager.exception.RestaurantNotFoundException;
import hit.projects.resturantmanager.pojo.Order;
import hit.projects.resturantmanager.pojo.Payment;
import hit.projects.resturantmanager.pojo.Table;
import hit.projects.resturantmanager.pojo.Waiter;
import hit.projects.resturantmanager.pojo.dto.WaiterDTO;
import hit.projects.resturantmanager.repository.WaiterRepository;
import hit.projects.resturantmanager.utils.Constant;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class WaiterServiceImpl implements WaiterService {
    @Autowired
    private WaiterRepository waiterRepository;
    @Autowired
    private WaiterAssembler waiterAssembler;
    @Autowired
    private WaiterDTOAssembler waiterDTOAssembler;
    @Autowired
    private OrderService orderService;
    @Autowired
    private TableService tableService;

//    public WaiterServiceImpl(WaiterRepository waiterRepository, WaiterAssembler waiterAssembler, WaiterDTOAssembler waiterDTOAssembler) {
//        this.waiterRepository = waiterRepository;
//        this.waiterAssembler = waiterAssembler;
//        this.waiterDTOAssembler = waiterDTOAssembler;
//    }

    /**
     * This method Return all Waiters with links (to him self and to all waiters).
     *
     * @return
     */
    @Override
    public CollectionModel<EntityModel<Waiter>> getAllWaiters() {
        List<Waiter> waiters = waiterRepository.findAll();
        List<EntityModel<Waiter>> waitersEntityModelList = waiters.stream().
                map(waiterAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(waitersEntityModelList,
                linkTo(methodOn(WaiterController.class).getAllWaiters()).withSelfRel());
    }

    /**
     * This method Return Waiter with self link.
     *
     * @return
     */
    @Override
    public EntityModel<Waiter> getWaiter(int personalId) {
        Waiter waiter = waiterRepository.findByPersonalId(personalId).
                orElseThrow(() -> new RestaurantNotFoundException(
                        (String.format(Constant.NOT_FOUND_MESSAGE, "personal id", personalId))));

        return waiterAssembler.toModel(waiter);
    }

    @Override
    public CollectionModel<EntityModel<Waiter>> getDutyStatus(boolean onDuty) {
        List<Waiter> waiters = waiterRepository.getAllByOnDuty(onDuty);
        List<EntityModel<Waiter>> waitersEntityModelList = waiters.stream().map(waiterAssembler::toModel).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(waiters)) {
            throw new RestaurantNotFoundException(
                    (String.format(Constant.NOT_FOUND_MESSAGE, "manager on duty", onDuty)));
        }

        return CollectionModel.of(waitersEntityModelList, linkTo(methodOn(WaiterController.class)
                .getAllWaiters()).withSelfRel());
    }

    /**
     * This method update specific waiter.
     * If the waiter personalId doesn't exist we save new waiter in DB.
     *
     * @param personalId
     * @param waiter
     * @return ResponseEntity<EntityModel < Waiter>>
     */
    @Override
    public EntityModel<Waiter> updateWaiter(int personalId, Waiter waiter) {
        return waiterRepository.findByPersonalId(personalId)
                .map(waiterToUpdate -> waiterAssembler.toModel(waiterRepository.save(waiterToUpdate.update(waiter))))
                .orElseGet(() -> {
                    waiter.setPersonalId(personalId);
                    return waiterAssembler.toModel(waiterRepository.save(waiter));
                });
    }

    /**
     * This method insert new waiter to the DB
     *
     * @param waiterToAdd
     * @return
     */
    @Override
    public EntityModel<Waiter> addNewWaiter(Waiter waiterToAdd) {
        //TODO: CHECK THIS SHIT IF THAT WORKING

        if (!waiterRepository.existsByPersonalId(waiterToAdd.getPersonalId())) {
            List<Table> tables = tableService.getAllTables().getContent().stream().toList().stream().map(EntityModel::getContent).toList();

            tableService.addWaiterToTables(waiterToAdd);
            waiterToAdd.setTableList(tables);
            return waiterAssembler.toModel(waiterRepository.save(waiterToAdd));
        }

        throw new RestaurantConflictException(
                (String.format(Constant.ALREADY_EXISTS_MESSAGE, "waiter", waiterToAdd.getFirstName())));

    }

    /**
     * This method delete waiter from DB
     *
     * @param personalId
     */
    @Override
    public void deleteWaiter(int personalId) {
        if (waiterRepository.existsByPersonalId(personalId)) {
            waiterRepository.deleteByPersonalId(personalId);
        }else {
            throw new RestaurantNotFoundException(
                    (String.format(Constant.NOT_FOUND_MESSAGE, "personal id", personalId)));
        }
    }

    @Override
    public CollectionModel<EntityModel<WaiterDTO>> getAllWaitersOnDutyInfo(boolean isOnDuty) {
        CollectionModel<EntityModel<WaiterDTO>> waiters = CollectionModel
                .of(waiterDTOAssembler
                        .toCollectionModel(StreamSupport
                                .stream(waiterRepository.getAllByOnDuty(isOnDuty).spliterator(), false)
                                .map(WaiterDTO::new)
                                .collect(Collectors.toList())));

        if (waiters.getContent().isEmpty()) {
            throw new RestaurantNotFoundException(String.format(Constant.NOT_FOUND_MESSAGE, "isOnDuty", isOnDuty));
        }

        return waiters;
    }

    @Override
    public EntityModel<WaiterDTO> getWaiterInfo(int personalId) {

        return waiterRepository.findByPersonalId(personalId).map(WaiterDTO::new).map(waiterDTOAssembler::toModel).orElseThrow(()-> new RestaurantNotFoundException(String.format(Constant.NOT_FOUND_MESSAGE, "personal id", personalId)));
    }

    @Override
    public CollectionModel<EntityModel<WaiterDTO>> getAllWaitersInfo() {
        CollectionModel<EntityModel<WaiterDTO>> waiters = CollectionModel
                .of(waiterDTOAssembler
                        .toCollectionModel(StreamSupport
                                .stream(waiterRepository.findAll().spliterator(), false)
                                .map(WaiterDTO::new)
                                .collect(Collectors.toList())));

        if (waiters.getContent().isEmpty()) {
            throw new RestaurantNotFoundException(Constant.NOT_FOUND_MESSAGE);
        }

        return waiters;
    }

    @Override
    public EntityModel<Order> payOrderBill(Payment payment) {
        return orderService.payOrderBill(payment.getOrderNumber(), payment.getPayment());
    }

    @Override
    public void addTableToWaiters(Table table) {
        List<Waiter> waiters = waiterRepository.findAll();

        for (Waiter waiter : waiters) {
            waiter.getTableList().add(table);
            waiterRepository.save(waiter);
        }
    }
}
