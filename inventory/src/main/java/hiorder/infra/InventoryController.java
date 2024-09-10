package hiorder.infra;

import hiorder.domain.*;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping("/inventories")
public class InventoryController {
    @Autowired
    InventoryRepository inventoryRepository;

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
        Inventory savedInventory = inventoryRepository.save(inventory);
        return ResponseEntity.ok(savedInventory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @RequestBody Inventory inventory) {
        return inventoryRepository.findById(id)
                .map(existingInventory -> {
                    BeanUtils.copyProperties(inventory, existingInventory, "id");
                    Inventory updatedInventory = inventoryRepository.save(existingInventory);
                    return ResponseEntity.ok(updatedInventory);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInventory(@PathVariable Long id) {
        return inventoryRepository.findById(id)
                .map(inventory -> {
                    inventoryRepository.delete(inventory);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventory(@PathVariable Long id) {
        return inventoryRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventories() {
        List<Inventory> inventories = (List<Inventory>) inventoryRepository.findAll();
        return ResponseEntity.ok(inventories);
    }

    @PutMapping("/{id}/decrease")
    public ResponseEntity<?> decreaseStock(@PathVariable Long id, @RequestParam int quantity) {
        return inventoryRepository.findById(id)
                .map(inventory -> {
                    inventory.decreaseStock(quantity);
                    inventoryRepository.save(inventory);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
//>>> Clean Arch / Inbound Adaptor
