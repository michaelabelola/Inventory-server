package com.buyern.inventory.Controllers;

import com.azure.core.annotation.BodyParam;
import com.buyern.inventory.Enums.SimpleMediaType;
import com.buyern.inventory.Objects.Media;
import com.buyern.inventory.Services.AdminInventoryService;
import com.buyern.inventory.dtos.InventoryDto;
import com.buyern.inventory.dtos.InventoryFeatureDto;
import com.buyern.inventory.dtos.ResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("admin/{entityId}")
public class AdminInventoryController {
    final AdminInventoryService adminInventoryService;

    @PostMapping("inventory/feature")
    private ResponseEntity<ResponseDTO> addFeatureToInventory(@PathVariable("entityId") String entityId, @RequestParam String inventoryUid, @RequestBody InventoryFeatureDto feature) {
        return adminInventoryService.addFeatureToInventory(inventoryUid, entityId, feature);
    }
    @GetMapping("inventory/features")
    private ResponseEntity<ResponseDTO> getInventoryFeatures(@PathVariable("entityId") String entityId, @RequestParam String inventoryUid, @RequestBody InventoryFeatureDto feature) {
        return adminInventoryService.getInventoryFeatures(inventoryUid, entityId, feature);
    }

    @GetMapping("inventory")
    private ResponseEntity<ResponseDTO> getInventory(@PathVariable String entityId, @RequestParam String inventoryId) {
        return adminInventoryService.getInventoryMain(inventoryId, entityId);
    }

    @GetMapping("inventories")
    private ResponseEntity<ResponseDTO> getInventories(@PathVariable String entityId, int page, int perPage) {
        return adminInventoryService.getInventories(entityId, page, perPage);
    }

    @GetMapping("multiple")
    private ResponseEntity<ResponseDTO> getInventories(@PathVariable Long entityId, @RequestParam List<Long> inventoryIds) {
        return adminInventoryService.getInventoriesMain(inventoryIds, entityId);
    }

    @PostMapping("inventory")
    private ResponseEntity<ResponseDTO> create(@PathVariable("entityId") String entityId, @RequestBody InventoryDto inventory) {
        return adminInventoryService.createInventory(inventory, entityId);
    }

    @PostMapping(value = "inventory/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<ResponseDTO> uploadImage(@PathVariable String entityId,
                                                    @BodyParam("inventoryId") String inventoryId,
                                                     @BodyParam("image") MultipartFile image) {
        return adminInventoryService.createInventoryUploadImage(entityId, inventoryId, image);
    }

    /**
     * inventory creation step 2
     * upload media or images
     */
    @PostMapping("inventory/media")
    private ResponseEntity<ResponseDTO> uploadMedia(@PathVariable String entityId,
                                                    @BodyParam("inventoryId") String inventoryId,
                                                    @BodyParam("name") String name,
                                                    @BodyParam("tag") String tag,
                                                    @BodyParam("type") SimpleMediaType type,
                                                    @ModelAttribute MultipartFile media) throws JsonProcessingException {
        return adminInventoryService.createInventoryUploadMedia(entityId, inventoryId, media, Media.builder().name(name).tag(tag).type(type).build());
    }

    @PostMapping("multiple")
    private ResponseEntity<ResponseDTO> createInventories(@PathVariable Long entityId, @RequestBody List<InventoryDto> inventories) {
        return adminInventoryService.createInventories(inventories, entityId);
    }

    @DeleteMapping("")
    private ResponseEntity<ResponseDTO> deleteInventory(@PathVariable Long entityId, @RequestParam Long inventoryId) {
        return adminInventoryService.deleteInventory(inventoryId, entityId);
    }

    @DeleteMapping("multiple")
    private ResponseEntity<ResponseDTO> deleteInventories(@PathVariable Long entityId, @RequestBody List<Long> inventoryIds) {
        return adminInventoryService.deleteInventories(inventoryIds, entityId);
    }

    @PutMapping("archive")
    private ResponseEntity<ResponseDTO> archiveInventory(@PathVariable Long entityId, @RequestBody Long inventoryId) {
        return adminInventoryService.deleteInventory(inventoryId, inventoryId);
    }
}
