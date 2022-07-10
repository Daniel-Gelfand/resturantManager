package hit.projects.resturantmanager.controller;

import hit.projects.resturantmanager.enums.TableStatus;
import hit.projects.resturantmanager.pojo.Order;
import hit.projects.resturantmanager.pojo.Table;
import hit.projects.resturantmanager.pojo.dto.TableDTO;
import hit.projects.resturantmanager.service.TableService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/table")
@RestController
public class TableController {
    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    /**
     * In this method we return all tables details.
     * @return all tables in DB.
     */
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Table>>> getAllTables() {
        return ResponseEntity.ok().body(tableService.getAllTables());

    }

    /**
     * In this method we return specific number by table number if number doesn't exist we throw new Restaurant not found exception.
     * @param number Expect to get valid table number.
     * @return specific number.
     */
    @GetMapping("/{number}")
    public ResponseEntity<EntityModel<Table>> getTable(@PathVariable int number) {
        return ResponseEntity.ok().body(tableService.getTable(number));
    }

    /**
     * In this method we return all table by table status.
     * @param status = Expect to get valid table status.
     * @return status ok and all tables by specific status.
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<CollectionModel<EntityModel<Table>>> getTable(@PathVariable TableStatus status) {
        return ResponseEntity.ok().body(tableService.getTableByStatus(status));
    }

    /**
     * In this method we update specific table by table number .
     * @param number Except to get  valid table number.
     * @param order Except to get valid order variable .
     * @return status ok if everything going well.
     */
    @PutMapping("/{number}")
    public ResponseEntity<?> updateOrderList(@PathVariable int number, @RequestBody Order order) {
        tableService.addOrder(number, order);
        return ResponseEntity.ok().build();
    }

    /**
     * In this method we create new table.
     * @param table Except to get valid table variable in the body request.
     * @return status ok if everything going well.
     */
    @PostMapping
    public ResponseEntity<?> createNewTable(@RequestBody Table table) {
        return new ResponseEntity<>(tableService.createTable(table), HttpStatus.CREATED);
    }

    /**
     * In this method we delete specific table by table number.
     * @param number Except to get valid table number.
     * @return status 202 deleted.
     */
    @DeleteMapping("/{number}")
    public ResponseEntity<?> deleteTable(@PathVariable int number) {
        tableService.deleteTable(number);
        return ResponseEntity.status(202).build();
    }

    /**
     * In this method we return DTO of table by table number.
     * @param tableNumber Except to get valid table number.
     * @return status ok(200).
     */
    @GetMapping("/{tableId}/info")
    public ResponseEntity<EntityModel<TableDTO>> getTableInfo(@PathVariable int tableNumber) {
        return ResponseEntity.ok().body(tableService.getTableInfo(tableNumber));
    }

    /**
     *  In this method we return all table in DTO version.
     * @return status 200.
     */
    @GetMapping("/info")
    public ResponseEntity<CollectionModel<EntityModel<TableDTO>>> getAllTablesInfo(){
        return ResponseEntity.ok().body(tableService.getAllTablesInfo());
    }
}
