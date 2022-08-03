package com.buyern.inventory.Controllers;

import com.azure.core.annotation.BodyParam;
import com.azure.core.annotation.QueryParam;
import com.buyern.inventory.Enums.SimpleMediaType;
import com.buyern.inventory.Services.InventoryItemService;
import com.buyern.inventory.dtos.InventoryItemDto;
import com.buyern.inventory.dtos.ResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("admin/{entityId}")
public class AdminInventoryItemController {
    final InventoryItemService inventoryItemService;

    @PostMapping("inventory/item")
    private ResponseEntity<ResponseDTO> addFeatureToInventory(@PathVariable("entityId") String entityId, @RequestParam String inventoryId, @RequestParam(required = false) Boolean hasMedia, @RequestBody InventoryItemDto inventoryItemDto) {
        return inventoryItemService.addInventoryItemToInventory(entityId, inventoryId, inventoryItemDto, (hasMedia != null) ? hasMedia : false);
    }

    @PostMapping(value = "inventory/item/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<ResponseDTO> uploadImage(@PathVariable String entityId,
                                                    @BodyParam("itemId") String itemId,
                                                    @BodyParam("image") MultipartFile image) {
        return inventoryItemService.UploadItemImage(entityId, itemId, image);
    }

    /**
     * inventory creation step 2
     * upload media or images
     */
    @PostMapping("inventory/item/media")
    private ResponseEntity<ResponseDTO> uploadMedia(@PathVariable String entityId,
                                                    @BodyParam("itemId") String itemId,
                                                    @BodyParam("name") String name,
                                                    @BodyParam("tag") String tag,
                                                    @BodyParam("type") SimpleMediaType type,
                                                    @BodyParam("media") MultipartFile media) {
        return inventoryItemService.uploadMedia(entityId, itemId, media, name, tag, type);
    }

    @GetMapping("inventory/item")
    private ResponseEntity<ResponseDTO> getInventoryItem(@PathVariable("entityId") String entityId, @RequestParam String itemId) {
        return inventoryItemService.getInventoryItem(entityId, itemId);
    }

    @GetMapping("inventory/items")
    private ResponseEntity<ResponseDTO> getInventoryItemsPagable(@PathVariable("entityId") String entityId, @QueryParam("page") int page, @QueryParam("perPage") int perPage) {
        return inventoryItemService.getInventoryItems(entityId, page, perPage);
    }

    @PostMapping("inventory/item/addToListing")
    private ResponseEntity<ResponseDTO> addItemToListing(@PathVariable String entityId,
                                                    @QueryParam("itemId") String itemId,
                                                    @QueryParam("listing") String listing) {
        System.out.println(entityId);
        System.out.println(itemId);
        System.out.println(listing);
        return inventoryItemService.addItemToListing(entityId, itemId, listing);
    }
}
