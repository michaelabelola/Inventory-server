package com.buyern.inventory.Services;

import com.buyern.inventory.Helpers.ListMapper;
import com.buyern.inventory.Model.InventoryItem;
import com.buyern.inventory.Model.InventoryPublicListingItem;
import com.buyern.inventory.Repositories.InventoryItemRepository;
import com.buyern.inventory.Repositories.Listings.PublicListingRepository;
import com.buyern.inventory.dtos.InventoryItemDto;
import com.buyern.inventory.dtos.ResponseDTO;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Data
public class PublicListingService {
    final PublicListingRepository publicListingRepository;
    final InventoryItemService inventoryItemService;
    InventoryItemRepository inventoryItemRepository;

    public ResponseEntity<ResponseDTO> addInventoryToPublicListing(String itemId, String entityId) {
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(publicListingRepository.save(InventoryItemDto.CREATE(inventoryItemService.getItemByUid(itemId, entityId)).toListingItem())).build());
    }

    public ResponseEntity<ResponseDTO> addInventoryItemsToPublicListing(String inventoryId, String entityId) {
        List<InventoryItem> inventoryItems = inventoryItemService.getItemsByUidAndInventoryId(inventoryId, entityId);
        List<InventoryPublicListingItem> listingItems = new ListMapper<InventoryItem, InventoryPublicListingItem>().map(inventoryItems, inventoryItem -> InventoryItemDto.CREATE(inventoryItem).toListingItem());
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(publicListingRepository.saveAll(listingItems)).build());
    }
}
