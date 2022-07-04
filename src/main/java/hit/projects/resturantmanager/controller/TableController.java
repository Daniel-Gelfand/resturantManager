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

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Table>>> getAllTables() {
        return ResponseEntity.ok().body(tableService.getAllTables());

    }

    @GetMapping("/{number}")
    public ResponseEntity<EntityModel<Table>> getTable(@PathVariable int number) {
        return ResponseEntity.ok().body(tableService.getTable(number));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<CollectionModel<EntityModel<Table>>> getTable(@PathVariable TableStatus status) {
        return ResponseEntity.ok().body(tableService.getTableByStatus(status));
    }

    @PutMapping("/{number}")
    public ResponseEntity<?> updateOrderList(@PathVariable int number, @RequestBody Order order) {
        tableService.addOrder(number, order);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> createNewTable(@RequestBody Table table) {
        return new ResponseEntity<>(tableService.createTable(table), HttpStatus.CREATED);
    }

    @DeleteMapping("/{number}")
    public ResponseEntity<?> deleteTable(@PathVariable int number) {
        tableService.deleteTable(number);
        return ResponseEntity.status(202).build();
    }

    @GetMapping("/{tableId}/info")
    public ResponseEntity<EntityModel<TableDTO>> getTableInfo(@PathVariable int tableId) {
        return ResponseEntity.ok().body(tableService.getTableInfo(tableId));
    }

    @GetMapping("/info")
    public ResponseEntity<CollectionModel<EntityModel<TableDTO>>> getAllTablesInfo(){
        return ResponseEntity.ok().body(tableService.getAllTablesInfo());
    }
}
