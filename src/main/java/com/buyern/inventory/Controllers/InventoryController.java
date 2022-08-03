package com.buyern.inventory.Controllers;

import com.buyern.inventory.Services.InventoryService;
import com.buyern.inventory.dtos.ResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class InventoryController {
final InventoryService inventoryService;
    @GetMapping("inventory/")
    private ResponseEntity<ResponseDTO> getInventory(@RequestParam Long inventoryId) {
        return inventoryService.getInventory(inventoryId);
    }
//    @GetMapping("inventory/withVariations")
//    private ResponseEntity<ResponseDTO> getInventoryWithVariations(@RequestParam Long entityId, @RequestParam Long inventoryId) {
//        return inventoryService.getInventoryWithVariations(inventoryId);
//    }
    @GetMapping("inventory/ownedByEntity")
    private ResponseEntity<ResponseDTO> getEntityInventory(@RequestParam String entityUid, @RequestParam String inventoryUid) {
        return inventoryService.getEntityInventory(entityUid, inventoryUid);
    }
    @GetMapping("inventories/ownedByEntity")
    private ResponseEntity<ResponseDTO> getEntityInventories(@RequestParam String entityUid) {
        return inventoryService.getEntityInventories(entityUid);
    }
}
