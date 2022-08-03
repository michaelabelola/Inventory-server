package com.buyern.inventory.Controllers;

import com.buyern.inventory.Services.PublicListingService;
import com.buyern.inventory.dtos.ResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("admin/{entityId}")
public class PublicListingController {
final PublicListingService publicListingService;
    @PostMapping("inventory/listing/addInventoryItem")
    private ResponseEntity<ResponseDTO> addInventoryToPublicListing(@PathVariable("entityId") String entityId, @RequestParam String itemId) {
        return publicListingService.addInventoryToPublicListing(itemId,entityId);
    }
    @PostMapping("inventory/listing/addInventory")
    private ResponseEntity<ResponseDTO> addInventoryItemsToPublicListing(@PathVariable("entityId") String entityId, @RequestParam String inventoryId) {
        return publicListingService.addInventoryItemsToPublicListing(inventoryId,entityId);
    }
}
